import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Tamaño de la ventana
    final int WIDTH = 800;
    final int HEIGHT = 600;
    MouseHandler mouseH = new MouseHandler();

    Thread gameThread;

    // 🎯 Jugador (ya como objeto)
    Cell player;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addMouseMotionListener(mouseH);


        // Crear jugador usando Factory
        player = EntityFactory.createPlayer();
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
    player.setTarget(mouseH.mouseX, mouseH.mouseY);
    player.update();
}


    // 🎨 Render
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Dibujar jugador
        player.draw(g2);

        g2.dispose();
    }
}
