package foot_court.place.ports.persistency.mysql.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "restaurants_workers")
@IdClass(RestaurantsWorkersId.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantsWorkersEntity {

    @Id
    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Id
    @Column(name = "employed_id", nullable = false)
    private Long employedId;
}