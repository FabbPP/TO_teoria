#ifndef POKEMON_H
#define POKEMON_H

#include <string>
#include <vector>
#include <memory>
#include "Movimiento.h"
#include "TipoElemento.h"

class Pokemon {
private:
    std::string nombre;
    std::vector<std::shared_ptr<TipoElemento>> tipos; 
    std::vector<std::unique_ptr<Movimiento>> movimientos; 
    int vida;
    int vidaMax;
    int ataque;
    int defensa;

public:
    Pokemon(std::string n, std::vector<std::shared_ptr<TipoElemento>> t, int v, int a, int d);

    void agregarMovimiento(std::unique_ptr<Movimiento> m);
    const Movimiento& getMovimiento(int index) const;

    // Getters
    std::string getNombre() const;
    int getVida() const;
    int getVidaMax() const;
    int getAtaque() const;
    int getDefensa() const;
    bool estaVivo() const;
    void mostrar() const;
    void recibirDano(int dmg);
    void curar(int cantidad);

    const std::vector<std::shared_ptr<TipoElemento>>& getTipos() const;
    const std::vector<std::unique_ptr<Movimiento>>& getMovimientos() const;
    
    void atacar(Pokemon& objetivo, const Movimiento& movimiento);
};
#endif // POKEMON_H