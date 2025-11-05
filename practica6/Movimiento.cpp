#include "Movimiento.h"
#include "Pokemon.h"
#include "global.h"
#include "Calculadores.h"
#include <iostream>

Movimiento::Movimiento(std::string n, std::shared_ptr<TipoElemento> t, int p,
                       const IMostrador* m,
                       std::shared_ptr<ICalculadorDano> calcDano,
                       std::shared_ptr<ICalculadorMultiplicador> calcMult)
    : nombre(n), tipo(t), potencia(p), mostrador(m),
      calculadorDano(calcDano ? calcDano : std::make_shared<CalculadorDanoBasico>()),
      calculadorMultiplicador(calcMult ? calcMult : std::make_shared<CalculadorMultiplicadorFuerza>()) {}

std::string Movimiento::getNombre() const { return nombre; }
std::shared_ptr<TipoElemento> Movimiento::getTipo() const { return tipo; }
int Movimiento::getPotencia() const { return potencia; }

// SRP: Método con responsabilidad única - calcular multiplicador
double Movimiento::obtenerMultiplicadorTipo(const Pokemon& objetivo) const {
    double fuerzaAtaque = tipo->getFuerza();
    
    double fuerzaDefensaTotal = 0.0;
    for (const auto& tipoDefensor : objetivo.getTipos()) {
        fuerzaDefensaTotal += tipoDefensor->getFuerza();
    }
    double fuerzaDefensaPromedio = fuerzaDefensaTotal / objetivo.getTipos().size();
    
    // DIP: Usa abstracción para calcular
    return calculadorMultiplicador->calcularMultiplicador(fuerzaAtaque, fuerzaDefensaPromedio);
}

// SRP: Método con responsabilidad única - mostrar mensajes
void Movimiento::mostrarMensajeEfectividad(double multiplicador) const {
    if (!mostrador) return;
    
    if (multiplicador >= 1.5) {
        mostrador->mostrarMensaje("¡El tipo es muy fuerte contra el oponente!", YELLOW);
    } else if (multiplicador >= 1.2) {
        mostrador->mostrarMensaje("El tipo tiene ventaja.", GREEN);
    } else if (multiplicador <= 0.7) {
        mostrador->mostrarMensaje("El tipo es debil contra el oponente...", CYAN);
    } else if (multiplicador <= 0.9) {
        mostrador->mostrarMensaje("El tipo tiene desventaja.", CYAN);
    }
}

void Movimiento::aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const {
    if (potencia > 0) {
        // SRP: Cada cálculo delegado a su clase responsable
        double multiplicador = obtenerMultiplicadorTipo(objetivo);
        
        // DIP: Usa abstracción para calcular daño
        int dmg = calculadorDano->calcularDano(
            atacante.getAtaque(),
            potencia,
            objetivo.getDefensa(),
            multiplicador
        );

        // DIP: Usa abstracción para mostrar mensajes
        if (mostrador) {
            mostrador->mostrarMensajeCombate(
                atacante.getNombre() + " usa " + nombre + 
                " (" + tipo->getNombre() + ", Fuerza: " + std::to_string(static_cast<int>(tipo->getFuerza())) + 
                ") y causa " + std::to_string(dmg) + 
                " de dano a " + objetivo.getNombre() + "."
            );
            
            mostrarMensajeEfectividad(multiplicador);
        }

        objetivo.recibirDano(dmg);
    } else {
        if (mostrador) {
            mostrador->mostrarMensaje(atacante.getNombre() + " usa " + nombre + ".");
        }
    }
}

// OCP: Extensión de funcionalidad
MovimientoEstado::MovimientoEstado(std::string n, std::shared_ptr<TipoElemento> t, int p,
                                   const IMostrador* m,
                                   std::shared_ptr<ICalculadorDano> calcDano,
                                   std::shared_ptr<ICalculadorMultiplicador> calcMult)
    : Movimiento(n, t, p, m, calcDano, calcMult) {}

void MovimientoEstado::aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const {
    Movimiento::aplicarEfecto(atacante, objetivo);
    if (mostrador) {
        mostrador->mostrarMensaje(objetivo.getNombre() + " ha sido envenenado!", MAGENTA);
    }
}

// OCP: Extensión de funcionalidad
MovimientoCuracion::MovimientoCuracion(std::string n, std::shared_ptr<TipoElemento> t, int p, int curacion,
                                       const IMostrador* m)
    : Movimiento(n, t, p, m), cantidadCuracion(curacion) {}

void MovimientoCuracion::aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const {
    if (mostrador) {
        mostrador->mostrarMensaje(
            atacante.getNombre() + " usa " + nombre +
            " y se cura " + std::to_string(cantidadCuracion) + " puntos de vida!", GREEN
        );
    }
    atacante.curar(cantidadCuracion);
}