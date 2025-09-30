#include <iostream>
#include <string>
#include <vector>
#include <cstdlib>
#include <ctime>
#include <limits> // Necesario para la limpieza del buffer

using namespace std;

// Constantes
const string RESET = "\033[0m";
const string RED = "\033[31m";
const string GREEN = "\033[32m";
const string YELLOW = "\033[33m";
const string BLUE = "\033[34m";
const string MAGENTA = "\033[35m";
const string CYAN = "\033[36m";

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

    // Getters
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

    // Getters
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

    // Destructor para liberar la memoria
    ~Entrenador() {
        for (Pokemon* p : equipo) {
            delete p;
        }
        for (Item* i : bolsa) {
            delete i;
        }
    }

    // Getters
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

// Nueva clase para la lógica del juego
class Juego {
private:
    Entrenador* jugador;
    Entrenador* rival;
    Pokemon* pokemonActivoJugador;
    Pokemon* pokemonActivoRival;

    void limpiarBuffer() {
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
    }

    void mostrarEstado() const {
        cout << BLUE << "\n--- Estado del Combate ---" << RESET << endl;
        cout << "Tu ";
        pokemonActivoJugador->mostrar();
        cout << " vs. " << "Rival's ";
        pokemonActivoRival->mostrar();
        cout << RESET << endl;
    }

    Pokemon* elegirPokemon(Entrenador* entrenador) {
        int idx;
        do {
            entrenador->mostrarEquipo();
            cout << "Elige un Pokemon: ";
            cin >> idx;
            limpiarBuffer();
            if (idx > 0 && idx <= entrenador->getEquipo().size() && entrenador->getEquipo()[idx - 1]->estaVivo()) {
                return entrenador->getEquipo()[idx - 1];
            } else {
                cout << "Selección inválida. Intenta de nuevo." << endl;
            }
        } while (true);
    }
    
    // Simula una acción del rival (IA básica)
    void accionRival() {
        // Lógica de curación simple
        if (pokemonActivoRival->getVida() <= pokemonActivoRival->getVidaMax() / 3) {
            for (auto item : rival->getBolsa()) {
                if (item->disponible()) {
                    item->usar(*pokemonActivoRival);
                    return;
                }
            }
        }
        
        // Ataca si no hay pociones o la vida es suficiente
        cout << YELLOW << rival->getNombre() << " ataca..." << RESET << endl;
        pokemonActivoRival->atacar(*pokemonActivoJugador);
    }

public:
    Juego() {
        jugador = new Entrenador("Ash");
        rival = new Entrenador("Lance");
        
        // Inicialización de equipos
        jugador->agregarPokemon(new Pokemon("Pikachu", 80, 25, 5));
        jugador->agregarPokemon(new Pokemon("Charmander", 95, 22, 6));
        jugador->agregarPokemon(new Pokemon("Bulbasaur", 100, 18, 8));
        jugador->agregarItem(new Item("Pocion", 30, 3));
        
        rival->agregarPokemon(new Pokemon("Dragonite", 110, 24, 9));
        rival->agregarPokemon(new Pokemon("Gyarados", 105, 21, 7));
        rival->agregarPokemon(new Pokemon("Aerodactyl", 100, 20, 6));
    }

    ~Juego() {
        delete jugador;
        delete rival;
    }

    void iniciarCombate() {
        cout << GREEN << "=== Combate Pokemon 3 vs 3! ===" << RESET << endl;
        // jugador->mostrarEquipo();
        rival->mostrarEquipo();
        
        // Elección inicial
        cout << CYAN << "Elige tu Pokemon inicial:" << RESET << endl;
        pokemonActivoJugador = elegirPokemon(jugador);
        cout << CYAN << "¡Adelante, " << pokemonActivoJugador->getNombre() << "!" << RESET << endl;
        
        // Elección del rival
        pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()];
        cout << CYAN << rival->getNombre() << " envia a " << pokemonActivoRival->getNombre() << "!" << RESET << endl;
        
        int turno = 1;
        while (jugador->tienePokemonVivo() && rival->tienePokemonVivo()) {
            cout << BLUE << "\n--- Turno " << turno++ << " ---" << RESET << endl;
            mostrarEstado();
            
            cout << "Accion: (1)Luchar (2)Bolsa (3)Pokemon -> ";
            int accion;
            cin >> accion;
            limpiarBuffer();
            
            // Lógica del jugador
            switch (accion) {
                case 1: // Luchar
                    pokemonActivoJugador->atacar(*pokemonActivoRival);
                    break;
                case 2: { // Bolsa
                    jugador->mostrarBolsa();
                    int op;
                    cout << "Elige item: ";
                    cin >> op;
                    limpiarBuffer();
                    if (op > 0 && op <= jugador->getBolsa().size()) {
                        Item* itemElegido = jugador->getBolsa()[op - 1];
                        itemElegido->usar(*pokemonActivoJugador);
                    }
                    break;
                }
                case 3: // Cambiar Pokemon
                    pokemonActivoJugador = elegirPokemon(jugador);
                    break;
                default:
                    cout << "Accion invalida." << endl;
                    break;
            }
            
            // Lógica del rival
            if (rival->tienePokemonVivo() && pokemonActivoRival->estaVivo()) {
                accionRival();
            }
            
            // Manejo de debilitados
            if (!pokemonActivoRival->estaVivo()) {
                cout << RED << pokemonActivoRival->getNombre() << " se ha debilitado!" << RESET << endl;
                if (rival->tienePokemonVivo()) {
                    pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()];
                    while (!pokemonActivoRival->estaVivo()) {
                        pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()];
                    }
                    cout << CYAN << rival->getNombre() << " envia a " << pokemonActivoRival->getNombre() << "!" << RESET << endl;
                }
            }
            
            if (!pokemonActivoJugador->estaVivo()) {
                cout << RED << pokemonActivoJugador->getNombre() << " se ha debilitado!" << RESET << endl;
                if (jugador->tienePokemonVivo()) {
                    cout << "Debes elegir un nuevo Pokemon." << endl;
                    pokemonActivoJugador = elegirPokemon(jugador);
                }
            }
        }
        
        // Resultado del combate
        if (jugador->tienePokemonVivo()) {
            cout << GREEN << "¡Has ganado el combate!" << RESET << endl;
        } else {
            cout << RED << "¡Has perdido el combate!" << RESET << endl;
        }
    }
};

int main() {
    srand(time(0));
    
    Juego miJuego;
    miJuego.iniciarCombate();
    
    return 0;
}