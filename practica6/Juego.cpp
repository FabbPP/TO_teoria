#include "Juego.h"
#include <iostream>
#include <cstdlib>
#include <ctime>
#include <limits>
#include "Movimiento.h"
#include "MovimientoMultiGolpe.h"
#include "TipoElemento.h"
#include <vector>

Juego::Juego() {
    srand(static_cast<unsigned int>(time(0)));
    ui = std::make_unique<UI>();

    jugador = std::make_unique<Entrenador>("Ash");
    rival = std::make_unique<Entrenador>("Lance");
    
    // === JUGADOR ===
    auto pikachu = std::make_unique<Pokemon>("Pikachu", 80, 25, 5);
    pikachu->agregarMovimiento(std::make_unique<Movimiento>("Impactrueno", std::make_shared<TipoElectrico>(), 30));
    pikachu->agregarMovimiento(std::make_unique<Movimiento>("Placaje", std::make_shared<TipoNormal>(), 10));
    pikachu->agregarMovimiento(std::make_unique<MovimientoMultiGolpe>("Doble Bofetón", std::make_shared<TipoNormal>(), 8, 2));
    jugador->agregarPokemon(std::move(pikachu));
    
    auto charmander = std::make_unique<Pokemon>("Charmander", 95, 22, 6);
    charmander->agregarMovimiento(std::make_unique<Movimiento>("Llamarada", std::make_shared<TipoFuego>(), 40));
    charmander->agregarMovimiento(std::make_unique<Movimiento>("Arañazo", std::make_shared<TipoNormal>(), 10));
    charmander->agregarMovimiento(std::make_unique<MovimientoCuracion>("Recuperacion", std::make_shared<TipoNormal>(), 0, 20)); 
    jugador->agregarPokemon(std::move(charmander));
    
    auto bulbasaur = std::make_unique<Pokemon>("Bulbasaur", 100, 18, 8);
    bulbasaur->agregarMovimiento(std::make_unique<Movimiento>("Látigo Cepa", std::make_shared<TipoPlanta>(), 25));
    bulbasaur->agregarMovimiento(std::make_unique<MovimientoEstado>("Bomba Lodo", std::make_shared<TipoVeneno>(), 30)); 
    jugador->agregarPokemon(std::move(bulbasaur));

    // === RIVAL ===
    auto gyarados = std::make_unique<Pokemon>("Gyarados", 105, 21, 7);
    gyarados->agregarMovimiento(std::make_unique<Movimiento>("Cascada", std::make_shared<TipoAgua>(), 30));
    gyarados->agregarMovimiento(std::make_unique<Movimiento>("Mordisco", std::make_shared<TipoNormal>(), 20));
    rival->agregarPokemon(std::move(gyarados));
    
    auto dragonite = std::make_unique<Pokemon>("Dragonite", 110, 24, 9);
    //dragonite->agregarMovimiento(std::make_unique<Movimiento>("Dragoaliento", std::make_shared<TipoDragon>(), 35));
    dragonite->agregarMovimiento(std::make_unique<Movimiento>("Puño Fuego", std::make_shared<TipoFuego>(), 30));
    rival->agregarPokemon(std::move(dragonite));

    auto onix = std::make_unique<Pokemon>("Onix", 120, 15, 12);
    //onix->agregarMovimiento(std::make_unique<Movimiento>("Lanzarrocas", std::make_shared<TipoRoca>(), 25));
    //onix->agregarMovimiento(std::make_unique<Movimiento>("Excavar", std::make_shared<TipoTierra>(), 30));
    rival->agregarPokemon(std::move(onix));

    // === ITEMS ===
    jugador->agregarItem(std::make_unique<Item>("Poción", 30, 3));
    rival->agregarItem(std::make_unique<Item>("Poción", 30, 3));
}

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
    ui->mostrarMensaje("=== Combate Pokémon 3 vs 3! ===", GREEN);
    ui->mostrarEquipo(*rival);
    
    ui->mostrarMensaje("Elige tu Pokémon inicial:", CYAN);
    pokemonActivoJugador = elegirPokemon(jugador.get());
    ui->mostrarMensaje("¡Adelante, " + pokemonActivoJugador->getNombre() + "!", CYAN);
    
    pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()].get();
    while (!pokemonActivoRival->estaVivo()) {
        pokemonActivoRival = rival->getEquipo()[rand() % rival->getEquipo().size()].get();
    }
    ui->mostrarMensaje(rival->getNombre() + " envía a " + pokemonActivoRival->getNombre() + "!", CYAN);
    
    int turno = 1;
    while (jugador->tienePokemonVivo() && rival->tienePokemonVivo()) {
        ui->mostrarMensaje("\n--- Turno " + std::to_string(turno++) + " ---", BLUE);
        mostrarEstado();
        
        int accion = ui->obtenerEleccion("Acción: (1)Luchar (2)Bolsa (3)Pokémon -> ", 3);
        
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
                int op = ui->obtenerEleccion("Elige ítem: ", jugador->getBolsa().size());
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
                ui->mostrarMensaje("Acción inválida.");
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
                ui->mostrarMensaje(rival->getNombre() + " envía a " + pokemonActivoRival->getNombre() + "!", CYAN);
            }
        }
        
        if (!pokemonActivoJugador->estaVivo()) {
            ui->mostrarMensaje(pokemonActivoJugador->getNombre() + " se ha debilitado!", RED);
            if (jugador->tienePokemonVivo()) {
                ui->mostrarMensaje("Debes elegir un nuevo Pokémon.");
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