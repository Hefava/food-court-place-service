package foot_court.place.domain.utils;

public class RestaurantUtils {

    public static final String ONLY_NUMBER_REGEX = "^\\+?\\d+$";

    public static final int MAX_PHONE_NUMBER_LENGTH = 13;

    public static final String VALID_RESTAURANT_NAME_REGEX = "^(?!\\d+$)[\\p{L}\\d\\s]+$";

    public static final int MAX_RESTAURANT_NAME_LENGHT = 50;

    //Error messages
    public static final String RESTAURANT_ALREADY_EXISTS = "Restaurant already exists";
    public static final String INVALID_RESTAURANT_NAME = "Invalid restaurant name";
    public static final String INVALID_NIT = "Invalid NIT";
    public static final String INVALID_PHONE_NUMBER = "Invalid phone number";
    public static final String INVALID_OWNER = "Invalid owner";

    private RestaurantUtils() {
        throw new AssertionError("Cannot instantiate this class");
    }
}