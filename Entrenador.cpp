#include "Entrenador.h"
#include <iostream>
#include "global.h"

Entrenador::Entrenador(string n) : nombre(n) {}

Entrenador::~Entrenador() {
    for (Pokemon* p : equipo) {
        delete p;
    }
    for (Item* i : bolsa) {
        delete i;
    }
}

string Entrenador::getNombre() const { return nombre; }
vector<Pokemon*>& Entrenador::getEquipo() { return equipo; }
vector<Item*>& Entrenador::getBolsa() { return bolsa; }

void Entrenador::agregarPokemon(Pokemon* p) { equipo.push_back(p); }
void Entrenador::agregarItem(Item* i) { bolsa.push_back(i); }

bool Entrenador::tienePokemonVivo() const {
    for (auto p : equipo) if (p->estaVivo()) return true;
    return false;
}

void Entrenador::mostrarEquipo() const {
    cout << YELLOW << nombre << " - Equipo:" << RESET << endl;
    for (size_t i = 0; i < equipo.size(); i++) {
        cout << i + 1 << ". ";
        equipo[i]->mostrar();
        cout << (equipo[i]->estaVivo() ? "" : " (Debilitado)") << endl;
    }
}

void Entrenador::mostrarBolsa() const {
    cout << MAGENTA << "Bolsa de " << nombre << ":" << RESET << endl;
    for (size_t i = 0; i < bolsa.size(); i++) {
        cout << i + 1 << ". ";
        bolsa[i]->mostrar();
        cout << endl;
    }
}