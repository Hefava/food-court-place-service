package foot_court.place.domain.utils;

public class PlaceUtils {

    public static final String ROLE_ADMINISTRATOR = "ADMINISTRATOR";

    public static final String ROLE_OWNER = "OWNER";

    public static final String ROLE_EMPLOYEE = "EMPLOYEE";

    public static final String ROLE_CUSTOMER = "CLIENT";

    public static final String ORDER_DEFAULT_ASC = "asc";

    public static final String ORDER_DEFAULT = "name";

    public static final String ORDER_PENDING = "pendiente";

    public static final String ORDER_PREPARATION = "en_preparacion";

    public static final String ORDER_READY = "listo";

    public static final String ORDER_DELIVERED = "entregado";

    public static final String ORDER_CANCELLED = "cancelado";

    public static final String CLIENT_HAS_ACTIVE_ORDER = "Client has active order";

    public static final String ORDER_STATUS_ERROR = "Order status error";

    public static final String NOT_RESTAURANT_CHEF = "You are not the chef of this restaurant";

    public static final String NOT_CHEF_ORDER = "You are not the chef of this order";

    private PlaceUtils() {
        throw new AssertionError("Cannot instantiate this class");
    }
}
