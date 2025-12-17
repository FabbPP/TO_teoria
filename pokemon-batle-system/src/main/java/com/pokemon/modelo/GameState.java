package com.pokemon.modelo;

import com.pokemon.*;
import com.pokemon.tipos.*;
import com.pokemon.movimientos.*;
import com.pokemon.calculadores.*;
import com.pokemon.interfaces.*;
import com.pokemon.singleton.ControlJuego;  // el singleton
import java.util.*;

//MODELO - Gestiona el estado completo del juego
// INTEGRADO CON SINGLETON ControlJuego para estado global

public class GameState {
    
    private ControlJuego controlJuego;  // Referencia al singleton
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
    
    // Observadores
    private List<GameStateObserver> observadores;
    
    public GameState() {
        this.estadoActual = EstadoJuego.MENU_PRINCIPAL;
        this.pokemonSeleccionados = new ArrayList<>();
        this.logCombate = new ArrayList<>();
        this.observadores = new ArrayList<>();
        this.turnoActual = 1;
        
        //obtener la instancia del singleton
        this.controlJuego = ControlJuego.getInstance();
        
        System.out.println("GameState obtuvo instancia de ControlJuego: " + controlJuego);
        
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
        
        // Crear Pokemon... (código existente)
        Pokemon pikachu = new Pokemon("Pikachu", Arrays.asList(tipos.get("Electrico")), 80, 25, 5);
        pikachu.setSpriteFrontal("/gif/pikachu_front.gif");
        pikachu.setSpriteTrasero("/gif/pikachu_back.gif");

        agregarMovimientosPikachu(pikachu);
        pokemonDisponibles.add(pikachu);
        
        // Charmander
        Pokemon charmander = new Pokemon("Charmander", Arrays.asList(tipos.get("Fuego")), 95, 22, 6);
        charmander.setSpriteFrontal("/gif/charmander_front.gif");
        charmander.setSpriteTrasero("/gif/charmander_back.gif");
        agregarMovimientosCharmander(charmander);
        pokemonDisponibles.add(charmander);

        // Squirtle
        Pokemon squirtle = new Pokemon("Squirtle", Arrays.asList(tipos.get("Agua")), 90, 20, 7);
        squirtle.setSpriteFrontal("/gif/squirtle_front.gif");
        squirtle.setSpriteTrasero("/gif/squirtle_back.gif");
        agregarMovimientosSquirtle(squirtle);
        pokemonDisponibles.add(squirtle);

        // Bulbasaur
        Pokemon bulbasaur = new Pokemon("Bulbasaur", Arrays.asList(tipos.get("Planta"), tipos.get("Veneno")), 100, 18, 8);
        bulbasaur.setSpriteFrontal("/gif/bulbasaur_front.gif");
        bulbasaur.setSpriteTrasero("/gif/bulbasaur_back.gif");
        agregarMovimientosBulbasaur(bulbasaur);
        pokemonDisponibles.add(bulbasaur);

        // Eevee
        Pokemon eevee = new Pokemon("Eevee", Arrays.asList(tipos.get("Normal")), 85, 23, 6);
        eevee.setSpriteFrontal("/gif/eevee_front.gif");
        eevee.setSpriteTrasero("/gif/eevee_back.gif");
        agregarMovimientosEevee(eevee);
        pokemonDisponibles.add(eevee);

        // Psyduck
        Pokemon psyduck = new Pokemon("Psyduck", Arrays.asList(tipos.get("Agua")), 88, 21, 5);
        psyduck.setSpriteFrontal("/gif/psyduck_front.gif");
        psyduck.setSpriteTrasero("/gif/psyduck_back.gif");
        agregarMovimientosPsyduck(psyduck);
        pokemonDisponibles.add(psyduck);

        // Growlithe
        Pokemon growlithe = new Pokemon("Growlithe", Arrays.asList(tipos.get("Fuego")), 92, 24, 6);
        growlithe.setSpriteFrontal("/gif/growlithe_front.gif");
        growlithe.setSpriteTrasero("/gif/growlithe_back.gif");
        agregarMovimientosGrowlithe(growlithe);
        pokemonDisponibles.add(growlithe);

        // Oddish
        Pokemon oddish = new Pokemon("Oddish", Arrays.asList(tipos.get("Planta"), tipos.get("Veneno")), 87, 19, 7);
        oddish.setSpriteFrontal("/gif/oddish_front.gif");
        oddish.setSpriteTrasero("/gif/oddish_back.gif");
        agregarMovimientosOddish(oddish);
        pokemonDisponibles.add(oddish);

        // Jigglypuff
        Pokemon jigglypuff = new Pokemon("Jigglypuff", Arrays.asList(tipos.get("Normal")), 110, 17, 4);
        jigglypuff.setSpriteFrontal("/gif/jigglypuff_front.gif");
        jigglypuff.setSpriteTrasero("/gif/jigglypuff_back.gif");
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
    
    
    public void seleccionarPokemon(Pokemon pokemon) {
        if (pokemonSeleccionados.size() < 3 && !pokemonSeleccionados.contains(pokemon)) {
            // Crear una copia del Pokemon para el jugador
            Pokemon copia = clonarPokemon(pokemon);
            pokemonSeleccionados.add(copia);
            
            // SINGLETON: Registrar captura
            controlJuego.registrarCaptura();
            
            notificarObservadores();
        }
    }
    
    private Pokemon clonarPokemon(Pokemon original) {
        Pokemon clon = new Pokemon(original.getNombre(), original.getTipos(), 
                                   original.getVidaMax(), original.getAtaque(), original.getDefensa());
                                   
        for (Movimiento mov : original.getMovimientos()) {
            clon.agregarMovimiento(mov);
        }

        clon.setSpriteFrontal(original.getSpriteFrontal());
        clon.setSpriteTrasero(original.getSpriteTrasero());

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
        
        // SINGLETON: Registrar dano infligido
        controlJuego.registrarDanoInfligido(danoRealizado);
        
        // Verificar si fue crítico (10% de probabilidad)
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
        // IA del rival
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
        
        //SINGLETON: Registrar daño recibido
        controlJuego.registrarDanoRecibido(danoRealizado);
        
        verificarEstadoCombate();
    }
    
    public void usarItem(Item item) {
        if (item.disponible()) {
            item.usar(pokemonActivoJugador);
            agregarLog("Usaste " + item.getNombre() + " en " + pokemonActivoJugador.getNombre());
            
            // SINGLETON: Registrar uso de item
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
                // SINGLETON: Registrar victoria
                controlJuego.registrarVictoria();
                cambiarEstado(EstadoJuego.VICTORIA);
                
                getControlJuego().mostrarEstadoCompleto();
                return;
            }
        }
        
        // Verificar si el Pokemon del jugador fue derrotado
        if (!pokemonActivoJugador.estaVivo()) {
            agregarLog(pokemonActivoJugador.getNombre() + " ha sido derrotado!");
            
            if (!jugador.tienePokemonVivo()) {
                // SINGLETON: Registrar derrota
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
        
        // SINGLETON: Reiniciar también el control del juego
        controlJuego.reiniciarJuego();
        
        cambiarEstado(EstadoJuego.MENU_PRINCIPAL);
    }
    
    //metodo para acceder al singleton
    
    public ControlJuego getControlJuego() {
        return controlJuego;
    }
    
    // GETTERS existentes
    public EstadoJuego getEstadoActual() { return estadoActual; }
    public List<Pokemon> getPokemonDisponibles() { return new ArrayList<>(pokemonDisponibles); }
    public List<Pokemon> getPokemonSeleccionados() { return new ArrayList<>(pokemonSeleccionados); }
    public Entrenador getJugador() { return jugador; }
    public Entrenador getRival() { return rival; }
    public Pokemon getPokemonActivoJugador() { return pokemonActivoJugador; }
    public Pokemon getPokemonActivoRival() { return pokemonActivoRival; }
    public List<String> getLogCombate() { return new ArrayList<>(logCombate); }
    public int getTurnoActual() { return turnoActual; }
    
    // Métodos del estado
    public void cambiarEstado(EstadoJuego nuevoEstado) {
        this.estadoActual = nuevoEstado;
        notificarObservadores();
    }
    
    // Patrón Observer
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