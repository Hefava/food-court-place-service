package foot_court.place.ports.persistency.mysql.repository;

import foot_court.place.ports.application.http.dto.PurchaseHistoryRequest;
import foot_court.place.ports.feign.FeingClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "traceability-service", url = "http://localhost:8080", configuration = FeingClientConfiguration.class)
public interface ITraceabilityFeign {
    @PostMapping("/purchase-history/generate-report")
    Void generateReport(@RequestBody PurchaseHistoryRequest purchaseHistoryRequest);

}