package foot_court.place.domain.utils;

public class PlaceUtils {

    public static final String ROLE_ADMINISTRATOR = "ADMINISTRATOR";

    public static final String ROLE_OWNER = "OWNER";

    public static final String ROLE_EMPLOYEE = "EMPLOYEE";

    public static final String ROLE_CUSTOMER = "CLIENT";

    public static final String ORDER_DEFAULT_ASC = "asc";

    public static final String ORDER_DEFAULT = "name";

    public static final String ORDER_PENDING = "Pendiente";

    public static final String ORDER_PREPARATION = "Preparacion";

    public static final String ORDER_READY = "Listo";

    public static final String CLIENT_HAS_ACTIVE_ORDER = "Client has active order";

    private PlaceUtils() {
        throw new AssertionError("Cannot instantiate this class");
    }
}
