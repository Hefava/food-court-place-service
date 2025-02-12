package foot_court.place;

import foot_court.place.domain.api.usecase.OrderUseCase;
import foot_court.place.domain.exception.ClientHasActiveOrderException;
import foot_court.place.domain.model.Order;
import foot_court.place.domain.model.OrderPlates;
import foot_court.place.domain.spi.*;
import foot_court.place.domain.utils.OrdersWithPlates;
import foot_court.place.domain.utils.PurchaseHistory;
import foot_court.place.domain.utils.pagination.PagedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.List;

import static foot_court.place.domain.utils.PlaceUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderUseCaseTest {

    private IOrderPersistencePort orderPersistencePort;
    private IUserPersistencePort userPersistencePort;
    private IRestaurantsPersistencePort restaurantsPersistencePort;
    private IMessagingFeignPersistencePort messagingFeignPersistencePort;
    private ITraceabilityFeignPersistencePort traceabilityFeignPersistencePort;
    private OrderUseCase orderUseCase;

    @BeforeEach
    void setUp() {
        orderPersistencePort = mock(IOrderPersistencePort.class);
        userPersistencePort = mock(IUserPersistencePort.class);
        restaurantsPersistencePort = mock(IRestaurantsPersistencePort.class);
        messagingFeignPersistencePort = mock(IMessagingFeignPersistencePort.class);
        traceabilityFeignPersistencePort = mock(ITraceabilityFeignPersistencePort.class);

        orderUseCase = new OrderUseCase(orderPersistencePort, userPersistencePort, restaurantsPersistencePort,
                messagingFeignPersistencePort, traceabilityFeignPersistencePort);
    }

    @Test
    void createOrder_ShouldCreateOrder_WhenNoActiveOrder() {
        // Given
        Long restaurantId = 1L;
        Long clientId = 10L;
        List<OrderPlates> orderPlates = List.of(new OrderPlates());

        Order mockOrder = new Order();
        mockOrder.setId(100L);
        mockOrder.setClientId(clientId);
        mockOrder.setRestaurantId(restaurantId);
        mockOrder.setStatus(ORDER_PENDING);

        when(userPersistencePort.getUserId()).thenReturn(clientId);
        when(orderPersistencePort.hasActiveOrder(clientId)).thenReturn(false);
        when(orderPersistencePort.createOrder(any(Order.class), eq(orderPlates)))
                .thenReturn(mockOrder);
        when(userPersistencePort.getEmail(clientId)).thenReturn("client@example.com");

        // When
        orderUseCase.createOrder(restaurantId, orderPlates);

        // Then
        verify(orderPersistencePort).createOrder(any(Order.class), eq(orderPlates));

        // Verificar que la trazabilidad se genera correctamente
        ArgumentCaptor<PurchaseHistory> traceabilityCaptor = ArgumentCaptor.forClass(PurchaseHistory.class);
        verify(traceabilityFeignPersistencePort).generateReport(traceabilityCaptor.capture());

        PurchaseHistory capturedTraceability = traceabilityCaptor.getValue();
        assertEquals(mockOrder.getId().toString(), capturedTraceability.getOrderId());
        assertEquals(clientId.toString(), capturedTraceability.getClientId());
        assertEquals("client@example.com", capturedTraceability.getClientEmail());
        assertEquals("", capturedTraceability.getLastStatus());
        assertEquals(ORDER_PENDING, capturedTraceability.getNewStatus());
    }

    @Test
    void createOrder_ShouldThrowException_WhenClientHasActiveOrder() {
        // Given
        Long clientId = 10L;
        when(userPersistencePort.getUserId()).thenReturn(clientId);
        when(orderPersistencePort.hasActiveOrder(clientId)).thenReturn(true);

        // When
        Executable action = () -> orderUseCase.createOrder(1L, List.of(new OrderPlates()));

        // Then
        assertThrows(ClientHasActiveOrderException.class, action);
        verify(orderPersistencePort, never()).createOrder(any(), any());
    }

    @Test
    void updateOrderStatus_ShouldUpdate_WhenOrderExistsAndChefHasAccess() {
        // Given
        Long orderId = 1L;
        Long chefId = 20L;
        Long clientId = 100L;
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order();
        order.setId(orderId);
        order.setClientId(clientId);
        order.setDateOrder(now);
        order.setRestaurantId(5L);
        order.setStatus(ORDER_PENDING);

        when(userPersistencePort.getUserId()).thenReturn(chefId);
        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);
        when(orderPersistencePort.verifyOrderStatus(orderId)).thenReturn(true);
        when(restaurantsPersistencePort.getRestaurantOfEmployee(chefId)).thenReturn(5L);

        // When
        orderUseCase.updateOrderStatus(orderId);

        // Then
        verify(orderPersistencePort).updateOrderStatus(order);
        verify(traceabilityFeignPersistencePort).generateReport(any());
    }

    @Test
    void updateOrderStatus_ShouldThrowException_WhenChefHasNoAccess() {
        // Given
        Long orderId = 1L;
        Long chefId = 20L;
        Order order = new Order();
        order.setId(orderId);
        order.setRestaurantId(5L);
        order.setStatus(ORDER_PENDING);

        when(userPersistencePort.getUserId()).thenReturn(chefId);
        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);
        when(orderPersistencePort.verifyOrderStatus(orderId)).thenReturn(true);
        when(restaurantsPersistencePort.getRestaurantOfEmployee(chefId)).thenReturn(99L); // Otro restaurante

        // When
        Executable action = () -> orderUseCase.updateOrderStatus(orderId);

        // Then
        assertThrows(IllegalArgumentException.class, action);
        verify(orderPersistencePort, never()).updateOrderStatus(any());
    }

    @Test
    void orderDelivered_ShouldUpdateStatus_WhenPinIsCorrect() {
        // Given
        Long orderId = 1L;
        Long chefId = 20L;
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order();
        order.setId(orderId);
        order.setClientId(100L);
        order.setDateOrder(now);
        order.setChefId(chefId);
        order.setStatus(ORDER_READY);

        String correctPin = "1234";
        when(userPersistencePort.getUserId()).thenReturn(chefId);
        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);
        when(messagingFeignPersistencePort.getPinByPhoneNumber(any())).thenReturn(correctPin);

        // When
        orderUseCase.orderDelivered(orderId, correctPin);

        // Then
        verify(orderPersistencePort).updateOrderStatus(order);
        verify(traceabilityFeignPersistencePort).generateReport(any());
    }

    @Test
    void orderDelivered_ShouldThrowException_WhenPinIsIncorrect() {
        // Given
        Long orderId = 1L;
        Long chefId = 20L;
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order();
        order.setId(orderId);
        order.setClientId(100L);
        order.setDateOrder(now);
        order.setChefId(chefId);
        order.setStatus(ORDER_READY);

        String correctPin = "1234";
        String wrongPin = "9999";

        when(userPersistencePort.getUserId()).thenReturn(chefId);
        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);
        when(messagingFeignPersistencePort.getPinByPhoneNumber(any())).thenReturn(correctPin);

        // When
        Executable action = () -> orderUseCase.orderDelivered(orderId, wrongPin);

        // Then
        assertThrows(IllegalArgumentException.class, action);
        verify(orderPersistencePort, never()).updateOrderStatus(any());
    }

    @Test
    void cancelOrder_ShouldCancel_WhenClientOwnsOrderAndStatusIsPending() {
        // Given
        Long orderId = 1L;
        Long clientId = 100L;
        Order order = new Order();
        order.setId(orderId);
        order.setClientId(clientId);
        order.setStatus(ORDER_PENDING);

        when(userPersistencePort.getUserId()).thenReturn(clientId);
        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);

        // When
        orderUseCase.cancelOrder(orderId);

        // Then
        assertEquals(ORDER_CANCELLED, order.getStatus());
        verify(orderPersistencePort).updateOrderStatus(order);
        verify(traceabilityFeignPersistencePort).generateReport(any());
    }

    @Test
    void cancelOrder_ShouldThrowException_WhenStatusIsNotPending() {
        // Given
        Long orderId = 1L;
        Long clientId = 100L;
        Order order = new Order();
        order.setId(orderId);
        order.setClientId(clientId);
        order.setStatus(ORDER_PREPARATION); // Estado inválido

        when(userPersistencePort.getUserId()).thenReturn(clientId);
        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);

        // When
        Executable action = () -> orderUseCase.cancelOrder(orderId);

        // Then
        assertThrows(IllegalArgumentException.class, action);
        verify(orderPersistencePort, never()).updateOrderStatus(any());
    }

    @Test
    void viewOrders_ShouldReturnPagedResult_WhenCalled() {
        // Given
        String status = "PENDING";
        int page = 0;
        int size = 10;
        Long restaurantId = 5L;

        List<OrdersWithPlates> content = List.of(); // Lista vacía o con datos de prueba
        int totalPages = 1;
        long totalCount = 0;

        PagedResult<OrdersWithPlates> expectedPagedResult = new PagedResult<>(content, page, size, totalPages, totalCount);

        when(userPersistencePort.getUserId()).thenReturn(20L);
        when(restaurantsPersistencePort.getRestaurantOfEmployee(20L)).thenReturn(restaurantId);
        when(orderPersistencePort.viewOrders(eq(status), eq(restaurantId), any())).thenReturn(expectedPagedResult);

        // When
        PagedResult<OrdersWithPlates> result = orderUseCase.viewOrders(status, page, size);

        // Then
        assertEquals(expectedPagedResult, result);
        verify(orderPersistencePort).viewOrders(eq(status), eq(restaurantId), any());
    }


    @Test
    void orderReady_ShouldUpdateStatusAndSendNotification_WhenChefIsValid() {
        // Given
        Long orderId = 1L;
        Long chefId = 20L;
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order();
        order.setId(orderId);
        order.setClientId(100L);
        order.setDateOrder(now);
        order.setChefId(chefId);
        order.setStatus(ORDER_PREPARATION);

        when(userPersistencePort.getUserId()).thenReturn(chefId);
        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);
        when(userPersistencePort.getPhoneNumber(order.getClientId())).thenReturn("1234567890");

        // When
        orderUseCase.orderReady(orderId);

        // Then
        assertEquals(ORDER_READY, order.getStatus());
        verify(orderPersistencePort).updateOrderStatus(order);
        verify(messagingFeignPersistencePort).sendMessage(anyString());
        verify(traceabilityFeignPersistencePort).generateReport(any());
    }

    @Test
    void orderReady_ShouldThrowException_WhenChefIsNotAssigned() {
        // Given
        Long orderId = 1L;
        Long chefId = 20L;
        Order order = new Order();
        order.setId(orderId);
        order.setClientId(100L);
        order.setChefId(99L); // Otro chef
        order.setStatus(ORDER_PREPARATION);

        when(userPersistencePort.getUserId()).thenReturn(chefId);
        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);

        // When
        Executable action = () -> orderUseCase.orderReady(orderId);

        // Then
        assertThrows(IllegalArgumentException.class, action);
        verify(orderPersistencePort, never()).updateOrderStatus(any());
    }

}