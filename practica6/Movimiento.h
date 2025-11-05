#ifndef MOVIMIENTO_H
#define MOVIMIENTO_H

#include <string>
#include <memory>
#include <vector>
#include "TipoElemento.h"
#include "Interfaces.h"

class Pokemon;

// OCP: Abierto para extensión mediante herencia
// SRP: Responsabilidad única - representar un movimiento
// DIP: Depende de abstracciones (IMostrador, ICalculadorDano, ICalculadorMultiplicador)
class Movimiento {
protected:
    std::string nombre;
    std::shared_ptr<TipoElemento> tipo;
    int potencia;
    
    // DIP: Dependencias son abstracciones, no implementaciones concretas
    const IMostrador* mostrador;
    std::shared_ptr<ICalculadorDano> calculadorDano;
    std::shared_ptr<ICalculadorMultiplicador> calculadorMultiplicador;

public:
    Movimiento(std::string n, std::shared_ptr<TipoElemento> t, int p,
               const IMostrador* m,
               std::shared_ptr<ICalculadorDano> calcDano = nullptr,
               std::shared_ptr<ICalculadorMultiplicador> calcMult = nullptr);
    
    virtual ~Movimiento() = default;

    std::string getNombre() const;
    std::shared_ptr<TipoElemento> getTipo() const;
    int getPotencia() const;

    // OCP: Método virtual para extensión
    virtual void aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const;
    
protected:
    // SRP: Métodos auxiliares con responsabilidades específicas
    double obtenerMultiplicadorTipo(const Pokemon& objetivo) const;
    void mostrarMensajeEfectividad(double multiplicador) const;
};

// OCP: Extensión sin modificar la clase base
class MovimientoEstado : public Movimiento {
public:
    MovimientoEstado(std::string n, std::shared_ptr<TipoElemento> t, int p,
                     const IMostrador* m,
                     std::shared_ptr<ICalculadorDano> calcDano = nullptr,
                     std::shared_ptr<ICalculadorMultiplicador> calcMult = nullptr);
    
    void aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const override;
};

// OCP: Extensión sin modificar la clase base
class MovimientoCuracion : public Movimiento {
private:
    int cantidadCuracion;
    
public:
    MovimientoCuracion(std::string n, std::shared_ptr<TipoElemento> t, int p, int curacion,
                       const IMostrador* m);
    
    void aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const override;
};

#endif // MOVIMIENTO_H