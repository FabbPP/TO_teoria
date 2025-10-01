#include "UI.h"
#include <iostream>
#include <limits>

void UI::mostrarEstado(const Pokemon* jugador, const Pokemon* rival) const {
    std::cout << BLUE << "\n--- Estado del Combate ---" << RESET << std::endl;
    std::cout << "Tu ";
    jugador->mostrar();
    std::cout << " vs. " << "Rival's ";
    rival->mostrar();
    std::cout << RESET << std::endl;
}

void UI::mostrarEquipo(const Entrenador& entrenador) const {
    std::cout << YELLOW << entrenador.getNombre() << " - Equipo:" << RESET << std::endl;
    for (size_t i = 0; i < entrenador.getEquipo().size(); ++i) {
        std::cout << i + 1 << ". ";
        entrenador.getEquipo()[i]->mostrar();
        std::cout << (entrenador.getEquipo()[i]->estaVivo() ? "" : " (Debilitado)") << std::endl;
    }
}

void UI::mostrarBolsa(const Entrenador& entrenador) const {
    std::cout << MAGENTA << "Bolsa de " << entrenador.getNombre() << ":" << RESET << std::endl;
    for (size_t i = 0; i < entrenador.getBolsa().size(); ++i) {
        std::cout << i + 1 << ". ";
        entrenador.getBolsa()[i]->mostrar();
        std::cout << std::endl;
    }
}

void UI::mostrarMensaje(const std::string& mensaje, const std::string& color) const {
    std::cout << color << mensaje << RESET << std::endl;
}

void UI::mostrarMensajeCombate(const std::string& mensaje) const {
    std::cout << mensaje << std::endl;
}

int UI::obtenerEleccion(const std::string& mensaje, int maxOpciones) const {
    int eleccion;
    while (true) {
        std::cout << mensaje;
        std::cin >> eleccion;
        if (std::cin.fail() || eleccion < 1 || eleccion > maxOpciones) {
            std::cout << "Opcion invalida. Intenta de nuevo." << std::endl;
            std::cin.clear();
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        } else {
            return eleccion;
        }
    }
}

int UI::obtenerOpcionLucha(const Pokemon& pokemon) const {
    std::cout << "Elige un movimiento:" << std::endl;
    for (size_t i = 0; i < pokemon.getMovimientos().size(); ++i) {
        std::cout << i + 1 << ". " << pokemon.getMovimiento(i).getNombre() << std::endl;
    }
    return obtenerEleccion("Elige un movimiento: ", pokemon.getMovimientos().size());
}