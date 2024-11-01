package foot_court.place.domain.api.usecase;

import foot_court.place.domain.api.IOrderServicePort;
import foot_court.place.domain.exception.ClientHasActiveOrderException;
import foot_court.place.domain.model.Order;
import foot_court.place.domain.model.OrderPlates;
import foot_court.place.domain.spi.IOrderPersistencePort;
import foot_court.place.domain.spi.IRestaurantsPersistencePort;
import foot_court.place.domain.spi.IUserPersistencePort;
import foot_court.place.domain.utils.OrdersWithPlates;
import foot_court.place.domain.utils.pagination.PageRequestUtil;
import foot_court.place.domain.utils.pagination.PagedResult;

import java.time.LocalDateTime;
import java.util.List;

import static foot_court.place.domain.utils.PlaceUtils.*;

public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;
    private final IUserPersistencePort userPersistencePort;
    private final IRestaurantsPersistencePort restaurantsPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IUserPersistencePort userPersistencePort, IRestaurantsPersistencePort restaurantsPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.restaurantsPersistencePort = restaurantsPersistencePort;
    }

    @Override
    public void createOrder(Long restaurantId, List<OrderPlates> orderPlates) {
        Long clientId = userPersistencePort.getUserId();
        if (verifyHasActiveOrder(clientId)) {
            throw new ClientHasActiveOrderException(CLIENT_HAS_ACTIVE_ORDER);
        }
        Order order = createOrder(clientId, restaurantId);
        orderPersistencePort.createOrder(order, orderPlates);
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
        if (orderPersistencePort.verifyOrderStatus(order.getId())) {
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
}
