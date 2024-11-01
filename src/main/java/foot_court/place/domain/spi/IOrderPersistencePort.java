package foot_court.place.domain.spi;

import foot_court.place.domain.model.Order;
import foot_court.place.domain.model.OrderPlates;
import foot_court.place.domain.utils.OrdersWithPlates;
import foot_court.place.domain.utils.pagination.PageRequestUtil;
import foot_court.place.domain.utils.pagination.PagedResult;

import java.util.List;

public interface IOrderPersistencePort {
    boolean hasActiveOrder(Long clientId);
    void createOrder(Order order, List<OrderPlates> orderPlates);
    PagedResult<OrdersWithPlates> viewOrders(String status, Long restaurantId,PageRequestUtil pageRequestDomain);
}
