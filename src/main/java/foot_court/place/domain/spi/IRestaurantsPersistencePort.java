package foot_court.place.domain.spi;

import foot_court.place.domain.model.Restaurant;
import foot_court.place.domain.utils.pagination.PageRequestUtil;
import foot_court.place.domain.utils.pagination.PagedResult;
import foot_court.place.domain.utils.pagination.SortUtil;

public interface IRestaurantsPersistencePort {
    void registerRestaurant(Restaurant restaurant);
    PagedResult<Restaurant> getRestaurants(SortUtil sortDomain, PageRequestUtil pageRequestDomain);
    boolean existsByName(String name);
    Restaurant findRestaurantById(Long restaurantId);
}
