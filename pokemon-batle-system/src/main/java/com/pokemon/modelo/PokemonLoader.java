package com.pokemon.modelo;

import com.pokemon.Pokemon;
import com.pokemon.tipos.TipoElemento;
import com.pokemon.movimientos.Movimiento;
import com.pokemon.interfaces.*;
import java.io.*;
import java.util.*;

public class PokemonLoader {
    
    public static List<Pokemon> cargarDesdeCSV(String rutaArchivo, Map<String, TipoElemento> tiposDisponibles,
                                             IMostrador mostrador, ICalculadorDano cDano, 
                                             ICalculadorMultiplicador cMult) {
        List<Pokemon> lista = new ArrayList<>();
        
        // Intentamos leer el archivo como recurso del sistema (dentro del jar/classpath)
        try (InputStream is = PokemonLoader.class.getResourceAsStream(rutaArchivo);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            
            if (is == null) {
                System.err.println("ERROR: No se encontró el archivo de datos: " + rutaArchivo);
                return lista;
            }

            String linea;
            boolean primeraLinea = true; // Para saltar la cabecera
            
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { primeraLinea = false; continue; }
                if (linea.trim().isEmpty()) continue;
                
                try {
                    String[] datos = linea.split(",");
                    // Formato: Nombre,Tipos,Vida,Ataque,Defensa,Movimientos,Front,Back
                    
                    String nombre = datos[0].trim();
                    
                    // Procesar Tipos (separados por ;)
                    List<TipoElemento> tiposPokemon = new ArrayList<>();
                    for (String t : datos[1].split(";")) {
                        if (tiposDisponibles.containsKey(t.trim())) {
                            tiposPokemon.add(tiposDisponibles.get(t.trim()));
                        }
                    }
                    
                    int vida = Integer.parseInt(datos[2].trim());
                    int ataque = Integer.parseInt(datos[3].trim());
                    int defensa = Integer.parseInt(datos[4].trim());
                    
                    Pokemon p = new Pokemon(nombre, tiposPokemon, vida, ataque, defensa);
                    
                    // Procesar Movimientos (separados por ;)
                    String[] movs = datos[5].split(";");
                    for (String movNombre : movs) {
                        Movimiento m = MoveFactory.crearMovimiento(movNombre.trim(), tiposDisponibles, 
                                                                 mostrador, cDano, cMult);
                        p.agregarMovimiento(m);
                    }
                    
                    // Sprites
                    p.setSpriteFrontal(datos[6].trim());
                    p.setSpriteTrasero(datos[7].trim());
                    
                    lista.add(p);
                    System.out.println("Cargado: " + nombre);
                    
                } catch (Exception e) {
                    System.err.println("Error procesando línea: " + linea);
                    e.printStackTrace();
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lista;
    }
}