#include "Movimiento.h"

// Golpea múltiples veces
class MovimientoMultiGolpe : public Movimiento {
private:
    int numeroGolpes;
    
public:
    MovimientoMultiGolpe(std::string n, Tipo t, int p, int golpes);
    
    void aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const override;
    
    int getNumeroGolpes() const { return numeroGolpes; }
};
