package foot_court.place.ports.persistency.mysql.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurants")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false, length = 50)
    private String address;

    @Column(name = "owner_id", nullable = false, length = 10)
    private String ownerId;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(name = "logo_url", nullable = false)
    private String logoUrl;

    @Column(nullable = false, length = 60)
    private String nit;
}
