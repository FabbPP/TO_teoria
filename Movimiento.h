#ifndef MOVIMIENTO_H
#define MOVIMIENTO_H

#include <string>
#include <vector>

// Tipos de Pokémon
enum Tipo {
    NORMAL, FUEGO, AGUA, PLANTA, ELECTRICO, HIELO, LUCHA, VENENO,
    TIERRA, VOLADOR, PSIQUICO, BICHO, ROCA, FANTASMA, DRAGON, ACERO,
    SINIESTRO, HADA, NINGUNO 
};

class Movimiento {
private:
    std::string nombre;
    Tipo tipo;
    int potencia;

public:
    Movimiento(std::string n, Tipo t, int p);

    std::string getNombre() const;
    Tipo getTipo() const;
    int getPotencia() const;
};

// Función global para calcular la efectividad del tipo
double calcularEfectividad(Tipo atacante, Tipo defensor);

// Función global para obtener el nombre del tipo
std::string getNombreTipo(Tipo t);

#endif // MOVIMIENTO_H