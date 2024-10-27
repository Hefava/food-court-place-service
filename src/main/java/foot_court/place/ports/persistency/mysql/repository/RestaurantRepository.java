package foot_court.place.ports.persistency.mysql.repository;

import foot_court.place.ports.persistency.mysql.entity.RestaurantsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantsEntity, Long> {
    Boolean existsByName(String name);
    Optional<RestaurantsEntity> findById(Long id);
}
