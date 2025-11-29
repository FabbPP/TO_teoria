package pokemon.ui;

import pokemon.Pokemon;
import pokemon.Entrenador;
import pokemon.interfaces.IMostrador;
import java.util.Scanner;
import static pokemon.Global.*;

/**
 * DIP: UI implementa la interfaz IMostrador
 * Clase responsable de la interacción con el usuario
 */
public class UI implements IMostrador {
    private Scanner scanner;
    
    public UI() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Implementación de IMostrador (DIP)
     */
    @Override
    public void mostrarMensaje(String mensaje, String color) {
        if (color.isEmpty()) {
            System.out.println(mensaje);
        } else {
            System.out.println(color + mensaje + RESET);
        }
    }
    
    @Override
    public void mostrarMensajeCombate(String mensaje) {
        System.out.println(mensaje);
    }
    
    /**
     * Métodos para mostrar información
     */
    public void mostrarEstado(Pokemon jugador, Pokemon rival) {
        System.out.println(BLUE + "\n--- Estado del Combate ---" + RESET);
        System.out.print("Tu ");
        jugador.mostrar();
        System.out.print(" vs. Rival's ");
        rival.mostrar();
        System.out.println(RESET);
    }
    
    public void mostrarEquipo(Entrenador entrenador) {
        System.out.println(YELLOW + entrenador.getNombre() + " - Equipo:" + RESET);
        for (int i = 0; i < entrenador.getEquipo().size(); i++) {
            System.out.print((i + 1) + ". ");
            entrenador.getEquipo().get(i).mostrar();
            System.out.println(entrenador.getEquipo().get(i).estaVivo() ? "" : " (Debilitado)");
        }
    }
    
    public void mostrarBolsa(Entrenador entrenador) {
        System.out.println(MAGENTA + "Bolsa de " + entrenador.getNombre() + ":" + RESET);
        for (int i = 0; i < entrenador.getBolsa().size(); i++) {
            System.out.print((i + 1) + ". ");
            entrenador.getBolsa().get(i).mostrar();
            System.out.println();
        }
    }
    
    /**
     * Métodos para obtener entrada del usuario
     */
    public int obtenerEleccion(String mensaje, int maxOpciones) {
        int eleccion;
        while (true) {
            System.out.print(mensaje);
            try {
                eleccion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                if (eleccion >= 1 && eleccion <= maxOpciones) {
                    return eleccion;
                } else {
                    System.out.println("Opción inválida. Intenta de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Intenta de nuevo.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
    }
    
    public int obtenerOpcionLucha(Pokemon pokemon) {
        System.out.println("Elige un movimiento:");
        for (int i = 0; i < pokemon.getMovimientos().size(); i++) {
            System.out.println((i + 1) + ". " + pokemon.getMovimiento(i).getNombre());
        }
        return obtenerEleccion("Elige un movimiento: ", pokemon.getMovimientos().size());
    }
    
    public void cerrar() {
        scanner.close();
    }
}