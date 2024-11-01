package foot_court.place.domain.model;

public class RestaurantsWorkers {
    private Long restaurant;
    private Long employedId;

    public RestaurantsWorkers() {
    }

    public RestaurantsWorkers(Long restaurant, Long employedId) {
        this.restaurant = restaurant;
        this.employedId = employedId;
    }

    public Long getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Long restaurant) {
        this.restaurant = restaurant;
    }

    public Long getEmployedId() {
        return employedId;
    }

    public void setEmployedId(Long employedId) {
        this.employedId = employedId;
    }
}
