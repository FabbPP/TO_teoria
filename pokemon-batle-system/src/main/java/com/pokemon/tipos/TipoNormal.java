package com.pokemon.tipos;

public class TipoNormal extends TipoElemento {
    public TipoNormal() {
        super("Normal", 50.0);
    }
    
    @Override
    public void mostrarInfo() {
        System.out.println("Tipo Normal - Fuerza: " + fuerza);
    }
}