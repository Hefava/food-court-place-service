package foot_court.place.ports.application.http.mapper;

import foot_court.place.domain.model.Restaurant;
import foot_court.place.ports.application.http.dto.RestaurantsResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantsResponseMapper {

    RestaurantsResponse toResponse(Restaurant restaurant);

    List<RestaurantsResponse> toResponseList(List<Restaurant> restaurantList);
}
