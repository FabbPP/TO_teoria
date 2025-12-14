package com.pokemon.singleton;

import com.pokemon.singleton.ControlJuego;
import com.pokemon.modelo.GameState;

/**
 * Demostración simplificada del patrón Singleton
 * Simula cómo distintos módulos del juego acceden al mismo ControlJuego
 */
public class DemoSingletonSimple {
    
    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("   DEMOSTRACIÓN DEL PATRÓN SINGLETON - Sistema Pokemon");
        System.out.println("=".repeat(70) + "\n");
        
        // ========== PARTE 1: Verificación de Singleton ==========
        System.out.println("PARTE 1: Verificando que solo existe una instancia\n");
        
        // Módulo 1: Sistema de Jugador
        ControlJuego moduloJugador = ControlJuego.getInstance();
        System.out.println("✓ Módulo Jugador obtuvo instancia: " + moduloJugador.hashCode());
        
        // Módulo 2: Sistema de Enemigos
        ControlJuego moduloEnemigos = ControlJuego.getInstance();
        System.out.println("✓ Módulo Enemigos obtuvo instancia: " + moduloEnemigos.hashCode());
        
        // Módulo 3: Sistema de Interfaz
        ControlJuego moduloInterfaz = ControlJuego.getInstance();
        System.out.println("✓ Módulo Interfaz obtuvo instancia: " + moduloInterfaz.hashCode());
        
        // Módulo 4: Sistema GameState
        ControlJuego moduloGameState = ControlJuego.getInstance();
        System.out.println("✓ Módulo GameState obtuvo instancia: " + moduloGameState.hashCode());
        
        // Verificar que son la misma instancia
        System.out.println("\n¿Son todas la misma instancia?");
        System.out.println("   moduloJugador == moduloEnemigos: " + (moduloJugador == moduloEnemigos));
        System.out.println("   moduloJugador == moduloInterfaz: " + (moduloJugador == moduloInterfaz));
        System.out.println("   moduloJugador == moduloGameState: " + (moduloJugador == moduloGameState));
        System.out.println("   ✓ ¡Todas apuntan a la MISMA instancia Singleton!");
        
        // ========== PARTE 2: Simulación de Juego ==========
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PARTE 2: Simulación del juego con distintos módulos\n");
        
        // Estado inicial
        System.out.println("--- Estado Inicial ---");
        System.out.println("Nivel: " + moduloJugador.getNivelActual());
        System.out.println("Puntaje: " + moduloJugador.getPuntaje());
        System.out.println("Vidas: " + moduloJugador.getVidas());
        
        // Módulo Jugador: Captura Pokemon
        System.out.println("\n[MÓDULO JUGADOR] Capturando Pokemon...");
        moduloJugador.registrarCaptura();  // Pikachu
        moduloJugador.registrarCaptura();  // Charmander
        moduloJugador.registrarCaptura();  // Bulbasaur
        
        // Módulo Combate: Primera batalla
        System.out.println("\n[MÓDULO COMBATE] Batalla 1 iniciada...");
        moduloEnemigos.registrarDanoInfligido(35);
        System.out.println("   → Daño infligido: 35");
        moduloEnemigos.registrarAtaqueCritico();
        moduloEnemigos.registrarDanoRecibido(20);
        System.out.println("   → Daño recibido: 20");
        moduloEnemigos.registrarVictoria();
        
        // Módulo Interfaz: Consultar estado
        System.out.println("\n[MÓDULO INTERFAZ] Consultando estado actual...");
        System.out.println("   Nivel: " + moduloInterfaz.getNivelActual());
        System.out.println("   Puntaje: " + moduloInterfaz.getPuntaje());
        System.out.println("   Victorias: " + moduloInterfaz.getCombatesGanados());
        System.out.println("   Pokemon capturados: " + moduloInterfaz.getPokemonCapturados());
        
        // Módulo Combate: Segunda batalla
        System.out.println("\n[MÓDULO COMBATE] Batalla 2 iniciada...");
        moduloEnemigos.registrarDanoInfligido(50);
        moduloEnemigos.registrarAtaqueCritico();
        moduloEnemigos.registrarDanoRecibido(28);
        moduloEnemigos.registrarUsoItem();
        moduloEnemigos.registrarVictoria();
        
        // Módulo GameState: Verificar desde GameState
        System.out.println("\n[MÓDULO GAMESTATE] Creando GameState...");
        GameState gameState = new GameState();
        ControlJuego controlDesdeGameState = gameState.getControlJuego();
        System.out.println("   ¿GameState tiene la misma instancia? " + 
                          (moduloJugador == controlDesdeGameState));
        System.out.println("   Puntaje desde GameState: " + controlDesdeGameState.getPuntaje());
        
        // ========== PARTE 3: Estado Final ==========
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PARTE 3: Estado final del juego\n");
        
        moduloJugador.mostrarEstadoCompleto();
        
        // ========== PARTE 4: Verificación Final ==========
        System.out.println("=".repeat(70));
        System.out.println("VERIFICACIÓN FINAL\n");
        
        System.out.println("Consultando desde todos los módulos:");
        System.out.println("   Módulo Jugador   - Puntaje: " + moduloJugador.getPuntaje() + 
                         ", Nivel: " + moduloJugador.getNivelActual());
        System.out.println("   Módulo Enemigos  - Puntaje: " + moduloEnemigos.getPuntaje() + 
                         ", Nivel: " + moduloEnemigos.getNivelActual());
        System.out.println("   Módulo Interfaz  - Puntaje: " + moduloInterfaz.getPuntaje() + 
                         ", Nivel: " + moduloInterfaz.getNivelActual());
        System.out.println("   Módulo GameState - Puntaje: " + controlDesdeGameState.getPuntaje() + 
                         ", Nivel: " + controlDesdeGameState.getNivelActual());
        
        System.out.println("\n✓ CONCLUSIÓN:");
        System.out.println("   Todos los módulos consultan y modifican el MISMO estado");
        System.out.println("   porque comparten la MISMA INSTANCIA del Singleton ControlJuego.");
        System.out.println("   ¡El patrón Singleton garantiza un estado global consistente!");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("   DEMOSTRACIÓN COMPLETADA EXITOSAMENTE");
        System.out.println("=".repeat(70) + "\n");
    }
}
