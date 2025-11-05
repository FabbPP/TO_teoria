#include "Calculadores.h"
#include <algorithm>

int CalculadorDanoBasico::calcularDano(int ataqueBase, int potencia, int defensaObjetivo, double multiplicador) const {
    int dmgBase = (ataqueBase + potencia) - (defensaObjetivo / 2);
    if (dmgBase < 1) dmgBase = 1;
    
    int dmg = static_cast<int>(dmgBase * multiplicador);
    if (dmg < 1) dmg = 1;
    
    return dmg;
}

CalculadorMultiplicadorFuerza::CalculadorMultiplicadorFuerza(double min, double max)
    : limiteMinimo(min), limiteMaximo(max) {}

double CalculadorMultiplicadorFuerza::calcularMultiplicador(double fuerzaAtaque, double fuerzaDefensa) const {
    if (fuerzaDefensa == 0) return 1.0;
    
    double multiplicador = fuerzaAtaque / fuerzaDefensa;
    
    // Limitar entre mínimo y máximo
    multiplicador = std::max(limiteMinimo, std::min(multiplicador, limiteMaximo));
    
    return multiplicador;
}