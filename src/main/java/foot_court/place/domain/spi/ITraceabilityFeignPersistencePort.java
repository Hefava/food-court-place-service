package foot_court.place.domain.spi;

import foot_court.place.domain.utils.PurchaseHistory;

public interface ITraceabilityFeignPersistencePort {
    void generateReport(PurchaseHistory purchaseHistory);
}
