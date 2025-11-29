package pokemon;

import pokemon.ui.UI;
import pokemon.movimientos.*;
import pokemon.tipos.*;
import pokemon.calculadores.*;
import pokemon.interfaces.*;
import java.util.Arrays;
import java.util.Random;
import static pokemon.Global.*;


/**
 * Clase principal que maneja la lógica del juego de combate Pokemon
 */
public class Juego {
    private Entrenador jugador;
    private Entrenador rival;
    private UI ui;
    private Pokemon pokemonActivoJugador;
    private Pokemon pokemonActivoRival;
    private Random random;
    
    public Juego() {
        this.random = new Random();
        this.ui = new UI();
        
        this.jugador = new Entrenador("Ash");
        this.rival = new Entrenador("Lance");
        
        // DIP: Crear calculadores (abstracciones)
        ICalculadorDano calculadorDano = new CalculadorDanoBasico();
        ICalculadorMultiplicador calculadorMult = new CalculadorMultiplicadorFuerza();
        
        // OCP: Crear tipos - fácilmente extensible
        TipoElemento tipoNormal = new TipoNormal();
        TipoElemento tipoFuego = new TipoFuego();
        TipoElemento tipoAgua = new TipoAgua();
        TipoElemento tipoElectrico = new TipoElectrico();
        TipoElemento tipoPlanta = new TipoPlanta();
        TipoElemento tipoVeneno = new TipoVeneno();
        
        // DIP: IMostrador en lugar de UI concreta
        IMostrador mostrador = ui;
        
        inicializarEquipoJugador(tipoNormal, tipoFuego, tipoAgua, tipoElectrico, 
                                tipoPlanta, tipoVeneno, mostrador, 
                                calculadorDano, calculadorMult);
        
        inicializarEquipoRival(tipoNormal, tipoFuego, tipoAgua, mostrador, 
                              calculadorDano, calculadorMult);
        
        // Agregar items
        jugador.agregarItem(new Item("Pocion", 30, 3));
        rival.agregarItem(new Item("Pocion", 30, 3));
    }
    
    private void inicializarEquipoJugador(TipoElemento tipoNormal, TipoElemento tipoFuego,
                                         TipoElemento tipoAgua, TipoElemento tipoElectrico,
                                         TipoElemento tipoPlanta, TipoElemento tipoVeneno,
                                         IMostrador mostrador, ICalculadorDano calcDano,
                                         ICalculadorMultiplicador calcMult) {
        // Pikachu
        Pokemon pikachu = new Pokemon("Pikachu", 
            Arrays.asList(tipoElectrico), 80, 25, 5);
        pikachu.agregarMovimiento(
            new Movimiento("Impactrueno", tipoElectrico, 30, mostrador, calcDano, calcMult)
        );
        pikachu.agregarMovimiento(
            new Movimiento("Placaje", tipoNormal, 10, mostrador, calcDano, calcMult)
        );
        pikachu.agregarMovimiento(
            new MovimientoMultiGolpe("Doble Bofeton", tipoNormal, 8, 2, 
                                    mostrador, calcDano, calcMult)
        );
        jugador.agregarPokemon(pikachu);
        
        // Charmander
        Pokemon charmander = new Pokemon("Charmander",
            Arrays.asList(tipoFuego), 95, 22, 6);
        charmander.agregarMovimiento(
            new Movimiento("Llamarada", tipoFuego, 40, mostrador, calcDano, calcMult)
        );
        charmander.agregarMovimiento(
            new Movimiento("Arañazo", tipoNormal, 10, mostrador, calcDano, calcMult)
        );
        charmander.agregarMovimiento(
            new MovimientoCuracion("Recuperacion", tipoNormal, 0, 20, mostrador)
        );
        jugador.agregarPokemon(charmander);
        
        // Bulbasaur
        Pokemon bulbasaur = new Pokemon("Bulbasaur",
            Arrays.asList(tipoPlanta, tipoVeneno), 100, 18, 8);
        bulbasaur.agregarMovimiento(
            new Movimiento("Latigo Cepa", tipoPlanta, 25, mostrador, calcDano, calcMult)
        );
        bulbasaur.agregarMovimiento(
            new MovimientoEstado("Bomba Lodo", tipoVeneno, 30, mostrador, calcDano, calcMult)
        );
        jugador.agregarPokemon(bulbasaur);
    }
    
    private void inicializarEquipoRival(TipoElemento tipoNormal, TipoElemento tipoFuego,
                                       TipoElemento tipoAgua, IMostrador mostrador,
                                       ICalculadorDano calcDano, ICalculadorMultiplicador calcMult) {
        // Gyarados
        Pokemon gyarados = new Pokemon("Gyarados",
            Arrays.asList(tipoAgua), 105, 21, 7);
        gyarados.agregarMovimiento(
            new Movimiento("Cascada", tipoAgua, 30, mostrador, calcDano, calcMult)
        );
        gyarados.agregarMovimiento(
            new Movimiento("Mordisco", tipoNormal, 20, mostrador, calcDano, calcMult)
        );
        rival.agregarPokemon(gyarados);
        
        // Dragonite
        Pokemon dragonite = new Pokemon("Dragonite",
            Arrays.asList(tipoNormal), 110, 24, 9);
        dragonite.agregarMovimiento(
            new Movimiento("Puño Fuego", tipoFuego, 30, mostrador, calcDano, calcMult)
        );
        dragonite.agregarMovimiento(
            new Movimiento("Placaje", tipoNormal, 20, mostrador, calcDano, calcMult)
        );
        rival.agregarPokemon(dragonite);
        
        // Onix
        Pokemon onix = new Pokemon("Onix",
            Arrays.asList(tipoNormal), 120, 15, 12);
        onix.agregarMovimiento(
            new Movimiento("Placaje", tipoNormal, 25, mostrador, calcDano, calcMult)
        );
        rival.agregarPokemon(onix);
    }
    
    private void mostrarEstado() {
        ui.mostrarEstado(pokemonActivoJugador, pokemonActivoRival);
    }
    
    private Pokemon elegirPokemon(Entrenador entrenador) {
        int idx;
        while (true) {
            ui.mostrarEquipo(entrenador);
            idx = ui.obtenerEleccion("Elige un Pokemon: ", entrenador.getEquipo().size());
            
            Pokemon elegido = entrenador.getEquipo().get(idx - 1);
            if (elegido.estaVivo()) {
                return elegido;
            } else {
                ui.mostrarMensaje("Selección inválida. Intenta de nuevo.", "");
            }
        }
    }
    
    private void accionRival() {
        if (pokemonActivoRival.getVida() <= pokemonActivoRival.getVidaMax() / 3) {
            for (Item item : rival.getBolsa()) {
                if (item.disponible()) {
                    item.usar(pokemonActivoRival);
                    ui.mostrarMensajeCombate(rival.getNombre() + " usa una " + item.getNombre() + "!");
                    return;
                }
            }
        }
        
        ui.mostrarMensajeCombate(rival.getNombre() + " ataca...");
        int numMovimientos = pokemonActivoRival.getMovimientos().size();
        int movimientoElegido = random.nextInt(numMovimientos);
        pokemonActivoRival.atacar(pokemonActivoJugador, 
                                  pokemonActivoRival.getMovimiento(movimientoElegido));
    }
    
    public void iniciarCombate() {
        ui.mostrarMensaje("=== Combate Pokemon 3 vs 3! ===", GREEN);
        ui.mostrarEquipo(rival);
        
        ui.mostrarMensaje("Elige tu Pokemon inicial:", CYAN);
        pokemonActivoJugador = elegirPokemon(jugador);
        ui.mostrarMensaje("¡Adelante, " + pokemonActivoJugador.getNombre() + "!", CYAN);
        
        // Elegir Pokemon rival aleatorio vivo
        pokemonActivoRival = rival.getEquipo().get(random.nextInt(rival.getEquipo().size()));
        while (!pokemonActivoRival.estaVivo()) {
            pokemonActivoRival = rival.getEquipo().get(random.nextInt(rival.getEquipo().size()));
        }
        ui.mostrarMensaje(rival.getNombre() + " envía a " + pokemonActivoRival.getNombre() + "!", CYAN);
        
        int turno = 1;
        while (jugador.tienePokemonVivo() && rival.tienePokemonVivo()) {
            ui.mostrarMensaje("\n--- Turno " + turno++ + " ---", BLUE);
            mostrarEstado();
            
            int accion = ui.obtenerEleccion("Accion: (1)Luchar (2)Bolsa (3)Pokemon -> ", 3);
            
            switch (accion) {
                case 1: // Luchar
                    int eleccionMovimiento = ui.obtenerOpcionLucha(pokemonActivoJugador);
                    if (eleccionMovimiento > 0 && eleccionMovimiento <= pokemonActivoJugador.getMovimientos().size()) {
                        pokemonActivoJugador.atacar(pokemonActivoRival, 
                            pokemonActivoJugador.getMovimiento(eleccionMovimiento - 1));
                    } else {
                        ui.mostrarMensaje("Selección de movimiento inválida. Pierdes el turno.", "");
                    }
                    break;
                    
                case 2: // Bolsa
                    ui.mostrarBolsa(jugador);
                    int op = ui.obtenerEleccion("Elige item: ", jugador.getBolsa().size());
                    if (op > 0 && op <= jugador.getBolsa().size()) {
                        Item itemElegido = jugador.getBolsa().get(op - 1);
                        itemElegido.usar(pokemonActivoJugador);
                    }
                    break;
                    
                case 3: // Cambiar Pokemon
                    pokemonActivoJugador = elegirPokemon(jugador);
                    break;
                    
                default:
                    ui.mostrarMensaje("Acción inválida.", "");
                    break;
            }
            
            // Turno del rival
            if (rival.tienePokemonVivo() && pokemonActivoRival.estaVivo()) {
                accionRival();
            }
            
            // Verificar si el Pokemon rival fue derrotado
            if (!pokemonActivoRival.estaVivo()) {
                ui.mostrarMensaje(pokemonActivoRival.getNombre() + " se ha debilitado!", RED);
                if (rival.tienePokemonVivo()) {
                    pokemonActivoRival = rival.getEquipo().get(random.nextInt(rival.getEquipo().size()));
                    while (!pokemonActivoRival.estaVivo()) {
                        pokemonActivoRival = rival.getEquipo().get(random.nextInt(rival.getEquipo().size()));
                    }
                    ui.mostrarMensaje(rival.getNombre() + " envía a " + 
                                    pokemonActivoRival.getNombre() + "!", CYAN);
                }
            }
            
            // Verificar si el Pokemon del jugador fue derrotado
            if (!pokemonActivoJugador.estaVivo()) {
                ui.mostrarMensaje(pokemonActivoJugador.getNombre() + " se ha debilitado!", RED);
                if (jugador.tienePokemonVivo()) {
                    ui.mostrarMensaje("Debes elegir un nuevo Pokemon.", "");
                    pokemonActivoJugador = elegirPokemon(jugador);
                }
            }
        }
        
        // Resultado final
        if (jugador.tienePokemonVivo()) {
            ui.mostrarMensaje("¡Has ganado el combate!", GREEN);
        } else {
            ui.mostrarMensaje("¡Has perdido el combate!", RED);
        }
    }
}