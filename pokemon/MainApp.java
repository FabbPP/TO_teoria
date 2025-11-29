package pokemon;

import pokemon.modelo.GameState;
import pokemon.controlador.GameController;
import pokemon.vista.GameFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

//Clase principal que inicia la aplicación con patrón MVC

public class MainApp {
    public static void main(String[] args) {
        // Configurar Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Iniciar en el Event Dispatch Thread de Swing
        SwingUtilities.invokeLater(() -> {
            // Crear el MODELO
            GameState modelo = new GameState();
            
            // Crear el CONTROLADOR
            GameController controlador = new GameController(modelo);
            
            // Crear la VISTA
            GameFrame frame = new GameFrame(controlador);
            frame.setVisible(true);
            
            System.out.println("=== POKEMON BATTLE SYSTEM - MVC ===");
            System.out.println("Juego iniciado correctamente");
        });
    }
}