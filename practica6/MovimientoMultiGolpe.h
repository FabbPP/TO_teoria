#ifndef MOVIMIENTO_MULTIGOLPE_H
#define MOVIMIENTO_MULTIGOLPE_H

#include "Movimiento.h"

// OCP: Extensión de Movimiento sin modificar la clase base
// LSP: Puede sustituir a Movimiento sin problemas
class MovimientoMultiGolpe : public Movimiento {
private:
    int numeroGolpes;
    
public:
    MovimientoMultiGolpe(std::string n, std::shared_ptr<TipoElemento> t, int p, int golpes,
                         const IMostrador* m,
                         std::shared_ptr<ICalculadorDano> calcDano = nullptr,
                         std::shared_ptr<ICalculadorMultiplicador> calcMult = nullptr);
    
    // LSP: Override correcto manteniendo el contrato
    void aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const override;
    
    int getNumeroGolpes() const { return numeroGolpes; }
};

#endif // MOVIMIENTO_MULTIGOLPE_H
