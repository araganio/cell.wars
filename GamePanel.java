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
 * Patrones usados:
 * - Strategy (movimiento)
 * - Factory Method (creación de entidades)
 */

import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Tamaño ventana
    final int WIDTH = 800;
    final int HEIGHT = 600;

    // Input
    KeyboardHandler keyH = new KeyboardHandler();
    MouseHandler mouseH = new MouseHandler();

    Thread gameThread;

    //  Jugador dividido en múltiples células
    ArrayList<Cell> playerCells = new ArrayList<>();

    //  Bots
    ArrayList<Cell> bots = new ArrayList<>();

    //  Comida
    ArrayList<Food> foods = new ArrayList<>();

    public GamePanel() {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        // Input
        this.addMouseMotionListener(mouseH);
        this.addKeyListener(keyH);

        this.setFocusable(true);

        // Crear jugador inicial
        playerCells.add(
                EntityFactory.createPlayer(mouseH)
        );

        //  Crear comida
        for (int i = 0; i < 100; i++) {
            foods.add(
                    EntityFactory.createFood(WIDTH, HEIGHT)
            );
        }

        //  Crear bots
        for (int i = 0; i < 5; i++) {

            bots.add(
                    EntityFactory.createBot(
                            foods,
                            bots,
                            playerCells.get(0)
                    )
            );
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

    //  Lógica del juego
    public void update() {

        //  Actualizar células del jugador
        for (Cell cell : playerCells) {
            cell.update();
        }

        //  Actualizar bots
        for (Cell bot : bots) {
            bot.update();
        }

        //  duplicar célula al presionar espacio (máximo 2 células)
        if (keyH.spacePressed && playerCells.size() < 2) {

            Cell original = playerCells.get(0);

            // tamaño nuevo
            double newSize = original.getSize() / 2;

            // reducir tamaño original
            original.setSize(newSize);

            // crear nueva célula
            Cell newCell = new Cell(
                    original.getX() + 80,
                    original.getY(),
                    newSize,
                    Color.GREEN
            );

            // mismo control del mouse
            newCell.setMovementStrategy(
                    new PlayerMovement(mouseH)
            );

            // agregar nueva célula
            playerCells.add(newCell);

            keyH.spacePressed = false;
        }

        //  Jugador come comida
        for (Cell player : playerCells) {

            for (int i = 0; i < foods.size(); i++) {

                Food food = foods.get(i);

                double dx = (player.getX() + player.getSize() / 2)
                        - (food.getX() + food.getSize() / 2);

                double dy = (player.getY() + player.getSize() / 2)
                        - (food.getY() + food.getSize() / 2);

                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < player.getSize() / 2) {

                    foods.remove(i);

                    player.grow(1);

                    foods.add(
                            EntityFactory.createFood(WIDTH, HEIGHT)
                    );

                    i--;
                }
            }
        }

        //  Bots comen comida
        for (Cell bot : bots) {

            for (int i = 0; i < foods.size(); i++) {

                Food food = foods.get(i);

                double dx = (bot.getX() + bot.getSize() / 2)
                        - (food.getX() + food.getSize() / 2);

                double dy = (bot.getY() + bot.getSize() / 2)
                        - (food.getY() + food.getSize() / 2);

                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < bot.getSize() / 2) {

                    foods.remove(i);

                    bot.grow(1);

                    foods.add(
                            EntityFactory.createFood(WIDTH, HEIGHT)
                    );

                    i--;
                }
            }
        }

        //  Jugador vs Bots
        for (Cell player : playerCells) {

            for (int i = 0; i < bots.size(); i++) {

                Cell bot = bots.get(i);

                if (player.getSize() > bot.getSize() * 1.1) {

                    if (isColliding(player, bot)) {

                        player.grow(bot.getSize() * 0.2);

                        bots.remove(i);

                        bots.add(
                                EntityFactory.createBot(
                                        foods,
                                        bots,
                                        playerCells.get(0)
                                )
                        );

                        i--;
                    }
                }
            }
        }

        //  Bots pueden comer jugador
        for (Cell bot : bots) {

            for (int i = 0; i < playerCells.size(); i++) {

                Cell player = playerCells.get(i);

                if (bot.getSize() > player.getSize() * 1.1) {

                    if (isColliding(bot, player)) {

                        playerCells.remove(i);

                        // si no quedan células → respawn
                        if (playerCells.isEmpty()) {

                            playerCells.add(
                                    EntityFactory.createPlayer(mouseH)
                            );
                        }

                        break;
                    }
                }
            }
        }

        //  Bots vs Bots
        for (int i = 0; i < bots.size(); i++) {

            Cell botA = bots.get(i);

            for (int j = 0; j < bots.size(); j++) {

                if (i == j)
                    continue;

                Cell botB = bots.get(j);

                if (botA.getSize() > botB.getSize() * 1.1) {

                    if (isColliding(botA, botB)) {

                        botA.grow(botB.getSize() * 0.2);

                        bots.remove(j);

                        bots.add(
                                EntityFactory.createBot(
                                        foods,
                                        bots,
                                        playerCells.get(0)
                                )
                        );

                        break;
                    }
                }
            }
        }
    }

    /**
     * Detecta colisión entre dos células
     */
    private boolean isColliding(Cell a, Cell b) {

        double dx = (a.getX() + a.getSize() / 2)
                - (b.getX() + b.getSize() / 2);

        double dy = (a.getY() + a.getSize() / 2)
                - (b.getY() + b.getSize() / 2);

        double distance = Math.sqrt(dx * dx + dy * dy);

        double collisionDistance =
                (a.getSize() + b.getSize()) / 2;

        return distance < collisionDistance;
    }

    //  Render
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //  Dibujar comida
        for (Food food : foods) {
            food.draw(g2);
        }

        //  Dibujar bots
        for (Cell bot : bots) {
            bot.draw(g2);
        }

        //  Dibujar jugador
        for (Cell player : playerCells) {
            player.draw(g2);
        }

        g2.dispose();
    }
}