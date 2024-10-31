package foot_court.place.domain.spi;

import foot_court.place.domain.model.Order;
import foot_court.place.domain.model.OrderPlates;

import java.util.List;

public interface IOrderPersistencePort {
    boolean hasActiveOrder(Long clientId);
    void createOrder(Order order, List<OrderPlates> orderPlates);
}
