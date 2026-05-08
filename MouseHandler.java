/**
 * Maneja la entrada del usuario mediante el mouse.
 * 
 * Captura la posición actual del cursor en pantalla y la pone
 * a disposición del sistema.
 * 
 * No contiene lógica de movimiento, solo provee datos.
 * 
 * Esto permite una correcta separación de responsabilidades:
 * - Input (MouseHandler)
 * - Lógica (GamePanel / Cell)
 */


import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class MouseHandler implements MouseMotionListener {

    public int mouseX;
    public int mouseY;

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }
}
