#ifndef UI_H
#define UI_H

#include <string>
#include <vector>
#include <memory>
#include "Entrenador.h"
#include "Pokemon.h"
#include "Item.h"
#include "global.h"


class UI {
public:
    // Métodos para mostrar información
    void mostrarEstado(const Pokemon* jugador, const Pokemon* rival) const;
    void mostrarEquipo(const Entrenador& entrenador) const;
    void mostrarBolsa(const Entrenador& entrenador) const;
    void mostrarMensaje(const std::string& mensaje, const std::string& color = RESET) const;
    void mostrarMensajeCombate(const std::string& mensaje) const;

    // Métodos para obtener la entrada del usuario
    int obtenerEleccion(const std::string& mensaje, int maxOpciones) const;
    int obtenerOpcionLucha(const Pokemon& pokemon) const;
};

#endif // UI_H