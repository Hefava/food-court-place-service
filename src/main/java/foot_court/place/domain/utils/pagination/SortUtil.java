package foot_court.place.domain.utils.pagination;

public class SortUtil {
    public String getProperty() {
        return property;
    }

    public Direction getDirection() {
        return direction;
    }

    private final String property;

    public SortUtil(String property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }

    private final Direction direction;

    public enum Direction {
        ASC, DESC
    }
}

