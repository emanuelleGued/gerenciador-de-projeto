import dao.*;
import model.*;
import util.DatabaseConnection;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Inicializar o banco de dados
        DatabaseConnection.initializeDatabase();

        try {
            // Testar Address
            AddressDAO addressDAO = new AddressDAO();
            Address address = new Address("123 Main St", "Springfield", "IL", "62704", "USA");
            addressDAO.addAddress(address);
            System.out.println("Address added: " + address);

            // Testar Organization
            OrganizationDAO orgDAO = new OrganizationDAO();
            Organization org = new Organization("Acme Corp", address.getId());
            orgDAO.addOrganization(org);
            System.out.println("Organization added: " + org);

            // Testar User
            UserDAO userDAO = new UserDAO();
            User user = new User("John Doe", "john@example.com", org.getId());
            userDAO.addUser(user);
            System.out.println("User added: " + user);

            // Testar Role
            RoleDAO roleDAO = new RoleDAO();
            Role role = new Role("Developer", "Software developer role");
            roleDAO.addRole(role);
            System.out.println("Role added: " + role);

            // Testar Project
            ProjectDAO projectDAO = new ProjectDAO();
            Project project = new Project("Website Redesign", "Redesign company website", "2023-01-01", "2023-06-30");
            projectDAO.addProject(project);
            System.out.println("Project added: " + project);

            // Listar todos os usuários
            System.out.println("\nAll Users:");
            userDAO.getAllUsers().forEach(System.out::println);

            // Listar todos os projetos
            System.out.println("\nAll Projects:");
            projectDAO.getAllProjects().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}