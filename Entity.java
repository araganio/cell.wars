import java.awt.Graphics2D;

public abstract class Entity {

    protected double x, y;
    protected double size;

    public Entity(double x, double y, double size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2);
}
