
/**
 * Clase principal del programa.
 * 
 * Se encarga de:
 * - Inicializar la ventana del juego
 * - Configurar el panel principal (GamePanel)
 * - Iniciar el hilo del juego (Game Loop)
 * 
 * Representa el punto de entrada de la aplicación.
 */

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        JFrame ventana = new JFrame("Cell Wars");
        GamePanel panel = new GamePanel();

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.add(panel);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        panel.startGameThread();
    }
}

