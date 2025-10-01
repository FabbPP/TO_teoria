#include "Juego.h"
#include <iostream>
#include <cstdlib>
#include <ctime>
#include <limits>
#include "global.h"

Juego::Juego() {
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

Juego::~Juego() {
    delete jugador;
    delete rival;
}

void Juego::limpiarBuffer() {
    cin.ignore(numeric_limits<streamsize>::max(), '\n');
}

void Juego::mostrarEstado() const {
    cout << BLUE << "\n--- Estado del Combate ---" << RESET << endl;
    cout << "Tu ";
    pokemonActivoJugador->mostrar();
    cout << " vs. " << "Rival's ";
    pokemonActivoRival->mostrar();
    cout << RESET << endl;
}

Pokemon* Juego::elegirPokemon(Entrenador* entrenador) {
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

void Juego::accionRival() {
    if (pokemonActivoRival->getVida() <= pokemonActivoRival->getVidaMax() / 3) {
        for (auto item : rival->getBolsa()) {
            if (item->disponible()) {
                item->usar(*pokemonActivoRival);
                return;
            }
        }
    }
    
    cout << YELLOW << rival->getNombre() << " ataca..." << RESET << endl;
    pokemonActivoRival->atacar(*pokemonActivoJugador);
}

void Juego::iniciarCombate() {
    cout << GREEN << "=== Combate Pokemon 3 vs 3! ===" << RESET << endl;
    jugador->mostrarEquipo();
    rival->mostrarEquipo();
    
    cout << CYAN << "Elige tu Pokemon inicial:" << RESET << endl;
    pokemonActivoJugador = elegirPokemon(jugador);
    cout << CYAN << "¡Adelante, " << pokemonActivoJugador->getNombre() << "!" << RESET << endl;
    
    pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()];
    while (!pokemonActivoRival->estaVivo()) {
        pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()];
    }
    cout << CYAN << rival->getNombre() << " envia a " << pokemonActivoRival->getNombre() << "!" << RESET << endl;
    
    int turno = 1;
    while (jugador->tienePokemonVivo() && rival->tienePokemonVivo()) {
        cout << BLUE << "\n--- Turno " << turno++ << " ---" << RESET << endl;
        mostrarEstado();
        
        cout << "Accion: (1)Luchar (2)Bolsa (3)Pokemon -> ";
        int accion;
        cin >> accion;
        limpiarBuffer();
        
        switch (accion) {
            case 1:
                pokemonActivoJugador->atacar(*pokemonActivoRival);
                break;
            case 2: {
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
            case 3:
                pokemonActivoJugador = elegirPokemon(jugador);
                break;
            default:
                cout << "Accion invalida." << endl;
                break;
        }
        
        if (rival->tienePokemonVivo() && pokemonActivoRival->estaVivo()) {
            accionRival();
        }
        
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
    
    if (jugador->tienePokemonVivo()) {
        cout << GREEN << "¡Has ganado el combate!" << RESET << endl;
    } else {
        cout << RED << "¡Has perdido el combate!" << RESET << endl;
    }
}