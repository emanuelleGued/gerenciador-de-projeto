package dao;

import model.Task;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    public void addTask(Task task) throws SQLException {
        String sql = "INSERT INTO Task (title, description, dueDate, completed, projectId) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getDueDate().toString());
            stmt.setBoolean(4, task.isCompleted());
            stmt.setInt(5, task.getProjectId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    task.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Task getTask(int id) throws SQLException {
        String sql = "SELECT * FROM Task WHERE id = ?";
        Task task = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                task = new Task();
                task.setId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setDueDate(LocalDate.parse(rs.getString("dueDate")));
                task.setCompleted(rs.getBoolean("completed"));
                task.setProjectId(rs.getInt("projectId"));
            }
        }
        return task;
    }

    public List<Task> getTasksByProject(int projectId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Task WHERE projectId = ? ORDER BY dueDate";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setDueDate(LocalDate.parse(rs.getString("dueDate")));
                task.setCompleted(rs.getBoolean("completed"));
                task.setProjectId(rs.getInt("projectId"));
                tasks.add(task);
            }
        }
        return tasks;
    }

    public void updateTask(Task task) throws SQLException {
        String sql = "UPDATE Task SET title = ?, description = ?, dueDate = ?, completed = ?, projectId = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getDueDate().toString());
            stmt.setBoolean(4, task.isCompleted());
            stmt.setInt(5, task.getProjectId());
            stmt.setInt(6, task.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteTask(int id) throws SQLException {
        String sql = "DELETE FROM Task WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}