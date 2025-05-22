package ui;

import dao.OrganizationDAO;
import dao.UserDAO;
import model.Organization;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class UserUI extends JFrame {
    private UserDAO userDAO;
    private OrganizationDAO organizationDAO;

    public UserUI() {
        userDAO = new UserDAO();
        organizationDAO = new OrganizationDAO();

        setTitle("Gerenciamento de Usuários");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabela de usuários
        JTable userTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(userTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton btnAdd = new JButton("Adicionar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Excluir");
        JButton btnBack = new JButton("Voltar");

        btnAdd.addActionListener(e -> showAddUserDialog());
        btnEdit.addActionListener(e -> showEditUserDialog(userTable));
        btnDelete.addActionListener(e -> deleteUser(userTable));
        btnBack.addActionListener(e -> {
            new MainMenu().setVisible(true);
            dispose();
        });

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBack);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Carregar dados
        loadUserData(userTable);

        add(panel);
    }

    private void loadUserData(JTable table) {
        try {
            List<User> users = userDAO.getAllUsers();
            String[] columnNames = {"ID", "Nome", "Email", "Organização"};
            Object[][] data = new Object[users.size()][4];

            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                data[i][0] = user.getId();
                data[i][1] = user.getName();
                data[i][2] = user.getEmail();

                try {
                    Organization org = organizationDAO.getOrganization(user.getOrganizationId());
                    data[i][3] = org != null ? org.getName() : "N/A";
                } catch (SQLException e) {
                    data[i][3] = "N/A";
                }
            }

            table.setModel(new javax.swing.table.DefaultTableModel(
                    data, columnNames
            ));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddUserDialog() {
        JDialog dialog = new JDialog(this, "Adicionar Usuário", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtName = new JTextField();
        JTextField txtEmail = new JTextField();
        JComboBox<String> cbOrganization = new JComboBox<>();

        try {
            List<Organization> orgs = organizationDAO.getAllOrganizations();
            for (Organization org : orgs) {
                cbOrganization.addItem(org.getId() + " - " + org.getName());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(dialog, "Erro ao carregar organizações: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }

        panel.add(new JLabel("Nome:"));
        panel.add(txtName);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Organização:"));
        panel.add(cbOrganization);

        JButton btnSave = new JButton("Salvar");
        JButton btnCancel = new JButton("Cancelar");

        btnSave.addActionListener(e -> {
            try {
                String selectedOrg = (String) cbOrganization.getSelectedItem();
                int orgId = selectedOrg != null ?
                        Integer.parseInt(selectedOrg.split(" - ")[0]) : 0;

                User user = new User(
                        txtName.getText(),
                        txtEmail.getText(),
                        orgId
                );

                userDAO.addUser(user);
                loadUserData((JTable) ((JScrollPane) getContentPane().getComponent(0)).getViewport().getView());
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Usuário adicionado com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Erro ao adicionar usuário: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        panel.add(btnSave);
        panel.add(btnCancel);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showEditUserDialog(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para editar",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int userId = (int) table.getValueAt(selectedRow, 0);
        try {
            User user = userDAO.getUser(userId);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog dialog = new JDialog(this, "Editar Usuário", true);
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField txtName = new JTextField(user.getName());
            JTextField txtEmail = new JTextField(user.getEmail());
            JComboBox<String> cbOrganization = new JComboBox<>();

            try {
                List<Organization> orgs = organizationDAO.getAllOrganizations();
                for (Organization org : orgs) {
                    cbOrganization.addItem(org.getId() + " - " + org.getName());
                    if (org.getId() == user.getOrganizationId()) {
                        cbOrganization.setSelectedItem(org.getId() + " - " + org.getName());
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(dialog, "Erro ao carregar organizações: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }

            panel.add(new JLabel("Nome:"));
            panel.add(txtName);
            panel.add(new JLabel("Email:"));
            panel.add(txtEmail);
            panel.add(new JLabel("Organização:"));
            panel.add(cbOrganization);

            JButton btnSave = new JButton("Salvar");
            JButton btnCancel = new JButton("Cancelar");

            btnSave.addActionListener(e -> {
                try {
                    String selectedOrg = (String) cbOrganization.getSelectedItem();
                    int orgId = selectedOrg != null ?
                            Integer.parseInt(selectedOrg.split(" - ")[0]) : 0;

                    user.setName(txtName.getText());
                    user.setEmail(txtEmail.getText());
                    user.setOrganizationId(orgId);

                    userDAO.updateUser(user);
                    loadUserData(table);
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Erro ao atualizar usuário: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnCancel.addActionListener(e -> dialog.dispose());

            panel.add(btnSave);
            panel.add(btnCancel);

            dialog.add(panel);
            dialog.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuário: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUser(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para excluir",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int userId = (int) table.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir este usuário?", "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                userDAO.deleteUser(userId);
                loadUserData(table);
                JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir usuário: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}