package foot_court.place.ports.application.http.dto;

import foot_court.place.domain.model.Order;
import foot_court.place.domain.model.OrderPlates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrdersResponse {
    private Order order;
    private List<OrderPlates> orderPlates;
}
