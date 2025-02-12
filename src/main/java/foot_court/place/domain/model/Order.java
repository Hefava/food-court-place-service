package foot_court.place.domain.model;

import java.time.LocalDateTime;

public class Order {
    private Long id;
    private Long clientId;
    private LocalDateTime dateOrder;
    private String status;
    private Long chefId;
    private Long restaurantId;

    public Order() {
    }

    public Order(Long id, Long clientId, LocalDateTime dateOrder, String status, Long chefId, Long restaurantId) {
        this.id = id;
        this.clientId = clientId;
        this.dateOrder = dateOrder;
        this.status = status;
        this.chefId = chefId;
        this.restaurantId = restaurantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(LocalDateTime dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getChefId() {
        return chefId;
    }

    public void setChefId(Long chefId) {
        this.chefId = chefId;
    }
}
