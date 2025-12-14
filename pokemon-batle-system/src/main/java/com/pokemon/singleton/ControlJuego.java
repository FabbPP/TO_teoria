package com.pokemon.singleton;


/**
 * PATRÓN SINGLETON - Control Global del Juego
 * 
 * Esta clase maneja el estado global del juego Pokemon:
 * - Nivel actual del jugador
 * - Puntaje acumulado
 * - Vidas restantes
 * - Estadísticas de combate
 * 
 * SINGLETON asegura que SOLO EXISTA UNA INSTANCIA en todo el juego,
 * permitiendo que todos los componentes (Modelo, Vista, Controlador)
 * accedan al MISMO estado.
 */
public class ControlJuego {
    
    // IMPLEMENTACIÓN DEL PATRÓN SINGLETON
    
    // 1. Instancia única (static) - Inicialización temprana (Eager)
    private static final ControlJuego INSTANCE = new ControlJuego();
    
    // 2. Constructor PRIVADO - Nadie puede hacer new ControlJuego()
    private ControlJuego() {
        // Inicializar valores por defecto
        this.nivelActual = 1;
        this.puntaje = 0;
        this.vidas = 3;
        this.combatesGanados = 0;
        this.combatesPerdidos = 0;
        this.pokemonCapturados = 0;
        this.itemsUsados = 0;
        this.ataquesCriticos = 0;
        this.danoTotalInfligido = 0;
        this.danoTotalRecibido = 0;
        
        System.out.println("[SINGLETON] ControlJuego inicializado");
    }
    
    // 3. Método público para obtener la ÚNICA instancia
    public static ControlJuego getInstance() {
        return INSTANCE;
    }
    
    // ATRIBUTOS DEL ESTADO GLOBAL
    
    // Estado básico del jugador
    private int nivelActual;           // Nivel actual del jugador
    private int puntaje;               // Puntuación acumulada
    private int vidas;                 // Vidas restantes
    
    // Estadísticas de combate
    private int combatesGanados;       // Victorias totales
    private int combatesPerdidos;      // Derrotas totales
    private int pokemonCapturados;     // Pokemon únicos capturados
    private int itemsUsados;           // Items consumidos
    private int ataquesCriticos;       // Ataques críticos realizados
    private int danoTotalInfligido;    // Daño total causado
    private int danoTotalRecibido;     // Daño total recibido
    
    // METODOS GETTERS
    
    public int getNivelActual() {
        return nivelActual;
    }
    
    public int getPuntaje() {
        return puntaje;
    }
    
    public int getVidas() {
        return vidas;
    }
    
    public int getCombatesGanados() {
        return combatesGanados;
    }
    
    public int getCombatesPerdidos() {
        return combatesPerdidos;
    }
    
    public int getPokemonCapturados() {
        return pokemonCapturados;
    }
    
    public int getItemsUsados() {
        return itemsUsados;
    }
    
    public int getAtaquesCriticos() {
        return ataquesCriticos;
    }
    
    public int getDanoTotalInfligido() {
        return danoTotalInfligido;
    }
    
    public int getDanoTotalRecibido() {
        return danoTotalRecibido;
    }
    
    // METODOS DE MODIFICACIÓN DEL ESTADO
    
    // Aumenta el puntaje del jugador
    
    public void agregarPuntaje(int puntos) {
        this.puntaje += puntos;
        System.out.println("[ControlJuego] +" + puntos + " puntos. Total: " + this.puntaje);
        
        // Verificar si sube de nivel cada 1000 puntos
        verificarSubidaNivel();
    }
    
    // Resta una vida al jugador
     
    public void perderVida() {
        if (vidas > 0) {
            vidas--;
            System.out.println("[ControlJuego] Perdiste una vida. Vidas restantes: " + vidas);
            
            if (vidas == 0) {
                System.out.println("[ControlJuego] ¡GAME OVER! No quedan vidas");
            }
        }
    }
    
    //Recupera una vida (máximo 5)
    
    public void recuperarVida() {
        if (vidas < 5) {
            vidas++;
            System.out.println("[ControlJuego] ¡Vida recuperada! Vidas: " + vidas);
        }
    }
    
    //Registra una victoria en combate
     
    public void registrarVictoria() {
        combatesGanados++;
        agregarPuntaje(500);  // 500 puntos por victoria
        System.out.println("[ControlJuego] ¡Victoria! Total: " + combatesGanados);
    }
    
    //Registra una derrota en combate
     
    public void registrarDerrota() {
        combatesPerdidos++;
        perderVida();
        System.out.println("[ControlJuego] Derrota registrada. Total: " + combatesPerdidos);
    }
    
    //Registra la captura de un Pokemon
     
    public void registrarCaptura() {
        pokemonCapturados++;
        agregarPuntaje(200);  // 200 puntos por captura
        System.out.println(" [ControlJuego] ¡Pokemon capturado! Total: " + pokemonCapturados);
    }
    
    //Registra el uso de un item
     
    public void registrarUsoItem() {
        itemsUsados++;
        System.out.println("[ControlJuego] Item usado. Total: " + itemsUsados);
    }
    
    //Registra un ataque crítico
     
    public void registrarAtaqueCritico() {
        ataquesCriticos++;
        agregarPuntaje(50);  // 50 puntos por crítico
        System.out.println("[ControlJuego] ¡Ataque crítico! Total: " + ataquesCriticos);
    }
    
    //Registra daño infligido al enemigo
     
    public void registrarDanoInfligido(int dano) {
        danoTotalInfligido += dano;
        agregarPuntaje(dano / 10);  // 1 punto cada 10 de daño
    }
    
    //Registra daño recibido del enemigo
     
    public void registrarDanoRecibido(int dano) {
        danoTotalRecibido += dano;
    }
    
    //Verifica si el jugador debe subir de nivel
    
    private void verificarSubidaNivel() {
        int nuevoNivel = (puntaje / 1000) + 1;
        if (nuevoNivel > nivelActual) {
            nivelActual = nuevoNivel;
            recuperarVida();  // Recupera una vida al subir de nivel
            System.out.println("[ControlJuego] ¡SUBISTE DE NIVEL! Nivel actual: " + nivelActual);
        }
    }
    
    //Calcula el ratio de victorias
     
    public double getRatioVictorias() {
        int totalCombates = combatesGanados + combatesPerdidos;
        if (totalCombates == 0) return 0.0;
        return (double) combatesGanados / totalCombates * 100;
    }
    
    //Calcula el daño promedio por combate
     
    public double getPromedioDanoPorCombate() {
        int totalCombates = combatesGanados + combatesPerdidos;
        if (totalCombates == 0) return 0.0;
        return (double) danoTotalInfligido / totalCombates;
    }
    
    //Reinicia el juego (nuevo inicio)
     
    public void reiniciarJuego() {
        this.nivelActual = 1;
        this.puntaje = 0;
        this.vidas = 3;
        this.combatesGanados = 0;
        this.combatesPerdidos = 0;
        this.pokemonCapturados = 0;
        this.itemsUsados = 0;
        this.ataquesCriticos = 0;
        this.danoTotalInfligido = 0;
        this.danoTotalRecibido = 0;
        
        System.out.println("[ControlJuego] Juego reiniciado");
    }
    
    //Muestra un resumen completo del estado del juego
     
    public void mostrarEstadoCompleto() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("--- ESTADO GLOBAL DEL JUEGO (SINGLETON)");
        System.out.println("=".repeat(60));
        System.out.println("- Nivel:              " + nivelActual);
        System.out.println("-  Puntaje:            " + puntaje);
        System.out.println("-   Vidas:             " + vidas);
        System.out.println("\n--- ESTADÍSTICAS DE COMBATE ---");
        System.out.println("-  Victorias:          " + combatesGanados);
        System.out.println("-  Derrotas:           " + combatesPerdidos);
        System.out.println("- Ratio de victorias: " + String.format("%.2f", getRatioVictorias()) + "%");
        System.out.println("\n--- ESTADÍSTICAS GENERALES ---");
        System.out.println("- Pokemon capturados: " + pokemonCapturados);
        System.out.println("- Items usados:       " + itemsUsados);
        System.out.println("- Ataques críticos:   " + ataquesCriticos);
        System.out.println("-  Daño infligido:    " + danoTotalInfligido);
        System.out.println("-  Daño recibido:     " + danoTotalRecibido);
        System.out.println("- Promedio daño/comb: " + String.format("%.2f", getPromedioDanoPorCombate()));
        System.out.println("=".repeat(60) + "\n");
    }
    
    //Verifica si el juego ha terminado (sin vidas)
     
    public boolean juegoTerminado() {
        return vidas <= 0;
    }
    
    // PROTECCION CONTRA CLONACIÓN Y SERIALIZACIÓN
    
    //Previene la clonación del Singleton
     
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("No se puede clonar un Singleton");
    }
}