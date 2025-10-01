#ifndef ENTRENADOR_H
#define ENTRENADOR_H

#include <string>
#include <vector>
#include <memory> 
#include "Pokemon.h"
#include "Item.h"

class Entrenador {
private:
    std::string nombre;
    std::vector<std::unique_ptr<Pokemon>> equipo;
    std::vector<std::unique_ptr<Item>> bolsa;

public:
    Entrenador(std::string n);

    // Getters 
    const std::string getNombre() const;
    const std::vector<std::unique_ptr<Pokemon>>& getEquipo() const;
    const std::vector<std::unique_ptr<Item>>& getBolsa() const;

    void agregarPokemon(std::unique_ptr<Pokemon> p);
    void agregarItem(std::unique_ptr<Item> i);
    bool tienePokemonVivo() const;
    void mostrarEquipo() const;
    void mostrarBolsa() const;
};

#endif // ENTRENADOR_H