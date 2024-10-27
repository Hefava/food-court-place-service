package foot_court.place.domain.spi;

import foot_court.place.domain.model.Restaurant;

public interface IRestaurantsPersistencePort {
    void registerRestaurant(Restaurant restaurant);
    boolean existsByName(String name);
    Restaurant findRestaurantById(Long restaurantId);
}
