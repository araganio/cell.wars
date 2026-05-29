import java.awt.Color;
import java.awt.Graphics2D;

public class Food extends Entity {

    private Color color;

    public Food(double x, double y, double size, Color color) {
        super(x, y, size);
        this.color = color;
    }

    @Override
    public void update() {
        // la comida no se mueve
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int)x, (int)y, (int)size, (int)size);
    }

    //git status Getters necesarios (encapsulación)
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }
}