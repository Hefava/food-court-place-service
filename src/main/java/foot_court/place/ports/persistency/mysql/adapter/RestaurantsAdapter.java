package foot_court.place.ports.persistency.mysql.adapter;

import foot_court.place.domain.model.Restaurant;
import foot_court.place.domain.spi.IRestaurantsPersistencePort;
import foot_court.place.ports.persistency.mysql.entity.RestaurantsEntity;
import foot_court.place.ports.persistency.mysql.mapper.RestaurantsEntityMapper;
import foot_court.place.ports.persistency.mysql.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantsAdapter implements IRestaurantsPersistencePort {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantsEntityMapper restaurantMapper;

    @Override
    public void registerRestaurant(Restaurant restaurant) {
        RestaurantsEntity restaurantEntity = restaurantMapper.toEntity(restaurant);
        restaurantRepository.save(restaurantEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return restaurantRepository.existsByName(name);
    }
}
