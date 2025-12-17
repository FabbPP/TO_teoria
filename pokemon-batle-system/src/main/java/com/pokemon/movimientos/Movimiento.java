package com.pokemon.movimientos;

import com.pokemon.Pokemon;
import com.pokemon.tipos.TipoElemento;
import com.pokemon.interfaces.IMostrador;
import com.pokemon.interfaces.ICalculadorDano;
import com.pokemon.interfaces.ICalculadorMultiplicador;
import com.pokemon.calculadores.CalculadorDanoBasico;
import com.pokemon.calculadores.CalculadorMultiplicadorTipos;
import static com.pokemon.Global.*;

public class Movimiento {
    protected String nombre;
    protected TipoElemento tipo;
    protected int potencia;
    
    protected IMostrador mostrador;
    protected ICalculadorDano calculadorDano;
    protected ICalculadorMultiplicador calculadorMultiplicador;
    
    public Movimiento(String nombre, TipoElemento tipo, int potencia,
                     IMostrador mostrador,
                     ICalculadorDano calcDano,
                     ICalculadorMultiplicador calcMult) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.potencia = potencia;
        this.mostrador = mostrador;
        this.calculadorDano = calcDano != null ? calcDano : new CalculadorDanoBasico();
        this.calculadorMultiplicador = calcMult != null ? calcMult : new CalculadorMultiplicadorTipos();
    }
    
    public String getNombre() { return nombre; }
    public TipoElemento getTipo() { return tipo; }
    public int getPotencia() { return potencia; }
    
    // Cálculo por tipos
    protected double obtenerMultiplicadorTipo(Pokemon objetivo) {
        double multiplicadorTotal = 1.0;
        
        // Iteramos sobre todos los tipos del objetivo 
        for (TipoElemento tipoDefensor : objetivo.getTipos()) {
            // Multiplicamos acumulativamente
            multiplicadorTotal *= calculadorMultiplicador.calcularMultiplicador(this.tipo, tipoDefensor);
        }
        
        return multiplicadorTotal;
    }
    
    protected void mostrarMensajeEfectividad(double multiplicador) {
        if (mostrador == null) return;
        
        if (multiplicador >= 2.0) {
            mostrador.mostrarMensaje("¡Es súper eficaz!", YELLOW);
        } else if (multiplicador >= 1.2) { 
            mostrador.mostrarMensaje("Es muy eficaz.", GREEN);
        } else if (multiplicador == 0.0) {
            mostrador.mostrarMensaje("No afecta al objetivo...", RESET);
        } else if (multiplicador <= 0.5) {
            mostrador.mostrarMensaje("No es muy eficaz...", CYAN);
        }
    }
    
    public void aplicarEfecto(Pokemon atacante, Pokemon objetivo) {
        if (potencia > 0) {
            double multiplicador = obtenerMultiplicadorTipo(objetivo);
            
            int dmg = calculadorDano.calcularDano(
                atacante.getAtaque(),
                potencia,
                objetivo.getDefensa(),
                multiplicador
            );
            
            if (mostrador != null) {
                mostrador.mostrarMensajeCombate(
                    atacante.getNombre() + " usa " + nombre +
                    " y causa " + dmg + " de daño a " + objetivo.getNombre() + "."
                );
                mostrarMensajeEfectividad(multiplicador);
            }
            objetivo.recibirDano(dmg);
        } else {
            if (mostrador != null) {
                mostrador.mostrarMensaje(atacante.getNombre() + " usa " + nombre + ".", RESET);
            }
        }
    }
}