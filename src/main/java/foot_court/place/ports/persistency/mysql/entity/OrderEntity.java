package foot_court.place.ports.persistency.mysql.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false, length = 10)
    private Long clientId;

    @Column(name = "date_order", nullable = false)
    private LocalDateTime dateOrder;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "chef_id", nullable = false, length = 10)
    private Long chefId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantsEntity restaurantId;
}
