#include "Movimiento.h"
#include "Pokemon.h"
#include "UI.h"
#include "global.h"
#include <iostream>
#include <vector>
#include <cmath>

UI ui;

Movimiento::Movimiento(std::string n, std::shared_ptr<TipoElemento> t, int p)
    : nombre(n), tipo(t), potencia(p) {}

std::string Movimiento::getNombre() const { return nombre; }
std::shared_ptr<TipoElemento> Movimiento::getTipo() const { return tipo; }
int Movimiento::getPotencia() const { return potencia; }

// Cálculo de daño según fuerza del tipo
void Movimiento::aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const {
    if (potencia > 0) {
        
        double fuerzaTipo = tipo->getFuerza();
        double multiplicador = fuerzaTipo / 50.0; // 50.0 = base neutral
        if (multiplicador < 0.5) multiplicador = 0.5; 
        if (multiplicador > 2.0) multiplicador = 2.0;

        int dmg = static_cast<int>(
            (atacante.getAtaque() + potencia) * multiplicador - (objetivo.getDefensa() / 2)
        );
        if (dmg < 1) dmg = 1;

        ui.mostrarMensajeCombate(
            atacante.getNombre() + " usa " + nombre + 
            " (" + tipo->getNombre() + ") y causa " + std::to_string(dmg) + 
            " de daño a " + objetivo.getNombre() + "."
        );

        if (multiplicador > 1.5)
            ui.mostrarMensaje("¡El tipo tiene gran fuerza!", YELLOW);
        else if (multiplicador < 0.8)
            ui.mostrarMensaje("El tipo no es muy fuerte...", CYAN);

        objetivo.recibirDaño(dmg);
    } else {
        ui.mostrarMensaje(atacante.getNombre() + " usa " + nombre + ".");
    }
}

MovimientoEstado::MovimientoEstado(std::string n, std::shared_ptr<TipoElemento> t, int p)
    : Movimiento(n, t, p) {}

void MovimientoEstado::aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const {
    Movimiento::aplicarEfecto(atacante, objetivo);
    ui.mostrarMensaje(objetivo.getNombre() + " ha sido envenenado!", MAGENTA);
}


MovimientoCuracion::MovimientoCuracion(std::string n, std::shared_ptr<TipoElemento> t, int p, int curacion)
    : Movimiento(n, t, p), cantidadCuracion(curacion) {}

void MovimientoCuracion::aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const {
    ui.mostrarMensaje(
        atacante.getNombre() + " usa " + nombre +
        " y se cura " + std::to_string(cantidadCuracion) + " puntos de vida!", GREEN
    );
    atacante.curar(cantidadCuracion);
}
