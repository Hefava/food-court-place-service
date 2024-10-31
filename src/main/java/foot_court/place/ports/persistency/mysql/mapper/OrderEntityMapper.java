package foot_court.place.ports.persistency.mysql.mapper;

import foot_court.place.domain.model.Order;
import foot_court.place.ports.persistency.mysql.entity.OrderEntity;
import foot_court.place.ports.persistency.mysql.entity.RestaurantsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper {

    @Mapping(target = "restaurantId", source = "restaurantId", qualifiedByName = "longToRestaurantsEntity")
    OrderEntity toEntity(Order order);

    @Mapping(target = "restaurantId", source = "restaurantId", qualifiedByName = "restaurantsEntityToLong")
    Order toModel(OrderEntity orderEntity);

    @Named("longToRestaurantsEntity")
    default RestaurantsEntity longToRestaurantsEntity(Long restaurantId) {
        if (restaurantId == null) {
            return null;
        }
        RestaurantsEntity restaurantEntity = new RestaurantsEntity();
        restaurantEntity.setId(restaurantId);
        return restaurantEntity;
    }

    @Named("restaurantsEntityToLong")
    default Long restaurantsEntityToLong(RestaurantsEntity restaurantEntity) {
        return restaurantEntity != null ? restaurantEntity.getId() : null;
    }
}