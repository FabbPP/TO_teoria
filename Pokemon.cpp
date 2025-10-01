#include "Pokemon.h"

// Métodos
Pokemon::Pokemon(string n, int v, int a, int d)
    : nombre(n), vida(v), vidaMax(v), ataque(a), defensa(d) {}

string Pokemon::getNombre() const { return nombre; }
int Pokemon::getVida() const { return vida; }
int Pokemon::getVidaMax() const { return vidaMax; }
int Pokemon::getAtaque() const { return ataque; }
int Pokemon::getDefensa() const { return defensa; }
bool Pokemon::estaVivo() const { return vida > 0; }

void Pokemon::mostrar() const {
    cout << nombre << " [" << vida << "/" << vidaMax << "]";
}

void Pokemon::recibirDaño(int dmg) {
    vida -= dmg;
    if (vida < 0) vida = 0;
}

void Pokemon::curar(int cantidad) {
    vida += cantidad;
    if (vida > vidaMax) vida = vidaMax;
}

void Pokemon::atacar(Pokemon& objetivo) {
    int dmg = ataque - (objetivo.defensa / 2);
    if (dmg < 1) dmg = 1;
    cout << nombre << " ataca causando " << dmg << " de daño a " 
         << objetivo.nombre << "." << endl;
    objetivo.recibirDaño(dmg);
}