package pokemon.vista;

import pokemon.controlador.GameController;
import pokemon.Pokemon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel para seleccionar los 3 Pokemon iniciales
 */
public class SeleccionPokemonPanel extends JPanel {
    private GameController controlador;
    private JPanel gridPanel;
    private JPanel seleccionadosPanel;
    private JButton btnConfirmar;
    private List<PokemonCard> cards;
    
    public SeleccionPokemonPanel(GameController controlador) {
        this.controlador = controlador;
        this.cards = new ArrayList<>();
        
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(40, 50, 70));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        // Panel superior con título e instrucciones
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        JLabel titulo = new JLabel("SELECCIONA TUS 3 POKEMON");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(new Color(255, 215, 0));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel instrucciones = new JLabel("Haz clic en 3 Pokemon para agregarlos a tu equipo");
        instrucciones.setFont(new Font("Arial", Font.PLAIN, 16));
        instrucciones.setForeground(Color.WHITE);
        instrucciones.setHorizontalAlignment(SwingConstants.CENTER);
        
        topPanel.add(titulo, BorderLayout.NORTH);
        topPanel.add(instrucciones, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Grid de Pokemon disponibles
        gridPanel = new JPanel(new GridLayout(3, 3, 15, 15));
        gridPanel.setOpaque(false);
        
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con Pokemon seleccionados y botones
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setOpaque(false);
        
        // Pokemon seleccionados
        JLabel lblSeleccionados = new JLabel("EQUIPO SELECCIONADO:");
        lblSeleccionados.setFont(new Font("Arial", Font.BOLD, 20));
        lblSeleccionados.setForeground(Color.WHITE);
        
        seleccionadosPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        seleccionadosPanel.setOpaque(false);
        seleccionadosPanel.setPreferredSize(new Dimension(0, 120));
        
        JPanel seleccionContainer = new JPanel(new BorderLayout());
        seleccionContainer.setOpaque(false);
        seleccionContainer.add(lblSeleccionados, BorderLayout.NORTH);
        seleccionContainer.add(seleccionadosPanel, BorderLayout.CENTER);
        
        // Botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botonesPanel.setOpaque(false);
        
        btnConfirmar = crearBoton("CONFIRMAR EQUIPO");
        btnConfirmar.setEnabled(false);
        btnConfirmar.addActionListener(e -> controlador.confirmarSeleccion());
        
        JButton btnCancelar = crearBoton("CANCELAR");
        btnCancelar.addActionListener(e -> controlador.cancelarSeleccion());
        
        botonesPanel.add(btnConfirmar);
        botonesPanel.add(btnCancelar);
        
        bottomPanel.add(seleccionContainer, BorderLayout.CENTER);
        bottomPanel.add(botonesPanel, BorderLayout.SOUTH);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    public void actualizar() {
        // Limpiar grid
        gridPanel.removeAll();
        cards.clear();
        
        // Agregar cards de Pokemon disponibles
        for (Pokemon pokemon : controlador.getModelo().getPokemonDisponibles()) {
            PokemonCard card = new PokemonCard(pokemon);
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!card.isSeleccionado() && controlador.getModelo().getPokemonSeleccionados().size() < 3) {
                        controlador.seleccionarPokemon(pokemon);
                        actualizarSeleccion();
                    }
                }
            });
            cards.add(card);
            gridPanel.add(card);
        }
        
        actualizarSeleccion();
        gridPanel.revalidate();
        gridPanel.repaint();
    }
    
    private void actualizarSeleccion() {
        List<Pokemon> seleccionados = controlador.getModelo().getPokemonSeleccionados();
        
        // Actualizar estado de las cards
        for (PokemonCard card : cards) {
            card.setSeleccionado(seleccionados.contains(card.getPokemon()));
        }
        
        // Actualizar panel de seleccionados
        seleccionadosPanel.removeAll();
        for (Pokemon p : seleccionados) {
            JLabel lbl = new JLabel(p.getNombre());
            lbl.setFont(new Font("Arial", Font.BOLD, 18));
            lbl.setForeground(Color.WHITE);
            lbl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
            ));
            lbl.setOpaque(true);
            lbl.setBackground(new Color(60, 70, 90));
            seleccionadosPanel.add(lbl);
        }
        
        // Habilitar botón confirmar si hay 3 Pokemon
        btnConfirmar.setEnabled(seleccionados.size() == 3);
        
        seleccionadosPanel.revalidate();
        seleccionadosPanel.repaint();
    }
    
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 18));
        boton.setPreferredSize(new Dimension(250, 50));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(220, 50, 50));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (boton.isEnabled()) {
                    boton.setBackground(new Color(255, 70, 70));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(220, 50, 50));
            }
        });
        
        return boton;
    }
    
    // Clase interna para representar una card de Pokemon
    private class PokemonCard extends JPanel {
        private Pokemon pokemon;
        private boolean seleccionado;
        
        public PokemonCard(Pokemon pokemon) {
            this.pokemon = pokemon;
            this.seleccionado = false;
            
            setLayout(new BorderLayout(5, 5));
            setBackground(new Color(60, 70, 90));
            setBorder(BorderFactory.createLineBorder(new Color(100, 110, 130), 2));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(180, 200));
            
            // Imagen placeholder
            JLabel imgLabel = new JLabel("IMG", SwingConstants.CENTER);
            imgLabel.setFont(new Font("Arial", Font.BOLD, 24));
            imgLabel.setForeground(Color.GRAY);
            imgLabel.setPreferredSize(new Dimension(0, 100));
            imgLabel.setOpaque(true);
            imgLabel.setBackground(new Color(80, 90, 110));
            
            // Info
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setOpaque(false);
            infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JLabel nombre = new JLabel(pokemon.getNombre());
            nombre.setFont(new Font("Arial", Font.BOLD, 16));
            nombre.setForeground(Color.WHITE);
            nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel tipo = new JLabel("Tipo: " + pokemon.getTipos().get(0).getNombre());
            tipo.setFont(new Font("Arial", Font.PLAIN, 12));
            tipo.setForeground(Color.LIGHT_GRAY);
            tipo.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel stats = new JLabel(String.format("HP:%d ATK:%d DEF:%d", 
                pokemon.getVidaMax(), pokemon.getAtaque(), pokemon.getDefensa()));
            stats.setFont(new Font("Arial", Font.PLAIN, 11));
            stats.setForeground(Color.LIGHT_GRAY);
            stats.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            infoPanel.add(nombre);
            infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            infoPanel.add(tipo);
            infoPanel.add(Box.createRigidArea(new Dimension(0, 3)));
            infoPanel.add(stats);
            
            add(imgLabel, BorderLayout.CENTER);
            add(infoPanel, BorderLayout.SOUTH);
            
            // Efecto hover
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!seleccionado) {
                        setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3));
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    if (!seleccionado) {
                        setBorder(BorderFactory.createLineBorder(new Color(100, 110, 130), 2));
                    }
                }
            });
        }
        
        public void setSeleccionado(boolean seleccionado) {
            this.seleccionado = seleccionado;
            if (seleccionado) {
                setBackground(new Color(50, 120, 50));
                setBorder(BorderFactory.createLineBorder(new Color(100, 255, 100), 4));
            } else {
                setBackground(new Color(60, 70, 90));
                setBorder(BorderFactory.createLineBorder(new Color(100, 110, 130), 2));
            }
        }
        
        public boolean isSeleccionado() {
            return seleccionado;
        }
        
        public Pokemon getPokemon() {
            return pokemon;
        }
    }
}