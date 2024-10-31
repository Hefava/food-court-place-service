package foot_court.place.ports.persistency.mysql.repository;

import foot_court.place.ports.persistency.mysql.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Boolean existsByClientIdAndStatusIn(Long clientId, List<String> statuses);
}
