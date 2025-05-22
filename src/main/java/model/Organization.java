package model;

public class Organization {
    private int id;
    private String name;
    private int addressId;

    public Organization() {}

    public Organization(String name, int addressId) {
        this.name = name;
        this.addressId = addressId;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAddressId() { return addressId; }
    public void setAddressId(int addressId) { this.addressId = addressId; }

    @Override
    public String toString() {
        return "Organization [id=" + id + ", name=" + name + ", addressId=" + addressId + "]";
    }
}