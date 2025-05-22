package ui;

import dao.AddressDAO;
import dao.OrganizationDAO;
import model.Address;
import model.Organization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class OrganizationUI extends JFrame {
    private OrganizationDAO organizationDAO;
    private AddressDAO addressDAO;

    public OrganizationUI() {
        organizationDAO = new OrganizationDAO();
        addressDAO = new AddressDAO();

        setTitle("Gerenciamento de Organizações");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabela de organizações
        JTable orgTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(orgTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton btnAdd = new JButton("Adicionar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Excluir");
        JButton btnBack = new JButton("Voltar");

        btnAdd.addActionListener(e -> showAddOrganizationDialog());
        btnEdit.addActionListener(e -> showEditOrganizationDialog(orgTable));
        btnDelete.addActionListener(e -> deleteOrganization(orgTable));
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
        loadOrganizationData(orgTable);

        add(panel);
    }

    private void loadOrganizationData(JTable table) {
        try {
            List<Organization> orgs = organizationDAO.getAllOrganizations();
            String[] columnNames = {"ID", "Nome", "Endereço"};
            Object[][] data = new Object[orgs.size()][3];

            for (int i = 0; i < orgs.size(); i++) {
                Organization org = orgs.get(i);
                data[i][0] = org.getId();
                data[i][1] = org.getName();

                try {
                    Address address = addressDAO.getAddress(org.getAddressId());
                    data[i][2] = address != null ?
                            address.getStreet() + ", " + address.getCity() : "N/A";
                } catch (SQLException e) {
                    data[i][2] = "N/A";
                }
            }

            table.setModel(new javax.swing.table.DefaultTableModel(
                    data, columnNames
            ));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar organizações: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddOrganizationDialog() {
        JDialog dialog = new JDialog(this, "Adicionar Organização", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Painel de informações básicas
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtName = new JTextField();
        JComboBox<String> cbAddress = new JComboBox<>();

        try {
            List<Address> addresses = addressDAO.getAllAddresses();
            for (Address address : addresses) {
                cbAddress.addItem(address.getId() + " - " + address.getStreet() + ", " + address.getCity());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(dialog, "Erro ao carregar endereços: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }

        JButton btnNewAddress = new JButton("Novo Endereço");
        btnNewAddress.addActionListener(e -> {
            showAddAddressDialog();
            dialog.dispose();
        });

        infoPanel.add(new JLabel("Nome:"));
        infoPanel.add(txtName);
        infoPanel.add(new JLabel("Endereço:"));
        infoPanel.add(cbAddress);
        infoPanel.add(new JLabel(""));
        infoPanel.add(btnNewAddress);

        tabbedPane.addTab("Informações", infoPanel);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        JButton btnSave = new JButton("Salvar");
        JButton btnCancel = new JButton("Cancelar");

        btnSave.addActionListener(e -> {
            try {
                String selectedAddress = (String) cbAddress.getSelectedItem();
                int addressId = selectedAddress != null ?
                        Integer.parseInt(selectedAddress.split(" - ")[0]) : 0;

                Organization org = new Organization(
                        txtName.getText(),
                        addressId
                );

                organizationDAO.addOrganization(org);
                loadOrganizationData((JTable) ((JScrollPane) getContentPane().getComponent(0)).getViewport().getView());
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Organização adicionada com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Erro ao adicionar organização: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        dialog.add(tabbedPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showEditOrganizationDialog(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma organização para editar",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int orgId = (int) table.getValueAt(selectedRow, 0);
        try {
            Organization org = organizationDAO.getOrganization(orgId);
            if (org == null) {
                JOptionPane.showMessageDialog(this, "Organização não encontrada",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog dialog = new JDialog(this, "Editar Organização", true);
            dialog.setSize(500, 400);
            dialog.setLocationRelativeTo(this);

            JTabbedPane tabbedPane = new JTabbedPane();

            // Painel de informações básicas
            JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField txtName = new JTextField(org.getName());
            JComboBox<String> cbAddress = new JComboBox<>();

            try {
                List<Address> addresses = addressDAO.getAllAddresses();
                for (Address address : addresses) {
                    cbAddress.addItem(address.getId() + " - " + address.getStreet() + ", " + address.getCity());
                    if (address.getId() == org.getAddressId()) {
                        cbAddress.setSelectedItem(address.getId() + " - " + address.getStreet() + ", " + address.getCity());
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(dialog, "Erro ao carregar endereços: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }

            JButton btnNewAddress = new JButton("Novo Endereço");
            btnNewAddress.addActionListener(e -> {
                showAddAddressDialog();
                dialog.dispose();
            });

            infoPanel.add(new JLabel("Nome:"));
            infoPanel.add(txtName);
            infoPanel.add(new JLabel("Endereço:"));
            infoPanel.add(cbAddress);
            infoPanel.add(new JLabel(""));
            infoPanel.add(btnNewAddress);

            tabbedPane.addTab("Informações", infoPanel);

            // Painel de botões
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

            JButton btnSave = new JButton("Salvar");
            JButton btnCancel = new JButton("Cancelar");

            btnSave.addActionListener(e -> {
                try {
                    String selectedAddress = (String) cbAddress.getSelectedItem();
                    int addressId = selectedAddress != null ?
                            Integer.parseInt(selectedAddress.split(" - ")[0]) : 0;

                    org.setName(txtName.getText());
                    org.setAddressId(addressId);

                    organizationDAO.updateOrganization(org);
                    loadOrganizationData(table);
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Organização atualizada com sucesso!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Erro ao atualizar organização: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnCancel.addActionListener(e -> dialog.dispose());

            buttonPanel.add(btnSave);
            buttonPanel.add(btnCancel);

            dialog.add(tabbedPane, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
            dialog.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar organização: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteOrganization(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma organização para excluir",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int orgId = (int) table.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir esta organização?", "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                organizationDAO.deleteOrganization(orgId);
                loadOrganizationData(table);
                JOptionPane.showMessageDialog(this, "Organização excluída com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir organização: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showAddAddressDialog() {
        JDialog dialog = new JDialog(this, "Adicionar Endereço", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtStreet = new JTextField();
        JTextField txtCity = new JTextField();
        JTextField txtState = new JTextField();
        JTextField txtZipCode = new JTextField();
        JTextField txtCountry = new JTextField();

        panel.add(new JLabel("Rua:"));
        panel.add(txtStreet);
        panel.add(new JLabel("Cidade:"));
        panel.add(txtCity);
        panel.add(new JLabel("Estado:"));
        panel.add(txtState);
        panel.add(new JLabel("CEP:"));
        panel.add(txtZipCode);
        panel.add(new JLabel("País:"));
        panel.add(txtCountry);

        JButton btnSave = new JButton("Salvar");
        JButton btnCancel = new JButton("Cancelar");

        btnSave.addActionListener(e -> {
            try {
                Address address = new Address(
                        txtStreet.getText(),
                        txtCity.getText(),
                        txtState.getText(),
                        txtZipCode.getText(),
                        txtCountry.getText()
                );

                addressDAO.addAddress(address);
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Endereço adicionado com sucesso!");
                // Atualiza a lista de endereços na organização
                new OrganizationUI().setVisible(true);
                dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Erro ao adicionar endereço: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        panel.add(btnSave);
        panel.add(btnCancel);

        dialog.add(panel);
        dialog.setVisible(true);
    }
}