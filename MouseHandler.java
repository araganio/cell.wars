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
