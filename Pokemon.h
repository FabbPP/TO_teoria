#ifndef POKEMON_H
#define POKEMON_H

#include <string>
#include <iostream>

using namespace std;

class Pokemon {
private:
    string nombre;
    int vida;
    int vidaMax;
    int ataque;
    int defensa;

public:
    Pokemon(string n, int v, int a, int d);

    // Getters
    string getNombre() const;
    int getVida() const;
    int getVidaMax() const;
    int getAtaque() const;
    int getDefensa() const;
    bool estaVivo() const;

    void mostrar() const;
    void recibirDaño(int dmg);
    void curar(int cantidad);
    void atacar(Pokemon& objetivo);
};

#endif // POKEMON_H