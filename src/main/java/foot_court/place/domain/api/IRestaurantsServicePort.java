package foot_court.place.domain.api;

import foot_court.place.domain.model.Restaurant;
import foot_court.place.domain.utils.pagination.PagedResult;

public interface IRestaurantsServicePort {
    void registerRestaurant(Restaurant restaurant);
    PagedResult<Restaurant> getRestaurants(String order, int page, int size);
}
