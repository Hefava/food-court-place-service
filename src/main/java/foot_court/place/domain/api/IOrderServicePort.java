package foot_court.place.domain.api;

import foot_court.place.domain.model.OrderPlates;

import java.util.List;

public interface IOrderServicePort {
    void createOrder(Long restaurantId, List<OrderPlates> orderPlates);
}
