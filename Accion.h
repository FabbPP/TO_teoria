#ifndef ACCION_H
#define ACCION_H

#include <string>

class Pokemon;

class Accion {
protected:
    std::string nombre;

public:
    Accion(const std::string& nombre);
    virtual ~Accion() = default;

    virtual void ejecutar(Pokemon& caster, Pokemon& target) = 0;

    std::string getNombre() const;
};

#endif // ACCION_H