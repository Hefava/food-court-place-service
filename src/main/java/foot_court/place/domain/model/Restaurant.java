package foot_court.place.domain.model;

public class Restaurant {
    private Long id;
    private String name;
    private String address;
    private String ownerId;
    private String phone;
    private String logoUrl;
    private String nit;

    public Restaurant() {
    }

    public Restaurant(Long id, String name, String address, String ownerId, String phone, String logoUrl, String nit) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.ownerId = ownerId;
        this.phone = phone;
        this.logoUrl = logoUrl;
        this.nit = nit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
}