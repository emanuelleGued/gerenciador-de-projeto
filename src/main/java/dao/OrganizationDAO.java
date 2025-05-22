package dao;

import model.Organization;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDAO {
    public void addOrganization(Organization organization) throws SQLException {
        String sql = "INSERT INTO Organization (name, addressId) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, organization.getName());
            stmt.setInt(2, organization.getAddressId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    organization.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Organization getOrganization(int id) throws SQLException {
        String sql = "SELECT * FROM Organization WHERE id = ?";
        Organization organization = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                organization = new Organization();
                organization.setId(rs.getInt("id"));
                organization.setName(rs.getString("name"));
                organization.setAddressId(rs.getInt("addressId"));
            }
        }
        return organization;
    }

    public List<Organization> getAllOrganizations() throws SQLException {
        List<Organization> organizations = new ArrayList<>();
        String sql = "SELECT * FROM Organization";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Organization organization = new Organization();
                organization.setId(rs.getInt("id"));
                organization.setName(rs.getString("name"));
                organization.setAddressId(rs.getInt("addressId"));
                organizations.add(organization);
            }
        }
        return organizations;
    }

    public void updateOrganization(Organization organization) throws SQLException {
        String sql = "UPDATE Organization SET name = ?, addressId = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, organization.getName());
            stmt.setInt(2, organization.getAddressId());
            stmt.setInt(3, organization.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteOrganization(int id) throws SQLException {
        String sql = "DELETE FROM Organization WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}