package pokemon.movimientos;

import pokemon.Pokemon;
import pokemon.tipos.TipoElemento;
import pokemon.interfaces.IMostrador;
import pokemon.interfaces.ICalculadorDano;
import pokemon.interfaces.ICalculadorMultiplicador;
import pokemon.calculadores.CalculadorDanoBasico;
import pokemon.calculadores.CalculadorMultiplicadorFuerza;
import static pokemon.Global.*;

public class Movimiento {
    protected String nombre;
    protected TipoElemento tipo;
    protected int potencia;
    
    // DIP: Dependencias son abstracciones
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
        this.calculadorMultiplicador = calcMult != null ? calcMult : new CalculadorMultiplicadorFuerza();
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public TipoElemento getTipo() {
        return tipo;
    }
    
    public int getPotencia() {
        return potencia;
    }
    
    // SRP: Método con responsabilidad única - calcular multiplicador
     
    protected double obtenerMultiplicadorTipo(Pokemon objetivo) {
        double fuerzaAtaque = tipo.getFuerza();
        
        double fuerzaDefensaTotal = 0.0;
        for (TipoElemento tipoDefensor : objetivo.getTipos()) {
            fuerzaDefensaTotal += tipoDefensor.getFuerza();
        }
        double fuerzaDefensaPromedio = fuerzaDefensaTotal / objetivo.getTipos().size();
        
        // DIP: Usa abstracción para calcular
        return calculadorMultiplicador.calcularMultiplicador(fuerzaAtaque, fuerzaDefensaPromedio);
    }
    
    //SRP: Método con responsabilidad única - mostrar mensajes
    protected void mostrarMensajeEfectividad(double multiplicador) {
        if (mostrador == null) return;
        
        if (multiplicador >= 1.5) {
            mostrador.mostrarMensaje("¡El tipo es muy fuerte contra el oponente!", YELLOW);
        } else if (multiplicador >= 1.2) {
            mostrador.mostrarMensaje("El tipo tiene ventaja.", GREEN);
        } else if (multiplicador <= 0.7) {
            mostrador.mostrarMensaje("El tipo es débil contra el oponente...", CYAN);
        } else if (multiplicador <= 0.9) {
            mostrador.mostrarMensaje("El tipo tiene desventaja.", CYAN);
        }
    }
    
    //OCP: Método virtual para extensión
    
    public void aplicarEfecto(Pokemon atacante, Pokemon objetivo) {
        if (potencia > 0) {
            // SRP: Cada cálculo delegado a su clase responsable
            double multiplicador = obtenerMultiplicadorTipo(objetivo);
            
            // DIP: Usa abstracción para calcular daño
            int dmg = calculadorDano.calcularDano(
                atacante.getAtaque(),
                potencia,
                objetivo.getDefensa(),
                multiplicador
            );
            
            // DIP: Usa abstracción para mostrar mensajes
            if (mostrador != null) {
                mostrador.mostrarMensajeCombate(
                    atacante.getNombre() + " usa " + nombre +
                    " (" + tipo.getNombre() + ", Fuerza: " + (int) tipo.getFuerza() +
                    ") y causa " + dmg + " de daño a " + objetivo.getNombre() + "."
                );
                
                mostrarMensajeEfectividad(multiplicador);
            }
            
            objetivo.recibirDano(dmg);
        } else {
            if (mostrador != null) {
                mostrador.mostrarMensaje(atacante.getNombre() + " usa " + nombre + ".",RESET );
            }
        }
    }
}