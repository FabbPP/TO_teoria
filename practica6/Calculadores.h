#ifndef CALCULADORES_H
#define CALCULADORES_H

#include "Interfaces.h"

// SRP: Clase única responsabilidad - calcular daño base
class CalculadorDanoBasico : public ICalculadorDano {
public:
    int calcularDano(int ataqueBase, int potencia, int defensaObjetivo, double multiplicador) const override;
};

// SRP: Clase única responsabilidad - calcular multiplicador por fuerza
class CalculadorMultiplicadorFuerza : public ICalculadorMultiplicador {
private:
    double limiteMinimo;
    double limiteMaximo;
    
public:
    CalculadorMultiplicadorFuerza(double min = 0.5, double max = 2.0);
    double calcularMultiplicador(double fuerzaAtaque, double fuerzaDefensa) const override;
};

#endif // CALCULADORES_H
