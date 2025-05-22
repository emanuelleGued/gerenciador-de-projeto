import ui.MainMenu;
import util.DatabaseConnection;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Inicializar o banco de dados
        DatabaseConnection.initializeDatabase();

        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Criar e exibir a janela principal
        SwingUtilities.invokeLater(() -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
        });
    }
}
