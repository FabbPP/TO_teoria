#include "Entrenador.h"
#include <iostream>
#include "global.h"

Entrenador::Entrenador(string n) : nombre(n) {}

// Entrenador::~Entrenador() {
//     for (Pokemon* p : equipo) {
//         delete p;
//     }
//     for (Item* i : bolsa) {
//         delete i;
//     }
// }

const std::string Entrenador::getNombre() const { return nombre; }
const std::vector<std::unique_ptr<Pokemon>>& Entrenador::getEquipo() const { return equipo; }
const std::vector<std::unique_ptr<Item>>& Entrenador::getBolsa() const { return bolsa; }

void Entrenador::agregarPokemon(std::unique_ptr<Pokemon> p) { 
    equipo.push_back(std::move(p)); 
}
void Entrenador::agregarItem(std::unique_ptr<Item> i) { 
    bolsa.push_back(std::move(i)); 
}

bool Entrenador::tienePokemonVivo() const {
    for (const auto& p : equipo) if (p->estaVivo()) return true;
    return false;
}

void Entrenador::mostrarEquipo() const {
    std::cout << YELLOW << nombre << " - Equipo:" << RESET << std::endl;
    for (size_t i = 0; i < equipo.size(); i++) {
        std::cout << i + 1 << ". ";
        equipo[i]->mostrar();
        std::cout << (equipo[i]->estaVivo() ? "" : " (Debilitado)") << std::endl;
    }
}

void Entrenador::mostrarBolsa() const {
    std::cout << MAGENTA << "Bolsa de " << nombre << ":" << RESET << std::endl;
    for (size_t i = 0; i < bolsa.size(); i++) {
        std::cout << i + 1 << ". ";
        bolsa[i]->mostrar();
        std::cout << std::endl;
    }
}