package com.pokemon.movimientos;

import com.pokemon.interfaces.IMostrador;
import com.pokemon.interfaces.ICalculadorDano;
import com.pokemon.interfaces.ICalculadorMultiplicador;
import com.pokemon.Pokemon;
import com.pokemon.tipos.TipoElemento;
import static com.pokemon.Global.*;

public class  MovimientoEstado extends Movimiento {
    public MovimientoEstado(String nombre, TipoElemento tipo, int potencia,
                           IMostrador mostrador,
                           ICalculadorDano calcDano,
                           ICalculadorMultiplicador calcMult) {
        super(nombre, tipo, potencia, mostrador, calcDano, calcMult);
    }
    
    @Override
    public void aplicarEfecto(Pokemon atacante, Pokemon objetivo) {
        super.aplicarEfecto(atacante, objetivo);
        if (mostrador != null) {
            mostrador.mostrarMensaje(objetivo.getNombre() + " ha sido envenenado!", MAGENTA);
        }
    }
}