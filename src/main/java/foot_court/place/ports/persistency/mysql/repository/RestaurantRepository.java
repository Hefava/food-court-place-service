package foot_court.place.ports.persistency.mysql.repository;

import foot_court.place.ports.persistency.mysql.entity.RestaurantsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantsEntity, Long> {
    Boolean existsByName(String name);
}
