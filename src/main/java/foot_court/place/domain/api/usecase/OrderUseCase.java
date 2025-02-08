package foot_court.place.domain.api.usecase;

import foot_court.place.domain.api.IOrderServicePort;
import foot_court.place.domain.exception.ClientHasActiveOrderException;
import foot_court.place.domain.model.Order;
import foot_court.place.domain.model.OrderPlates;
import foot_court.place.domain.spi.*;
import foot_court.place.domain.utils.OrdersWithPlates;
import foot_court.place.domain.utils.PlaceUtils;
import foot_court.place.domain.utils.PurchaseHistory;
import foot_court.place.domain.utils.pagination.PageRequestUtil;
import foot_court.place.domain.utils.pagination.PagedResult;

import java.time.LocalDateTime;
import java.util.List;

import static foot_court.place.domain.utils.PlaceUtils.*;

public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;
    private final IUserPersistencePort userPersistencePort;
    private final IRestaurantsPersistencePort restaurantsPersistencePort;
    private final IMessagingFeignPersistencePort messagingFeignPersistencePort;
    private final ITraceabilityFeignPersistencePort traceabilityFeignPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IUserPersistencePort userPersistencePort, IRestaurantsPersistencePort restaurantsPersistencePort, IMessagingFeignPersistencePort messagingFeignPersistencePort, ITraceabilityFeignPersistencePort traceabilityFeignPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.restaurantsPersistencePort = restaurantsPersistencePort;
        this.messagingFeignPersistencePort = messagingFeignPersistencePort;
        this.traceabilityFeignPersistencePort = traceabilityFeignPersistencePort;
    }

    @Override
    public void createOrder(Long restaurantId, List<OrderPlates> orderPlates) {
        Long clientId = userPersistencePort.getUserId();
        if (verifyHasActiveOrder(clientId)) {
            throw new ClientHasActiveOrderException(CLIENT_HAS_ACTIVE_ORDER);
        }
        Order order = createOrder(clientId, restaurantId);
        order = orderPersistencePort.createOrder(order, orderPlates);
        createOrderTraceability(order);
    }

    @Override
    public PagedResult<OrdersWithPlates> viewOrders(String status, int page, int size) {
        PageRequestUtil pageRequestDomain = new PageRequestUtil(page, size);
        Long restaurantId = restaurantsPersistencePort.getRestaurantOfEmployee(userPersistencePort.getUserId());
        return orderPersistencePort.viewOrders(status, restaurantId, pageRequestDomain);
    }

    @Override
    public void updateOrderStatus(Long orderId) {
        Long chefId = userPersistencePort.getUserId();
        Order order = orderPersistencePort.getOrderById(orderId);
        validateOrderStatus(order);
        validateChefAccessToOrder(order, chefId);
        assignOrderToChef(order, chefId);
        orderPersistencePort.updateOrderStatus(order);
    }

    @Override
    public void orderReady(Long orderId) {
        Long chefId = userPersistencePort.getUserId();
        Order order = orderPersistencePort.getOrderById(orderId);
        validateOrderChef(order, chefId);
        validateOrderStatusForUpdate(order);
        updateOrderToReady(order);
        sendNotificationToClient(order);
    }

    @Override
    public void orderDelivered(Long orderId, String pin) {
        Long chefId = userPersistencePort.getUserId();
        Order order = orderPersistencePort.getOrderById(orderId);
        String phoneNumberOfClient = userPersistencePort.getPhoneNumber(order.getClientId());
        validateOrderChef(order, chefId);
        if (!messagingFeignPersistencePort.getPinByPhoneNumber(phoneNumberOfClient).equals(pin)) {
            throw new IllegalArgumentException(PIN_ERROR);
        }
        order.setStatus(ORDER_DELIVERED);
        orderPersistencePort.updateOrderStatus(order);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Long clientId = userPersistencePort.getUserId();
        Order order = orderPersistencePort.getOrderById(orderId);

        validateOrderOwnership(order, clientId);
        validateOrderStatusForCancellation(order);

        order.setStatus(PlaceUtils.ORDER_CANCELLED);
        orderPersistencePort.updateOrderStatus(order);
    }

    private void createOrderTraceability(Order order) {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        Long clientId = userPersistencePort.getUserId();
        purchaseHistory.setOrderId(order.getId().toString());
        purchaseHistory.setClientId(clientId.toString());
        String clientEmail = userPersistencePort.getEmail(clientId);
        purchaseHistory.setClientEmail(clientEmail);
        purchaseHistory.setStatusDate(LocalDateTime.now());
        purchaseHistory.setLastStatus("");
        purchaseHistory.setNewStatus(ORDER_PENDING);
        purchaseHistory.setEmployeeId("");
        purchaseHistory.setEmployeeEmail("");
        traceabilityFeignPersistencePort.generateReport(purchaseHistory);
    }

    private boolean verifyHasActiveOrder(Long clientId) {
        return orderPersistencePort.hasActiveOrder(clientId);
    }

    private Order createOrder(Long clientId, Long restaurantId) {
        Order order = new Order();
        order.setClientId(clientId);
        order.setChefId(0L);
        order.setRestaurantId(restaurantId);
        order.setDateOrder(LocalDateTime.now());
        order.setStatus(ORDER_PENDING);
        return order;
    }

    private void validateOrderStatus(Order order) {
        if (!orderPersistencePort.verifyOrderStatus(order.getId())) {
            throw new IllegalArgumentException(ORDER_STATUS_ERROR);
        }
    }

    private void validateChefAccessToOrder(Order order, Long chefId) {
        Long chefRestaurantId = restaurantsPersistencePort.getRestaurantOfEmployee(chefId);
        if (!order.getRestaurantId().equals(chefRestaurantId)) {
            throw new IllegalArgumentException(NOT_RESTAURANT_CHEF);
        }
    }

    private void assignOrderToChef(Order order, Long chefId) {
        order.setChefId(chefId);
        order.setStatus(ORDER_PREPARATION);
    }

    private void validateOrderChef(Order order, Long chefId) {
        if (!order.getChefId().equals(chefId)) {
            throw new IllegalArgumentException(NOT_CHEF_ORDER);
        }
    }

    private void validateOrderStatusForUpdate(Order order) {
        if (!ORDER_PREPARATION.equals(order.getStatus())) {
            throw new IllegalArgumentException(ORDER_STATUS_ERROR);
        }
    }

    private void updateOrderToReady(Order order) {
        order.setStatus(ORDER_READY);
        orderPersistencePort.updateOrderStatus(order);
    }

    private void sendNotificationToClient(Order order) {
        String phoneNumberOfClient = userPersistencePort.getPhoneNumber(order.getClientId());
        messagingFeignPersistencePort.sendMessage(phoneNumberOfClient);
    }

    private void validateOrderOwnership(Order order, Long clientId) {
        if (!order.getClientId().equals(clientId)) {
            throw new IllegalArgumentException(NOT_OWNER_OF_ORDER);
        }
    }

    private void validateOrderStatusForCancellation(Order order) {
        if (!ORDER_PENDING.equals(order.getStatus())) {
            throw new IllegalArgumentException(ORDER_ALREADY_IN_PREPARATION);
        }
    }
}
