package foot_court.place.ports.application.http.mapper;

import foot_court.place.domain.model.Category;
import foot_court.place.domain.model.Plate;
import foot_court.place.domain.model.Restaurant;
import foot_court.place.ports.application.http.dto.CreatePlateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreatePlateRequestMapper {

    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "restaurantId", target = "restaurantId")
    Plate toPlate(CreatePlateRequest createPlateRequest);

    // Métodos personalizados para mapear Long a objetos y viceversa
    default Category mapCategory(Long categoryId) {
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