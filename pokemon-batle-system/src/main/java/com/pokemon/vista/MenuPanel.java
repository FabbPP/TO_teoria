package com.pokemon.vista;

import com.pokemon.controlador.GameController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel del menú principal
 */
public class MenuPanel extends JPanel {
    private GameController controlador;
    
    public MenuPanel(GameController controlador) {
        this.controlador = controlador;
        
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 50));
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        // Panel central con título y botones
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        
        // Título
        JLabel titulo = new JLabel("POKEMON BATTLE");
        titulo.setFont(new Font("Arial", Font.BOLD, 72));
        titulo.setForeground(new Color(255, 215, 0));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitulo = new JLabel("Sistema de Combate");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 24));
        subtitulo.setForeground(Color.WHITE);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Espaciado
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(titulo);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(subtitulo);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 80)));
        
        // Botones
        JButton btnNuevoJuego = crearBoton("NUEVO JUEGO");
        JButton btnSalir = crearBoton("SALIR");
        
        btnNuevoJuego.addActionListener(e -> controlador.iniciarNuevoJuego());
        btnSalir.addActionListener(e -> controlador.salirJuego());
        
        centerPanel.add(btnNuevoJuego);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(btnSalir);
        centerPanel.add(Box.createVerticalGlue());
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Footer
        JLabel footer = new JLabel("Presiona NUEVO JUEGO para comenzar");
        footer.setFont(new Font("Arial", Font.ITALIC, 14));
        footer.setForeground(Color.LIGHT_GRAY);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        footer.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(footer, BorderLayout.SOUTH);
    }
    
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 28));
        boton.setPreferredSize(new Dimension(350, 70));
        boton.setMaximumSize(new Dimension(350, 70));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.setBackground(new Color(220, 50, 50));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
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
}