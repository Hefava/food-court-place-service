package foot_court.place.ports.application.http.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlateRequest {
    private String name;
    private String description;
    private Long categoryId;
    private Long price;
    private Long restaurantId;
    private String imageUrl;
}
