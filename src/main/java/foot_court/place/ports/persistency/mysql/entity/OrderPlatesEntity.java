package foot_court.place.ports.persistency.mysql.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plates_by_order")
@IdClass(OrderPlatesId.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderPlatesEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plate_id")
    private PlateEntity plateId;

    @Column(nullable = false)
    private Integer quantity;
}