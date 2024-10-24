package foot_court.place.ports.application.http.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePlateRequest {
    private Long id;
    private String description;
    private Long price;
}
