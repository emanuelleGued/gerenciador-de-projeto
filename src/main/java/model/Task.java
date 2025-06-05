package model;

import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean completed;
    private int projectId;

    public Task() {}

    public Task(String title, String description, LocalDate dueDate, boolean completed, int projectId) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
        this.projectId = projectId;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    @Override
    public String toString() {
        return "Task [id=" + id + ", title=" + title + ", description=" + description +
                ", dueDate=" + dueDate + ", completed=" + completed + ", projectId=" + projectId + "]";
    }
}