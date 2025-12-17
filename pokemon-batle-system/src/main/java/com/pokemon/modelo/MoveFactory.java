package com.pokemon.modelo;

import com.pokemon.movimientos.*;
import com.pokemon.tipos.TipoElemento;
import com.pokemon.interfaces.*;
import java.util.Map;

public class MoveFactory {
    
    public static Movimiento crearMovimiento(String nombre, Map<String, TipoElemento> tipos, 
                                           IMostrador mostrador, ICalculadorDano cDano, 
                                           ICalculadorMultiplicador cMult) {
        switch (nombre) {
            // ELECTRICOS
            case "Impactrueno": 
                return new Movimiento("Impactrueno", tipos.get("Electrico"), 30, mostrador, cDano, cMult);
            
            // FUEGO
            case "Llamarada":
                return new Movimiento("Llamarada", tipos.get("Fuego"), 40, mostrador, cDano, cMult);
            case "Ascuas":
                return new Movimiento("Ascuas", tipos.get("Fuego"), 32, mostrador, cDano, cMult);
                
            // AGUA
            case "Pistola Agua":
                return new Movimiento("Pistola Agua", tipos.get("Agua"), 35, mostrador, cDano, cMult);
            case "Confusion": // (Psyduck suele usar ataques psiquicos/agua)
                return new Movimiento("Confusion", tipos.get("Agua"), 28, mostrador, cDano, cMult);
                
            // PLANTA/VENENO
            case "Latigo Cepa":
                return new Movimiento("Latigo Cepa", tipos.get("Planta"), 25, mostrador, cDano, cMult);
            case "Absorber":
                return new Movimiento("Absorber", tipos.get("Planta"), 22, mostrador, cDano, cMult);
            case "Bomba Lodo":
                return new MovimientoEstado("Bomba Lodo", tipos.get("Veneno"), 30, mostrador, cDano, cMult);
            case "Polvo Veneno":
                return new MovimientoEstado("Polvo Veneno", tipos.get("Veneno"), 25, mostrador, cDano, cMult);
            case "Sintesis":
                return new MovimientoCuracion("Sintesis", tipos.get("Planta"), 0, 25, mostrador);
            case "Megaagotar":
                return new MovimientoCuracion("Megaagotar", tipos.get("Planta"), 0, 25, mostrador);
                
            // NORMAL
            case "Placaje":
            case "Tackle":
            case "Arañazo":
                return new Movimiento(nombre, tipos.get("Normal"), 10, mostrador, cDano, cMult);
            case "Mordisco":
                return new Movimiento("Mordisco", tipos.get("Normal"), 15, mostrador, cDano, cMult);
            case "Canto":
                return new Movimiento("Canto", tipos.get("Normal"), 18, mostrador, cDano, cMult);
                
            // ESPECIALES (MultiGolpe / Curacion)
            case "Doble Bofeton":
            case "Ataque Rapido": // Lo simularemos como multigolpe
                return new MovimientoMultiGolpe(nombre, tipos.get("Normal"), 8, 2, mostrador, cDano, cMult);
            
            case "Recuperacion":
                return new MovimientoCuracion("Recuperacion", tipos.get("Normal"), 0, 20, mostrador);
            case "Descanso":
                return new MovimientoCuracion("Descanso", tipos.get("Normal"), 0, 30, mostrador);
                
            default:
                System.out.println("ADVERTENCIA: Movimiento no reconocido: " + nombre);
                return new Movimiento("Combate", tipos.get("Normal"), 10, mostrador, cDano, cMult);
        }
    }
}