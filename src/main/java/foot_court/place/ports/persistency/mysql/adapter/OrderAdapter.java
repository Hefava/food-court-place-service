package foot_court.place.ports.persistency.mysql.adapter;

import foot_court.place.domain.model.Order;
import foot_court.place.domain.model.OrderPlates;
import foot_court.place.domain.spi.IOrderPersistencePort;
import foot_court.place.ports.persistency.mysql.entity.OrderEntity;
import foot_court.place.ports.persistency.mysql.entity.OrderPlatesEntity;
import foot_court.place.ports.persistency.mysql.mapper.OrderEntityMapper;
import foot_court.place.ports.persistency.mysql.mapper.OrderPlatesEntityMapper;
import foot_court.place.ports.persistency.mysql.repository.OrderPlatesRepository;
import foot_court.place.ports.persistency.mysql.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static foot_court.place.domain.utils.PlaceUtils.*;

@Component
@RequiredArgsConstructor
public class OrderAdapter implements IOrderPersistencePort {
    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final OrderPlatesRepository orderPlatesRepository;
    private final OrderPlatesEntityMapper orderPlatesEntityMapper;

    @Override
    public boolean hasActiveOrder(Long clientId) {
        return orderRepository.existsByClientIdAndStatusIn(clientId, List.of(ORDER_PENDING, ORDER_PREPARATION, ORDER_READY));
    }

    @Override
    public void createOrder(Order order, List<OrderPlates> orderPlates) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(order);
        orderRepository.save(orderEntity);

        List<OrderPlatesEntity> orderPlatesEntities = orderPlates.stream()
                .map(orderPlate -> {
                    OrderPlatesEntity entity = orderPlatesEntityMapper.toEntity(orderPlate);
                    entity.setOrderId(orderEntity);
                    return entity;
                })
                .toList();

        orderPlatesRepository.saveAll(orderPlatesEntities);
    }

}
