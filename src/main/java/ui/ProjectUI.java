package ui;

import dao.OrganizationDAO;
import dao.ProjectDAO;
import dao.TaskDAO;
import model.Organization;
import model.Project;
import model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ProjectUI extends JFrame {
    private ProjectDAO projectDAO;
    private OrganizationDAO organizationDAO;

    public ProjectUI() {
        projectDAO = new ProjectDAO();
        organizationDAO = new OrganizationDAO();

        setTitle("Gerenciamento de Projetos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabela de projetos
        JTable projectTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(projectTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));


        JButton btnAdd = new JButton("Adicionar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Excluir");
        JButton btnBack = new JButton("Voltar");
        JButton btnTasks = new JButton("Tarefas");

        btnTasks.addActionListener(e -> {
            int selectedRow = projectTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um projeto para gerenciar tarefas",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int projectId = (int) projectTable.getValueAt(selectedRow, 0);
            new ProjectTasksUI(projectId).setVisible(true);
            dispose();
        });

        btnAdd.addActionListener(e -> showAddProjectDialog());
        btnEdit.addActionListener(e -> showEditProjectDialog(projectTable));
        btnDelete.addActionListener(e -> deleteProject(projectTable));
        btnBack.addActionListener(e -> {
            new MainMenu().setVisible(true);
            dispose();
        });

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnTasks);
        buttonPanel.add(btnBack);


        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Carregar dados
        loadProjectData(projectTable);

        add(panel);
    }

    private void showAddProjectDialog() {
        JDialog dialog = new JDialog(this, "Adicionar Projeto", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtName = new JTextField();
        JTextArea txtDescription = new JTextArea(3, 20);
        JScrollPane descriptionScroll = new JScrollPane(txtDescription);
        JTextField txtStartDate = new JTextField();
        JTextField txtEndDate = new JTextField();
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
        panel.add(new JLabel("Descrição:"));
        panel.add(descriptionScroll);
        panel.add(new JLabel("Data Início (YYYY-MM-DD):"));
        panel.add(txtStartDate);
        panel.add(new JLabel("Data Término (YYYY-MM-DD):"));
        panel.add(txtEndDate);
        panel.add(new JLabel("Organização:"));
        panel.add(cbOrganization);

        JButton btnSave = new JButton("Salvar");
        JButton btnCancel = new JButton("Cancelar");

        btnSave.addActionListener(e -> {
            try {
                String selectedOrg = (String) cbOrganization.getSelectedItem();
                int orgId = selectedOrg != null ?
                        Integer.parseInt(selectedOrg.split(" - ")[0]) : 0;

                Project project = new Project(
                        txtName.getText(),
                        txtDescription.getText(),
                        txtStartDate.getText(),
                        txtEndDate.getText()
                );
                // Se seu modelo Project tem organizationId, adicione:
                // project.setOrganizationId(orgId);

                projectDAO.addProject(project);
                loadProjectData((JTable) ((JScrollPane) getContentPane().getComponent(0)).getViewport().getView());
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Projeto adicionado com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Erro ao adicionar projeto: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        panel.add(btnSave);
        panel.add(btnCancel);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void loadProjectData(JTable table) {
        try {
            List<Project> projects = projectDAO.getAllProjects();
            TaskDAO taskDAO = new TaskDAO();

            String[] columnNames = {"ID", "Nome", "Descrição", "Data Início", "Data Término", "Tarefas"};
            Object[][] data = new Object[projects.size()][6];

            for (int i = 0; i < projects.size(); i++) {
                Project project = projects.get(i);
                data[i][0] = project.getId();
                data[i][1] = project.getName();
                data[i][2] = project.getDescription();
                data[i][3] = project.getStartDate();
                data[i][4] = project.getEndDate();

                // Contar tarefas concluídas e totais
                List<Task> tasks = taskDAO.getTasksByProject(project.getId());
                int totalTasks = tasks.size();
                int completedTasks = (int) tasks.stream().filter(Task::isCompleted).count();
                data[i][5] = completedTasks + "/" + totalTasks + " tarefas";
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
            JOptionPane.showMessageDialog(this, "Erro ao carregar projetos: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showEditProjectDialog(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um projeto para editar",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int projectId = (int) table.getValueAt(selectedRow, 0);
        try {
            Project project = projectDAO.getProject(projectId);
            if (project == null) {
                JOptionPane.showMessageDialog(this, "Projeto não encontrado",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog dialog = new JDialog(this, "Editar Projeto", true);
            dialog.setSize(500, 400);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField txtName = new JTextField(project.getName());
            JTextArea txtDescription = new JTextArea(project.getDescription(), 3, 20);
            JScrollPane descriptionScroll = new JScrollPane(txtDescription);
            JTextField txtStartDate = new JTextField(project.getStartDate());
            JTextField txtEndDate = new JTextField(project.getEndDate());
            JComboBox<String> cbOrganization = new JComboBox<>();

            try {
                List<Organization> orgs = organizationDAO.getAllOrganizations();
                for (Organization org : orgs) {
                    cbOrganization.addItem(org.getId() + " - " + org.getName());
                    // Se você tiver organização no projeto:
                    // if (org.getId() == project.getOrganizationId()) {
                    //     cbOrganization.setSelectedItem(org.getId() + " - " + org.getName());
                    // }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(dialog, "Erro ao carregar organizações: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }

            panel.add(new JLabel("Nome:"));
            panel.add(txtName);
            panel.add(new JLabel("Descrição:"));
            panel.add(descriptionScroll);
            panel.add(new JLabel("Data Início (YYYY-MM-DD):"));
            panel.add(txtStartDate);
            panel.add(new JLabel("Data Término (YYYY-MM-DD):"));
            panel.add(txtEndDate);
            panel.add(new JLabel("Organização:"));
            panel.add(cbOrganization);

            JButton btnSave = new JButton("Salvar");
            JButton btnCancel = new JButton("Cancelar");

            btnSave.addActionListener(e -> {
                try {
                    String selectedOrg = (String) cbOrganization.getSelectedItem();
                    int orgId = selectedOrg != null ? 
                            Integer.parseInt(selectedOrg.split(" - ")[0]) : 0;

                    project.setName(txtName.getText());
                    project.setDescription(txtDescription.getText());
                    project.setStartDate(txtStartDate.getText());
                    project.setEndDate(txtEndDate.getText());
                    // Se tiver organização no projeto:
                    // project.setOrganizationId(orgId);

                    projectDAO.updateProject(project);
                    loadProjectData(table);
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Projeto atualizado com sucesso!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Erro ao atualizar projeto: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnCancel.addActionListener(e -> dialog.dispose());

            panel.add(btnSave);
            panel.add(btnCancel);

            dialog.add(panel);
            dialog.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar projeto: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProject(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um projeto para excluir",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int projectId = (int) table.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir este projeto?", "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                projectDAO.deleteProject(projectId);
                loadProjectData(table);
                JOptionPane.showMessageDialog(this, "Projeto excluído com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir projeto: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
