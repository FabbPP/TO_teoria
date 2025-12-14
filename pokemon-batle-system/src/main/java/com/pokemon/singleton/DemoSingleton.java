package com.pokemon.singleton;

import com.pokemon.singleton.ControlJuego;
import com.pokemon.modelo.GameState;
import com.pokemon.Pokemon;
import com.pokemon.tipos.*;
import java.util.Arrays;


public class DemoSingleton {
    
    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("DEMOSTRACIÓN DEL PATRÓN SINGLETON - Sistema Pokemon");
        System.out.println("=".repeat(70) + "\n");
        
        System.out.println("PARTE 1: Verificando que es Singleton\n");
        
        // Módulo 1: Jugador
        ControlJuego control1 = ControlJuego.getInstance();
        System.out.println("Módulo Jugador obtuvo instancia: " + control1);
        
        // Módulo 2: Enemigos
        ControlJuego control2 = ControlJuego.getInstance();
        System.out.println("Módulo Enemigos obtuvo instancia: " + control2);
        
        // Módulo 3: Interfaz
        ControlJuego control3 = ControlJuego.getInstance();
        System.out.println("Módulo Interfaz obtuvo instancia: " + control3);
        
        // Módulo 4: GameState
        ControlJuego control4 = ControlJuego.getInstance();
        System.out.println("Módulo GameState obtuvo instancia: " + control4);
        
        // Verificar que son la misma instancia
        System.out.println("\n¿Son la misma instancia?");
        System.out.println("   control1 == control2: " + (control1 == control2));
        System.out.println("   control1 == control3: " + (control1 == control3));
        System.out.println("   control1 == control4: " + (control1 == control4));
        System.out.println("   Todas son iguales: " + 
            (control1 == control2 && control2 == control3 && control3 == control4));
        
        pausa();
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PARTE 2: Simulación de un juego completo\n");
        
        // Estado inicial
        System.out.println("--- Estado Inicial ---");
        control1.mostrarEstadoCompleto();
        
        pausa();
        
        System.out.println("\nMÓDULO JUGADOR: Capturando Pokemon...\n");
        control1.registrarCaptura();  // Pikachu
        control1.registrarCaptura();  // Charmander
        control1.registrarCaptura();  // Bulbasaur
        
        pausa();
        
        System.out.println("\nMÓDULO COMBATE: Iniciando combate...\n");
        
        // Turno 1
        System.out.println("--- Turno 1 ---");
        control2.registrarDanoInfligido(35);
        System.out.println("   Pikachu causa 35 de daño");
        control2.registrarDanoRecibido(20);
        System.out.println("   Recibe 20 de daño\n");
        
        // Turno 2
        System.out.println("--- Turno 2 ---");
        control2.registrarDanoInfligido(42);
        System.out.println("   Pikachu causa 42 de daño");
        control2.registrarAtaqueCritico();
        System.out.println("   ¡Ataque crítico!");
        control2.registrarDanoRecibido(15);
        System.out.println("   Recibe 15 de daño\n");
        
        // Turno 3 - Usa item
        System.out.println("--- Turno 3 ---");
        control2.registrarUsoItem();
        System.out.println("   Usa poción para curar");
        control2.registrarDanoRecibido(25);
        System.out.println("   Recibe 25 de daño\n");
        
        // Victoria
        System.out.println("--- Resultado ---");
        control2.registrarVictoria();
        
        pausa();
        
        System.out.println("\n🖼️  MÓDULO INTERFAZ: Consultando estado...\n");
        System.out.println("Estado actual del juego:");
        control3.mostrarEstadoCompleto();
        
        pausa();
        System.out.println("\n⚔️ MÓDULO COMBATE: Segunda batalla...\n");
        
        System.out.println("--- Turno 1 ---");
        control2.registrarDanoInfligido(50);
        control2.registrarAtaqueCritico();
        control2.registrarDanoRecibido(30);
        
        System.out.println("--- Turno 2 ---");
        control2.registrarDanoInfligido(45);
        control2.registrarDanoRecibido(28);
        
        System.out.println("--- Resultado ---");
        control2.registrarVictoria();
        
        pausa();
        
        System.out.println("\nMÓDULO COMBATE: Tercera batalla...\n");
        
        System.out.println("--- Turno 1 ---");
        control2.registrarDanoInfligido(25);
        control2.registrarDanoRecibido(45);
        
        System.out.println("--- Turno 2 ---");
        control2.registrarDanoInfligido(30);
        control2.registrarDanoRecibido(50);
        
        System.out.println("--- Resultado ---");
        control2.registrarDerrota();
        
        pausa();
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ESTADO FINAL DEL JUEGO\n");
        control1.mostrarEstadoCompleto();
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("VERIFICACIÓN FINAL\n");
        
        System.out.println("Consultando desde diferentes módulos:");
        System.out.println("   Módulo Jugador   - Nivel: " + control1.getNivelActual() + 
                         ", Puntaje: " + control1.getPuntaje());
        System.out.println("   Módulo Enemigos  - Nivel: " + control2.getNivelActual() + 
                         ", Puntaje: " + control2.getPuntaje());
        System.out.println("   Módulo Interfaz  - Nivel: " + control3.getNivelActual() + 
                         ", Puntaje: " + control3.getPuntaje());
        System.out.println("   Módulo GameState - Nivel: " + control4.getNivelActual() + 
                         ", Puntaje: " + control4.getPuntaje());
        
        System.out.println("\nTodos los módulos leen el MISMO estado porque usan");
        System.out.println("   la MISMA INSTANCIA del Singleton ControlJuego");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INTEGRACIÓN CON GAMESTATE\n");
        
        GameState gameState = new GameState();
        ControlJuego controlDesdeGameState = gameState.getControlJuego();
        
        System.out.println("GameState obtuvo instancia: " + controlDesdeGameState);
        System.out.println("¿Es la misma que obtuvimos antes? " + (control1 == controlDesdeGameState));
        
        System.out.println("\nDatos desde GameState:");
        System.out.println("   Nivel: " + controlDesdeGameState.getNivelActual());
        System.out.println("   Puntaje: " + controlDesdeGameState.getPuntaje());
        System.out.println("   Vidas: " + controlDesdeGameState.getVidas());
        
        
    }
    
    private static void pausa() {
        System.out.println("\n[Presiona Enter para continuar...]");
        try {
            System.in.read();
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (Exception e) {
            // Ignorar
        }
    }
}