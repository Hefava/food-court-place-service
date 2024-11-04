package foot_court.place.ports.application.http.mapper;

import foot_court.place.domain.utils.OrdersWithPlates;
import foot_court.place.ports.application.http.dto.ViewOrdersResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ViewOrdersResponseMapper {
    List<ViewOrdersResponse> toResponseList(List<OrdersWithPlates> ordersList);
}
