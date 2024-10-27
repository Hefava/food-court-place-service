package foot_court.place.domain.utils;

public class PlateUtils {

    public static final String PLATE_NOT_FOUND = "Plate not found with ID: ";

    public static final String PLATE_PRICE_ERROR = "Plate price must be greater than 0";

    public static final String NOT_OWNER = "You are not the owner of the restaurant that owns this plate";

    private PlateUtils() {
        throw new AssertionError("Cannot instantiate this class");
    }
}
