package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Sistema de Gerenciamento");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnUsers = new JButton("Gerenciar Usuários");
        JButton btnOrganizations = new JButton("Gerenciar Organizações");
        JButton btnProjects = new JButton("Gerenciar Projetos");
        JButton btnRoles = new JButton("Gerenciar Funções");
        JButton btnExit = new JButton("Sair");

        btnUsers.addActionListener(e -> {
            new UserUI().setVisible(true);
            dispose();
        });

        btnOrganizations.addActionListener(e -> {
            new OrganizationUI().setVisible(true);
            dispose();
        });

        btnProjects.addActionListener(e -> {
            new ProjectUI().setVisible(true);
            dispose();
        });

        btnRoles.addActionListener(e -> {
            new RoleUI().setVisible(true);
            dispose();
        });

        btnExit.addActionListener(e -> System.exit(0));

        panel.add(btnUsers);
        panel.add(btnOrganizations);
        panel.add(btnProjects);
        panel.add(btnRoles);
        panel.add(btnExit);

        add(panel);
    }
}