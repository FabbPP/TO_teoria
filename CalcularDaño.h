#ifndef CALCULADORDANO_H
#define CALCULADORDANO_H

#include "Pokemon.h"
#include "Movimiento.h"

struct ResultadoDano {
    int dano;
    bool esCritico;
    double efectividad;
};

class CalculadorDano {
private:
    double probabilidadCritico;
    double multiplicadorCritico;

public:
    CalculadorDano(double probCritico = 0.0625, double multCritico = 1.5);
   
    ResultadoDano calcularDano(
        const Pokemon& atacante, 
        const Pokemon& objetivo, 
        const Movimiento& movimiento
    ) const;
    
    // Calcula solo el daño base
    int calcularDanoBase(
        const Pokemon& atacante, 
        const Pokemon& objetivo, 
        int potenciaMovimiento
    ) const;
    
    // Calcula la efectividad de tipos
    double calcularEfectividadTotal(
        Tipo tipoAtaque, 
        const std::vector<Tipo>& tiposDefensor
    ) const;
    
    // Determina si el ataque es crítico
    bool esCritico() const;
    
    // Getters y setters
    void setProbabilidadCritico(double prob);
    void setMultiplicadorCritico(double mult);
    double getProbabilidadCritico() const;
    double getMultiplicadorCritico() const;
};

#endif // CALCULADORDANO_H