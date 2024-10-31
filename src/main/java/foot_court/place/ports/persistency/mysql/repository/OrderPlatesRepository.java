package foot_court.place.ports.persistency.mysql.repository;

import foot_court.place.ports.persistency.mysql.entity.OrderPlatesEntity;
import foot_court.place.ports.persistency.mysql.entity.OrderPlatesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPlatesRepository extends JpaRepository<OrderPlatesEntity, OrderPlatesId> {
}