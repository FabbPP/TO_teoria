#ifndef ENTRENADOR_H
#define ENTRENADOR_H

#include <string>
#include <vector>
#include "Pokemon.h"
#include "Item.h"

using namespace std;

class Entrenador {
private:
    string nombre;
    vector<Pokemon*> equipo;
    vector<Item*> bolsa;

public:
    Entrenador(string n);
    ~Entrenador(); // Destructor 

    // Getters
    string getNombre() const;
    vector<Pokemon*>& getEquipo();
    vector<Item*>& getBolsa();

    void agregarPokemon(Pokemon* p);
    void agregarItem(Item* i);
    bool tienePokemonVivo() const;
    void mostrarEquipo() const;
    void mostrarBolsa() const;
};

#endif // ENTRENADOR_H