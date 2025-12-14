package com.pokemon.vista;

import com.pokemon.modelo.GameState;
import com.pokemon.modelo.GameState.GameStateObserver;
import com.pokemon.controlador.GameController;
import javax.swing.*;
import java.awt.*;

//VISTA PRINCIPAL - Ventana del juego que contiene todos los paneles

public class GameFrame extends JFrame implements GameStateObserver {
    private GameController controlador;
    private GameState modelo;
    
    // Paneles de diferentes estados
    private MenuPanel menuPanel;
    private SeleccionPokemonPanel seleccionPanel;
    private SeleccionInicialPanel seleccionInicialPanel;
    private CombatePanel combatePanel;
    private ResultadoPanel resultadoPanel;
    
    private CardLayout cardLayout;
    private JPanel containerPanel;
    
    public GameFrame(GameController controlador) {
        this.controlador = controlador;
        this.modelo = controlador.getModelo();
        
        // Configurar ventana
        setTitle("Pokemon Battle System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Configurar CardLayout para cambiar entre paneles
        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);
        
        // Crear paneles
        inicializarPaneles();
        
        // Agregar container al frame
        add(containerPanel);
        
        // Registrarse como observador del modelo
        modelo.agregarObservador(this);
        
        // Mostrar panel inicial
        actualizarVista();
    }
    
    private void inicializarPaneles() {
        menuPanel = new MenuPanel(controlador);
        seleccionPanel = new SeleccionPokemonPanel(controlador);
        seleccionInicialPanel = new SeleccionInicialPanel(controlador);
        combatePanel = new CombatePanel(controlador);
        resultadoPanel = new ResultadoPanel(controlador);
        
        containerPanel.add(menuPanel, "MENU");
        containerPanel.add(seleccionPanel, "SELECCION");
        containerPanel.add(seleccionInicialPanel, "INICIAL");
        containerPanel.add(combatePanel, "COMBATE");
        containerPanel.add(resultadoPanel, "RESULTADO");
    }
    
    @Override
    public void onEstadoCambiado(GameState estado) {
        actualizarVista();
    }
    
    private void actualizarVista() {
        switch (modelo.getEstadoActual()) {
            case MENU_PRINCIPAL:
                cardLayout.show(containerPanel, "MENU");
                break;
            case SELECCION_POKEMON:
                seleccionPanel.actualizar();
                cardLayout.show(containerPanel, "SELECCION");
                break;
            case SELECCION_INICIAL:
                seleccionInicialPanel.actualizar();
                cardLayout.show(containerPanel, "INICIAL");
                break;
            case COMBATE:
                combatePanel.actualizar();
                cardLayout.show(containerPanel, "COMBATE");
                break;
            case VICTORIA:
            case DERROTA:
                resultadoPanel.actualizar();
                cardLayout.show(containerPanel, "RESULTADO");
                break;
        }
        
        containerPanel.revalidate();
        containerPanel.repaint();
    }
}