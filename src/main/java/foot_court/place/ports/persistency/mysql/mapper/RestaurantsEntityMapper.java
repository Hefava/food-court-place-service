package foot_court.place.ports.persistency.mysql.mapper;

import foot_court.place.domain.model.Restaurant;
import foot_court.place.ports.persistency.mysql.entity.RestaurantsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantsEntityMapper {
    RestaurantsEntity toEntity(Restaurant restaurant);
    Restaurant toDomain(RestaurantsEntity restaurantsEntity);
}
