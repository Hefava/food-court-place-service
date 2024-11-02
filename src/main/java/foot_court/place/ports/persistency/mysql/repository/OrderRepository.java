package foot_court.place.ports.persistency.mysql.repository;

import foot_court.place.ports.persistency.mysql.entity.OrderEntity;
import foot_court.place.ports.persistency.mysql.entity.RestaurantsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Boolean existsByClientIdAndStatusIn(Long clientId, List<String> statuses);
    Page<OrderEntity> findByStatusAndRestaurantId(String status, RestaurantsEntity restaurant, Pageable pageable);
    Optional<OrderEntity> findByChefId(Long chefId);
}
