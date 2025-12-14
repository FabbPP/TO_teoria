package com.pokemon;

import com.pokemon.movimientos.Movimiento;
import com.pokemon.tipos.TipoElemento;
import java.util.ArrayList;
import java.util.List;

//Clase que representa un Pokemon en el sistema de combate

public class Pokemon {
    private String nombre;
    private List<TipoElemento> tipos;
    private List<Movimiento> movimientos;
    private int vida;
    private int vidaMax;
    private int ataque;
    private int defensa;
    private String spriteFrontal;
    private String spriteTrasero;
    
    public Pokemon(String nombre, List<TipoElemento> tipos, int vida, int ataque, int defensa) {
        this.nombre = nombre;
        this.tipos = new ArrayList<>(tipos);
        this.movimientos = new ArrayList<>();
        this.vida = vida;
        this.vidaMax = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        
    }
    
    public void agregarMovimiento(Movimiento movimiento) {
        movimientos.add(movimiento);
    }
    
    public Movimiento getMovimiento(int index) {
        return movimientos.get(index);
    }
    
    // Getters
    public String getNombre() {
        return nombre;
    }
    
    public int getVida() {
        return vida;
    }
    
    public int getVidaMax() {
        return vidaMax;
    }
    
    public int getAtaque() {
        return ataque;
    }
    
    public int getDefensa() {
        return defensa;
    }
    public String getSpriteFrontal() {
        return spriteFrontal;
    }

    public void setSpriteFrontal(String spriteFrontal) {
        this.spriteFrontal = spriteFrontal;
    }

    public String getSpriteTrasero() {
        return spriteTrasero;
    }

    public void setSpriteTrasero(String spriteTrasero) {
        this.spriteTrasero = spriteTrasero;
    }
    
    // Método de compatibilidad (opcional, para que no fallen otras clases que usen getSpritePath)
    public String getSpritePath() {
        return spriteFrontal; 
    }

    public boolean estaVivo() {
        return vida > 0;
    }
    
    public List<TipoElemento> getTipos() {
        return new ArrayList<>(tipos); // Retorna copia defensiva
    }
    
    public List<Movimiento> getMovimientos() {
        return new ArrayList<>(movimientos); // Retorna copia defensiva
    }
    
    public void mostrar() {
        System.out.print(nombre + " [" + vida + "/" + vidaMax + "]");
    }
    
    public void recibirDano(int dmg) {
        vida -= dmg;
        if (vida < 0) vida = 0;
    }
    
    public void curar(int cantidad) {
        vida += cantidad;
        if (vida > vidaMax) vida = vidaMax;
    }
    
    public void atacar(Pokemon objetivo, Movimiento movimiento) {
        movimiento.aplicarEfecto(this, objetivo);
    }
}