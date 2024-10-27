package foot_court.place.ports.persistency.mysql.mapper;

import foot_court.place.domain.model.Category;
import foot_court.place.domain.model.Plate;
import foot_court.place.domain.model.Restaurant;
import foot_court.place.ports.persistency.mysql.entity.PlateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryEntityMapper.class, RestaurantsEntityMapper.class})
public interface PlateEntityMapper {

    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "restaurantId", target = "restaurantId")
    PlateEntity toEntity(Plate plate);

    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "restaurantId", target = "restaurantId")
    Plate toPlate(PlateEntity plateEntity);

    // Métodos personalizados para mapear Long a objetos y viceversa
    default Category map(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return new Category(categoryId.toString(), null, null);
    }

    default Long map(Category category) {
        if (category == null) {
            return null;
        }
        return Long.parseLong(category.getId());
    }

    default Restaurant mapRestaurant(Long restaurantId) {
        if (restaurantId == null) {
            return null;
        }
        return new Restaurant(restaurantId, null, null, null, null, null, null);
    }

    default Long map(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }
        return restaurant.getId();
    }
}