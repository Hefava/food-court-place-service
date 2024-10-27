package foot_court.place.domain.utils;

public class TokenHolder {
    private static final ThreadLocal<String> currentToken = new ThreadLocal<>();

    private TokenHolder() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void setToken(String token) {
        currentToken.set(token);
    }

    public static String getToken() {
        return currentToken.get();
    }

    public static void clear() {
        currentToken.remove();
    }
}