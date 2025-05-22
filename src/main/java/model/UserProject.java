package model;

public class UserProject {
    private int userId;
    private int projectId;
    private int roleId;

    public UserProject() {}

    public UserProject(int userId, int projectId, int roleId) {
        this.userId = userId;
        this.projectId = projectId;
        this.roleId = roleId;
    }

    // Getters e Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }
    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    @Override
    public String toString() {
        return "UserProject [userId=" + userId + ", projectId=" + projectId + ", roleId=" + roleId + "]";
    }
}