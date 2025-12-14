package com.pokemon.vista;

import com.pokemon.controlador.GameController;
import com.pokemon.modelo.GameState.EstadoJuego;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel de resultado del combate (Victoria o Derrota)
 */
public class ResultadoPanel extends JPanel {
    private GameController controlador;
    private JLabel tituloLabel;
    private JLabel mensajeLabel;
    
    public ResultadoPanel(GameController controlador) {
        this.controlador = controlador;
        
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 50));
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        // Panel central
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        
        // Título (Victoria o Derrota)
        tituloLabel = new JLabel("VICTORIA");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 72));
        tituloLabel.setForeground(new Color(255, 215, 0));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Mensaje
        mensajeLabel = new JLabel("¡Has ganado el combate!");
        mensajeLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        mensajeLabel.setForeground(Color.WHITE);
        mensajeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Botones
        JButton btnMenuPrincipal = crearBoton("VOLVER AL MENÚ");
        JButton btnSalir = crearBoton("SALIR");
        
        btnMenuPrincipal.addActionListener(e -> controlador.volverAlMenu());
        btnSalir.addActionListener(e -> controlador.salirJuego());
        
        // Agregar componentes
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(tituloLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(mensajeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 80)));
        centerPanel.add(btnMenuPrincipal);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(btnSalir);
        centerPanel.add(Box.createVerticalGlue());
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    public void actualizar() {
        EstadoJuego estado = controlador.getModelo().getEstadoActual();
        
        if (estado == EstadoJuego.VICTORIA) {
            tituloLabel.setText("¡VICTORIA!");
            tituloLabel.setForeground(new Color(255, 215, 0));
            mensajeLabel.setText("¡Has derrotado al rival!");
            setBackground(new Color(20, 50, 20));
        } else if (estado == EstadoJuego.DERROTA) {
            tituloLabel.setText("DERROTA");
            tituloLabel.setForeground(new Color(220, 50, 50));
            mensajeLabel.setText("Todos tus Pokemon fueron derrotados");
            setBackground(new Color(50, 20, 20));
        }
    }
    
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 24));
        boton.setPreferredSize(new Dimension(350, 60));
        boton.setMaximumSize(new Dimension(350, 60));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.setBackground(new Color(220, 50, 50));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3));
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
}