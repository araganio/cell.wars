/**
 * Fábrica de entidades del juego.
 * 
 * Se encarga de centralizar la creación de objetos como:
 * - Jugador (Cell)
 * - Bots (futuro)
 * - Comida (futuro)
 * - Power-ups (futuro)
 * 
 * Implementa el patrón Factory Method, permitiendo:
 * - Desacoplar la creación de objetos
 * - Facilitar la extensión del sistema
 * - Evitar dependencias directas en el código principal
 */

import java.awt.Color;
import java.util.Random;

/**
 * Fábrica de entidades del juego.
 * 
 * Se encarga de crear:
 * - Jugador
 * - Bots
 * - Comida
 * 
 * Aplica el patrón Factory Method para desacoplar la creación de objetos.
 */
public class EntityFactory {

    private static Random random = new Random();

    /**
     * Crea el jugador con movimiento controlado por el mouse
     */
    public static Cell createPlayer(MouseHandler mouseH) {

        Cell player = new Cell(350, 250, 50, Color.GREEN);

        // Strategy: movimiento con mouse
        player.setMovementStrategy(new PlayerMovement(mouseH));

        return player;
    }

    /**
     * Crea un bot con movimiento aleatorio
     */
    public static Cell createBot() {

        int x = random.nextInt(800);
        int y = random.nextInt(600);

        Cell bot = new Cell(x, y, 40, Color.RED);

        // Strategy: movimiento automático
        bot.setMovementStrategy(new RandomMovement());

        return bot;
    }

    /**
     * Crea comida en posición aleatoria
     */
    public static Food createFood(int maxWidth, int maxHeight) {

        int x = random.nextInt(maxWidth);
        int y = random.nextInt(maxHeight);

        return new Food(x, y, 8, Color.ORANGE);
    }
}
