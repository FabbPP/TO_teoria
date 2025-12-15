# 🎮 Sistema de Combate Pokemon - Proyecto Final

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white">
  <img src="https://img.shields.io/badge/Maven-3.6+-red?logo=apache-maven&logoColor=white">
  <img src="https://img.shields.io/badge/Patrón-Singleton-blue?style=flat">
  <img src="https://img.shields.io/badge/Arquitectura-MVC-green?style=flat">
  <img src="https://img.shields.io/badge/SOLID-Principles-yellow?style=flat">
  <img src="https://img.shields.io/badge/Actividad%20Académica-UNSA-red?style=flat">
</p>

## 📝 Descripción del Proyecto

### Juego de Combate Pokemon: Implementación de Principios SOLID y Patrones de Diseño

Este proyecto implementa un **Sistema de Combate Pokemon completo** que sirve como caso de estudio para demostrar la aplicación práctica de principios SOLID y patrones de diseño en el desarrollo de software orientado a objetos. El sistema garantiza código mantenible, escalable y robusto mediante el uso de arquitecturas y abstracciones apropiadas.

El sistema permite a los jugadores **seleccionar un equipo de tres Pokemon**, cada uno con sus propios tipos, estadísticas y movimientos. Los combates se desarrollan **por turnos**, permitiendo al jugador:
- 🎯 **Atacar** con diferentes movimientos
- 💊 **Usar items** para curar o mejorar Pokemon
- 🔄 **Cambiar** de Pokemon activo durante el combate

### Mecánica del Juego

#### Sistema de Combate por Turnos
El combate sigue estas reglas fundamentales:

1. **Selección de Acción**: En cada turno, el jugador puede elegir entre atacar, usar un item o cambiar de Pokemon
2. **Cálculo de Daño**: El daño se calcula considerando:
   - Estadísticas de ataque y defensa
   - Multiplicadores por tipo elemental (fuego vs planta = x2, agua vs fuego = x2, etc.)
   - Potencia del movimiento utilizado
   - Probabilidad de crítico (10% de daño adicional)
3. **Condiciones de Victoria**: 
   - Victoria: Derrotar todos los Pokemon del rival
   - Derrota: Perder todos tus Pokemon
4. **Sistema de Puntuación**: Ganar puntos por victorias, capturas y daño infligido

#### Tipos Elementales Implementados
- 🔥 **Fuego**: Fuerte contra Planta, débil contra Agua
- 💧 **Agua**: Fuerte contra Fuego, débil contra Eléctrico y Planta
- ⚡ **Eléctrico**: Fuerte contra Agua, débil contra Planta
- 🌿 **Planta**: Fuerte contra Agua y Eléctrico, débil contra Fuego y Veneno
- ☠️ **Veneno**: Fuerte contra Planta
- ⭐ **Normal**: Sin ventajas ni desventajas

### Características Principales

- ✅ **Patrón Singleton**: Gestión centralizada del estado global mediante `ControlJuego`
- ✅ **Arquitectura MVC**: Separación clara entre Modelo, Vista y Controlador
- ✅ **Principios SOLID**: Aplicación completa de los 5 principios
- ✅ **Interfaz Gráfica**: GUI con sprites animados en formato GIF
- ✅ **Sistema de Combate**: Mecánicas de ataque, defensa y tipos elementales
- ✅ **Sistema de Puntuación**: Niveles, experiencia y estadísticas globales
- ✅ **Extensibilidad**: Fácil adición de nuevos tipos, Pokemon y movimientos
- ✅ **Testing**: Tests unitarios con JUnit 5, Mockito y AssertJ
- ✅ **Maven**: Gestión de dependencias y build automatizado
- ✅ **Documentación**: JavaDoc completo y reportes de cobertura

## 🎯 Objetivos del Proyecto

- ✅ Implementar un sistema de combate funcional siguiendo **principios SOLID**
- ✅ Aplicar **patrones de diseño** apropiados para resolver problemas específicos
- ✅ Desarrollar una **arquitectura MVC** clara y mantenible
- ✅ Demostrar **extensibilidad** mediante el uso de abstracciones
- ✅ Crear código **testeable** con alta cobertura de pruebas
- ✅ Documentar la arquitectura y decisiones de diseño

---

## 🏛️ Arquitectura MVC Implementada

El proyecto sigue estrictamente el patrón **Modelo-Vista-Controlador**, garantizando separación de responsabilidades:

### 📦 Modelo (`modelo/`)
Contiene la **lógica de negocio** y el estado del juego:
- `GameState.java`: Gestiona el estado completo del combate, turnos y validaciones
- Entidades: `Pokemon.java`, `Entrenador.java`, `Item.java`
- Sin dependencias con la Vista

### 🖼️ Vista (`vista/`)
Componentes de **interfaz gráfica** (Swing):
- `GameFrame.java`: Ventana principal
- `MenuPanel.java`: Menú de inicio
- `SeleccionPokemonPanel.java`: Selección de equipo
- `CombatePanel.java`: Pantalla de combate con sprites animados
- `EstadisticasPanel.java`: Panel de estadísticas globales
- `ResultadoPanel.java`: Pantalla de victoria/derrota

### 🎮 Controlador (`controlador/`)
Intermediario entre Modelo y Vista:
- `GameController.java`: Coordina acciones del jugador y actualiza vistas
- Recibe eventos de la Vista
- Llama métodos del Modelo
- Actualiza la Vista con los resultados

### 🔄 Flujo de Comunicación

```
Usuario → Vista → Controlador → Modelo
                                   ↓
Usuario ← Vista ← Controlador ← Modelo
```

**Ejemplo de flujo de ataque:**
1. Usuario presiona botón "Atacar" en `CombatePanel` (Vista)
2. Vista notifica al `GameController` (Controlador)
3. Controlador llama a `GameState.ejecutarAtaque()` (Modelo)
4. Modelo calcula daño y actualiza estado
5. Controlador obtiene nuevo estado del Modelo
6. Vista se actualiza con los cambios

---

## 🔧 Flujo de Pantallas

```
┌─────────────┐
│  MenuPanel  │  Menú Principal
└──────┬──────┘
       │
       ├─→ Nuevo Combate
       │   └─→ ┌──────────────────────┐
       │       │SeleccionPokemonPanel │ Seleccionar 3 Pokemon
       │       └──────────┬───────────┘
       │                  │
       │                  ↓
       │       ┌──────────────────────┐
       │       │ SeleccionInicialPanel│ Elegir Pokemon inicial
       │       └──────────┬───────────┘
       │                  │
       │                  ↓
       │       ┌──────────────────────┐
       │       │   CombatePanel       │ ← Combate por turnos
       │       │  - Atacar            │   (Pantalla principal)
       │       │  - Usar Item         │
       │       │  - Cambiar Pokemon   │
       │       └──────────┬───────────┘
       │                  │
       │                  ↓
       │       ┌──────────────────────┐
       │       │  ResultadoPanel      │ Victoria/Derrota
       │       └──────────┬───────────┘
       │                  │
       │                  └─→ Volver al Menú
       │
       ├─→ Ver Estadísticas
       │   └─→ ┌──────────────────────┐
       │       │ EstadisticasPanel    │ Estadísticas globales
       │       └──────────────────────┘
       │
       └─→ Salir
```

---

## 📁 Estructura del Proyecto

```
pokemon-batle-system/
│
├── pom.xml                                          # Configuración Maven
├── README.md                                        # Este archivo
│
└── src/
    └── main/
        ├── java/com/pokemon/
        │   ├── MainApp.java                         # Entrada principal (GUI)
        │   ├── Pokemon.java                         # Entidad Pokemon
        │   ├── Item.java                            # Entidad Item
        │   ├── Entrenador.java                      # Entidad Entrenador
        │   ├── Global.java                          # Constantes globales
        │   │
        │   ├── modelo/                              # MODELO (MVC)
        │   │   └── GameState.java                   # Estado del juego
        │   │
        │   ├── vista/                               # VISTA (MVC)
        │   │   ├── GameFrame.java                   # Ventana principal
        │   │   ├── MenuPanel.java                   # Panel menú
        │   │   ├── SeleccionPokemonPanel.java      # Selección Pokemon
        │   │   ├── SeleccionInicialPanel.java      # Selección inicial
        │   │   ├── CombatePanel.java                # Panel combate
        │   │   ├── ResultadoPanel.java              # Resultado
        │   │   └── EstadisticasPanel.java           # Panel estadísticas
        │   │
        │   ├── controlador/                         # CONTROLADOR (MVC)
        │   │   └── GameController.java              # Controlador
        │   │
        │   ├── singleton/                           # *PATRÓN SINGLETON*
        │   │   ├── ControlJuego.java                # Clase Singleton
        │   │   └── DemoSingleton.java               # Demostración
        │   │
        │   ├── interfaces/                          # SOLID (ISP)
        │   │   ├── IMostrador.java
        │   │   ├── ICalculadorDano.java
        │   │   └── ICalculadorMultiplicador.java
        │   │
        │   ├── calculadores/                        # SOLID (SRP)
        │   │   ├── CalculadorDanoBasico.java
        │   │   └── CalculadorMultiplicadorFuerza.java
        │   │
        │   ├── tipos/                               # SOLID (OCP)
        │   │   ├── TipoElemento.java
        │   │   ├── TipoNormal.java
        │   │   ├── TipoFuego.java
        │   │   ├── TipoAgua.java
        │   │   ├── TipoElectrico.java
        │   │   ├── TipoPlanta.java
        │   │   └── TipoVeneno.java
        │   │
        │   └── movimientos/                         # SOLID (LSP)
        │       ├── Movimiento.java
        │       ├── MovimientoEstado.java
        │       ├── MovimientoCuracion.java
        │       └── MovimientoMultiGolpe.java
        │
        └── resources/
            └── gif/                                 # Sprites animados
                ├── pikachu_front.gif
                ├── pikachu_back.gif
                ├── charmander_front.gif
                ├── charmander_back.gif
                ├── bulbasaur_front.gif
                ├── bulbasaur_back.gif
                ├── squirtle_front.gif
                ├── squirtle_back.gif
                ├── eevee_front.gif
                ├── eevee_back.gif
                ├── psyduck_front.gif
                ├── psyduck_back.gif
                ├── growlithe_front.gif
                ├── growlithe_back.gif
                ├── oddish_front.gif
                ├── oddish_back.gif
                ├── jigglypuff_front.gif
                └── jigglypuff_back.gif
```

---

## Fragmentos del Código y Explicación

### 1. Definición de la Clase Singleton ControlJuego

La clase `ControlJuego` define los atributos necesarios para administrar el estado global del juego, incluyendo nivel, puntaje, vidas y estadísticas de combate. El constructor es privado para impedir la creación directa de instancias.

```java
public class ControlJuego {
    
    private static final ControlJuego INSTANCE = new ControlJuego();
    
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
    
    public static ControlJuego getInstance() {
        return INSTANCE;
    }
```

**Explicación:**

- **INSTANCE** es la instancia única creada al cargar la clase (Eager Initialization).
- El **constructor privado** impide que se creen instancias con `new ControlJuego()`.
- **getInstance()** es el único punto de acceso global a la instancia.
- Los atributos almacenan el **estado global completo** del juego.
- La inicialización temprana (Eager) garantiza thread-safety sin sincronización adicional.

---

### 2. Atributos del Estado Global

```java
    
    
    private int nivelActual;           
    private int puntaje;           
    private int vidas;             
    
    private int combatesGanados;    
    private int combatesPerdidos;     
    private int pokemonCapturados;    
    private int itemsUsados;        
    private int ataquesCriticos;       
    private int danoTotalInfligido;  
    private int danoTotalRecibido;   
}
```

**Explicación:**

- **Estado básico**: Nivel, puntaje y vidas del jugador.
- **Estadísticas de combate**: Victorias, derrotas y métricas de batalla.
- **Métricas de juego**: Pokemon capturados, items usados, críticos.
- **Daño**: Registro completo de daño infligido y recibido.
- Todos los atributos son **privados** para encapsulación.

---

### 3. Métodos para Modificar el Estado

#### Agregar Puntaje y Subir de Nivel

```java
    //Aumenta el puntaje del jugador
    
    public void agregarPuntaje(int puntos) {
        this.puntaje += puntos;
        System.out.println("[ControlJuego] +" + puntos + " puntos. Total: " + this.puntaje);
        
        verificarSubidaNivel();
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
```

**Explicación:**

- **agregarPuntaje()** incrementa el puntaje y verifica si corresponde subir de nivel.
- Cada **1000 puntos** se sube un nivel.
- Al subir de nivel se **recupera una vida** como bonus.
- El sistema de niveles es **automático** basado en puntuación.
- Mensaje de log para debugging y seguimiento.

#### Gestión de Vidas

```java
    public void perderVida() {
        if (vidas > 0) {
            vidas--;
            System.out.println("[ControlJuego] Perdiste una vida. Vidas restantes: " + vidas);
            
            if (vidas == 0) {
                System.out.println("[ControlJuego] ¡GAME OVER! No quedan vidas");
            }
        }
    }
    
    public void recuperarVida() {
        if (vidas < 5) {
            vidas++;
            System.out.println("[ControlJuego] ¡Vida recuperada! Vidas: " + vidas);
        }
    }
    public boolean juegoTerminado() {
        return vidas <= 0;
    }
```

**Explicación:**

- **perderVida()** reduce el contador y verifica game over.
- **recuperarVida()** incrementa vidas con límite de 5.
- **juegoTerminado()** permite verificar el estado del juego.
- Las vidas son un recurso limitado y valioso.
- Mensajes informativos en cada cambio.

---

### 4. Registro de Eventos de Combate

```java
  
    public void registrarVictoria() {
        combatesGanados++;
        agregarPuntaje(500);  // 500 puntos por victoria
        System.out.println("[ControlJuego] ¡Victoria! Total: " + combatesGanados);
    }
 
    public void registrarDerrota() {
        combatesPerdidos++;
        perderVida();
        System.out.println("[ControlJuego] Derrota registrada. Total: " + combatesPerdidos);
    }

    public void registrarCaptura() {
        pokemonCapturados++;
        agregarPuntaje(200);  // 200 puntos por captura
        System.out.println("[ControlJuego] ¡Pokemon capturado! Total: " + pokemonCapturados);
    }
    

    public void registrarUsoItem() {
        itemsUsados++;
        System.out.println("[ControlJuego] Item usado. Total: " + itemsUsados);
    }
    

    public void registrarAtaqueCritico() {
        ataquesCriticos++;
        agregarPuntaje(50);  // 50 puntos por crítico
        System.out.println("[ControlJuego] ¡Ataque crítico! Total: " + ataquesCriticos);
    }

    public void registrarDanoInfligido(int dano) {
        danoTotalInfligido += dano;
        agregarPuntaje(dano / 10);  // 1 punto cada 10 de daño
    }

    public void registrarDanoRecibido(int dano) {
        danoTotalRecibido += dano;
    }
```

**Explicación:**

- Cada evento del juego se **registra centralizadamente**.
- **Victoria**: +500 puntos, incrementa contador.
- **Derrota**: Pierde vida, incrementa contador.
- **Captura**: +200 puntos por Pokemon capturado.
- **Daño infligido**: +1 punto cada 10 de daño.
- **Crítico**: +50 puntos, evento especial.
- **Item usado**: Solo incrementa contador, sin puntos.
- Todo se gestiona desde **un solo punto**.

---

### 5. Sistema de Puntuación

```java
// Tabla de puntuación implementada:

ACCIÓN                      PUNTOS
---------------------------------
Victoria en combate         +500
Capturar Pokemon            +200
Ataque crítico              +50
Daño infligido              +1 cada 10
Usar item                   0 puntos

// Sistema de niveles:
Nivel 1:  0 - 999 puntos
Nivel 2:  1000 - 1999 puntos
Nivel 3:  2000 - 2999 puntos
Nivel N:  (N-1)*1000 - (N*1000 - 1)

```

**Explicación:**

- Sistema de **puntuación progresiva** basado en acciones.
- Las victorias dan **más puntos** que las acciones individuales.
- El daño contribuye gradualmente al puntaje.
- Los niveles crean una **sensación de progresión**.
- Incentiva jugar estratégicamente para maximizar puntos.

---

### 6. Cálculo de Estadísticas

```java

    public double getRatioVictorias() {
        int totalCombates = combatesGanados + combatesPerdidos;
        if (totalCombates == 0) return 0.0;
        return (double) combatesGanados / totalCombates * 100;
    }
    
    public double getPromedioDanoPorCombate() {
        int totalCombates = combatesGanados + combatesPerdidos;
        if (totalCombates == 0) return 0.0;
        return (double) danoTotalInfligido / totalCombates;
    }
```

**Explicación:**

- **getRatioVictorias()**: Calcula el porcentaje de victorias.
- **getPromedioDanoPorCombate()**: Promedio de daño infligido.
- Ambos métodos manejan el **caso de división por cero**.
- Proporcionan métricas útiles para el jugador.
- Estadísticas en **tiempo real**.

---

### 7. Método para Mostrar Estado Completo

```java

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
```

**Explicación:**

- Muestra un **resumen completo** del estado global.
- Incluye **todas las estadísticas** relevantes.
- Formato **visual y legible** con separadores y emojis.
- Útil para **debugging** y verificación.
- Llamado desde la demostración del Singleton.

---

### 8. Protección Contra Clonación

```java
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("No se puede clonar un Singleton");
    }
```

**Explicación:**

- Sobrescribe el método **clone()** de Object.
- Lanza una excepción si se intenta clonar.
- **Protege** la unicidad del Singleton.
- Previene violaciones del patrón mediante reflexión.

---

### 9. Integración con GameState (Modelo MVC)

```java
public class GameState {
    private ControlJuego controlJuego;
    
    public GameState() {
        // Obtener la instancia única del Singleton
        this.controlJuego = ControlJuego.getInstance();
    }
    
    public void ejecutarAtaque(Movimiento movimiento) {
        // ... código de ataque ...
        
        // Registrar daño en el Singleton
        int dano = calcularDano();
        controlJuego.registrarDanoInfligido(dano);
        
        // Si es crítico (10% probabilidad)
        if (new Random().nextInt(100) < 10) {
            controlJuego.registrarAtaqueCritico();
        }
    }
    
    private void verificarEstadoCombate() {
        if (!pokemonActivoRival.estaVivo() && !rival.tienePokemonVivo()) {
            // Victoria del jugador
            controlJuego.registrarVictoria();
            cambiarEstado(EstadoJuego.VICTORIA);
        } else if (!pokemonActivoJugador.estaVivo() && !jugador.tienePokemonVivo()) {
            // Derrota del jugador
            controlJuego.registrarDerrota();
            cambiarEstado(EstadoJuego.DERROTA);
        }
    }
}
```

**Explicación:**

- El **Modelo** obtiene la instancia del Singleton en su constructor.
- Cada evento de combate se **registra automáticamente**.
- El Singleton actúa como **repositorio central** de estadísticas.
- No es necesario pasar referencias entre objetos.
- Separación clara de responsabilidades.

---

## 🚀 Compilación y Ejecución

### Requisitos Previos

- **Java 21** o superior
- **Maven 3.6+**
- Sistema operativo: Windows, Linux (Ubuntu/Debian) o macOS

### Instalación de Requisitos

#### En Windows

1. **Instalar Java 21:**
   - Descargar desde [Oracle](https://www.oracle.com/java/technologies/downloads/) o [OpenJDK](https://adoptium.net/)
   - Ejecutar el instalador y seguir las instrucciones
   - Verificar: `java -version` en CMD o PowerShell

2. **Instalar Maven:**
   - Descargar desde [Apache Maven](https://maven.apache.org/download.cgi)
   - Extraer en `C:\Program Files\Maven`
   - Agregar `C:\Program Files\Maven\bin` a la variable PATH
   - Verificar: `mvn -version`

#### En Linux (Ubuntu/Debian)

```bash
# Actualizar repositorios
sudo apt update

# Instalar Java 21
sudo apt install openjdk-21-jdk -y

# Instalar Maven
sudo apt install maven -y

# Verificar instalación
java -version
mvn -version
```

#### En macOS

```bash
# Instalar Homebrew (si no está instalado)
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Instalar Java 21 y Maven
brew install openjdk@21 maven

# Verificar instalación
java -version
mvn -version
```

---

### Pasos de Compilación

#### 1. Clonar o Descargar el Proyecto

```bash
# Si está en un repositorio Git
git clone <url-del-repositorio>
cd TO_teoria/pokemon-batle-system

# O navegar al directorio si ya está descargado
cd ruta/al/proyecto/pokemon-batle-system
```

#### 2. Compilar el Proyecto

**En Windows (CMD o PowerShell):**
```cmd
mvn clean compile
```

**En Linux/macOS (Terminal):**
```bash
mvn clean compile
```

#### 3. Generar JAR Ejecutable

```bash
# Compilar y empaquetar
mvn clean package

# Sin ejecutar tests (más rápido)
mvn clean package -DskipTests
```

**Resultado:** Se generará `target/pokemon-battle-system.jar`

---

### Opciones de Ejecución

#### Opción 1: Ejecutar con Maven (Recomendado)

**Ejecutar el juego completo con GUI:**

Windows:
```cmd
mvn exec:java -Dexec.mainClass="com.pokemon.MainApp"
```

Linux/macOS:
```bash
mvn exec:java -Dexec.mainClass="com.pokemon.MainApp"
```

**Ejecutar demostración del Singleton (consola):**
```bash
mvn exec:java -Dexec.mainClass="com.pokemon.singleton.DemoSingleton"
```

#### Opción 2: Ejecutar JAR Directamente

Después de compilar con `mvn clean package`:

**Windows:**
```cmd
java -jar target\pokemon-battle-system-fat.jar
```

**Linux/macOS:**
```bash
java -jar target/pokemon-battle-system-fat.jar
```

#### Opción 3: Ejecutar desde Clases Compiladas

```bash
# Compilar primero
mvn clean compile

# Ejecutar
java -cp target/classes com.pokemon.MainApp
```

## Ventajas del Singleton en este Proyecto

### 1. **Estado Global Consistente**
```java
// Todos los módulos ven el MISMO estado
gameState.registrarVictoria();      // Modelo actualiza
estadisticasPanel.actualizar();      // Vista lee el cambio
// Sin necesidad de sincronización compleja
```

### 2. **Acceso Sencillo**
```java
// No necesitas pasar referencias
ControlJuego control = ControlJuego.getInstance();
// Disponible desde cualquier lugar
```

### 3. **Centralización**
```java
// Un solo lugar para todas las estadísticas
control.mostrarEstadoCompleto();
// En lugar de preguntar a múltiples objetos
```

### 4. **Ahorro de Memoria**
```java
// Una sola instancia para toda la aplicación
// vs múltiples copias del estado
```

### 5. **Sincronización Automática**
```java
// Cambios visibles inmediatamente en todos los módulos
control1.registrarVictoria();
int victorias = control2.getCombatesGanados();  // Actualizado
```

---


## 📊 Tecnologías y Herramientas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 21 | Lenguaje de programación principal |
| Maven | 3.6+ | Gestión de dependencias y build |
| JUnit 5 | 5.10.1 | Framework de testing |
| Mockito | 5.7.0 | Framework para mocking en tests |
| AssertJ | 3.24.2 | Assertions fluidas para tests |
| JaCoCo | 0.8.11 | Análisis de cobertura de código |
| Swing | Built-in | Interfaz gráfica de usuario |

## 🏗️ Principios SOLID Aplicados

### 1. **S**ingle Responsibility Principle (SRP)
*"Una clase debe tener una sola razón para cambiar"*

**Implementación:**
- `CalculadorDanoBasico`: Única responsabilidad de calcular daño base
- `CalculadorMultiplicadorFuerza`: Solo calcula multiplicadores de tipo
- `GameState`: Solo gestiona el estado del juego
- `CombatePanel`: Solo renderiza la interfaz de combate

**Beneficio:** Cada clase es fácil de entender, mantener y testear

### 2. **O**pen/Closed Principle (OCP)
*"Abierto para extensión, cerrado para modificación"*

**Implementación:**
```java
// Clase base cerrada a modificación
public abstract class TipoElemento {
    public abstract double calcularMultiplicador(TipoElemento tipoDefensor);
}

// Extensiones abiertas - agregar nuevos tipos sin modificar existentes
public class TipoFuego extends TipoElemento { /* ... */ }
public class TipoAgua extends TipoElemento { /* ... */ }
```

**Beneficio:** Agregar nuevos tipos Pokemon sin tocar código existente

### 3. **L**iskov Substitution Principle (LSP)
*"Los subtipos deben ser sustituibles por sus tipos base"*

**Implementación:**
```java
// Todos los movimientos son intercambiables
Movimiento mov = new MovimientoEstado("Gruñido", TipoNormal.getInstance());
Movimiento cur = new MovimientoCuracion("Síntesis", TipoPlanta.getInstance());

// Cualquier movimiento funciona en este método
pokemon.aprenderMovimiento(mov);
pokemon.aprenderMovimiento(cur);
```

**Beneficio:** Polimorfismo real y código flexible

### 4. **I**nterface Segregation Principle (ISP)
*"Interfaces específicas mejor que interfaces generales"*

**Implementación:**
- `ICalculadorDano`: Solo para calcular daño
- `IMostrador`: Solo para mostrar información
- `ICalculadorMultiplicador`: Solo para multiplicadores

**Beneficio:** Las clases solo implementan lo que necesitan

### 5. **D**ependency Inversion Principle (DIP)
*"Depender de abstracciones, no de concreciones"*

**Implementación:**
```java
public class Pokemon {
    private final ICalculadorDano calculadorDano;
    
    // Depende de la abstracción, no de la implementación concreta
    public Pokemon(ICalculadorDano calculador) {
        this.calculadorDano = calculador;
    }
}
```

**Beneficio:** Facilita testing con mocks y cambio de implementaciones

---

## 🔄 Extensibilidad del Sistema

El diseño permite **fácil extensión** sin modificar código existente:

### Agregar Nuevo Tipo Elemental
```java
public class TipoVolador extends TipoElemento {
    private static final TipoVolador INSTANCE = new TipoVolador();
    
    @Override
    public double calcularMultiplicador(TipoElemento tipoDefensor) {
        // Definir ventajas/desventajas
        if (tipoDefensor instanceof TipoPlanta) return 2.0;
        if (tipoDefensor instanceof TipoElectrico) return 0.5;
        return 1.0;
    }
}
```

### Agregar Nuevo Pokemon
```java
Pokemon pidgey = new Pokemon(
    "Pidgey",
    TipoVolador.getInstance(),
    45, 40, 35, // HP, Ataque, Defensa
    "pidgey_front.gif",
    "pidgey_back.gif"
);
```

### Agregar Nuevo Movimiento Especial
```java
public class MovimientoEstadistica extends Movimiento {
    @Override
    public void ejecutar(Pokemon atacante, Pokemon defensor) {
        // Lógica específica del nuevo tipo de movimiento
        atacante.aumentarAtaque(10);
    }
}
```

### Agregar Nuevo Item
```java
Item revivir = new Item(
    "Revivir",
    "Revive a un Pokemon debilitado",
    ItemType.REVIVE,
    pokemon -> pokemon.revivir()
);
```

**Ventaja:** El sistema está diseñado para crecer sin romper funcionalidad existente

## 📖 Cómo Usar el Sistema

### Iniciar el Juego

1. **Compilar y ejecutar**:
   ```bash
   cd pokemon-batle-system
   mvn clean compile exec:java -Dexec.mainClass="com.pokemon.MainApp"
   ```

2. **Menú Principal**: Se mostrará el menú con opciones
   - Nuevo Combate
   - Ver Estadísticas
   - Salir

3. **Seleccionar Pokemon**: Elige tu equipo de Pokemon

4. **Combatir**: Utiliza movimientos estratégicamente

5. **Ver Estadísticas**: Consulta tu progreso y estadísticas globales

### Demostración del Singleton

Para ver cómo funciona el patrón Singleton:

```bash
mvn exec:java -Dexec.mainClass="com.pokemon.singleton.DemoSingleton"
```

Esto mostrará:
- Creación de la instancia única
- Múltiples referencias al mismo objeto
- Registro de eventos y estadísticas
- Estado global compartido

## 👥 Autores

- **-** - Desarrollo inicial
- **-** - Desarrollo inicial
- **-** - Desarrollo inicial
- **Universidad Nacional de San Agustín** - Curso de Tópicos de Optimización


## 💡 Conclusiones

- El patrón **Singleton** centraliza el control del estado global del juego
- Garantiza que solo exista **una instancia** accesible desde todos los módulos
- Facilita la **gestión de estadísticas** y progresión del jugador
- Se integra perfectamente con la **arquitectura MVC**
- Permite que **Modelo, Vista y Controlador** compartan el mismo estado
- Simplifica el código al eliminar la necesidad de pasar referencias
- El sistema de **puntuación y niveles** está completamente centralizado
- La demostración en `DemoSingleton.java` prueba que **múltiples referencias** apuntan al mismo objeto
- Los **tests unitarios** verifican el correcto funcionamiento del patrón
- Es importante considerar la gestión de memoria y el **reinicio** del estado al comenzar nuevas partidas
- La aplicación de **principios SOLID** hace el código mantenible y extensible
- La arquitectura **MVC** proporciona clara separación de responsabilidades

---
