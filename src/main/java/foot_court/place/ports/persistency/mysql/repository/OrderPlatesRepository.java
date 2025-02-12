package foot_court.place.ports.persistency.mysql.repository;

import foot_court.place.ports.persistency.mysql.entity.OrderPlatesEntity;
import foot_court.place.ports.persistency.mysql.entity.OrderEntity;
import foot_court.place.ports.persistency.mysql.entity.OrderPlatesId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderPlatesRepository extends JpaRepository<OrderPlatesEntity, OrderPlatesId> {
    List<OrderPlatesEntity> findByOrderId(OrderEntity order);
    default List<OrderPlatesEntity> findByOrderId(Long orderId) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderId);
        return findByOrderId(orderEntity);
    }
}