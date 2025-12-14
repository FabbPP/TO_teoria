package com.pokemon.calculadores;

import com.pokemon.interfaces.ICalculadorDano;

public class CalculadorDanoBasico implements ICalculadorDano {
    
    @Override
    public int calcularDano(int ataqueBase, int potencia, int defensaObjetivo, double multiplicador) {
        int dmgBase = (ataqueBase + potencia) - (defensaObjetivo / 2);
        if (dmgBase < 1) dmgBase = 1;
        
        int dmg = (int) (dmgBase * multiplicador);
        if (dmg < 1) dmg = 1;
        
        return dmg;
    }
}
