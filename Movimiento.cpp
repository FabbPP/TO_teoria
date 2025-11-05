#include "Movimiento.h"
#include "Pokemon.h" 
#include "UI.h" 
#include "CalculadorDano.h"
#include <iostream>
#include <vector>
#include "global.h"

UI ui;
CalculadorDano calculadorDano; 

Movimiento::Movimiento(std::string n, Tipo t, int p)
    : nombre(n), tipo(t), potencia(p) {}

std::string Movimiento::getNombre() const { return nombre; }
Tipo Movimiento::getTipo() const { return tipo; }
int Movimiento::getPotencia() const { return potencia; }

const std::vector<std::vector<double>> tablaEfectividad = {
//               NOR  FUE  AGU  PLA  ELE  HIE  LUCH VEN  TIE  VOL  PSI  BIC  ROC  FAN  DRA  ACE  SIN  HAD
/* NORMAL   */ {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 0.0, 1.0, 0.5, 1.0, 1.0},
/* FUEGO    */ {1.0, 0.5, 0.5, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 0.5, 2.0, 1.0, 1.0},
/* AGUA     */ {1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.5, 1.0, 1.0, 1.0},
/* PLANTA   */ {1.0, 0.5, 2.0, 0.5, 1.0, 1.0, 1.0, 0.5, 2.0, 0.5, 1.0, 0.5, 2.0, 1.0, 0.5, 0.5, 1.0, 1.0},
/* ELECTRICO*/ {1.0, 1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 1.0, 0.0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0},
/* HIELO    */ {1.0, 0.5, 0.5, 2.0, 1.0, 0.5, 1.0, 1.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0},
/* LUCHA    */ {2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.5, 1.0, 0.5, 0.5, 0.5, 2.0, 0.0, 1.0, 2.0, 2.0, 0.5},
/* VENENO   */ {1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 0.5, 1.0, 1.0, 1.0, 0.5, 0.5, 1.0, 0.0, 1.0, 2.0},
/* TIERRA   */ {1.0, 2.0, 1.0, 0.5, 2.0, 1.0, 1.0, 2.0, 1.0, 0.0, 1.0, 0.5, 2.0, 1.0, 1.0, 2.0, 1.0, 1.0},
/* VOLADOR  */ {1.0, 1.0, 1.0, 2.0, 0.5, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0, 0.5, 1.0, 1.0},
/* PSIQUICO */ {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 0.5, 0.5, 1.0},
/* BICHO    */ {1.0, 0.5, 1.0, 2.0, 1.0, 1.0, 0.5, 0.5, 1.0, 0.5, 2.0, 1.0, 1.0, 0.5, 1.0, 0.5, 2.0, 0.5},
/* ROCA     */ {1.0, 2.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 0.5, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0},
/* FANTASMA */ {0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 1.0},
/* DRAGON   */ {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 0.0},
/* ACERO    */ {1.0, 0.5, 0.5, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 1.0, 2.0},
/* SINIESTRO*/ {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 0.5},
/* HADA     */ {1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 2.0, 1.0}
};

double calcularEfectividad(Tipo atacante, Tipo defensor) {
    int indiceAtacante = static_cast<int>(atacante);
    int indiceDefensor = static_cast<int>(defensor);
    
    return tablaEfectividad[indiceAtacante][indiceDefensor];
}

void Movimiento::aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const {
    if (potencia > 0) {
        //para calcular
        ResultadoDano resultado = calculadorDano.calcularDano(atacante, objetivo, *this);
        
        std::string mensaje = atacante.getNombre() + " usa " + this->getNombre();
        if (resultado.esCritico) {
            mensaje += " ¡Golpe critico¡";
        }
        mensaje += " y causa " + std::to_string(resultado.dano) + " de daño a " + objetivo.getNombre() + ".";
        ui.mostrarMensajeCombate(mensaje);
        ui.mostrarMensaje("--Calculamos efectividad",GREEN);
        if (resultado.efectividad >= 2.0) {
            ui.mostrarMensaje("Muye efectivo", YELLOW);
        } else if (resultado.efectividad < 1.0 && resultado.efectividad > 0.0) {
            ui.mostrarMensaje("no muy efectivo", CYAN);
        } else {
            ui.mostrarMensaje("no tiene efecto", CYAN);
        }
        objetivo.recibirDaño(resultado.dano);
    } else {
        ui.mostrarMensaje(atacante.getNombre() + " usa " + this->getNombre() + ".");
    }
}

// Implementación de MovimientoEstado
MovimientoEstado::MovimientoEstado(std::string n, Tipo t, int p)
    : Movimiento(n, t, p) {}
void MovimientoEstado::aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const {
    Movimiento::aplicarEfecto(atacante, objetivo); 
    ui.mostrarMensaje(objetivo.getNombre() + " ha sido envenenado!", MAGENTA);
}

// Implementación de MovimientoCuracion
MovimientoCuracion::MovimientoCuracion(std::string n, Tipo t, int p, int curacion)
    : Movimiento(n, t, p), cantidadCuracion(curacion) {}
void MovimientoCuracion::aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const {
    ui.mostrarMensaje(atacante.getNombre() + " usa " + this->getNombre() + " y se cura " + std::to_string(cantidadCuracion) + " puntos de vida!", GREEN);
    atacante.curar(cantidadCuracion);
}

// Función para obtener el nombre del tipo como string
std::string getNombreTipo(Tipo t) {
    switch (t) {
        case NORMAL: return "Normal";
        case FUEGO: return "Fuego";
        case AGUA: return "Agua";
        case PLANTA: return "Planta";
        case ELECTRICO: return "Eléctrico";
        case HIELO: return "Hielo";
        case LUCHA: return "Lucha";
        case VENENO: return "Veneno";
        case TIERRA: return "Tierra";
        case VOLADOR: return "Volador";
        case PSIQUICO: return "Psíquico";
        case BICHO: return "Bicho";
        case ROCA: return "Roca";
        case FANTASMA: return "Fantasma";
        case DRAGON: return "Dragón";
        case ACERO: return "Acero";
        case SINIESTRO: return "Siniestro";
        case HADA: return "Hada";
        default: return "Desconocido";
    }
}