package pokemon.vista;

import pokemon.controlador.GameController;
import pokemon.Pokemon;
import pokemon.movimientos.Movimiento;
import pokemon.Item;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
/**
 * Panel principal de combate
 */
public class CombatePanel extends JPanel {
    private GameController controlador;
    
    // Paneles principales
    private JPanel areaRival;
    private JPanel areaJugador;
    private JPanel areaAcciones;
    private JPanel logPanel;
    
    // Componentes de estado
    private JLabel rivalNombreLabel;
    private JProgressBar rivalVidaBar;
    private JLabel rivalVidaLabel;
    private JLabel rivalSpriteLabel;
    
    private JLabel jugadorNombreLabel;
    private JProgressBar jugadorVidaBar;
    private JLabel jugadorVidaLabel;
    private JLabel jugadorSpriteLabel;
    
    private JTextArea logTextArea;
    private JLabel turnoLabel;
    
    // Panel de acciones
    private CardLayout accionesLayout;
    private JPanel accionesContainer;
    
    public CombatePanel(GameController controlador) {
        this.controlador = controlador;
        
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(40, 50, 70));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        // Panel superior - Pokemon rival
        areaRival = crearAreaRival();
        add(areaRival, BorderLayout.NORTH);
        
        // Panel central - Log de combate
        logPanel = crearLogPanel();
        add(logPanel, BorderLayout.CENTER);
        
        // Panel inferior izquierdo - Pokemon jugador
        areaJugador = crearAreaJugador();
        
        // Panel inferior derecho - Acciones
        areaAcciones = crearAreaAcciones();
        
        // Combinar jugador y acciones
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.add(areaJugador);
        bottomPanel.add(areaAcciones);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JPanel crearAreaRival() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(0, 180));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 50, 50), 2),
            "RIVAL",
            0, 0,
            new Font("Arial", Font.BOLD, 16),
            Color.WHITE
        ));
        
        // Sprite del rival
        rivalSpriteLabel = new JLabel("RIVAL", SwingConstants.CENTER);
        rivalSpriteLabel.setFont(new Font("Arial", Font.BOLD, 48));
        rivalSpriteLabel.setForeground(Color.GRAY);
        rivalSpriteLabel.setPreferredSize(new Dimension(200, 0));
        rivalSpriteLabel.setOpaque(true);
        rivalSpriteLabel.setBackground(new Color(80, 90, 110));
        
        // Info del rival
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        rivalNombreLabel = new JLabel("???");
        rivalNombreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        rivalNombreLabel.setForeground(Color.WHITE);
        
        rivalVidaBar = new JProgressBar(0, 100);
        rivalVidaBar.setStringPainted(true);
        rivalVidaBar.setForeground(new Color(50, 200, 50));
        rivalVidaBar.setBackground(new Color(100, 30, 30));
        rivalVidaBar.setPreferredSize(new Dimension(0, 30));
        
        rivalVidaLabel = new JLabel("HP: 0/0");
        rivalVidaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        rivalVidaLabel.setForeground(Color.WHITE);
        
        infoPanel.add(rivalNombreLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(rivalVidaBar);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(rivalVidaLabel);
        
        panel.add(rivalSpriteLabel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearAreaJugador() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(0, 200));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(50, 120, 200), 2),
            "TU POKEMON",
            0, 0,
            new Font("Arial", Font.BOLD, 16),
            Color.WHITE
        ));
        
        // Sprite del jugador
        jugadorSpriteLabel = new JLabel("JUGADOR", SwingConstants.CENTER);
        jugadorSpriteLabel.setFont(new Font("Arial", Font.BOLD, 36));
        jugadorSpriteLabel.setForeground(Color.GRAY);
        jugadorSpriteLabel.setPreferredSize(new Dimension(180, 0));
        jugadorSpriteLabel.setOpaque(true);
        jugadorSpriteLabel.setBackground(new Color(80, 90, 110));
        
        // Info del jugador
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        jugadorNombreLabel = new JLabel("???");
        jugadorNombreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        jugadorNombreLabel.setForeground(Color.WHITE);
        
        jugadorVidaBar = new JProgressBar(0, 100);
        jugadorVidaBar.setStringPainted(true);
        jugadorVidaBar.setForeground(new Color(50, 200, 50));
        jugadorVidaBar.setBackground(new Color(100, 30, 30));
        jugadorVidaBar.setPreferredSize(new Dimension(0, 30));
        
        jugadorVidaLabel = new JLabel("HP: 0/0");
        jugadorVidaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        jugadorVidaLabel.setForeground(Color.WHITE);
        
        infoPanel.add(jugadorNombreLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(jugadorVidaBar);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(jugadorVidaLabel);
        
        panel.add(jugadorSpriteLabel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearLogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 2),
            "LOG DE COMBATE",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            Color.WHITE
        ));
        
        turnoLabel = new JLabel("Turno: 1");
        turnoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        turnoLabel.setForeground(new Color(255, 215, 0));
        turnoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnoLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        logTextArea.setBackground(new Color(30, 35, 45));
        logTextArea.setForeground(Color.WHITE);
        logTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setBorder(null);
        
        panel.add(turnoLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearAreaAcciones() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(0, 200));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            "ACCIONES",
            0, 0,
            new Font("Arial", Font.BOLD, 16),
            Color.WHITE
        ));
        
        // CardLayout para cambiar entre menús
        accionesLayout = new CardLayout();
        accionesContainer = new JPanel(accionesLayout);
        accionesContainer.setOpaque(false);
        
        // Menú principal
        JPanel menuPrincipal = crearMenuPrincipal();
        accionesContainer.add(menuPrincipal, "PRINCIPAL");
        
        // Menú de ataques (se crea dinámicamente)
        accionesContainer.add(new JPanel(), "ATAQUES");
        
        // Menú de items (se crea dinámicamente)
        accionesContainer.add(new JPanel(), "ITEMS");
        
        // Menú de cambio de Pokemon (se crea dinámicamente)
        accionesContainer.add(new JPanel(), "POKEMON");
        
        panel.add(accionesContainer, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearMenuPrincipal() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnLuchar = crearBotonAccion("LUCHAR");
        JButton btnMochila = crearBotonAccion("MOCHILA");
        JButton btnPokemon = crearBotonAccion("POKEMON");
        JButton btnRendirse = crearBotonAccion("RENDIRSE");
        
        btnLuchar.addActionListener(e -> mostrarMenuAtaques());
        btnMochila.addActionListener(e -> mostrarMenuItems());
        btnPokemon.addActionListener(e -> mostrarMenuPokemon());
        btnRendirse.addActionListener(e -> controlador.volverAlMenu());
        
        panel.add(btnLuchar);
        panel.add(btnMochila);
        panel.add(btnPokemon);
        panel.add(btnRendirse);
        
        return panel;
    }
    
    private void mostrarMenuAtaques() {
        JPanel panelAtaques = new JPanel(new GridLayout(3, 2, 10, 10));
        panelAtaques.setOpaque(false);
        panelAtaques.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        Pokemon pokemon = controlador.getModelo().getPokemonActivoJugador();
        if (pokemon != null) {
            for (Movimiento mov : pokemon.getMovimientos()) {
                JButton btnMov = crearBotonMovimiento(mov);
                btnMov.addActionListener(e -> {
                    controlador.atacar(mov);
                    accionesLayout.show(accionesContainer, "PRINCIPAL");
                });
                panelAtaques.add(btnMov);
            }
        }
        
        JButton btnVolver = crearBotonAccion("VOLVER");
        btnVolver.addActionListener(e -> accionesLayout.show(accionesContainer, "PRINCIPAL"));
        panelAtaques.add(btnVolver);
        
        accionesContainer.add(panelAtaques, "ATAQUES");
        accionesLayout.show(accionesContainer, "ATAQUES");
    }
    
    private void mostrarMenuItems() {
        JPanel panelItems = new JPanel(new GridLayout(3, 2, 10, 10));
        panelItems.setOpaque(false);
        panelItems.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        if (controlador.getModelo().getJugador() != null) {
            for (Item item : controlador.getModelo().getJugador().getBolsa()) {
                JButton btnItem = crearBotonItem(item);
                btnItem.addActionListener(e -> {
                    controlador.usarItem(item);
                    accionesLayout.show(accionesContainer, "PRINCIPAL");
                });
                panelItems.add(btnItem);
            }
        }
        
        JButton btnVolver = crearBotonAccion("VOLVER");
        btnVolver.addActionListener(e -> accionesLayout.show(accionesContainer, "PRINCIPAL"));
        panelItems.add(btnVolver);
        
        accionesContainer.add(panelItems, "ITEMS");
        accionesLayout.show(accionesContainer, "ITEMS");
    }
    
    private void mostrarMenuPokemon() {
        JPanel panelPokemon = new JPanel(new GridLayout(2, 2, 10, 10));
        panelPokemon.setOpaque(false);
        panelPokemon.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        if (controlador.getModelo().getJugador() != null) {
            for (Pokemon p : controlador.getModelo().getJugador().getEquipo()) {
                if (p != controlador.getModelo().getPokemonActivoJugador()) {
                    JButton btnPoke = crearBotonPokemon(p);
                    btnPoke.addActionListener(e -> {
                        controlador.cambiarPokemon(p);
                        accionesLayout.show(accionesContainer, "PRINCIPAL");
                    });
                    panelPokemon.add(btnPoke);
                }
            }
        }
        
        JButton btnVolver = crearBotonAccion("VOLVER");
        btnVolver.addActionListener(e -> accionesLayout.show(accionesContainer, "PRINCIPAL"));
        panelPokemon.add(btnVolver);
        
        accionesContainer.add(panelPokemon, "POKEMON");
        accionesLayout.show(accionesContainer, "POKEMON");
    }
    
    private JButton crearBotonAccion(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(60, 70, 90));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(80, 90, 110));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(60, 70, 90));
            }
        });
        
        return boton;
    }
    
    private JButton crearBotonMovimiento(Movimiento mov) {
        String texto = String.format("<html><center>%s<br><small>Poder: %d</small></center></html>", 
            mov.getNombre(), mov.getPotencia());
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(220, 50, 50));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(255, 70, 70));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(220, 50, 50));
            }
        });
        
        return boton;
    }
    
    private JButton crearBotonItem(Item item) {
        String texto = String.format("<html><center>%s<br><small>x%d</small></center></html>", 
            item.getNombre(), item.getUsos());
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(50, 120, 200));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setEnabled(item.disponible());
        
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (boton.isEnabled()) {
                    boton.setBackground(new Color(70, 140, 220));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(50, 120, 200));
            }
        });
        
        return boton;
    }
    
    private JButton crearBotonPokemon(Pokemon pokemon) {
        String estado = pokemon.estaVivo() ? 
            String.format("HP: %d/%d", pokemon.getVida(), pokemon.getVidaMax()) : 
            "DEBILITADO";
        String texto = String.format("<html><center>%s<br><small>%s</small></center></html>", 
            pokemon.getNombre(), estado);
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBackground(pokemon.estaVivo() ? new Color(50, 150, 50) : new Color(100, 100, 100));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setEnabled(pokemon.estaVivo());
        
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (boton.isEnabled()) {
                    boton.setBackground(new Color(70, 170, 70));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (pokemon.estaVivo()) {
                    boton.setBackground(new Color(50, 150, 50));
                }
            }
        });
        
        return boton;
    }
    
    public void actualizar() {
        Pokemon jugador = controlador.getModelo().getPokemonActivoJugador();
        Pokemon rival = controlador.getModelo().getPokemonActivoRival();
        
        if (jugador != null) {
            jugadorNombreLabel.setText(jugador.getNombre());
            jugadorVidaLabel.setText(String.format("HP: %d/%d", jugador.getVida(), jugador.getVidaMax()));
            jugadorVidaBar.setMaximum(jugador.getVidaMax());
            jugadorVidaBar.setValue(jugador.getVida());
            actualizarColorBarra(jugadorVidaBar, jugador.getVida(), jugador.getVidaMax());
        }
        
        if (rival != null) {
            rivalNombreLabel.setText(rival.getNombre());
            rivalVidaLabel.setText(String.format("HP: %d/%d", rival.getVida(), rival.getVidaMax()));
            rivalVidaBar.setMaximum(rival.getVidaMax());
            rivalVidaBar.setValue(rival.getVida());
            actualizarColorBarra(rivalVidaBar, rival.getVida(), rival.getVidaMax());
        }
        
        // ==== SPRITE JUGADOR ====
        if (jugador != null && jugador.getSpritePath() != null) {

            URL url = getClass().getResource(jugador.getSpritePath());

            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                jugadorSpriteLabel.setIcon(new ImageIcon(img));
                jugadorSpriteLabel.setText("");
            } else {
                // Placeholder estilizado para jugador
                jugadorSpriteLabel.setIcon(null);
                jugadorSpriteLabel.setText("JUGADOR");
                jugadorSpriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
                jugadorSpriteLabel.setForeground(Color.WHITE);
                jugadorSpriteLabel.setOpaque(true);
                jugadorSpriteLabel.setBackground(new Color(80, 90, 110));
            }

        } else {
            jugadorSpriteLabel.setIcon(null);
            jugadorSpriteLabel.setText("JUGADOR");
            jugadorSpriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jugadorSpriteLabel.setForeground(Color.WHITE);
            jugadorSpriteLabel.setOpaque(true);
            jugadorSpriteLabel.setBackground(new Color(80, 90, 110));
        }



        // ==== SPRITE RIVAL ====
        if (rival != null && rival.getSpritePath() != null) {

            URL url = getClass().getResource(rival.getSpritePath());

            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                rivalSpriteLabel.setIcon(new ImageIcon(img));
                rivalSpriteLabel.setText("");
            } else {
                // Placeholder estilizado para rival
                rivalSpriteLabel.setIcon(null);
                rivalSpriteLabel.setText("RIVAL");
                rivalSpriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
                rivalSpriteLabel.setForeground(Color.WHITE);
                rivalSpriteLabel.setOpaque(true);
                rivalSpriteLabel.setBackground(new Color(80, 90, 110));
            }

        } else {
            rivalSpriteLabel.setIcon(null);
            rivalSpriteLabel.setText("RIVAL");
            rivalSpriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
            rivalSpriteLabel.setForeground(Color.WHITE);
            rivalSpriteLabel.setOpaque(true);
            rivalSpriteLabel.setBackground(new Color(80, 90, 110));
        }


        
        // Actualizar log
        StringBuilder logBuilder = new StringBuilder();
        for (String linea : controlador.getModelo().getLogCombate()) {
            logBuilder.append(linea).append("\n");
        }
        logTextArea.setText(logBuilder.toString());
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
        
        // Actualizar turno
        turnoLabel.setText("Turno: " + controlador.getModelo().getTurnoActual());
        
        // Volver al menú principal de acciones
        accionesLayout.show(accionesContainer, "PRINCIPAL");
        
        repaint();
    }
    
    private void actualizarColorBarra(JProgressBar barra, int vidaActual, int vidaMax) {
        double porcentaje = (double) vidaActual / vidaMax;
        if (porcentaje > 0.5) {
            barra.setForeground(new Color(50, 200, 50));
        } else if (porcentaje > 0.25) {
            barra.setForeground(new Color(255, 200, 0));
        } else {
            barra.setForeground(new Color(220, 50, 50));
        }
    }
}