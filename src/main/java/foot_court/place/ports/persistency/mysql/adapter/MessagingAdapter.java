package foot_court.place.ports.persistency.mysql.adapter;

import foot_court.place.domain.spi.IMessagingFeignPersistencePort;
import foot_court.place.ports.application.http.dto.OrderReadyRequest;
import foot_court.place.ports.persistency.mysql.repository.IMessagingFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagingAdapter implements IMessagingFeignPersistencePort {
    private final IMessagingFeign messagingFeign;

    @Override
    public void sendMessage(String phoneNumber) {
        OrderReadyRequest orderReadyRequest = new OrderReadyRequest();
        orderReadyRequest.setPhoneNumber(phoneNumber);
        messagingFeign.notifyOrderReady(orderReadyRequest);
    }

    @Override
    public String getPinByPhoneNumber(String phoneNumber) {
        return messagingFeign.getPin(phoneNumber);
    }
}