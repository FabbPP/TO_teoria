package pokemon;

import static pokemon.Global.*;

/**
 * Clase que representa un item consumible en el juego
 */
public class Item {
    private String nombre;
    private int efectoCuracion;
    private int usos;
    
    public Item(String nombre, int efectoCuracion, int usos) {
        this.nombre = nombre;
        this.efectoCuracion = efectoCuracion;
        this.usos = usos;
    }
    
    // Getters
    public String getNombre() {
        return nombre;
    }
    
    public int getEfecto() {
        return efectoCuracion;
    }
    
    public int getUsos() {
        return usos;
    }
    
    public boolean disponible() {
        return usos > 0;
    }
    
    public void usar(Pokemon pokemon) {
        if (usos > 0) {
            System.out.println(GREEN + "Se usa " + nombre + " en " +
                             pokemon.getNombre() + " +" + efectoCuracion +
                             " de vida!" + RESET);
            pokemon.curar(efectoCuracion);
            usos--;
        } else {
            System.out.println(RED + "¡No quedan " + nombre + "!" + RESET);
        }
    }
    
    public void mostrar() {
        System.out.print(nombre + " (cura " + efectoCuracion +
                        ", usos: " + usos + ")");
    }
}