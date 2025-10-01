#include "Juego.h"
#include <iostream>
#include <limits>
#include <cstdlib>
#include <ctime>
#include "global.h"

Juego::Juego() {
    srand(static_cast<unsigned int>(time(0)));

    jugador = std::make_unique<Entrenador>("Ash");
    rival = std::make_unique<Entrenador>("Lance");
    
    //Jugador
    auto pikachu = std::make_unique<Pokemon>("Pikachu", std::vector<Tipo>{ELECTRICO}, 80, 25, 5);
    pikachu->agregarMovimiento(Movimiento("Impactrueno", ELECTRICO, 30));
    pikachu->agregarMovimiento(Movimiento("Placaje", NORMAL, 10));
    jugador->agregarPokemon(std::move(pikachu));

    auto charmander = std::make_unique<Pokemon>("Charmander", std::vector<Tipo>{FUEGO}, 95, 22, 6);
    charmander->agregarMovimiento(Movimiento("Llamarada", FUEGO, 40));
    charmander->agregarMovimiento(Movimiento("Araniazo", NORMAL, 10));
    jugador->agregarPokemon(std::move(charmander));

    auto bulbasaur = std::make_unique<Pokemon>("Bulbasaur", std::vector<Tipo>{PLANTA, VENENO}, 100, 18, 8);
    bulbasaur->agregarMovimiento(Movimiento("Latigo Cepa", PLANTA, 25));
    bulbasaur->agregarMovimiento(Movimiento("Bomba Lodo", VENENO, 30));
    jugador->agregarPokemon(std::move(bulbasaur));

    // Rival
    auto gyarados = std::make_unique<Pokemon>("Gyarados", std::vector<Tipo>{AGUA, VOLADOR}, 105, 21, 7);
    gyarados->agregarMovimiento(Movimiento("Cascada", AGUA, 30));
    gyarados->agregarMovimiento(Movimiento("Mordisco", SINIESTRO, 20));
    rival->agregarPokemon(std::move(gyarados));
    
    auto dragonite = std::make_unique<Pokemon>("Dragonite", std::vector<Tipo>{DRAGON, VOLADOR}, 110, 24, 9);
    dragonite->agregarMovimiento(Movimiento("Dragoaliento", DRAGON, 35));
    dragonite->agregarMovimiento(Movimiento("Punio Fuego", FUEGO, 30));
    rival->agregarPokemon(std::move(dragonite));

    auto onix = std::make_unique<Pokemon>("Onix", std::vector<Tipo>{ROCA, TIERRA}, 120, 15, 12);
    onix->agregarMovimiento(Movimiento("Lanzarrocas", ROCA, 25));
    onix->agregarMovimiento(Movimiento("Excavar", TIERRA, 30));
    rival->agregarPokemon(std::move(onix));

    // Agregar items a las bolsas
    jugador->agregarItem(std::make_unique<Item>("Pocion", 30, 3));
    rival->agregarItem(std::make_unique<Item>("Pocion", 30, 3));
}

// Juego::~Juego() {
//     delete jugador;
//     delete rival;
// }

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
            return entrenador->getEquipo()[idx - 1].get();
        } else {
            std::cout << "Seleccion invalida. Intenta de nuevo." << std::endl;
        }
    } while (true);
}

void Juego::accionRival() {
    if (pokemonActivoRival->getVida() <= pokemonActivoRival->getVidaMax() / 3) {
        for (const auto& item : rival->getBolsa()) {
            if (item->disponible()) {
                item->usar(*pokemonActivoRival);
                return;
            }
        }
    }
    
    std::cout << YELLOW << rival->getNombre() << " ataca..." << RESET << std::endl;
    // La IA del rival usa un movimiento aleatorio
    int numMovimientos = pokemonActivoRival->getMovimientos().size();
    int movimientoElegido = rand() % numMovimientos;
    pokemonActivoRival->atacar(*pokemonActivoJugador, pokemonActivoRival->getMovimiento(movimientoElegido));
}

void Juego::iniciarCombate() {
    std::cout << GREEN << "=== Combate Pokemon 3 vs 3! ===" << RESET << std::endl;
    // jugador->mostrarEquipo();
    rival->mostrarEquipo();
    
    std::cout << CYAN << "Elige tu Pokemon inicial:" << RESET << std::endl;
    pokemonActivoJugador = elegirPokemon(jugador.get());
    std::cout << CYAN << "¡Adelante, " << pokemonActivoJugador->getNombre() << "!" << RESET << std::endl;
    
    pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()].get();
    while (!pokemonActivoRival->estaVivo()) {
        pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()].get();
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
            case 1: {
                std::cout << "Elige un movimiento:" << std::endl;
                // Mostrar los movimientos del Pokemon activo para que el jugador elija
                for (size_t i = 0; i < pokemonActivoJugador->getMovimientos().size(); ++i) {
                    std::cout << i + 1 << ". " << pokemonActivoJugador->getMovimiento(i).getNombre() << std::endl;
                }
                int eleccionMovimiento;
                std::cin >> eleccionMovimiento;
                limpiarBuffer();
                
                if (eleccionMovimiento > 0 && static_cast<size_t>(eleccionMovimiento) <= pokemonActivoJugador->getMovimientos().size()) {
                    pokemonActivoJugador->atacar(*pokemonActivoRival, pokemonActivoJugador->getMovimiento(eleccionMovimiento - 1));
                } else {
                    std::cout << "Seleccion de movimiento invalida. Pierdes el turno." << std::endl;
                }
                break;
            }
            case 2: {
                jugador->mostrarBolsa();
                int op;
                std::cout << "Elige item: ";
                std::cin >> op;
                limpiarBuffer();
                if (op > 0 && static_cast<size_t>(op) <= jugador->getBolsa().size()) {
                    Item* itemElegido = jugador->getBolsa()[op - 1].get();
                    itemElegido->usar(*pokemonActivoJugador);
                }
                break;
            }
            case 3:
                pokemonActivoJugador = elegirPokemon(jugador.get());
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
                pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()].get();
                while (!pokemonActivoRival->estaVivo()) {
                    pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()].get();
                }
                std::cout << CYAN << rival->getNombre() << " envia a " << pokemonActivoRival->getNombre() << "!" << RESET << std::endl;
            }
        }
        
        if (!pokemonActivoJugador->estaVivo()) {
            std::cout << RED << pokemonActivoJugador->getNombre() << " se ha debilitado!" << RESET << std::endl;
            if (jugador->tienePokemonVivo()) {
                std::cout << "Debes elegir un nuevo Pokemon." << std::endl;
                pokemonActivoJugador = elegirPokemon(jugador.get());
            }
        }
    }
    
    if (jugador->tienePokemonVivo()) {
        std::cout << GREEN << "Has ganado el combate!" << RESET << std::endl;
    } else {
        std::cout << RED << "Has perdido el combate!" << RESET << std::endl;
    }
}