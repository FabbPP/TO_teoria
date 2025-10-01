#include "Juego.h"
#include <iostream>
#include <cstdlib>
#include <ctime>
#include <limits>
#include "Movimiento.h"
#include <vector>

Juego::Juego() {
    srand(static_cast<unsigned int>(time(0)));
    ui = std::make_unique<UI>();

    jugador = std::make_unique<Entrenador>("Ash");
    rival = std::make_unique<Entrenador>("Lance");
    
    //Jugador
    auto pikachu = std::make_unique<Pokemon>("Pikachu", std::vector<Tipo>{ELECTRICO}, 80, 25, 5);
    pikachu->agregarMovimiento(Movimiento("Impactrueno", ELECTRICO, 30));
    pikachu->agregarMovimiento(Movimiento("Placaje", NORMAL, 10));
    jugador->agregarPokemon(std::move(pikachu));

    auto charmander = std::make_unique<Pokemon>("Charmander", std::vector<Tipo>{FUEGO}, 95, 22, 6);
    charmander->agregarMovimiento(Movimiento("Llamarada", FUEGO, 40));
    charmander->agregarMovimiento(Movimiento("Arañazo", NORMAL, 10));
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
    dragonite->agregarMovimiento(Movimiento("Puño Fuego", FUEGO, 30));
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

void Juego::mostrarEstado() const {
    ui->mostrarEstado(pokemonActivoJugador, pokemonActivoRival);
}

Pokemon* Juego::elegirPokemon(Entrenador* entrenador) {
    int idx;
    do {
        ui->mostrarEquipo(*entrenador);
        idx = ui->obtenerEleccion("Elige un Pokemon: ", entrenador->getEquipo().size());
        
        if (entrenador->getEquipo()[idx - 1]->estaVivo()) {
            return entrenador->getEquipo()[idx - 1].get();
        } else {
            ui->mostrarMensaje("Selección inválida. Intenta de nuevo.");
        }
    } while (true);
}

void Juego::accionRival() {
    if (pokemonActivoRival->getVida() <= pokemonActivoRival->getVidaMax() / 3) {
        for (const auto& item : rival->getBolsa()) {
            if (item->disponible()) {
                item->usar(*pokemonActivoRival);
                ui->mostrarMensajeCombate(rival->getNombre() + " usa una " + item->getNombre() + "!");
                return;
            }
        }
    }
    
    ui->mostrarMensajeCombate(rival->getNombre() + " ataca...");
    int numMovimientos = pokemonActivoRival->getMovimientos().size();
    int movimientoElegido = rand() % numMovimientos;
    pokemonActivoRival->atacar(*pokemonActivoJugador, pokemonActivoRival->getMovimiento(movimientoElegido));
}

void Juego::iniciarCombate() {
    ui->mostrarMensaje("=== Combate Pokemon 3 vs 3! ===", GREEN);
    // ui->mostrarEquipo(*jugador);
    ui->mostrarEquipo(*rival);
    
    ui->mostrarMensaje("Elige tu Pokemon inicial:", CYAN);
    pokemonActivoJugador = elegirPokemon(jugador.get());
    ui->mostrarMensaje("¡Adelante, " + pokemonActivoJugador->getNombre() + "!", CYAN);
    
    pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()].get();
    while (!pokemonActivoRival->estaVivo()) {
        pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()].get();
    }
    ui->mostrarMensaje(rival->getNombre() + " envia a " + pokemonActivoRival->getNombre() + "!", CYAN);
    
    int turno = 1;
    while (jugador->tienePokemonVivo() && rival->tienePokemonVivo()) {
        ui->mostrarMensaje("\n--- Turno " + std::to_string(turno++) + " ---", BLUE);
        mostrarEstado();
        
        int accion = ui->obtenerEleccion("Accion: (1)Luchar (2)Bolsa (3)Pokemon -> ", 3);
        
        switch (accion) {
            case 1: {
                int eleccionMovimiento = ui->obtenerOpcionLucha(*pokemonActivoJugador);
                
                if (eleccionMovimiento > 0 && static_cast<size_t>(eleccionMovimiento) <= pokemonActivoJugador->getMovimientos().size()) {
                    pokemonActivoJugador->atacar(*pokemonActivoRival, pokemonActivoJugador->getMovimiento(eleccionMovimiento - 1));
                } else {
                    ui->mostrarMensaje("Selección de movimiento inválida. Pierdes el turno.");
                }
                break;
            }
            case 2: {
                ui->mostrarBolsa(*jugador);
                int op = ui->obtenerEleccion("Elige item: ", jugador->getBolsa().size());
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
                ui->mostrarMensaje("Accion invalida.");
                break;
        }
        
        if (rival->tienePokemonVivo() && pokemonActivoRival->estaVivo()) {
            accionRival();
        }
        
        if (!pokemonActivoRival->estaVivo()) {
            ui->mostrarMensaje(pokemonActivoRival->getNombre() + " se ha debilitado!", RED);
            if (rival->tienePokemonVivo()) {
                pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()].get();
                while (!pokemonActivoRival->estaVivo()) {
                    pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()].get();
                }
                ui->mostrarMensaje(rival->getNombre() + " envia a " + pokemonActivoRival->getNombre() + "!", CYAN);
            }
        }
        
        if (!pokemonActivoJugador->estaVivo()) {
            ui->mostrarMensaje(pokemonActivoJugador->getNombre() + " se ha debilitado!", RED);
            if (jugador->tienePokemonVivo()) {
                ui->mostrarMensaje("Debes elegir un nuevo Pokemon.");
                pokemonActivoJugador = elegirPokemon(jugador.get());
            }
        }
    }
    
    if (jugador->tienePokemonVivo()) {
        ui->mostrarMensaje("¡Has ganado el combate!", GREEN);
    } else {
        ui->mostrarMensaje("¡Has perdido el combate!", RED);
    }
}