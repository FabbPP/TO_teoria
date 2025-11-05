#include "Juego.h"
#include <iostream>
#include <cstdlib>
#include <ctime>
#include <limits>
#include "Movimiento.h"
#include "MovimientoMultiGolpe.h"
#include "TipoElemento.h"
#include "Calculadores.h"
#include <vector>

Juego::Juego() {
    srand(static_cast<unsigned int>(time(0)));
    ui = std::make_unique<UI>();

    jugador = std::make_unique<Entrenador>("Ash");
    rival = std::make_unique<Entrenador>("Lance");
    
    // DIP: Crear calculadores (abstracciones)
    auto calculadorDano = std::make_shared<CalculadorDanoBasico>();
    auto calculadorMult = std::make_shared<CalculadorMultiplicadorFuerza>();
    
    // OCP: Crear tipos - fácilmente extensible
    auto tipoNormal = std::make_shared<TipoNormal>();
    auto tipoFuego = std::make_shared<TipoFuego>();
    auto tipoAgua = std::make_shared<TipoAgua>();
    auto tipoElectrico = std::make_shared<TipoElectrico>();
    auto tipoPlanta = std::make_shared<TipoPlanta>();
    auto tipoVeneno = std::make_shared<TipoVeneno>();
    
    // DIP: IMostrador* en lugar de UI concreta
    const IMostrador* mostrador = ui.get();
    
    // === JUGADOR ===
    auto pikachu = std::make_unique<Pokemon>("Pikachu", 
        std::vector<std::shared_ptr<TipoElemento>>{tipoElectrico}, 
        80, 25, 5);
    
    // DIP: Pasar abstracciones a los movimientos
    pikachu->agregarMovimiento(
        std::make_unique<Movimiento>("Impactrueno", tipoElectrico, 30, mostrador, calculadorDano, calculadorMult)
    );
    pikachu->agregarMovimiento(
        std::make_unique<Movimiento>("Placaje", tipoNormal, 10, mostrador, calculadorDano, calculadorMult)
    );
    pikachu->agregarMovimiento(
        std::make_unique<MovimientoMultiGolpe>("Doble Bofeton", tipoNormal, 8, 2, mostrador, calculadorDano, calculadorMult)
    );
    jugador->agregarPokemon(std::move(pikachu));
    
    auto charmander = std::make_unique<Pokemon>("Charmander", 
        std::vector<std::shared_ptr<TipoElemento>>{tipoFuego}, 
        95, 22, 6);
    charmander->agregarMovimiento(
        std::make_unique<Movimiento>("Llamarada", tipoFuego, 40, mostrador, calculadorDano, calculadorMult)
    );
    charmander->agregarMovimiento(
        std::make_unique<Movimiento>("Arañazo", tipoNormal, 10, mostrador, calculadorDano, calculadorMult)
    );
    charmander->agregarMovimiento(
        std::make_unique<MovimientoCuracion>("Recuperacion", tipoNormal, 0, 20, mostrador)
    );
    jugador->agregarPokemon(std::move(charmander));
    
    auto bulbasaur = std::make_unique<Pokemon>("Bulbasaur", 
        std::vector<std::shared_ptr<TipoElemento>>{tipoPlanta, tipoVeneno}, 
        100, 18, 8);
    bulbasaur->agregarMovimiento(
        std::make_unique<Movimiento>("Latigo Cepa", tipoPlanta, 25, mostrador, calculadorDano, calculadorMult)
    );
    bulbasaur->agregarMovimiento(
        std::make_unique<MovimientoEstado>("Bomba Lodo", tipoVeneno, 30, mostrador, calculadorDano, calculadorMult)
    );
    jugador->agregarPokemon(std::move(bulbasaur));

    // === RIVAL ===
    auto gyarados = std::make_unique<Pokemon>("Gyarados", 
        std::vector<std::shared_ptr<TipoElemento>>{tipoAgua}, 
        105, 21, 7);
    gyarados->agregarMovimiento(
        std::make_unique<Movimiento>("Cascada", tipoAgua, 30, mostrador, calculadorDano, calculadorMult)
    );
    gyarados->agregarMovimiento(
        std::make_unique<Movimiento>("Mordisco", tipoNormal, 20, mostrador, calculadorDano, calculadorMult)
    );
    rival->agregarPokemon(std::move(gyarados));
    
    auto dragonite = std::make_unique<Pokemon>("Dragonite", 
        std::vector<std::shared_ptr<TipoElemento>>{tipoNormal}, 
        110, 24, 9);
    dragonite->agregarMovimiento(
        std::make_unique<Movimiento>("Puño Fuego", tipoFuego, 30, mostrador, calculadorDano, calculadorMult)
    );
    dragonite->agregarMovimiento(
        std::make_unique<Movimiento>("Placaje", tipoNormal, 20, mostrador, calculadorDano, calculadorMult)
    );
    rival->agregarPokemon(std::move(dragonite));

    auto onix = std::make_unique<Pokemon>("Onix", 
        std::vector<std::shared_ptr<TipoElemento>>{tipoNormal}, 
        120, 15, 12);
    onix->agregarMovimiento(
        std::make_unique<Movimiento>("Placaje", tipoNormal, 25, mostrador, calculadorDano, calculadorMult)
    );
    rival->agregarPokemon(std::move(onix));

    // === ITEMS ===
    jugador->agregarItem(std::make_unique<Item>("Pocion", 30, 3));
    rival->agregarItem(std::make_unique<Item>("Pocion", 30, 3));
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
            ui->mostrarMensaje("Seleccion invalida. Intenta de nuevo.");
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
    ui->mostrarEquipo(*rival);
    
    ui->mostrarMensaje("Elige tu Pokemon inicial:", CYAN);
    pokemonActivoJugador = elegirPokemon(jugador.get());
    ui->mostrarMensaje("Adelante, " + pokemonActivoJugador->getNombre() + "!", CYAN);
    
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
                    ui->mostrarMensaje("Seleccion de movimiento invalida. Pierdes el turno.");
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
        ui->mostrarMensaje(" Has ganado el combate!", GREEN);
    } else {
        ui->mostrarMensaje(" Has perdido el combate!", RED);
    }
}