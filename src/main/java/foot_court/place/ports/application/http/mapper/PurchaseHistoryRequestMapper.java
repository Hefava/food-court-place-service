package foot_court.place.ports.application.http.mapper;

import foot_court.place.domain.utils.PurchaseHistory;
import foot_court.place.ports.application.http.dto.PurchaseHistoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseHistoryRequestMapper {
    PurchaseHistoryRequest toDto(PurchaseHistory purchaseHistory);
}
