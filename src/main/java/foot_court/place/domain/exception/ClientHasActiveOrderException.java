package foot_court.place.domain.exception;

public class ClientHasActiveOrderException extends RuntimeException {
    public ClientHasActiveOrderException(String message) {
        super(message);
    }
}
