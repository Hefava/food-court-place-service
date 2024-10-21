package foot_court.place.ports.feign;

public enum StatusConstants {
    BAD_REQUEST(400, "Error in the request"),
    NOT_FOUND(404, "Resource not found"),
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    UNKNOWN_ERROR(0, "Unknown error");

    private final int code;
    private final String message;

    StatusConstants(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}