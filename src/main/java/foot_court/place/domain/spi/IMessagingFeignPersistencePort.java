package foot_court.place.domain.spi;

public interface IMessagingFeignPersistencePort {
    void sendMessage(String phoneNumber);
    String getPinByPhoneNumber(String phoneNumber);
}
