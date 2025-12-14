package com.pokemon.movimientos;

import com.pokemon.Pokemon;
import com.pokemon.tipos.TipoElemento;
import com.pokemon.interfaces.IMostrador;
import com.pokemon.interfaces.ICalculadorDano;
import com.pokemon.interfaces.ICalculadorMultiplicador;
import static com.pokemon.Global.*;

//OCP: Extensión de Movimiento sin modificar la clase base
//LSP: Puede sustituir a Movimiento sin problemas
 
public class MovimientoMultiGolpe extends Movimiento {
    private int numeroGolpes;
    
    public MovimientoMultiGolpe(String nombre, TipoElemento tipo, int potencia,
                               int golpes, IMostrador mostrador,
                               ICalculadorDano calcDano,
                               ICalculadorMultiplicador calcMult) {
        super(nombre, tipo, potencia, mostrador, calcDano, calcMult);
        this.numeroGolpes = golpes;
    }
    
    //LSP: Override correcto manteniendo el contrato
    
    @Override
    public void aplicarEfecto(Pokemon atacante, Pokemon objetivo) {
        if (mostrador != null) {
            mostrador.mostrarMensaje(atacante.getNombre() + " usa " + nombre + "!", "");
        }
        
        // SRP: Delega cálculos a las clases responsables
        double multiplicador = obtenerMultiplicadorTipo(objetivo);
        
        int golpesAcertados = 0;
        for (int i = 0; i < numeroGolpes && objetivo.estaVivo(); i++) {
            // DIP: Usa abstracción para calcular daño
            int dmg = calculadorDano.calcularDano(
                atacante.getAtaque(),
                potencia,
                objetivo.getDefensa(),
                multiplicador
            );
            
            if (mostrador != null) {
                mostrador.mostrarMensaje(
                    "¡Golpe " + (i + 1) + "! Causa " + dmg + " de daño.",
                    YELLOW
                );
            }
            
            objetivo.recibirDano(dmg);
            golpesAcertados++;
        }
        
        if (mostrador != null) {
            mostrador.mostrarMensaje(
                "¡" + golpesAcertados + " golpes acertados!",
                GREEN
            );
            
            mostrarMensajeEfectividad(multiplicador);
        }
    }
    
    public int getNumeroGolpes() {
        return numeroGolpes;
    }
}