package foot_court.place.ports.application.http.dto;

import lombok.*;

@Getter
@Setter
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetPlatesResponse {
    private String name;
    private String description;
    private Long price;
    private String imageUrl;
}
