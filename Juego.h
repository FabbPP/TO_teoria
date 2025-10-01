#ifndef JUEGO_H
#define JUEGO_H

#include <memory>
#include "Entrenador.h"
#include "Pokemon.h"
#include "Item.h"

class Juego {
private:
    std::unique_ptr<Entrenador> jugador;
    std::unique_ptr<Entrenador> rival;
    Pokemon* pokemonActivoJugador;
    Pokemon* pokemonActivoRival;

    // Métodos privados 
    void limpiarBuffer();
    void mostrarEstado() const;
    Pokemon* elegirPokemon(Entrenador* entrenador);
    void accionRival();

public:
    Juego();
    // ~Juego();
    void iniciarCombate();
};

#endif // JUEGO_H