/**
 * Clase principal del motor del juego.
 * 
 * Se encarga de:
 * - Ejecutar el Game Loop (update/render)
 * - Dibujar todos los elementos en pantalla
 * - Coordinar la lógica del juego
 * - Gestionar la interacción entre objetos
 * 
 * Implementa un bucle a 60 FPS para asegurar fluidez.
 * 
 * Actúa como el núcleo del sistema, donde se integran:
 * - Input (MouseHandler)
 * - Entidades (Cell, etc.)
 * 
 * En el futuro, esta clase trabajará junto con un Facade
 * para simplificar la gestión de subsistemas.
 */
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.*;

/**
 * Clase principal del motor del juego.
 * 
 * Se encarga de:
 * - Ejecutar el Game Loop (update/render)
 * - Dibujar todos los elementos en pantalla
 * - Coordinar la lógica del juego
 */
public class GamePanel extends JPanel implements Runnable {

    // Tamaño de la ventana
    final int WIDTH = 800;
    final int HEIGHT = 600;

    MouseHandler mouseH = new MouseHandler();

    Thread gameThread;

    // 🎯 Jugador
    Cell player;

    // 🤖 Bots
    ArrayList<Cell> bots = new ArrayList<>();

    // 🍔 Comida
    ArrayList<Food> foods = new ArrayList<>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addMouseMotionListener(mouseH);

        // 🟢 Crear jugador (con Strategy)
        player = EntityFactory.createPlayer(mouseH);

        // 🔴 Crear bots
        for (int i = 0; i < 5; i++) {
            bots.add(EntityFactory.createBot());
        }

        // 🍔 Crear comida
        for (int i = 0; i < 30; i++) {
            foods.add(EntityFactory.createFood(WIDTH, HEIGHT));
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double FPS = 60;
        double drawInterval = 1000000000 / FPS;
        double delta = 0;

        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    // 🔄 Lógica del juego
    public void update() {

        // 🟢 Jugador
        player.update();

        // 🔴 Bots
        for (Cell bot : bots) {
            bot.update();
        }

        // 🍔 Colisiones con comida
        for (int i = 0; i < foods.size(); i++) {

            Food food = foods.get(i);

            double dx = (player.getX() + player.getSize() / 2) - 
                        (food.getX() + food.getSize() / 2);

            double dy = (player.getY() + player.getSize() / 2) - 
                        (food.getY() + food.getSize() / 2);

            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < player.getSize() / 2) {

                foods.remove(i);

                player.grow(1);

                foods.add(EntityFactory.createFood(WIDTH, HEIGHT));

                i--;
            }
        }
    }

    // 🎨 Render
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // 🍔 Dibujar comida
        for (Food food : foods) {
            food.draw(g2);
        }

        // 🔴 Dibujar bots
        for (Cell bot : bots) {
            bot.draw(g2);
        }

        // 🟢 Dibujar jugador
        player.draw(g2);

        g2.dispose();
    }
}