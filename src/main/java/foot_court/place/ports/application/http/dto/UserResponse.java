package foot_court.place.ports.application.http.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String username;
    private String role;
    private Boolean authorized;
}
