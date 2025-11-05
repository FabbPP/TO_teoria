#include "MovimientoMultiGolpe.h"
#include "Pokemon.h"
#include "global.h"

MovimientoMultiGolpe::MovimientoMultiGolpe(std::string n, std::shared_ptr<TipoElemento> t, int p, int golpes,
                                           const IMostrador* m,
                                           std::shared_ptr<ICalculadorDano> calcDano,
                                           std::shared_ptr<ICalculadorMultiplicador> calcMult)
    : Movimiento(n, t, p, m, calcDano, calcMult), numeroGolpes(golpes) {}

void MovimientoMultiGolpe::aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const {
    if (mostrador) {
        mostrador->mostrarMensaje(atacante.getNombre() + " usa " + nombre + "!");
    }
    
    // SRP: Delega cálculos a las clases responsables
    double multiplicador = obtenerMultiplicadorTipo(objetivo);
    
    int golpesAcertados = 0;
    for (int i = 0; i < numeroGolpes && objetivo.estaVivo(); ++i) {
        // DIP: Usa abstracción para calcular daño
        int dmg = calculadorDano->calcularDano(
            atacante.getAtaque(),
            potencia,
            objetivo.getDefensa(),
            multiplicador
        );
        
        if (mostrador) {
            mostrador->mostrarMensaje(
                "¡Golpe " + std::to_string(i + 1) + "! Causa " + std::to_string(dmg) + " de dano.", 
                YELLOW
            );
        }
        
        objetivo.recibirDano(dmg);
        golpesAcertados++;
    }
    
    if (mostrador) {
        mostrador->mostrarMensaje(
            "¡" + std::to_string(golpesAcertados) + " golpes acertados!", 
            GREEN
        );
        
        mostrarMensajeEfectividad(multiplicador);
    }
}