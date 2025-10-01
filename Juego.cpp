#include "Juego.h"
#include <iostream>
#include <limits>
#include <cstdlib>
#include <ctime>
#include "global.h"

Juego::Juego() {
    jugador = new Entrenador("Ash");
    rival = new Entrenador("Lance");
    
    //Jugador
    Pokemon* pikachu = new Pokemon("Pikachu", {ELECTRICO}, 80, 25, 5);
    pikachu->agregarMovimiento(Movimiento("Impactrueno", ELECTRICO, 30));
    pikachu->agregarMovimiento(Movimiento("Placaje", NORMAL, 10)); // Un segundo movimiento
    jugador->agregarPokemon(pikachu);

    Pokemon* bulbasaur = new Pokemon("Bulbasaur", {PLANTA, VENENO}, 100, 18, 8);
    bulbasaur->agregarMovimiento(Movimiento("Latigo Cepa", PLANTA, 25));
    bulbasaur->agregarMovimiento(Movimiento("Bomba Lodo", VENENO, 30));
    jugador->agregarPokemon(bulbasaur);

    // Rival
    Pokemon* dragonite = new Pokemon("Dragonite", {DRAGON, VOLADOR}, 110, 24, 9);
    dragonite->agregarMovimiento(Movimiento("Dragoaliento", DRAGON, 35));
    rival->agregarPokemon(dragonite);
    
    Pokemon* gyarados = new Pokemon("Gyarados", {AGUA, VOLADOR}, 105, 21, 7);
    gyarados->agregarMovimiento(Movimiento("Cascada", AGUA, 30));
    rival->agregarPokemon(gyarados);
}

Juego::~Juego() {
    delete jugador;
    delete rival;
}

void Juego::limpiarBuffer() {
    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
}

void Juego::mostrarEstado() const {
    std::cout << BLUE << "\n--- Estado del Combate ---" << RESET << std::endl;
    std::cout << "Tu ";
    pokemonActivoJugador->mostrar();
    std::cout << " vs. " << "Rival's ";
    pokemonActivoRival->mostrar();
    std::cout << RESET << std::endl;
}

Pokemon* Juego::elegirPokemon(Entrenador* entrenador) {
    int idx;
    do {
        entrenador->mostrarEquipo();
        std::cout << "Elige un Pokemon: ";
        std::cin >> idx;
        limpiarBuffer();
        if (idx > 0 && static_cast<size_t>(idx) <= entrenador->getEquipo().size() && entrenador->getEquipo()[idx - 1]->estaVivo()) {
            return entrenador->getEquipo()[idx - 1];
        } else {
            std::cout << "Seleccion invalida. Intenta de nuevo." << std::endl;
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
    
    std::cout << YELLOW << rival->getNombre() << " ataca..." << RESET << std::endl;
    pokemonActivoRival->atacar(*pokemonActivoJugador, pokemonActivoRival->getMovimiento(0));
}

void Juego::iniciarCombate() {
    std::cout << GREEN << "=== Combate Pokemon 2 vs 2! ===" << RESET << std::endl;
    // jugador->mostrarEquipo();
    rival->mostrarEquipo();
    
    std::cout << CYAN << "Elige tu Pokemon inicial:" << RESET << std::endl;
    pokemonActivoJugador = elegirPokemon(jugador);
    std::cout << CYAN << "Adelante, " << pokemonActivoJugador->getNombre() << "!" << RESET << std::endl;
    
    pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()];
    while (!pokemonActivoRival->estaVivo()) {
        pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()];
    }
    std::cout << CYAN << rival->getNombre() << " envia a " << pokemonActivoRival->getNombre() << "!" << RESET << std::endl;
    
    int turno = 1;
    while (jugador->tienePokemonVivo() && rival->tienePokemonVivo()) {
        std::cout << BLUE << "\n--- Turno " << turno++ << " ---" << RESET << std::endl;
        mostrarEstado();
        
        std::cout << "Accion: (1)Luchar (2)Bolsa (3)Pokemon -> ";
        int accion;
        std::cin >> accion;
        limpiarBuffer();
        
        switch (accion) {
            case 1:
                pokemonActivoJugador->atacar(*pokemonActivoRival, pokemonActivoJugador->getMovimiento(0));
                break;
            case 2: {
                jugador->mostrarBolsa();
                int op;
                std::cout << "Elige item: ";
                std::cin >> op;
                limpiarBuffer();
                if (op > 0 && static_cast<size_t>(op) <= jugador->getBolsa().size()) {
                    Item* itemElegido = jugador->getBolsa()[op - 1];
                    itemElegido->usar(*pokemonActivoJugador);
                }
                break;
            }
            case 3:
                pokemonActivoJugador = elegirPokemon(jugador);
                break;
            default:
                std::cout << "Accion invalida." << std::endl;
                break;
        }
        
        if (rival->tienePokemonVivo() && pokemonActivoRival->estaVivo()) {
            accionRival();
        }
        
        if (!pokemonActivoRival->estaVivo()) {
            std::cout << RED << pokemonActivoRival->getNombre() << " se ha debilitado!" << RESET << std::endl;
            if (rival->tienePokemonVivo()) {
                pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()];
                while (!pokemonActivoRival->estaVivo()) {
                    pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()];
                }
                std::cout << CYAN << rival->getNombre() << " envia a " << pokemonActivoRival->getNombre() << "!" << RESET << std::endl;
            }
        }
        
        if (!pokemonActivoJugador->estaVivo()) {
            std::cout << RED << pokemonActivoJugador->getNombre() << " se ha debilitado!" << RESET << std::endl;
            if (jugador->tienePokemonVivo()) {
                std::cout << "Debes elegir un nuevo Pokemon." << std::endl;
                pokemonActivoJugador = elegirPokemon(jugador);
            }
        }
    }
    
    if (jugador->tienePokemonVivo()) {
        std::cout << GREEN << "Has ganado el combate!" << RESET << std::endl;
    } else {
        std::cout << RED << "Has perdido el combate!" << RESET << std::endl;
    }
}