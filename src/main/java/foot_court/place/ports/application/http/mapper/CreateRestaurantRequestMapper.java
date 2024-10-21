package foot_court.place.ports.application.http.mapper;

import foot_court.place.domain.model.Restaurant;
import foot_court.place.ports.application.http.dto.CreateRestaurantRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateRestaurantRequestMapper {
    Restaurant toDomain(CreateRestaurantRequest request);

    CreateRestaurantRequest toDto(Restaurant restaurant);
}
