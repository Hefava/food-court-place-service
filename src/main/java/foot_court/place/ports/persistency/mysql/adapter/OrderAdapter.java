package foot_court.place.ports.persistency.mysql.adapter;

import foot_court.place.domain.model.Order;
import foot_court.place.domain.model.OrderPlates;
import foot_court.place.domain.spi.IOrderPersistencePort;
import foot_court.place.domain.utils.OrdersWithPlates;
import foot_court.place.domain.utils.pagination.PageRequestUtil;
import foot_court.place.domain.utils.pagination.PagedResult;
import foot_court.place.ports.persistency.mysql.entity.OrderEntity;
import foot_court.place.ports.persistency.mysql.entity.OrderPlatesEntity;
import foot_court.place.ports.persistency.mysql.entity.RestaurantsEntity;
import foot_court.place.ports.persistency.mysql.mapper.OrderEntityMapper;
import foot_court.place.ports.persistency.mysql.mapper.OrderPlatesEntityMapper;
import foot_court.place.ports.persistency.mysql.repository.OrderPlatesRepository;
import foot_court.place.ports.persistency.mysql.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public PagedResult<OrdersWithPlates> viewOrders(String status, Long restaurantId, PageRequestUtil pageRequestDomain) {
        PageRequest pageRequest = PageRequest.of(pageRequestDomain.getPage(), pageRequestDomain.getSize());

        RestaurantsEntity restaurant = new RestaurantsEntity();
        restaurant.setId(restaurantId);

        var page = orderRepository.findByStatusAndRestaurantId(status, restaurant, pageRequest);

        List<OrdersWithPlates> ordersWithPlatesList = page.getContent().stream()
                .map(orderEntity -> {
                    Order order = orderEntityMapper.toModel(orderEntity);
                    List<OrderPlates> orderPlatesList = orderPlatesRepository.findByOrderId(orderEntity.getId()).stream()
                            .map(orderPlatesEntityMapper::toModel)
                            .toList();
                    return new OrdersWithPlates(order, orderPlatesList);
                })
                .toList();

        return new PagedResult<>(
                ordersWithPlatesList,
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}
