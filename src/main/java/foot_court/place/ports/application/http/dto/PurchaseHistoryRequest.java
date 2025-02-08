package foot_court.place.ports.application.http.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryRequest {
    private String orderId;
    private String clientId;
    private String clientEmail;
    private LocalDateTime statusDate;
    private String lastStatus;
    private String newStatus;
    private String employeeId;
    private String employeeEmail;
}
