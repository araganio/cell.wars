import java.awt.Color;
import java.awt.Graphics2D;

public class Cell extends Entity {

    private Color color;
    private double speed = 2.5;

    // 🎯 Objetivo (posición del mouse)
    private int targetX;
    private int targetY;

    public Cell(double x, double y, double size, Color color) {
        super(x, y, size);
        this.color = color;
    }

    // 📍 Recibe la posición del mouse
    public void setTarget(int x, int y) {
        this.targetX = x;
        this.targetY = y;
    }

    // 🔄 Movimiento
    @Override
    public void update() {

        double dx = targetX - x;
        double dy = targetY - y;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 1) {
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
    }

    // 🎨 Dibujo
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int)x, (int)y, (int)size, (int)size);
    }
}
