package foot_court.place.ports.application.http.dto;

import lombok.*;

@Getter
@Setter
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantsResponse {
    private String name;
    private String logoUrl;
}