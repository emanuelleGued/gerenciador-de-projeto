package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:project_management.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection()) {
            // Tabela Address
            conn.createStatement().executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Address (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "street TEXT, " +
                            "city TEXT, " +
                            "state TEXT, " +
                            "zipCode TEXT, " +
                            "country TEXT)"
            );

            // Tabela Organization
            conn.createStatement().executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Organization (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name TEXT, " +
                            "addressId INTEGER, " +
                            "FOREIGN KEY (addressId) REFERENCES Address(id))"
            );

            // Tabela User
            conn.createStatement().executeUpdate(
                    "CREATE TABLE IF NOT EXISTS User (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name TEXT, " +
                            "email TEXT UNIQUE, " +
                            "organizationId INTEGER, " +
                            "FOREIGN KEY (organizationId) REFERENCES Organization(id))"
            );

            // Tabela Role
            conn.createStatement().executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Role (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name TEXT UNIQUE, " +
                            "description TEXT)"
            );

            // Tabela Project
            conn.createStatement().executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Project (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name TEXT, " +
                            "description TEXT, " +
                            "startDate TEXT, " +
                            "endDate TEXT)"
            );

            // Tabela de relacionamento UserProject (com Role)
            conn.createStatement().executeUpdate(
                    "CREATE TABLE IF NOT EXISTS UserProject (" +
                            "userId INTEGER, " +
                            "projectId INTEGER, " +
                            "roleId INTEGER, " +
                            "PRIMARY KEY (userId, projectId), " +
                            "FOREIGN KEY (userId) REFERENCES User(id), " +
                            "FOREIGN KEY (projectId) REFERENCES Project(id), " +
                            "FOREIGN KEY (roleId) REFERENCES Role(id))"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}