#ifndef MOVIMIENTO_H
#define MOVIMIENTO_H

#include <string>
#include <memory>
#include <vector>
#include "TipoElemento.h"

class Pokemon;

class Movimiento {
protected:
    std::string nombre;
    std::shared_ptr<TipoElemento> tipo;
    int potencia;

public:
    Movimiento(std::string n, std::shared_ptr<TipoElemento> t, int p);
    virtual ~Movimiento() = default;

    std::string getNombre() const;
    std::shared_ptr<TipoElemento> getTipo() const;
    int getPotencia() const;

    virtual void aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const;
};

// Movimiento con efecto de estado
class MovimientoEstado : public Movimiento {
public:
    MovimientoEstado(std::string n, std::shared_ptr<TipoElemento> t, int p);
    void aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const override;
};

// Movimiento de curación
class MovimientoCuracion : public Movimiento {
private:
    int cantidadCuracion;
public:
    MovimientoCuracion(std::string n, std::shared_ptr<TipoElemento> t, int p, int curacion);
    void aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const override;
};

#endif // MOVIMIENTO_H
