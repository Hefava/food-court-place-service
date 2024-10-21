package foot_court.place.domain.api;

import foot_court.place.domain.model.Restaurant;

public interface IRestaurantsServicePort {
    void registerRestaurant(Restaurant restaurant);
}
