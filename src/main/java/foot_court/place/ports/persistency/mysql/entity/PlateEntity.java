package foot_court.place.ports.persistency.mysql.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plates")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false, length = 50)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryId;

    @Column(nullable = false)
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantsEntity restaurantId;

    @Column(name = "image_url" ,nullable = false, length = 40)
    private String imageUrl;

    @Column(nullable = false)
    private Boolean active;
}
