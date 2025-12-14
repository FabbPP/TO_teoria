package pokemon.vista;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Helper para aplicar estilos consistentes a componentes (por ahora botones)
 */
public class ComponentStyles {
    public static void styleButton(JButton boton,
                                   Color normalBg, Color hoverBg,
                                   Color fg, Color borderColor,
                                   Color disabledBg, Color disabledFg) {
        // Forzar pintura del fondo y borde para evitar que el LAF lo ignore
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setBorderPainted(true);
        boton.setFocusPainted(false);

        boton.setBackground(normalBg);
        boton.setForeground(fg);
        boton.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (boton.isEnabled()) boton.setBackground(hoverBg);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (boton.isEnabled()) boton.setBackground(normalBg);
            }
        });

        // Guardar valores originales para restaurar después
        final Color origBg = boton.getBackground();
        final Color origFg = boton.getForeground();
        final javax.swing.border.Border origBorder = boton.getBorder();

        // Ajustar apariencia cuando cambia el estado enabled/disabled
        ChangeListener cl = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean enabled = boton.getModel().isEnabled();
                if (!enabled) {
                    boton.setBackground(disabledBg);
                    boton.setForeground(disabledFg);
                    boton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    // Borde punteado más evidente
                    boton.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createDashedBorder(Color.DARK_GRAY, 2f, 5f),
                            BorderFactory.createEmptyBorder(4, 8, 4, 8)
                    ));
                    boton.setToolTipText("No disponible");
                } else {
                    boton.setBackground(normalBg);
                    boton.setForeground(fg);
                    boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    boton.setBorder(origBorder);
                    boton.setToolTipText(null);
                }
            }
        };

        boton.getModel().addChangeListener(cl);

        // Además escuchar la propiedad 'enabled' por si se cambia directamente
        boton.addPropertyChangeListener("enabled", evt -> {
            boolean enabled = (boolean) evt.getNewValue();
            if (!enabled) {
                boton.setBackground(disabledBg);
                boton.setForeground(disabledFg);
                boton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                boton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createDashedBorder(Color.DARK_GRAY, 2f, 5f),
                        BorderFactory.createEmptyBorder(4, 8, 4, 8)
                ));
                boton.setToolTipText("No disponible");
            } else {
                boton.setBackground(normalBg);
                boton.setForeground(fg);
                boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                boton.setBorder(origBorder);
                boton.setToolTipText(null);
            }
        });

        // Aplicar estado inicial (por si viene disabled desde el comienzo)
        if (!boton.getModel().isEnabled()) {
            boton.setBackground(disabledBg);
            boton.setForeground(disabledFg);
            boton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
