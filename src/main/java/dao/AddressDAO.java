package dao;

import model.Address;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {
    public void addAddress(Address address) throws SQLException {
        String sql = "INSERT INTO Address (street, city, state, zipCode, country) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, address.getStreet());
            stmt.setString(2, address.getCity());
            stmt.setString(3, address.getState());
            stmt.setString(4, address.getZipCode());
            stmt.setString(5, address.getCountry());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    address.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Address getAddress(int id) throws SQLException {
        String sql = "SELECT * FROM Address WHERE id = ?";
        Address address = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                address = new Address();
                address.setId(rs.getInt("id"));
                address.setStreet(rs.getString("street"));
                address.setCity(rs.getString("city"));
                address.setState(rs.getString("state"));
                address.setZipCode(rs.getString("zipCode"));
                address.setCountry(rs.getString("country"));
            }
        }
        return address;
    }

    public List<Address> getAllAddresses() throws SQLException {
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT * FROM Address";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Address address = new Address();
                address.setId(rs.getInt("id"));
                address.setStreet(rs.getString("street"));
                address.setCity(rs.getString("city"));
                address.setState(rs.getString("state"));
                address.setZipCode(rs.getString("zipCode"));
                address.setCountry(rs.getString("country"));
                addresses.add(address);
            }
        }
        return addresses;
    }

    public void updateAddress(Address address) throws SQLException {
        String sql = "UPDATE Address SET street = ?, city = ?, state = ?, zipCode = ?, country = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address.getStreet());
            stmt.setString(2, address.getCity());
            stmt.setString(3, address.getState());
            stmt.setString(4, address.getZipCode());
            stmt.setString(5, address.getCountry());
            stmt.setInt(6, address.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteAddress(int id) throws SQLException {
        String sql = "DELETE FROM Address WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}