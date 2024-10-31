package foot_court.place.domain.api.usecase;

import foot_court.place.domain.api.IOrderServicePort;
import foot_court.place.domain.exception.ClientHasActiveOrderException;
import foot_court.place.domain.model.Order;
import foot_court.place.domain.model.OrderPlates;
import foot_court.place.domain.spi.IOrderPersistencePort;
import foot_court.place.domain.spi.IUserPersistencePort;

import java.time.LocalDateTime;
import java.util.List;

import static foot_court.place.domain.utils.PlaceUtils.CLIENT_HAS_ACTIVE_ORDER;
import static foot_court.place.domain.utils.PlaceUtils.ORDER_PENDING;

public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;
    private final IUserPersistencePort userPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IUserPersistencePort userPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.userPersistencePort = userPersistencePort;
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
}
