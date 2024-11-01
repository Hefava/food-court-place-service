package foot_court.place.ports.persistency.mysql.mapper;

import foot_court.place.domain.model.OrderPlates;
import foot_court.place.ports.persistency.mysql.entity.OrderEntity;
import foot_court.place.ports.persistency.mysql.entity.OrderPlatesEntity;
import foot_court.place.ports.persistency.mysql.entity.PlateEntity;
import foot_court.place.ports.persistency.mysql.repository.OrderRepository;
import foot_court.place.ports.persistency.mysql.repository.PlatesRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class OrderPlatesEntityMapper {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PlatesRepository plateRepository;

    @Mapping(source = "orderId", target = "orderId", qualifiedByName = "mapOrderIdToOrderEntity")
    @Mapping(source = "plateId", target = "plateId", qualifiedByName = "mapPlateIdToPlateEntity")
    public abstract OrderPlatesEntity toEntity(OrderPlates orderPlates);

    @Mapping(source = "orderId.id", target = "orderId")
    @Mapping(source = "plateId.id", target = "plateId")
    public abstract OrderPlates toModel(OrderPlatesEntity orderPlatesEntity);

    @Named("mapOrderIdToOrderEntity")
    protected OrderEntity mapOrderIdToOrderEntity(Long orderId) {
        return orderId != null ? orderRepository.findById(orderId).orElse(null) : null;
    }

    @Named("mapPlateIdToPlateEntity")
    protected PlateEntity mapPlateIdToPlateEntity(Long plateId) {
        return plateId != null ? plateRepository.findById(plateId).orElse(null) : null;
    }
}