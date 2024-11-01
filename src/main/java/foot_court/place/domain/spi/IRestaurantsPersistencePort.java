package foot_court.place.domain.spi;

import foot_court.place.domain.model.Plate;
import foot_court.place.domain.model.Restaurant;
import foot_court.place.domain.model.RestaurantsWorkers;
import foot_court.place.domain.utils.pagination.PageRequestUtil;
import foot_court.place.domain.utils.pagination.PagedResult;
import foot_court.place.domain.utils.pagination.SortUtil;

public interface IRestaurantsPersistencePort {
    void registerRestaurant(Restaurant restaurant);
    void enterEmployee(RestaurantsWorkers restaurantsWorkers);
    Long getRestaurantOfEmployee(Long employeeId);
    boolean isOwnerOfRestaurant(Long ownerId, Long restaurantId);
    PagedResult<Restaurant> getRestaurants(SortUtil sortDomain, PageRequestUtil pageRequestDomain);
    PagedResult<Plate> getMenu(Long restaurantId, Long categoryId, SortUtil sortDomain, PageRequestUtil pageRequestDomain);
    boolean existsByName(String name);
    Restaurant findRestaurantById(Long restaurantId);
}
