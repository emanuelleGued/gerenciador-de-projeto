package ui;

import dao.RoleDAO;
import model.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class RoleUI extends JFrame {
    private RoleDAO roleDAO;

    public RoleUI() {
        roleDAO = new RoleDAO();

        setTitle("Gerenciamento de Funções");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabela de funções
        JTable roleTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(roleTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton btnAdd = new JButton("Adicionar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Excluir");
        JButton btnBack = new JButton("Voltar");

        btnAdd.addActionListener(e -> showAddRoleDialog());
        btnEdit.addActionListener(e -> showEditRoleDialog(roleTable));
        btnDelete.addActionListener(e -> deleteRole(roleTable));
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
        loadRoleData(roleTable);

        add(panel);
    }

    private void loadRoleData(JTable table) {
        try {
            List<Role> roles = roleDAO.getAllRoles();
            String[] columnNames = {"ID", "Nome", "Descrição"};
            Object[][] data = new Object[roles.size()][3];

            for (int i = 0; i < roles.size(); i++) {
                Role role = roles.get(i);
                data[i][0] = role.getId();
                data[i][1] = role.getName();
                data[i][2] = role.getDescription();
            }

            table.setModel(new javax.swing.table.DefaultTableModel(
                    data, columnNames
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar funções: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddRoleDialog() {
        JDialog dialog = new JDialog(this, "Adicionar Função", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtName = new JTextField();
        JTextArea txtDescription = new JTextArea(3, 20);
        JScrollPane descriptionScroll = new JScrollPane(txtDescription);

        panel.add(new JLabel("Nome:"));
        panel.add(txtName);
        panel.add(new JLabel("Descrição:"));
        panel.add(descriptionScroll);

        JButton btnSave = new JButton("Salvar");
        JButton btnCancel = new JButton("Cancelar");

        btnSave.addActionListener(e -> {
            try {
                Role role = new Role(
                        txtName.getText(),
                        txtDescription.getText()
                );

                roleDAO.addRole(role);
                loadRoleData((JTable) ((JScrollPane) getContentPane().getComponent(0)).getViewport().getView());
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Função adicionada com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Erro ao adicionar função: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        panel.add(btnSave);
        panel.add(btnCancel);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showEditRoleDialog(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma função para editar",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int roleId = (int) table.getValueAt(selectedRow, 0);
        try {
            Role role = roleDAO.getRole(roleId);
            if (role == null) {
                JOptionPane.showMessageDialog(this, "Função não encontrada",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog dialog = new JDialog(this, "Editar Função", true);
            dialog.setSize(400, 250);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField txtName = new JTextField(role.getName());
            JTextArea txtDescription = new JTextArea(role.getDescription(), 3, 20);
            JScrollPane descriptionScroll = new JScrollPane(txtDescription);

            panel.add(new JLabel("Nome:"));
            panel.add(txtName);
            panel.add(new JLabel("Descrição:"));
            panel.add(descriptionScroll);

            JButton btnSave = new JButton("Salvar");
            JButton btnCancel = new JButton("Cancelar");

            btnSave.addActionListener(e -> {
                try {
                    role.setName(txtName.getText());
                    role.setDescription(txtDescription.getText());

                    roleDAO.updateRole(role);
                    loadRoleData(table);
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Função atualizada com sucesso!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Erro ao atualizar função: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnCancel.addActionListener(e -> dialog.dispose());

            panel.add(btnSave);
            panel.add(btnCancel);

            dialog.add(panel);
            dialog.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar função: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRole(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma função para excluir",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int roleId = (int) table.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir esta função?", "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                roleDAO.deleteRole(roleId);
                loadRoleData(table);
                JOptionPane.showMessageDialog(this, "Função excluída com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir função: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
