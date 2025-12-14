package com.pokemon.movimientos;

import com.pokemon.Pokemon;
import com.pokemon.tipos.TipoElemento;
import com.pokemon.interfaces.IMostrador;
import static com.pokemon.Global.*;

public class  MovimientoCuracion extends Movimiento {
    private int cantidadCuracion;
    
    public MovimientoCuracion(String nombre, TipoElemento tipo, int potencia,
                             int curacion, IMostrador mostrador) {
        super(nombre, tipo, potencia, mostrador, null, null);
        this.cantidadCuracion = curacion;
    }
    
    @Override
    public void aplicarEfecto(Pokemon atacante, Pokemon objetivo) {
        if (mostrador != null) {
            mostrador.mostrarMensaje(
                atacante.getNombre() + " usa " + nombre +
                " y se cura " + cantidadCuracion + " puntos de vida!", GREEN
            );
        }
        atacante.curar(cantidadCuracion);
    }
}