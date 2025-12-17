package com.pokemon.modelo;

import com.pokemon.*;
import com.pokemon.tipos.*;
import com.pokemon.movimientos.*;
import com.pokemon.calculadores.*;
import com.pokemon.interfaces.*;
import com.pokemon.singleton.ControlJuego;
import java.util.*;

// MODELO - Gestiona el estado completo del juego
public class GameState {
    
    private ControlJuego controlJuego;
    
    // Estados del juego
    public enum EstadoJuego {
        MENU_PRINCIPAL, SELECCION_POKEMON, SELECCION_INICIAL, COMBATE, VICTORIA, DERROTA
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
    
    // Observadores
    private List<GameStateObserver> observadores;

    // --- CORRECCIÓN: Variable para el mostrador de mensajes ---
    private IMostrador mostradorLog; 
    
    public GameState() {
        this.estadoActual = EstadoJuego.MENU_PRINCIPAL;
        this.pokemonSeleccionados = new ArrayList<>();
        this.logCombate = new ArrayList<>();
        this.observadores = new ArrayList<>();
        this.turnoActual = 1;
        
        this.controlJuego = ControlJuego.getInstance();
        
        inicializarCalculadores();
        // --- CORRECCIÓN: Inicializamos el mostrador ANTES de cargar los Pokémon ---
        inicializarMostrador(); 
        inicializarTipos();
        inicializarPokemonDisponibles();
    }
    
    private void inicializarCalculadores() {
        this.calculadorDano = new CalculadorDanoBasico();
        this.calculadorMultiplicador = new CalculadorMultiplicadorTipos();
    }

    // --- CORRECCIÓN: Método para conectar los ataques con el Log ---
    private void inicializarMostrador() {
        this.mostradorLog = new IMostrador() {
            @Override
            public void mostrarMensaje(String mensaje, String color) {
                // Aquí capturamos "Es muy eficaz", "Inmune", etc.
                agregarLog(mensaje);
            }

            @Override
            public void mostrarMensajeCombate(String mensaje) {
                // Opcional: si algún ataque manda mensajes genéricos
                // agregarLog(mensaje);
            }
        };
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
        // --- CORRECCIÓN: Pasamos 'this.mostradorLog' (el real) en vez del dummy ---
        pokemonDisponibles = PokemonLoader.cargarDesdeCSV(
            "/data/pokemon_data.csv", 
            tipos,
            this.mostradorLog, // <--- AQUÍ ESTÁ LA CLAVE
            calculadorDano,
            calculadorMultiplicador
        );
        
        // Respaldo
        if (pokemonDisponibles.isEmpty()) {
            System.err.println("¡ALERTA! Falló carga CSV. Usando respaldo.");
            Pokemon p = new Pokemon("Pikachu Backup", Arrays.asList(tipos.get("Electrico")), 80, 25, 5);
            // También pasamos mostradorLog aquí
            p.agregarMovimiento(new Movimiento("Impactrueno", tipos.get("Electrico"), 30, this.mostradorLog, calculadorDano, calculadorMultiplicador));
            p.setSpriteFrontal("/gif/pikachu_front.gif");
            p.setSpriteTrasero("/gif/pikachu_back.gif");
            pokemonDisponibles.add(p);
        }
    }
    
    // --- LÓGICA DE JUEGO (Igual que antes) ---
    
    public void seleccionarPokemon(Pokemon pokemon) {
        if (pokemonSeleccionados.size() < 3 && !pokemonSeleccionados.contains(pokemon)) {
            Pokemon copia = clonarPokemon(pokemon);
            pokemonSeleccionados.add(copia);
            controlJuego.registrarCaptura();
            notificarObservadores();
        }
    }
    
    private Pokemon clonarPokemon(Pokemon original) {
        Pokemon clon = new Pokemon(original.getNombre(), original.getTipos(), 
                                   original.getVidaMax(), original.getAtaque(), original.getDefensa());
        
        // Al copiar los movimientos, estos ya tienen el 'mostradorLog' dentro
        for (Movimiento mov : original.getMovimientos()) {
            clon.agregarMovimiento(mov);
        }

        clon.setSpriteFrontal(original.getSpriteFrontal());
        clon.setSpriteTrasero(original.getSpriteTrasero());

        return clon;
    }
    
    public void iniciarJuego() {
        jugador = new Entrenador("Ash");
        rival = new Entrenador("Gary");
        
        for (Pokemon p : pokemonSeleccionados) {
            jugador.agregarPokemon(p);
        }
        
        jugador.agregarItem(new Item("Pocion", 30, 3));
        jugador.agregarItem(new Item("Super Pocion", 50, 2));
        
        List<Pokemon> disponiblesRival = new ArrayList<>(pokemonDisponibles);
        Collections.shuffle(disponiblesRival);
        
        for (int i = 0; i < 3 && i < disponiblesRival.size(); i++) {
            Pokemon rivalPokemon = clonarPokemon(disponiblesRival.get(i));
            rival.agregarPokemon(rivalPokemon);
        }
        
        rival.agregarItem(new Item("Pocion", 30, 3));
        
        cambiarEstado(EstadoJuego.SELECCION_INICIAL);
    }
    
    public void establecerPokemonInicial(Pokemon pokemon) {
        this.pokemonActivoJugador = pokemon;
        List<Pokemon> equipoRival = rival.getEquipo();
        this.pokemonActivoRival = equipoRival.get(new Random().nextInt(equipoRival.size()));
        
        agregarLog("¡Adelante, " + pokemonActivoJugador.getNombre() + "!");
        agregarLog(rival.getNombre() + " envía a " + pokemonActivoRival.getNombre() + "!");
        
        cambiarEstado(EstadoJuego.COMBATE);
    }
    
    public void ejecutarAtaque(Movimiento movimiento) {
        agregarLog("--- Turno " + turnoActual + " ---");
        
        int vidaAntesAtaque = pokemonActivoRival.getVida();
        
        // Al ejecutar el ataque, el movimiento usará 'mostradorLog' para escribir "Es muy eficaz"
        pokemonActivoJugador.atacar(pokemonActivoRival, movimiento);
        
        int danoRealizado = vidaAntesAtaque - pokemonActivoRival.getVida();
        
        agregarLog(pokemonActivoJugador.getNombre() + " usa " + movimiento.getNombre() + 
                   " y causa " + danoRealizado + " de daño!");
        
        controlJuego.registrarDanoInfligido(danoRealizado);
        
        if (new Random().nextInt(100) < 10) {
            controlJuego.registrarAtaqueCritico();
            agregarLog("¡Ataque crítico!");
        }
        
        verificarEstadoCombate();
        
        if (estadoActual == EstadoJuego.COMBATE && pokemonActivoRival.estaVivo()) {
            turnoRival();
        }
        
        turnoActual++;
        notificarObservadores();
    }
    
    private void turnoRival() {
        if (pokemonActivoRival.getVida() <= pokemonActivoRival.getVidaMax() / 3) {
            for (Item item : rival.getBolsa()) {
                if (item.disponible()) {
                    item.usar(pokemonActivoRival);
                    agregarLog(rival.getNombre() + " usa " + item.getNombre() + "!");
                    return;
                }
            }
        }
        
        List<Movimiento> movimientos = pokemonActivoRival.getMovimientos();
        if (!movimientos.isEmpty()) {
            Movimiento movimientoElegido = movimientos.get(new Random().nextInt(movimientos.size()));
            
            int vidaAntesAtaque = pokemonActivoJugador.getVida();
            pokemonActivoRival.atacar(pokemonActivoJugador, movimientoElegido);
            int danoRealizado = vidaAntesAtaque - pokemonActivoJugador.getVida();
            
            agregarLog(pokemonActivoRival.getNombre() + " usa " + movimientoElegido.getNombre() + 
                       " y causa " + danoRealizado + " de daño!");
            
            controlJuego.registrarDanoRecibido(danoRealizado);
        }
        
        verificarEstadoCombate();
    }
    
    public void usarItem(Item item) {
        if (item.disponible()) {
            item.usar(pokemonActivoJugador);
            agregarLog("Usaste " + item.getNombre() + " en " + pokemonActivoJugador.getNombre());
            controlJuego.registrarUsoItem();
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
            turnoRival();
            turnoActual++;
            notificarObservadores();
        }
    }
    
    private void verificarEstadoCombate() {
        if (!pokemonActivoRival.estaVivo()) {
            agregarLog(pokemonActivoRival.getNombre() + " ha sido derrotado!");
            if (rival.tienePokemonVivo()) {
                for (Pokemon p : rival.getEquipo()) {
                    if (p.estaVivo() && p != pokemonActivoRival) {
                        pokemonActivoRival = p;
                        agregarLog(rival.getNombre() + " envía a " + pokemonActivoRival.getNombre() + "!");
                        break;
                    }
                }
            } else {
                controlJuego.registrarVictoria();
                cambiarEstado(EstadoJuego.VICTORIA);
                return;
            }
        }
        
        if (!pokemonActivoJugador.estaVivo()) {
            agregarLog(pokemonActivoJugador.getNombre() + " ha sido derrotado!");
            if (!jugador.tienePokemonVivo()) {
                controlJuego.registrarDerrota();
                cambiarEstado(EstadoJuego.DERROTA);
                getControlJuego().mostrarEstadoCompleto();
            }
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
        controlJuego.reiniciarJuego();
        cambiarEstado(EstadoJuego.MENU_PRINCIPAL);
    }
    
    public ControlJuego getControlJuego() { return controlJuego; }
    public EstadoJuego getEstadoActual() { return estadoActual; }
    public List<Pokemon> getPokemonDisponibles() { return new ArrayList<>(pokemonDisponibles); }
    public List<Pokemon> getPokemonSeleccionados() { return new ArrayList<>(pokemonSeleccionados); }
    public Entrenador getJugador() { return jugador; }
    public Entrenador getRival() { return rival; }
    public Pokemon getPokemonActivoJugador() { return pokemonActivoJugador; }
    public Pokemon getPokemonActivoRival() { return pokemonActivoRival; }
    public List<String> getLogCombate() { return new ArrayList<>(logCombate); }
    public int getTurnoActual() { return turnoActual; }
    
    public void cambiarEstado(EstadoJuego nuevoEstado) {
        this.estadoActual = nuevoEstado;
        notificarObservadores();
    }
    
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