package com.pokemon.vista;


import com.pokemon.singleton.ControlJuego;
import javax.swing.*;
import java.awt.*;

//Panel que muestra las estadísticas globales del juego
// Obtiene datos directamente del SINGLETON ControlJuego

public class EstadisticasPanel extends JPanel {
    
    // Referencias a componentes visuales
    private JLabel lblNivel;
    private JLabel lblPuntaje;
    private JLabel lblVidas;
    private JLabel lblVictorias;
    private JLabel lblDerrotas;
    private JLabel lblRatio;
    private JLabel lblCapturados;
    private JLabel lblItems;
    private JLabel lblCriticos;
    private JProgressBar barraProgreso;
    
    // Referencia al Singleton
    private ControlJuego controlJuego;
    
    public EstadisticasPanel() {
        // ============================================
        // OBTENER LA INSTANCIA ÚNICA DEL SINGLETON
        // ============================================
        this.controlJuego = ControlJuego.getInstance();
        
        System.out.println("📊 EstadisticasPanel obtuvo instancia de ControlJuego: " + controlJuego);
        
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(40, 50, 70));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        inicializarComponentes();
        actualizar();
    }
    
    private void inicializarComponentes() {
        // Panel principal con grid
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        mainPanel.setOpaque(false);
        
        // Título
        JLabel titulo = new JLabel("📊 ESTADÍSTICAS GLOBALES", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(255, 215, 0));
        
        // ============================================
        // SECCIÓN 1: ESTADO DEL JUGADOR
        // ============================================
        JPanel seccionJugador = crearSeccion("👤 JUGADOR");
        
        lblNivel = crearEtiquetaDato("Nivel: --");
        lblPuntaje = crearEtiquetaDato("Puntaje: --");
        lblVidas = crearEtiquetaDato("Vidas: --");
        
        seccionJugador.add(lblNivel);
        seccionJugador.add(lblPuntaje);
        seccionJugador.add(lblVidas);
        
        // Barra de progreso de nivel
        barraProgreso = new JProgressBar(0, 1000);
        barraProgreso.setStringPainted(true);
        barraProgreso.setString("Progreso al siguiente nivel");
        barraProgreso.setForeground(new Color(100, 200, 100));
        barraProgreso.setBackground(new Color(60, 70, 90));
        seccionJugador.add(barraProgreso);
        
        // ============================================
        // SECCIÓN 2: COMBATES
        // ============================================
        JPanel seccionCombates = crearSeccion("⚔️ COMBATES");
        
        lblVictorias = crearEtiquetaDato("Victorias: --");
        lblDerrotas = crearEtiquetaDato("Derrotas: --");
        lblRatio = crearEtiquetaDato("Ratio: --%");
        
        seccionCombates.add(lblVictorias);
        seccionCombates.add(lblDerrotas);
        seccionCombates.add(lblRatio);
        
        // ============================================
        // SECCIÓN 3: ESTADÍSTICAS GENERALES
        // ============================================
        JPanel seccionGeneral = crearSeccion("📈 GENERAL");
        
        lblCapturados = crearEtiquetaDato("Pokemon: --");
        lblItems = crearEtiquetaDato("Items usados: --");
        lblCriticos = crearEtiquetaDato("Críticos: --");
        
        seccionGeneral.add(lblCapturados);
        seccionGeneral.add(lblItems);
        seccionGeneral.add(lblCriticos);
        
        // Botón de actualizar
        JButton btnActualizar = new JButton("🔄 ACTUALIZAR");
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 16));
        btnActualizar.setBackground(new Color(220, 50, 50));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(e -> actualizar());
        
        // Agregar secciones al panel principal
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setOpaque(false);
        
        contenedor.add(seccionJugador);
        contenedor.add(Box.createRigidArea(new Dimension(0, 20)));
        contenedor.add(seccionCombates);
        contenedor.add(Box.createRigidArea(new Dimension(0, 20)));
        contenedor.add(seccionGeneral);
        contenedor.add(Box.createRigidArea(new Dimension(0, 20)));
        contenedor.add(btnActualizar);
        
        add(titulo, BorderLayout.NORTH);
        add(new JScrollPane(contenedor), BorderLayout.CENTER);
    }
    
    private JPanel crearSeccion(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            titulo,
            0, 0,
            new Font("Arial", Font.BOLD, 18),
            Color.WHITE
        ));
        
        return panel;
    }
    
    private JLabel crearEtiquetaDato(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return label;
    }
    
    /**
     * Actualiza todos los datos desde el Singleton
     */
    public void actualizar() {
        // ============================================
        // LEER DATOS DEL SINGLETON
        // ============================================
        
        // Jugador
        lblNivel.setText("⭐ Nivel: " + controlJuego.getNivelActual());
        lblPuntaje.setText("💰 Puntaje: " + controlJuego.getPuntaje());
        lblVidas.setText("❤️ Vidas: " + controlJuego.getVidas());
        
        // Barra de progreso
        int puntajeRestante = controlJuego.getPuntaje() % 1000;
        barraProgreso.setValue(puntajeRestante);
        barraProgreso.setString(puntajeRestante + " / 1000 puntos");
        
        // Combates
        lblVictorias.setText("🏆 Victorias: " + controlJuego.getCombatesGanados());
        lblDerrotas.setText("😞 Derrotas: " + controlJuego.getCombatesPerdidos());
        lblRatio.setText("📊 Ratio: " + String.format("%.1f", controlJuego.getRatioVictorias()) + "%");
        
        // General
        lblCapturados.setText("⚡ Pokemon: " + controlJuego.getPokemonCapturados());
        lblItems.setText("💊 Items: " + controlJuego.getItemsUsados());
        lblCriticos.setText("💥 Críticos: " + controlJuego.getAtaquesCriticos());
        
        // Actualizar colores según estado
        actualizarColores();
        
        repaint();
    }
    
    private void actualizarColores() {
        // Cambiar color de vidas según cantidad
        if (controlJuego.getVidas() <= 1) {
            lblVidas.setForeground(new Color(255, 100, 100));
        } else if (controlJuego.getVidas() <= 2) {
            lblVidas.setForeground(new Color(255, 200, 100));
        } else {
            lblVidas.setForeground(Color.WHITE);
        }
        
        // Cambiar color de ratio según porcentaje
        double ratio = controlJuego.getRatioVictorias();
        if (ratio >= 70) {
            lblRatio.setForeground(new Color(100, 255, 100));
        } else if (ratio >= 50) {
            lblRatio.setForeground(new Color(255, 255, 100));
        } else {
            lblRatio.setForeground(new Color(255, 100, 100));
        }
    }
}