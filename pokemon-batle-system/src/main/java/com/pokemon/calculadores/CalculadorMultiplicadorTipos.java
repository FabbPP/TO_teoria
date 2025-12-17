package com.pokemon.calculadores;

import com.pokemon.interfaces.ICalculadorMultiplicador;
import com.pokemon.tipos.TipoElemento;
import java.util.HashMap;
import java.util.Map;

public class CalculadorMultiplicadorTipos implements ICalculadorMultiplicador {
    private Map<String, Map<String, Double>> tablaEfectividad;

    public CalculadorMultiplicadorTipos() {
        tablaEfectividad = new HashMap<>();
        inicializarTabla();
    }

    private void inicializarTabla() {
        agregarRegla("Agua", "Fuego", 2.0);
        agregarRegla("Agua", "Planta", 0.5);
        agregarRegla("Fuego", "Planta", 2.0);
        agregarRegla("Fuego", "Agua", 0.5);
        agregarRegla("Planta", "Agua", 2.0);
        agregarRegla("Planta", "Fuego", 0.5);
        agregarRegla("Planta", "Veneno", 0.5);
        agregarRegla("Electrico", "Agua", 2.0);
        agregarRegla("Electrico", "Tierra", 0.0);
        agregarRegla("Normal", "Fantasma", 0.0);
        // ...
    }
    
    private void agregarRegla(String atq, String def, double val) {
        tablaEfectividad.computeIfAbsent(atq, k -> new HashMap<>()).put(def, val);
    }

    @Override
    public double calcularMultiplicador(TipoElemento atacante, TipoElemento defensor) {
        if (tablaEfectividad.containsKey(atacante.getNombre()) &&
            tablaEfectividad.get(atacante.getNombre()).containsKey(defensor.getNombre())) {
            return tablaEfectividad.get(atacante.getNombre()).get(defensor.getNombre());
        }
        return 1.0; // Daño neutro por defecto
    }
}