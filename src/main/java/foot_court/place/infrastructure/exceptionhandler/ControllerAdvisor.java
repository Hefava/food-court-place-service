package foot_court.place.infrastructure.exceptionhandler;

import foot_court.place.domain.exception.ClientHasActiveOrderException;
import foot_court.place.domain.exception.MultipleRestaurantValidationExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    private static final String MESSAGE = "errors";

    @ExceptionHandler(MultipleRestaurantValidationExceptions.class)
    public ResponseEntity<Map<String, List<String>>> handleMultipleUserValidationExceptions(
            MultipleRestaurantValidationExceptions multipleRestaurantValidationExceptions) {
        Map<String, List<String>> response = new HashMap<>();
        response.put(MESSAGE, multipleRestaurantValidationExceptions.getErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ClientHasActiveOrderException.class)
    public ResponseEntity<Map<String, String>> handleClientHasActiveOrderException(
            ClientHasActiveOrderException clientHasActiveOrderException) {
        Map<String, String> response = new HashMap<>();
        response.put(MESSAGE, clientHasActiveOrderException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
            IllegalArgumentException illegalArgumentException) {
        Map<String, String> response = new HashMap<>();
        response.put(MESSAGE, illegalArgumentException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
