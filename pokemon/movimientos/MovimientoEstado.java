package pokemon.movimientos;

import pokemon.interfaces.IMostrador;
import pokemon.interfaces.ICalculadorDano;
import pokemon.interfaces.ICalculadorMultiplicador;
import pokemon.Pokemon;
import pokemon.tipos.TipoElemento;
import static pokemon.Global.*;

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