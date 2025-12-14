package com.pokemon.calculadores;

import com.pokemon.interfaces.ICalculadorMultiplicador;


public class CalculadorMultiplicadorFuerza implements ICalculadorMultiplicador {
    private final double limiteMinimo;
    private final double limiteMaximo;
    
    public CalculadorMultiplicadorFuerza() {
        this(0.5, 2.0);
    }
    
    public CalculadorMultiplicadorFuerza(double min, double max) {
        this.limiteMinimo = min;
        this.limiteMaximo = max;
    }
    
    @Override
    public double calcularMultiplicador(double fuerzaAtaque, double fuerzaDefensa) {
        if (fuerzaDefensa == 0) return 1.0;
        
        double multiplicador = fuerzaAtaque / fuerzaDefensa;
        
        // Limitar entre mínimo y máximo
        multiplicador = Math.max(limiteMinimo, Math.min(multiplicador, limiteMaximo));
        
        return multiplicador;
    }
}