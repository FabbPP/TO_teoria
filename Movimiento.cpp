#include "Movimiento.h"
#include <iostream>
#include <vector>

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

// Tabla de efectividad de tipos
double calcularEfectividad(Tipo atacante, Tipo defensor) {
    int indiceAtacante = static_cast<int>(atacante);
    int indiceDefensor = static_cast<int>(defensor);
    
    return tablaEfectividad[indiceAtacante][indiceDefensor];
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