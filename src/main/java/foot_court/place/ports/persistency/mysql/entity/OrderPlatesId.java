package foot_court.place.ports.persistency.mysql.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderPlatesId implements Serializable {
    private Long orderId;
    private Long plateId;
}