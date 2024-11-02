package foot_court.place.ports.persistency.mysql.repository;

import foot_court.place.ports.application.http.dto.OrderReadyRequest;
import foot_court.place.ports.feign.FeingClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "messaging-service", url = "http://localhost:8084", configuration = FeingClientConfiguration.class)
public interface IMessagingFeign {
    @PostMapping("/notification/order-ready")
    Void notifyOrderReady(@RequestBody OrderReadyRequest orderReadyRequest);
}
