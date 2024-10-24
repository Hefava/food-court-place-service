package foot_court.place.ports.persistency.mysql.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false, length = 50)
    private String description;
}
