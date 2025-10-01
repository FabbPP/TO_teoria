#ifndef POKEMON_H
#define POKEMON_H

#include <string>
#include <vector>
#include "Movimiento.h"

class Pokemon {
private:
    std::string nombre;
    std::vector<Tipo> tipos; 
    std::vector<Movimiento> movimientos; 
    int vida;
    int vidaMax;
    int ataque;
    int defensa;

public:
    Pokemon(std::string n, std::vector<Tipo> t, int v, int a, int d);

    void agregarMovimiento(const Movimiento& m);
    Movimiento getMovimiento(int index) const;

    // Getters
    std::string getNombre() const;
    int getVida() const;
    int getVidaMax() const;
    int getAtaque() const;
    int getDefensa() const;
    bool estaVivo() const;

    void mostrar() const;
    void recibirDaño(int dmg);
    void curar(int cantidad);
    // void atacar(Pokemon& objetivo);

    const std::vector<Tipo>& getTipos() const;
    void atacar(Pokemon& objetivo, const Movimiento& movimiento);
};

#endif // POKEMON_H