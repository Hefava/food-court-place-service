package foot_court.place.ports.persistency.mysql.adapter;

import foot_court.place.domain.spi.ITraceabilityFeignPersistencePort;
import foot_court.place.domain.utils.PurchaseHistory;
import foot_court.place.ports.application.http.dto.PurchaseHistoryRequest;
import foot_court.place.ports.application.http.mapper.PurchaseHistoryRequestMapper;
import foot_court.place.ports.persistency.mysql.repository.ITraceabilityFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraceabilityAdapter implements ITraceabilityFeignPersistencePort {
    private final ITraceabilityFeign traceabilityFeign;
    private final PurchaseHistoryRequestMapper purchaseHistoryRequestMapper;

    @Override
    public void generateReport(PurchaseHistory purchaseHistory) {
        PurchaseHistoryRequest purchaseHistoryRequest = purchaseHistoryRequestMapper.toDto(purchaseHistory);
        traceabilityFeign.generateReport(purchaseHistoryRequest);
    }
}