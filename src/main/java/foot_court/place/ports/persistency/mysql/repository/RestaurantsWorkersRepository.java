package foot_court.place.ports.persistency.mysql.repository;

import foot_court.place.ports.persistency.mysql.entity.RestaurantsWorkersEntity;
import foot_court.place.ports.persistency.mysql.entity.RestaurantsWorkersId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantsWorkersRepository extends JpaRepository<RestaurantsWorkersEntity, RestaurantsWorkersId> {
    Optional<RestaurantsWorkersEntity> findByEmployedId(Long employedId);
    List<RestaurantsWorkersEntity> findByRestaurantIdIn(List<Long> restaurantIds);
}
