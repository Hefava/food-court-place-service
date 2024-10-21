package foot_court.place.domain.exception;
import java.util.List;

public class MultipleRestaurantValidationExceptions extends RuntimeException {

    private final List<String> errors;

    public MultipleRestaurantValidationExceptions(List<String> errors) {
        super("Multiple validation errors occurred");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
