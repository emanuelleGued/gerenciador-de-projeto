package dao;

import model.Project;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {
    public void addProject(Project project) throws SQLException {
        String sql = "INSERT INTO Project (name, description, startDate, endDate) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setString(3, project.getStartDate());
            stmt.setString(4, project.getEndDate());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    project.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Project getProject(int id) throws SQLException {
        String sql = "SELECT * FROM Project WHERE id = ?";
        Project project = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setStartDate(rs.getString("startDate"));
                project.setEndDate(rs.getString("endDate"));
            }
        }
        return project;
    }

    public List<Project> getAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM Project";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setStartDate(rs.getString("startDate"));
                project.setEndDate(rs.getString("endDate"));
                projects.add(project);
            }
        }
        return projects;
    }

    public void updateProject(Project project) throws SQLException {
        String sql = "UPDATE Project SET name = ?, description = ?, startDate = ?, endDate = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setString(3, project.getStartDate());
            stmt.setString(4, project.getEndDate());
            stmt.setInt(5, project.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteProject(int id) throws SQLException {
        String sql = "DELETE FROM Project WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}