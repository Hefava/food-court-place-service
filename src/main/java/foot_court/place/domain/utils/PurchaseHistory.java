package foot_court.place.domain.utils;

import java.time.LocalDateTime;

public class PurchaseHistory {
    private String orderId;
    private String clientId;
    private String clientEmail;
    private LocalDateTime statusDate;
    private String lastStatus;
    private String newStatus;
    private String employeeId;
    private String employeeEmail;

    public PurchaseHistory() {
    }

    public PurchaseHistory(String orderId, String clientId, String clientEmail, LocalDateTime statusDate, String lastStatus, String newStatus, String employeeId, String employeeEmail) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.clientEmail = clientEmail;
        this.statusDate = statusDate;
        this.lastStatus = lastStatus;
        this.newStatus = newStatus;
        this.employeeId = employeeId;
        this.employeeEmail = employeeEmail;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
}
