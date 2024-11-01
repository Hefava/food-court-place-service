package foot_court.place.domain.api;

import foot_court.place.domain.model.OrderPlates;
import foot_court.place.domain.utils.OrdersWithPlates;
import foot_court.place.domain.utils.pagination.PagedResult;

import java.util.List;

public interface IOrderServicePort {
    void createOrder(Long restaurantId, List<OrderPlates> orderPlates);
    PagedResult<OrdersWithPlates> viewOrders(String status, int page, int size);
    void updateOrderStatus(Long orderId);
}
