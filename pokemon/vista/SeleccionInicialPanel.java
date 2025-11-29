package pokemon.vista;

import pokemon.controlador.GameController;
import pokemon.Pokemon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel para elegir el Pokemon inicial del combate
 */
public class SeleccionInicialPanel extends JPanel {
    private GameController controlador;
    private JPanel pokemonPanel;
    
    public SeleccionInicialPanel(GameController controlador) {
        this.controlador = controlador;
        
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(40, 50, 70));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        // Título
        JLabel titulo = new JLabel("ELIGE TU POKEMON INICIAL");
        titulo.setFont(new Font("Arial", Font.BOLD, 42));
        titulo.setForeground(new Color(255, 215, 0));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        
        add(titulo, BorderLayout.NORTH);
        
        // Panel central para los Pokemon
        pokemonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        pokemonPanel.setOpaque(false);
        
        add(pokemonPanel, BorderLayout.CENTER);
    }
    
    public void actualizar() {
        pokemonPanel.removeAll();
        
        if (controlador.getModelo().getJugador() != null) {
            for (Pokemon pokemon : controlador.getModelo().getJugador().getEquipo()) {
                JPanel card = crearPokemonCard(pokemon);
                pokemonPanel.add(card);
            }
        }
        
        pokemonPanel.revalidate();
        pokemonPanel.repaint();
    }
    
    private JPanel crearPokemonCard(Pokemon pokemon) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setPreferredSize(new Dimension(250, 320));
        card.setBackground(new Color(60, 70, 90));
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        /* Imagen placeholder
        JLabel imgLabel = new JLabel("IMG", SwingConstants.CENTER);
        imgLabel.setFont(new Font("Arial", Font.BOLD, 36));
        imgLabel.setForeground(Color.GRAY);
        imgLabel.setPreferredSize(new Dimension(0, 180));
        imgLabel.setOpaque(true);
        imgLabel.setBackground(new Color(80, 90, 110));
        */

        String nombreImg = pokemon.getNombre().toLowerCase() + ".png"; 
        System.out.println("Cargando imagen de: " + nombreImg);
        String ruta = "/pokemon/resources/image/" + nombreImg;

        ImageIcon icono = null;

        try {
            icono = new ImageIcon(getClass().getResource(ruta));
            // Escalar imagen a tamaño fijo
            Image imagen = icono.getImage().getScaledInstance(150, 120, Image.SCALE_SMOOTH);
            icono = new ImageIcon(imagen);
        } catch (Exception e) {
            System.out.println("No se encontró la imagen: " + ruta);
        }

        // Si no encontró imagen, usar texto placeholder
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        if (icono != null) {
            imgLabel.setIcon(icono);
        } else {
            imgLabel.setText("IMG");
            imgLabel.setForeground(Color.GRAY);
        }

        imgLabel.setPreferredSize(new Dimension(0, 100));
        imgLabel.setOpaque(true);
        imgLabel.setBackground(new Color(80, 90, 110));
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel nombre = new JLabel(pokemon.getNombre());
        nombre.setFont(new Font("Arial", Font.BOLD, 24));
        nombre.setForeground(Color.WHITE);
        nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel tipo = new JLabel("Tipo: " + pokemon.getTipos().get(0).getNombre());
        tipo.setFont(new Font("Arial", Font.PLAIN, 14));
        tipo.setForeground(Color.LIGHT_GRAY);
        tipo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel stats = new JLabel(String.format("HP:%d | ATK:%d | DEF:%d", 
            pokemon.getVidaMax(), pokemon.getAtaque(), pokemon.getDefensa()));
        stats.setFont(new Font("Arial", Font.PLAIN, 13));
        stats.setForeground(Color.LIGHT_GRAY);
        stats.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(nombre);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(tipo);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(stats);
        
        card.add(imgLabel, BorderLayout.CENTER);
        card.add(infoPanel, BorderLayout.SOUTH);
        
        // Efecto hover y click
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(80, 90, 110));
                card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 100), 4));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(new Color(60, 70, 90));
                card.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3));
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                controlador.elegirPokemonInicial(pokemon);
            }
        });
        
        return card;
    }
}