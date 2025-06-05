package ui;

import dao.ProjectDAO;
import dao.TaskDAO;
import model.Project;
import model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProjectTasksUI extends JFrame {
    private TaskDAO taskDAO;
    private ProjectDAO projectDAO;
    private int projectId;
    private JTable taskTable;

    public ProjectTasksUI(int projectId) {
        this.projectId = projectId;
        taskDAO = new TaskDAO();
        projectDAO = new ProjectDAO();

        setTitle("Gerenciamento de Tarefas do Projeto");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título com nome do projeto
        try {
            Project project = projectDAO.getProject(projectId);
            JLabel titleLabel = new JLabel("Tarefas do Projeto: " + project.getName());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(titleLabel, BorderLayout.NORTH);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tabela de tarefas
        taskTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(taskTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));

        JButton btnAdd = new JButton("Adicionar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Excluir");
        JButton btnToggleComplete = new JButton("Marcar/Desmarcar");
        JButton btnBack = new JButton("Voltar");

        btnAdd.addActionListener(e -> showAddTaskDialog());
        btnEdit.addActionListener(e -> showEditTaskDialog());
        btnDelete.addActionListener(e -> deleteTask());
        btnToggleComplete.addActionListener(e -> toggleTaskCompletion());
        btnBack.addActionListener(e -> {
            new ProjectUI().setVisible(true);
            dispose();
        });

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnToggleComplete);
        buttonPanel.add(btnBack);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Carregar dados
        loadTaskData();

        add(panel);
    }

    private void loadTaskData() {
        try {
            List<Task> tasks = taskDAO.getTasksByProject(projectId);
            String[] columnNames = {"ID", "Título", "Descrição", "Prazo", "Concluída"};
            Object[][] data = new Object[tasks.size()][5];

            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                data[i][0] = task.getId();
                data[i][1] = task.getTitle();
                data[i][2] = task.getDescription();
                data[i][3] = task.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                data[i][4] = task.isCompleted() ? "Sim" : "Não";
            }

            taskTable.setModel(new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tarefas: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddTaskDialog() {
        JDialog dialog = new JDialog(this, "Adicionar Tarefa", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtTitle = new JTextField();
        JTextArea txtDescription = new JTextArea(3, 20);
        JScrollPane descriptionScroll = new JScrollPane(txtDescription);
        JTextField txtDueDate = new JTextField();

        panel.add(new JLabel("Título:"));
        panel.add(txtTitle);
        panel.add(new JLabel("Descrição:"));
        panel.add(descriptionScroll);
        panel.add(new JLabel("Prazo (AAAA-MM-DD):"));
        panel.add(txtDueDate);

        JButton btnSave = new JButton("Salvar");
        JButton btnCancel = new JButton("Cancelar");

        btnSave.addActionListener(e -> {
            try {
                Task task = new Task(
                        txtTitle.getText(),
                        txtDescription.getText(),
                        LocalDate.parse(txtDueDate.getText()),
                        false,
                        projectId
                );

                taskDAO.addTask(task);
                loadTaskData();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Tarefa adicionada com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Erro ao adicionar tarefa: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        panel.add(btnSave);
        panel.add(btnCancel);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showEditTaskDialog() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para editar",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int taskId = (int) taskTable.getValueAt(selectedRow, 0);
        try {
            Task task = taskDAO.getTask(taskId);
            if (task == null) {
                JOptionPane.showMessageDialog(this, "Tarefa não encontrada",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog dialog = new JDialog(this, "Editar Tarefa", true);
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField txtTitle = new JTextField(task.getTitle());
            JTextArea txtDescription = new JTextArea(task.getDescription(), 3, 20);
            JScrollPane descriptionScroll = new JScrollPane(txtDescription);
            JTextField txtDueDate = new JTextField(task.getDueDate().toString());

            panel.add(new JLabel("Título:"));
            panel.add(txtTitle);
            panel.add(new JLabel("Descrição:"));
            panel.add(descriptionScroll);
            panel.add(new JLabel("Prazo (AAAA-MM-DD):"));
            panel.add(txtDueDate);

            JButton btnSave = new JButton("Salvar");
            JButton btnCancel = new JButton("Cancelar");

            btnSave.addActionListener(e -> {
                try {
                    task.setTitle(txtTitle.getText());
                    task.setDescription(txtDescription.getText());
                    task.setDueDate(LocalDate.parse(txtDueDate.getText()));

                    taskDAO.updateTask(task);
                    loadTaskData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Tarefa atualizada com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Erro ao atualizar tarefa: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnCancel.addActionListener(e -> dialog.dispose());

            panel.add(btnSave);
            panel.add(btnCancel);

            dialog.add(panel);
            dialog.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tarefa: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTask() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para excluir",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int taskId = (int) taskTable.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir esta tarefa?", "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                taskDAO.deleteTask(taskId);
                loadTaskData();
                JOptionPane.showMessageDialog(this, "Tarefa excluída com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir tarefa: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void toggleTaskCompletion() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para marcar/desmarcar",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int taskId = (int) taskTable.getValueAt(selectedRow, 0);
        try {
            Task task = taskDAO.getTask(taskId);
            if (task == null) {
                JOptionPane.showMessageDialog(this, "Tarefa não encontrada",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            task.setCompleted(!task.isCompleted());
            taskDAO.updateTask(task);
            loadTaskData();
            JOptionPane.showMessageDialog(this, "Status da tarefa atualizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar tarefa: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}