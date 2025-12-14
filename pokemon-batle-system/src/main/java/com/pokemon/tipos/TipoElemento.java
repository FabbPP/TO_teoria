package com.pokemon.tipos;

public abstract class TipoElemento {
    protected String nombre;
    protected double fuerza;
    
    public TipoElemento(String nombre, double fuerza) {
        this.nombre = nombre;
        this.fuerza = fuerza;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public double getFuerza() {
        return fuerza;
    }
    
    public void mostrarInfo() {
        System.out.println("Tipo: " + nombre + ", Fuerza: " + fuerza);
    }
}
