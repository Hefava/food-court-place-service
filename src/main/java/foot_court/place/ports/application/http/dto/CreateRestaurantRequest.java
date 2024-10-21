package foot_court.place.ports.application.http.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantRequest {
    private String name;
    private String address;
    private String ownerId;
    private String phone;
    private String logoUrl;
    private String nit;
}
