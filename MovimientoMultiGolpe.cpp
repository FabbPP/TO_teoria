#include "MovimientoMultiGolpe.h"
#include "Pokemon.h"
#include "UI.h"
#include <iostream>

extern UI ui;

MovimientoMultiGolpe::MovimientoMultiGolpe(std::string n, Tipo t, int p, int golpes)
    : Movimiento(n, t, p), numeroGolpes(golpes) {}

void MovimientoMultiGolpe::aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const {
    ui.mostrarMensaje(atacante.getNombre() + " usa " + this->getNombre() + "!");
    
    int golpesAcertados = 0;
    for (int i = 0; i < numeroGolpes && objetivo.estaVivo(); ++i) {

        double efectividadTotal = 1.0;
        for (Tipo tipoDefensor : objetivo.getTipos()) {
            efectividadTotal *= calcularEfectividad(this->getTipo(), tipoDefensor);
        }
        
        int dmg = (atacante.getAtaque() + this->getPotencia()) - (objetivo.getDefensa() / 2);
        if (dmg < 1) dmg = 1;
        dmg *= efectividadTotal;
        
        ui.mostrarMensaje("¡Golpe " + std::to_string(i + 1) + "! Causa " + std::to_string(dmg) + " de daño.", YELLOW);
        objetivo.recibirDaño(dmg);
        golpesAcertados++;
    }
    
    ui.mostrarMensaje("¡" + std::to_string(golpesAcertados) + " golpes acertados!", GREEN);
    
    double efectividadTotal = 1.0;
    for (Tipo tipoDefensor : objetivo.getTipos()) {
        efectividadTotal *= calcularEfectividad(this->getTipo(), tipoDefensor);
    }
    
    if (efectividadTotal >= 2.0) {
        ui.mostrarMensaje("Es efectivo", YELLOW);
    } else if (efectividadTotal < 1.0 && efectividadTotal > 0.0) {
        ui.mostrarMensaje("No es efectivo", CYAN);
    }
}