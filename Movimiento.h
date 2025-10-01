#ifndef MOVIMIENTO_H
#define MOVIMIENTO_H

#include <string>
#include <vector>

class Pokemon; 

// Tipos de Pokémon
enum Tipo {
    NORMAL, FUEGO, AGUA, PLANTA, ELECTRICO, HIELO, LUCHA, VENENO,
    TIERRA, VOLADOR, PSIQUICO, BICHO, ROCA, FANTASMA, DRAGON, ACERO,
    SINIESTRO, HADA, NINGUNO 
};

class Movimiento {
protected: 
    std::string nombre;
    Tipo tipo;
    int potencia;

public:
    Movimiento(std::string n, Tipo t, int p);
    virtual ~Movimiento() = default; 

    std::string getNombre() const;
    Tipo getTipo() const;
    int getPotencia() const;

    virtual void aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const;
};

// Clases derivadas para movimientos con efectos especiales
class MovimientoEstado : public Movimiento {
public:
    MovimientoEstado(std::string n, Tipo t, int p);
    void aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const override;
};

class MovimientoCuracion : public Movimiento {
private:
    int cantidadCuracion;
public:
    MovimientoCuracion(std::string n, Tipo t, int p, int curacion);
    void aplicarEfecto(Pokemon& atacante, Pokemon& objetivo) const override;
};

// Función global para calcular la efectividad del tipo
double calcularEfectividad(Tipo atacante, Tipo defensor);

// Función global para obtener el nombre del tipo
std::string getNombreTipo(Tipo t);

#endif // MOVIMIENTO_H
