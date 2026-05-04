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

