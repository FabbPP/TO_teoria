#include "Pokemon.h"
#include <iostream>
#include "global.h"

// Implementación de métodos
Pokemon::Pokemon(std::string n, std::vector<Tipo> t, int v, int a, int d)
    : nombre(n), tipos(t), vida(v), vidaMax(v), ataque(a), defensa(d) {}

std::string Pokemon::getNombre() const { return nombre; }
int Pokemon::getVida() const { return vida; }
int Pokemon::getVidaMax() const { return vidaMax; }
int Pokemon::getAtaque() const { return ataque; }
int Pokemon::getDefensa() const { return defensa; }
bool Pokemon::estaVivo() const { return vida > 0; }

void Pokemon::agregarMovimiento(const Movimiento& m) { movimientos.push_back(m); }
Movimiento Pokemon::getMovimiento(int index) const { return movimientos.at(index); }

const std::vector<Tipo>& Pokemon::getTipos() const { return tipos; }

const std::vector<Movimiento>& Pokemon::getMovimientos() const {
    return movimientos;
}

void Pokemon::mostrar() const {
    std::cout << nombre << " [" << vida << "/" << vidaMax << "]";
}

void Pokemon::recibirDaño(int dmg) {
    vida -= dmg;
    if (vida < 0) vida = 0;
}

void Pokemon::curar(int cantidad) {
    vida += cantidad;
    if (vida > vidaMax) vida = vidaMax;
}

// void Pokemon::atacar(Pokemon& objetivo) {
//     int dmg = ataque - (objetivo.defensa / 2);
//     if (dmg < 1) dmg = 1;
//     cout << nombre << " ataca causando " << dmg << " de daño a " 
//          << objetivo.nombre << "." << endl;
//     objetivo.recibirDaño(dmg);
// }

void Pokemon::atacar(Pokemon& objetivo, const Movimiento& movimiento) {
    double efectividadTotal = 1.0;
    
    // Multiplica la efectividad para cada uno de los tipos
    for (Tipo tipoDefensor : objetivo.getTipos()) {
        efectividadTotal *= calcularEfectividad(movimiento.getTipo(), tipoDefensor);
    }
    
    int dmg = (ataque + movimiento.getPotencia()) - (objetivo.getDefensa() / 2);
    if (dmg < 1) dmg = 1;

    dmg *= efectividadTotal;

    std::cout << nombre << " usa " << movimiento.getNombre() << " y causa " << dmg << " de danio a " 
              << objetivo.getNombre() << "." << std::endl;
    
    // Mensajes de efectividad
    if (efectividadTotal >= 2.0) {
        std::cout << YELLOW << "Es super efectivo!" << RESET << std::endl;
    } else if (efectividadTotal < 1.0 && efectividadTotal > 0.0) {
        std::cout << CYAN << "No es muy efectivo..." << RESET << std::endl;
    } else if (efectividadTotal == 0.0) {
        std::cout << CYAN << "No tiene efecto..." << RESET << std::endl;
    }

    objetivo.recibirDaño(dmg);
}