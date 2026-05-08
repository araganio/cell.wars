/**
 * Clase abstracta base para todas las entidades del juego.
 * 
 * Representa cualquier objeto presente en el mundo del juego (jugador, bots,
 * comida, power-ups, etc).
 * 
 * Define atributos comunes como posición y tamaño, así como los métodos
 * esenciales que deben implementar todas las entidades.
 * 
 * Esta clase es clave para permitir:
 * - Polimorfismo
 * - Extensibilidad del sistema
 * - Aplicación de patrones como Decorator y Factory
 */
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
