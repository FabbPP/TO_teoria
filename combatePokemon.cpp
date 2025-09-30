#include <iostream>
#include <string>
#include <vector>
#include <cstdlib>
#include <ctime>
using namespace std;

#define RESET "\033[0m"
#define RED "\033[31m"
#define GREEN "\033[32m"
#define YELLOW "\033[33m"
#define BLUE "\033[34m"
#define MAGENTA "\033[35m"
#define CYAN "\033[36m"

// Clase Pokemon
class Pokemon {
private:
    string nombre;
    int vida;
    int vidaMax;
    int ataque;
    int defensa;

public:
    // Constructor
    Pokemon(string n, int v, int a, int d)
        : nombre(n), vida(v), vidaMax(v), ataque(a), defensa(d) {}

    string getNombre() const { return nombre; }
    int getVida() const { return vida; }
    int getVidaMax() const { return vidaMax; }
    int getAtaque() const { return ataque; }
    int getDefensa() const { return defensa; }
    bool estaVivo() const { return vida > 0; }

    void mostrar() const {
        cout << nombre << " [" << vida << "/" << vidaMax << "]";
    }

    void recibirDaño(int dmg) {
        vida -= dmg;
        if (vida < 0) vida = 0;
    }

    void curar(int cantidad) {
        vida += cantidad;
        if (vida > vidaMax) vida = vidaMax;
    }

    void atacar(Pokemon& objetivo) {
        int dmg = ataque - (objetivo.defensa / 2);
        if (dmg < 1) dmg = 1;
        cout << nombre << " ataca causando " << dmg << " de daño a " 
             << objetivo.nombre << "." << endl;
        objetivo.recibirDaño(dmg);
    }
};

// Clase Item
class Item {
private:
    string nombre;
    int efectoCuracion;
    int usos;

public:
    Item(string n, int e, int u) : nombre(n), efectoCuracion(e), usos(u) {}

    string getNombre() const { return nombre; }
    int getEfecto() const { return efectoCuracion; }
    int getUsos() const { return usos; }
    bool disponible() const { return usos > 0; }

    void usar(Pokemon& p) {
        if (usos > 0) {
            cout << GREEN << "Se usa " << nombre << " en " 
                 << p.getNombre() << " +" << efectoCuracion 
                 << " de vida!" << RESET << endl;
            p.curar(efectoCuracion);
            usos--;
        } else {
            cout << RED << "No quedan " << nombre << "!" << RESET << endl;
        }
    }

    void mostrar() const {
        cout << nombre << " (cura " << efectoCuracion 
             << ", usos: " << usos << ")";
    }
};

// Clase Entrenador
class Entrenador {
private:
    string nombre;
    vector<Pokemon*> equipo;
    vector<Item*> bolsa;

public:
    Entrenador(string n) : nombre(n) {}

    string getNombre() const { return nombre; }
    vector<Pokemon*>& getEquipo() { return equipo; }
    vector<Item*>& getBolsa() { return bolsa; }

    void agregarPokemon(Pokemon* p) { equipo.push_back(p); }
    void agregarItem(Item* i) { bolsa.push_back(i); }

    bool tienePokemonVivo() const {
        for (auto p : equipo) if (p->estaVivo()) return true;
        return false;
    }

    void mostrarEquipo() const {
        cout << YELLOW << nombre << " - Equipo:" << RESET << endl;
        for (size_t i = 0; i < equipo.size(); i++) {
            cout << i + 1 << ". ";
            equipo[i]->mostrar();
            cout << (equipo[i]->estaVivo() ? "" : " (Debilitado)") << endl;
        }
    }

    void mostrarBolsa() const {
        cout << MAGENTA << "Bolsa de " << nombre << ":" << RESET << endl;
        for (size_t i = 0; i < bolsa.size(); i++) {
            cout << i + 1 << ". ";
            bolsa[i]->mostrar();
            cout << endl;
        }
    }
};

int simularMovimientoPorValor(Pokemon atacante, Pokemon defensor) {
    int dmg = atacante.getAtaque() - (defensor.getDefensa() / 2);
    if (dmg < 1) dmg = 1;
    return dmg;
}

void ejecutarCombatePorReferencia(Pokemon& atacante, Pokemon& defensor) {
    atacante.atacar(defensor);
}

void usarItemPorPuntero(Pokemon* p, Item* item) {
    item->usar(*p);
}


int main() {
    srand(time(0));

    Entrenador* jugador = new Entrenador("Ash");
    Entrenador* rival = new Entrenador("Lance");

    jugador->agregarPokemon(new Pokemon("Pikachu", 80, 25, 5));
    jugador->agregarPokemon(new Pokemon("Charmander", 95, 22, 6));
    jugador->agregarPokemon(new Pokemon("Bulbasaur", 100, 18, 8));
    jugador->agregarItem(new Item("Pocion", 30, 3));

    rival->agregarPokemon(new Pokemon("Dragonite", 110, 24, 9));
    rival->agregarPokemon(new Pokemon("Gyarados", 105, 21, 7));
    rival->agregarPokemon(new Pokemon("Aerodactyl", 100, 20, 6));

    cout << GREEN << "=== Combate Pokemon 3 vs 3! ===" << RESET << endl;
    jugador->mostrarEquipo();
    rival->mostrarEquipo();

    // Eleccion inicial
    cout << CYAN << "Elige tu Pokemon inicial:" << RESET << endl;
    int idxInicial;
    Pokemon* activo;
    do {
        cout << "Numero: ";
        cin >> idxInicial;
        idxInicial--;
        if (idxInicial >= 0 && idxInicial < jugador->getEquipo().size() && jugador->getEquipo()[idxInicial]->estaVivo()) {
            activo = jugador->getEquipo()[idxInicial];
            break;
        } else {
            cout << "Invalido, intenta de nuevo." << endl;
        }
    } while (true);
    cout << CYAN << "¡Adelante, " << activo->getNombre() << "!" << RESET << endl;

    Pokemon* rivalActivo = rival->getEquipo()[rand() % 3];
    cout << CYAN << rival->getNombre() << " envia a " << rivalActivo->getNombre() << "!" << RESET << endl;

    int turno = 1;
    
    while (jugador->tienePokemonVivo() && rival->tienePokemonVivo()) {
        cout << BLUE << "\n--- Turno " << turno++ << " ---" << RESET << endl;
        cout << GREEN; activo->mostrar(); 
        cout << "  vs  ";  
        rivalActivo->mostrar(); cout << RESET << endl;

        cout << "Accion: (1)Luchar (2)Bolsa (3)Pokemon (4)Preview -> ";
        int accion; cin >> accion;

        if (accion == 1) {
            // Llamar a la funcion que recibe por referencia
            cout << "--- Antes de la funcion por referencia ---" << endl;
            rivalActivo->mostrar(); cout << endl;
            ejecutarCombatePorReferencia(*activo, *rivalActivo);
            cout << "--- Despues de la funcion por referencia ---" << endl;
            rivalActivo->mostrar(); cout << endl;
        } else if (accion == 2) {
            // Llamar a la funcion que recibe un puntero
            jugador->mostrarBolsa();
            int op; cout << "Elige item: "; cin >> op; op--;
            if (op >= 0 && op < jugador->getBolsa().size()) {
                cout << "--- Antes de la funcion por puntero ---" << endl;
                activo->mostrar(); cout << endl;
                usarItemPorPuntero(activo, jugador->getBolsa()[op]);
                cout << "--- Despues de la funcion por puntero ---" << endl;
                activo->mostrar(); cout << endl;
            }
        } else if (accion == 3) {
            jugador->mostrarEquipo();
            int op; cout << "Elige Pokemon: "; cin >> op; op--;
            if (op >= 0 && op < jugador->getEquipo().size() && jugador->getEquipo()[op]->estaVivo())
                activo = jugador->getEquipo()[op];
        } else if (accion == 4) {
            // Llamar a la funcion que recibe por valor
            cout << "--- Llamada a la funcion por valor ---" << endl;
            cout << "Daño estimado (sin modificar objetos): " << simularMovimientoPorValor(*activo, *rivalActivo) << endl;
            cout << "--- Los objetos no se han modificado ---" << endl;
        }

        // Logica de combate y cambio de Pokemon si se debilitan
        if (!rivalActivo->estaVivo()) {
            cout << RED << rivalActivo->getNombre() << " se ha debilitado!" << RESET << endl;
            if (rival->tienePokemonVivo()) {
                for (auto p : rival->getEquipo()) {
                    if (p->estaVivo()) {
                        rivalActivo = p;
                        cout << CYAN << rival->getNombre() << " envia a " << rivalActivo->getNombre() << "!" << RESET << endl;
                        break;
                    }
                }
            } else {
                cout << GREEN << "¡Has ganado el combate!" << RESET << endl;
                break;
            }
        }

        if (rivalActivo->estaVivo()) {
            cout << YELLOW << rival->getNombre() << " ataca..." << RESET << endl;
            rivalActivo->atacar(*activo);
        }

        if (!activo->estaVivo()) {
            cout << RED << activo->getNombre() << " se ha debilitado!" << RESET << endl;
            if (jugador->tienePokemonVivo()) {
                do {
                    jugador->mostrarEquipo();
                    cout << "Elige un nuevo Pokemon: ";
                    int idx; cin >> idx; idx--;
                    if (idx >= 0 && idx < jugador->getEquipo().size() && jugador->getEquipo()[idx]->estaVivo()) {
                        activo = jugador->getEquipo()[idx];
                        cout << CYAN << "¡Ve, " << activo->getNombre() << "!" << RESET << endl;
                        break;
                    }
                } while (true);
            } else {
                cout << RED << "¡Has perdido el combate!" << RESET << endl;
                break;
            }
        }
    }

    for (auto p : jugador->getEquipo()) {
        delete p;
    }
    for (auto i : jugador->getBolsa()) {
        delete i;
    }
    for (auto p : rival->getEquipo()) {
        delete p;
    }
    delete jugador;
    delete rival;

    return 0;
}