package model;

public class User {
    private int id;
    private String name;
    private String email;
    private int organizationId;

    public User() {}

    public User(String name, String email, int organizationId) {
        this.name = name;
        this.email = email;
        this.organizationId = organizationId;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getOrganizationId() { return organizationId; }
    public void setOrganizationId(int organizationId) { this.organizationId = organizationId; }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", organizationId=" + organizationId + "]";
    }
}