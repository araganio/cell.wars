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
 * - Mantener su posición y tamaño
 * - Ejecutar su estrategia de movimiento (Strategy)
 * - Dibujarse en pantalla
 * - Crecer al consumir comida
 */
public class Cell extends Entity {

    private Color color;
    private double speed = 2.5;

    // 🧠 Strategy de movimiento
    private MovementStrategy movementStrategy;

    public Cell(double x, double y, double size, Color color) {
        super(x, y, size);
        this.color = color;
    }

    /**
     * Asigna la estrategia de movimiento (Strategy Pattern)
     */
    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    /**
     * Ejecuta la estrategia de movimiento actual
     */
    public void move() {
        if (movementStrategy != null) {
            movementStrategy.move(this);
        }
    }

    /**
     * Actualización de la célula en cada frame
     */
    @Override
    public void update() {
        move();
    }

    /**
     * Dibuja la célula en pantalla
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int) x, (int) y, (int) size, (int) size);
    }

    /**
     * Hace crecer la célula al consumir comida
     */
    public void grow(double amount) {
        size += amount;
    }

    // 🔓 Getters necesarios (encapsulación)
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }

    public double getSpeed() {
        return speed;
    }

    // 🔧 Permite que las estrategias modifiquen la posición
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
}