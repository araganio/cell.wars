/**
 * Representa una célula en el juego (jugador o bot).
 * 
 * Se encarga de:
 * - Gestionar su posición en el mapa
 * - Moverse hacia un objetivo (mouse o IA)
 * - Dibujarse en pantalla
 * 
 * La lógica de movimiento se basa en vectores normalizados para lograr
 * un desplazamiento suave y constante.
 * 
 * Esta clase será extendida en el futuro para:
 * - Implementar comportamientos de IA (Strategy)
 * - Aplicar mejoras temporales (Decorator)
 * - Gestionar estados (State)
 */
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Representa una célula en el juego (jugador o bot).
 * 
 * Se encarga de:
 * - Mantener posición y tamaño
 * - Ejecutar movimiento (Strategy Pattern)
 * - Dibujarse en pantalla
 * - Crecer al consumir comida
 */
public class Cell extends Entity {

    private Color color;

    //  Velocidad base
    private double baseSpeed = 20;

    //  Estrategia de movimiento
    private MovementStrategy movementStrategy;

    public Cell(double x, double y, double size, Color color) {

        super(x, y, size);

        this.color = color;
    }

    /**
     * Asigna una estrategia de movimiento
     */
    public void setMovementStrategy(MovementStrategy strategy) {

        this.movementStrategy = strategy;
    }

    /**
     * Ejecuta movimiento usando Strategy
     */
    public void move() {

        if (movementStrategy != null) {
            movementStrategy.move(this);
        }
    }

    /**
     * Actualización por frame
     */
    @Override
    public void update() {

        move();
    }

    /**
     * Dibuja la célula
     */
    @Override
    public void draw(Graphics2D g2) {

        g2.setColor(color);

        g2.fillOval(
                (int) x,
                (int) y,
                (int) size,
                (int) size
        );
    }

    /**
     * Hace crecer la célula
     */
    public void grow(double amount) {

        size += amount * 0.5;
    }

    // =========================
    // GETTERS
    // =========================

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }

    /**
     *  Velocidad dinámica según tamaño
     */
    public double getSpeed() {

        double speed = baseSpeed / Math.sqrt(size);

        // velocidad mínima
        if (speed < 1.2) {
            speed = 1.2;
        }

        return speed;
    }

    // =========================
    // SETTERS
    // =========================

    /**
     * Permite mover la célula
     */
    public void setPosition(double x, double y) {

        this.x = x;
        this.y = y;
    }

    /**
     * Permite cambiar tamaño
     */
    public void setSize(double size) {

        this.size = size;
    }
}