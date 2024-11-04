package foot_court.place.domain.api;

import foot_court.place.domain.model.Plate;
import foot_court.place.domain.model.Restaurant;
import foot_court.place.domain.utils.pagination.PagedResult;

public interface IRestaurantsServicePort {
    void registerRestaurant(Restaurant restaurant);
    void enterEmployee(Long ownerId, Long restaurantId, Long employeeId);
    PagedResult<Restaurant> getRestaurants(String order, int page, int size);
    PagedResult<Plate> getMenu(Long restaurantId, Long categoryId, String order, int page, int size);
}
