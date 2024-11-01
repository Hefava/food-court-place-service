package foot_court.place.domain.utils;

import foot_court.place.domain.model.Order;
import foot_court.place.domain.model.OrderPlates;

import java.util.List;

public class OrdersWithPlates {
    private Order order;
    private List<OrderPlates> orderPlates;

    public OrdersWithPlates(Order order, List<OrderPlates> orderPlates) {
        this.order = order;
        this.orderPlates = orderPlates;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderPlates> getOrderPlates() {
        return orderPlates;
    }

    public void setOrderPlates(List<OrderPlates> orderPlates) {
        this.orderPlates = orderPlates;
    }
}
