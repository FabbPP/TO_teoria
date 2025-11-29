package pokemon.modelo;

import pokemon.*;
import pokemon.tipos.*;
import pokemon.movimientos.*;
import pokemon.calculadores.*;
import pokemon.interfaces.*;
import java.util.*;

/**
 * MODELO - Gestiona el estado completo del juego
 */
public class GameState {
    // Estados del juego
    public enum EstadoJuego {
        MENU_PRINCIPAL,
        SELECCION_POKEMON,
        SELECCION_INICIAL,
        COMBATE,
        VICTORIA,
        DERROTA
    }
    
    private EstadoJuego estadoActual;
    private Entrenador jugador;
    private Entrenador rival;
    private Pokemon pokemonActivoJugador;
    private Pokemon pokemonActivoRival;
    private List<Pokemon> pokemonDisponibles;
    private List<Pokemon> pokemonSeleccionados;
    
    // Calculadores
    private ICalculadorDano calculadorDano;
    private ICalculadorMultiplicador calculadorMultiplicador;
    
    // Tipos
    private Map<String, TipoElemento> tipos;
    
    // Log de combate
    private List<String> logCombate;
    private int turnoActual;
    
    // Observadores para notificar cambios
    private List<GameStateObserver> observadores;
    
    public GameState() {
        this.estadoActual = EstadoJuego.MENU_PRINCIPAL;
        this.pokemonSeleccionados = new ArrayList<>();
        this.logCombate = new ArrayList<>();
        this.observadores = new ArrayList<>();
        this.turnoActual = 1;
        
        inicializarCalculadores();
        inicializarTipos();
        inicializarPokemonDisponibles();
    }
    
    private void inicializarCalculadores() {
        this.calculadorDano = new CalculadorDanoBasico();
        this.calculadorMultiplicador = new CalculadorMultiplicadorFuerza();
    }
    
    private void inicializarTipos() {
        tipos = new HashMap<>();
        tipos.put("Normal", new TipoNormal());
        tipos.put("Fuego", new TipoFuego());
        tipos.put("Agua", new TipoAgua());
        tipos.put("Electrico", new TipoElectrico());
        tipos.put("Planta", new TipoPlanta());
        tipos.put("Veneno", new TipoVeneno());
    }
    
    private void inicializarPokemonDisponibles() {
        pokemonDisponibles = new ArrayList<>();
        // Pikachu
        Pokemon pikachu = new Pokemon("Pikachu", Arrays.asList(tipos.get("Electrico")), 80, 25, 5);
        pikachu.setSpritePath("/pokemon/resources/image/pikachu.png");
        agregarMovimientosPikachu(pikachu);
        pokemonDisponibles.add(pikachu);

        // Charmander
        Pokemon charmander = new Pokemon("Charmander", Arrays.asList(tipos.get("Fuego")), 95, 22, 6);
        charmander.setSpritePath("/pokemon/resources/image/charmander.png");
        agregarMovimientosCharmander(charmander);
        pokemonDisponibles.add(charmander);

        // Squirtle
        Pokemon squirtle = new Pokemon("Squirtle", Arrays.asList(tipos.get("Agua")), 90, 20, 7);
        squirtle.setSpritePath(null); // cuando tengas squirtle.png
        agregarMovimientosSquirtle(squirtle);
        pokemonDisponibles.add(squirtle);

        // Bulbasaur
        Pokemon bulbasaur = new Pokemon("Bulbasaur", Arrays.asList(tipos.get("Planta"), tipos.get("Veneno")), 100, 18, 8);
        bulbasaur.setSpritePath(null); // cuando tengas bulbasaur.png
        agregarMovimientosBulbasaur(bulbasaur);
        pokemonDisponibles.add(bulbasaur);

        // Eevee
        Pokemon eevee = new Pokemon("Eevee", Arrays.asList(tipos.get("Normal")), 85, 23, 6);
        eevee.setSpritePath(null); // cuando tengas eevee.png
        agregarMovimientosEevee(eevee);
        pokemonDisponibles.add(eevee);

        // Psyduck
        Pokemon psyduck = new Pokemon("Psyduck", Arrays.asList(tipos.get("Agua")), 88, 21, 5);
        psyduck.setSpritePath(null); // cuando tengas psyduck.png
        agregarMovimientosPsyduck(psyduck);
        pokemonDisponibles.add(psyduck);

        // Growlithe
        Pokemon growlithe = new Pokemon("Growlithe", Arrays.asList(tipos.get("Fuego")), 92, 24, 6);
        growlithe.setSpritePath(null); // cuando tengas growlithe.png
        agregarMovimientosGrowlithe(growlithe);
        pokemonDisponibles.add(growlithe);

        // Oddish
        Pokemon oddish = new Pokemon("Oddish", Arrays.asList(tipos.get("Planta"), tipos.get("Veneno")), 87, 19, 7);
        oddish.setSpritePath(null); // cuando tengas oddish.png
        agregarMovimientosOddish(oddish);
        pokemonDisponibles.add(oddish);

        // Jigglypuff
        Pokemon jigglypuff = new Pokemon("Jigglypuff", Arrays.asList(tipos.get("Normal")), 110, 17, 4);
        jigglypuff.setSpritePath(null); // cuando tengas jigglypuff.png
        agregarMovimientosJigglypuff(jigglypuff);
        pokemonDisponibles.add(jigglypuff);
    }

    
    // Métodos para agregar movimientos (simplificados - null para IMostrador)
    private void agregarMovimientosPikachu(Pokemon p) {
        p.agregarMovimiento(new Movimiento("Impactrueno", tipos.get("Electrico"), 30, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new Movimiento("Placaje", tipos.get("Normal"), 10, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new MovimientoMultiGolpe("Doble Bofeton", tipos.get("Normal"), 8, 2, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new MovimientoCuracion("Recuperacion", tipos.get("Normal"), 0, 20, null));
    }
    
    private void agregarMovimientosCharmander(Pokemon p) {
        p.agregarMovimiento(new Movimiento("Llamarada", tipos.get("Fuego"), 40, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new Movimiento("Arañazo", tipos.get("Normal"), 10, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new MovimientoCuracion("Recuperacion", tipos.get("Normal"), 0, 20, null));
    }
    
    private void agregarMovimientosSquirtle(Pokemon p) {
        p.agregarMovimiento(new Movimiento("Pistola Agua", tipos.get("Agua"), 35, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new Movimiento("Placaje", tipos.get("Normal"), 10, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new MovimientoCuracion("Recuperacion", tipos.get("Normal"), 0, 20, null));
    }
    
    private void agregarMovimientosBulbasaur(Pokemon p) {
        p.agregarMovimiento(new Movimiento("Latigo Cepa", tipos.get("Planta"), 25, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new MovimientoEstado("Bomba Lodo", tipos.get("Veneno"), 30, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new MovimientoCuracion("Sintesis", tipos.get("Planta"), 0, 25, null));
    }
    
    private void agregarMovimientosEevee(Pokemon p) {
        p.agregarMovimiento(new Movimiento("Tackle", tipos.get("Normal"), 20, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new MovimientoMultiGolpe("Ataque Rapido", tipos.get("Normal"), 10, 2, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new MovimientoCuracion("Descanso", tipos.get("Normal"), 0, 30, null));
    }
    
    private void agregarMovimientosPsyduck(Pokemon p) {
        p.agregarMovimiento(new Movimiento("Confusion", tipos.get("Agua"), 28, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new Movimiento("Arañazo", tipos.get("Normal"), 10, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new MovimientoCuracion("Recuperacion", tipos.get("Normal"), 0, 20, null));
    }
    
    private void agregarMovimientosGrowlithe(Pokemon p) {
        p.agregarMovimiento(new Movimiento("Ascuas", tipos.get("Fuego"), 32, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new Movimiento("Mordisco", tipos.get("Normal"), 15, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new MovimientoCuracion("Recuperacion", tipos.get("Normal"), 0, 20, null));
    }
    
    private void agregarMovimientosOddish(Pokemon p) {
        p.agregarMovimiento(new Movimiento("Absorber", tipos.get("Planta"), 22, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new MovimientoEstado("Polvo Veneno", tipos.get("Veneno"), 25, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new MovimientoCuracion("Megaagotar", tipos.get("Planta"), 0, 25, null));
    }
    
    private void agregarMovimientosJigglypuff(Pokemon p) {
        p.agregarMovimiento(new Movimiento("Canto", tipos.get("Normal"), 18, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new MovimientoMultiGolpe("Doble Bofeton", tipos.get("Normal"), 8, 2, null, calculadorDano, calculadorMultiplicador));
        p.agregarMovimiento(new MovimientoCuracion("Descanso", tipos.get("Normal"), 0, 35, null));
    }
    
    // GETTERS
    public EstadoJuego getEstadoActual() { return estadoActual; }
    public List<Pokemon> getPokemonDisponibles() { return new ArrayList<>(pokemonDisponibles); }
    public List<Pokemon> getPokemonSeleccionados() { return new ArrayList<>(pokemonSeleccionados); }
    public Entrenador getJugador() { return jugador; }
    public Entrenador getRival() { return rival; }
    public Pokemon getPokemonActivoJugador() { return pokemonActivoJugador; }
    public Pokemon getPokemonActivoRival() { return pokemonActivoRival; }
    public List<String> getLogCombate() { return new ArrayList<>(logCombate); }
    public int getTurnoActual() { return turnoActual; }
    
    // SETTERS Y MÉTODOS DE ESTADO
    public void cambiarEstado(EstadoJuego nuevoEstado) {
        this.estadoActual = nuevoEstado;
        notificarObservadores();
    }
    
    public void seleccionarPokemon(Pokemon pokemon) {
        if (pokemonSeleccionados.size() < 3 && !pokemonSeleccionados.contains(pokemon)) {
            // Crear una copia del Pokemon para el jugador
            Pokemon copia = clonarPokemon(pokemon);
            pokemonSeleccionados.add(copia);
            notificarObservadores();
        }
    }
    
    private Pokemon clonarPokemon(Pokemon original) {
        Pokemon clon = new Pokemon(original.getNombre(), original.getTipos(), 
                                   original.getVidaMax(), original.getAtaque(), original.getDefensa());
        for (Movimiento mov : original.getMovimientos()) {
            clon.agregarMovimiento(mov);
        }
        clon.setSpritePath(original.getSpritePath());

        return clon;
    }
    
    public void iniciarJuego() {
        // Crear entrenadores
        jugador = new Entrenador("Ash");
        rival = new Entrenador("Gary");
        
        // Agregar Pokemon seleccionados al jugador
        for (Pokemon p : pokemonSeleccionados) {
            jugador.agregarPokemon(p);
        }
        
        // Agregar items al jugador
        jugador.agregarItem(new Item("Pocion", 30, 3));
        jugador.agregarItem(new Item("Super Pocion", 50, 2));
        
        // Seleccionar Pokemon aleatorios para el rival (diferentes a los del jugador)
        List<Pokemon> disponiblesRival = new ArrayList<>(pokemonDisponibles);
        disponiblesRival.removeAll(pokemonSeleccionados);
        Collections.shuffle(disponiblesRival);
        
        for (int i = 0; i < 3; i++) {
            Pokemon rivalPokemon = clonarPokemon(disponiblesRival.get(i));
            rival.agregarPokemon(rivalPokemon);
        }
        
        // Agregar items al rival
        rival.agregarItem(new Item("Pocion", 30, 3));
        
        cambiarEstado(EstadoJuego.SELECCION_INICIAL);
    }
    
    public void establecerPokemonInicial(Pokemon pokemon) {
        this.pokemonActivoJugador = pokemon;
        
        // Rival elige aleatoriamente
        List<Pokemon> equipoRival = rival.getEquipo();
        this.pokemonActivoRival = equipoRival.get(new Random().nextInt(equipoRival.size()));
        
        agregarLog("¡Adelante, " + pokemonActivoJugador.getNombre() + "!");
        agregarLog(rival.getNombre() + " envía a " + pokemonActivoRival.getNombre() + "!");
        
        cambiarEstado(EstadoJuego.COMBATE);
    }
    
    public void ejecutarAtaque(Movimiento movimiento) {
        agregarLog("--- Turno " + turnoActual + " ---");
        
        // Turno del jugador
        int vidaAntesAtaque = pokemonActivoRival.getVida();
        pokemonActivoJugador.atacar(pokemonActivoRival, movimiento);
        int danoRealizado = vidaAntesAtaque - pokemonActivoRival.getVida();
        
        agregarLog(pokemonActivoJugador.getNombre() + " usa " + movimiento.getNombre() + 
                   " y causa " + danoRealizado + " de daño!");
        
        verificarEstadoCombate();
        
        if (estadoActual == EstadoJuego.COMBATE && pokemonActivoRival.estaVivo()) {
            turnoRival();
        }
        
        turnoActual++;
        notificarObservadores();
    }
    
    private void turnoRival() {
        // IA simple del rival
        if (pokemonActivoRival.getVida() <= pokemonActivoRival.getVidaMax() / 3) {
            // Intentar usar poción
            for (Item item : rival.getBolsa()) {
                if (item.disponible()) {
                    item.usar(pokemonActivoRival);
                    agregarLog(rival.getNombre() + " usa " + item.getNombre() + "!");
                    return;
                }
            }
        }
        
        // Atacar con movimiento aleatorio
        List<Movimiento> movimientos = pokemonActivoRival.getMovimientos();
        Movimiento movimientoElegido = movimientos.get(new Random().nextInt(movimientos.size()));
        
        int vidaAntesAtaque = pokemonActivoJugador.getVida();
        pokemonActivoRival.atacar(pokemonActivoJugador, movimientoElegido);
        int danoRealizado = vidaAntesAtaque - pokemonActivoJugador.getVida();
        
        agregarLog(pokemonActivoRival.getNombre() + " usa " + movimientoElegido.getNombre() + 
                   " y causa " + danoRealizado + " de daño!");
        
        verificarEstadoCombate();
    }
    
    public void usarItem(Item item) {
        if (item.disponible()) {
            item.usar(pokemonActivoJugador);
            agregarLog("Usaste " + item.getNombre() + " en " + pokemonActivoJugador.getNombre());
            
            // Turno del rival después de usar item
            turnoRival();
            turnoActual++;
            notificarObservadores();
        }
    }
    
    public void cambiarPokemon(Pokemon nuevoPokemon) {
        if (nuevoPokemon.estaVivo() && nuevoPokemon != pokemonActivoJugador) {
            agregarLog(pokemonActivoJugador.getNombre() + " regresa!");
            this.pokemonActivoJugador = nuevoPokemon;
            agregarLog("¡Adelante, " + pokemonActivoJugador.getNombre() + "!");
            
            // Turno del rival después de cambiar
            turnoRival();
            turnoActual++;
            notificarObservadores();
        }
    }
    
    private void verificarEstadoCombate() {
        // Verificar si el Pokemon rival fue derrotado
        if (!pokemonActivoRival.estaVivo()) {
            agregarLog(pokemonActivoRival.getNombre() + " ha sido derrotado!");
            
            if (rival.tienePokemonVivo()) {
                // Rival saca otro Pokemon
                for (Pokemon p : rival.getEquipo()) {
                    if (p.estaVivo() && p != pokemonActivoRival) {
                        pokemonActivoRival = p;
                        agregarLog(rival.getNombre() + " envía a " + pokemonActivoRival.getNombre() + "!");
                        break;
                    }
                }
            } else {
                // Victoria del jugador
                cambiarEstado(EstadoJuego.VICTORIA);
                return;
            }
        }
        
        // Verificar si el Pokemon del jugador fue derrotado
        if (!pokemonActivoJugador.estaVivo()) {
            agregarLog(pokemonActivoJugador.getNombre() + " ha sido derrotado!");
            
            if (!jugador.tienePokemonVivo()) {
                // Derrota del jugador
                cambiarEstado(EstadoJuego.DERROTA);
            }
            // Si tiene Pokemon vivos, el controlador manejará el cambio
        }
    }
    
    private void agregarLog(String mensaje) {
        logCombate.add(mensaje);
        if (logCombate.size() > 10) {
            logCombate.remove(0);
        }
    }
    
    public void reiniciarJuego() {
        pokemonSeleccionados.clear();
        logCombate.clear();
        turnoActual = 1;
        jugador = null;
        rival = null;
        pokemonActivoJugador = null;
        pokemonActivoRival = null;
        inicializarPokemonDisponibles();
        cambiarEstado(EstadoJuego.MENU_PRINCIPAL);
    }
    
    // PATRÓN OBSERVER
    public void agregarObservador(GameStateObserver observador) {
        observadores.add(observador);
    }
    
    private void notificarObservadores() {
        for (GameStateObserver obs : observadores) {
            obs.onEstadoCambiado(this);
        }
    }
    
    public interface GameStateObserver {
        void onEstadoCambiado(GameState estado);
    }
}