package pokemon.controlador;

import pokemon.modelo.GameState;
import pokemon.modelo.GameState.EstadoJuego;
import pokemon.*;
import pokemon.movimientos.Movimiento;

//Maneja las acciones del usuario y actualiza el modelo

public class GameController {
    private GameState modelo;
    
    public GameController(GameState modelo) {
        this.modelo = modelo;
    }
    
    // acciones del menú principal
    public void iniciarNuevoJuego() {
        modelo.reiniciarJuego();
        modelo.cambiarEstado(EstadoJuego.SELECCION_POKEMON);
    }
    
    public void salirJuego() {
        System.exit(0);
    }
    
    // acciones de selección de pokémon
    public void seleccionarPokemon(Pokemon pokemon) {
        if (modelo.getPokemonSeleccionados().size() < 3) {
            modelo.seleccionarPokemon(pokemon);
        }
    }
    
    public void confirmarSeleccion() {
        if (modelo.getPokemonSeleccionados().size() == 3) {
            modelo.iniciarJuego();
        }
    }
    
    public void cancelarSeleccion() {
        modelo.reiniciarJuego();
    }
    
    // acciones de selección de pokémon inicial
    public void elegirPokemonInicial(Pokemon pokemon) {
        if (modelo.getJugador().getEquipo().contains(pokemon)) {
            modelo.establecerPokemonInicial(pokemon);
        }
    }
    
    // acciones de combate
    public void atacar(Movimiento movimiento) {
        if (modelo.getEstadoActual() == EstadoJuego.COMBATE && 
            modelo.getPokemonActivoJugador().estaVivo()) {
            modelo.ejecutarAtaque(movimiento);
        }
    }
    
    public void usarItem(Item item) {
        if (modelo.getEstadoActual() == EstadoJuego.COMBATE && item.disponible()) {
            modelo.usarItem(item);
        }
    }
    
    public void cambiarPokemon(Pokemon nuevoPokemon) {
        if (modelo.getEstadoActual() == EstadoJuego.COMBATE && nuevoPokemon.estaVivo()) {
            modelo.cambiarPokemon(nuevoPokemon);
        }
    }
    
    //acciones de final de combate
    public void volverAlMenu() {
        modelo.reiniciarJuego();
    }
    
    // getters de modelo
    public GameState getModelo() {
        return modelo;
    }
}