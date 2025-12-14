package com.pokemon.tipos;

public class  TipoPlanta extends TipoElemento {
    public TipoPlanta() {
        super("Planta", 70.0);
    }
    
    @Override
    public void mostrarInfo() {
        System.out.println("Tipo Planta - Fuerza: " + fuerza);
    }
}