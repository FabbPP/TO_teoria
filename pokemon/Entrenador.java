package pokemon;

import java.util.ArrayList;
import java.util.List;
import static pokemon.Global.*;

//Clase que representa un entrenador con su equipo y bolsa de items

public class Entrenador {
    private String nombre;
    private List<Pokemon> equipo;
    private List<Item> bolsa;
    
    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.equipo = new ArrayList<>();
        this.bolsa = new ArrayList<>();
    }
    
    // Getters
    public String getNombre() {
        return nombre;
    }
    
    public List<Pokemon> getEquipo() {
        return equipo;
    }
    
    public List<Item> getBolsa() {
        return bolsa;
    }
    
    public void agregarPokemon(Pokemon pokemon) {
        equipo.add(pokemon);
    }
    
    public void agregarItem(Item item) {
        bolsa.add(item);
    }
    
    public boolean tienePokemonVivo() {
        for (Pokemon p : equipo) {
            if (p.estaVivo()) return true;
        }
        return false;
    }
    
    public void mostrarEquipo() {
        System.out.println(YELLOW + nombre + " - Equipo:" + RESET);
        for (int i = 0; i < equipo.size(); i++) {
            System.out.print((i + 1) + ". ");
            equipo.get(i).mostrar();
            System.out.println(equipo.get(i).estaVivo() ? "" : " (Debilitado)");
        }
    }
    
    public void mostrarBolsa() {
        System.out.println(MAGENTA + "Bolsa de " + nombre + ":" + RESET);
        for (int i = 0; i < bolsa.size(); i++) {
            System.out.print((i + 1) + ". ");
            bolsa.get(i).mostrar();
            System.out.println();
        }
    }
}