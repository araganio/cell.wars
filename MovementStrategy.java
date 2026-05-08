/**
 * Estrategia de movimiento para las células.
 * Permite definir diferentes comportamientos (jugador, bots, etc).
 */
public interface MovementStrategy {
    void move(Cell cell);
}