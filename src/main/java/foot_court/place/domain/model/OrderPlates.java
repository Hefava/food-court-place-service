package foot_court.place.domain.model;

public class OrderPlates {
    private Long orderId;
    private Long plateId;
    private Integer quantity;

    public OrderPlates(Long orderId, Long plateId, Integer quantity) {
        this.orderId = orderId;
        this.plateId = plateId;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPlateId() {
        return plateId;
    }

    public void setPlateId(Long plateId) {
        this.plateId = plateId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
