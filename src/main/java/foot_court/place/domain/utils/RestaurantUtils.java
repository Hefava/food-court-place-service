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
    public static final String NOT_OWNER = "User is not the owner of the restaurant";

    // SQL query constant
    public static final String PLATES_QUERY = "SELECT * FROM plates p " +
            "WHERE p.restaurant_id = :restaurantId " +
            "AND (:categoryId IS NULL OR p.category_id = :categoryId)";


    private RestaurantUtils() {
        throw new AssertionError("Cannot instantiate this class");
    }
}