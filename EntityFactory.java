import java.awt.Color;

public class EntityFactory {

    public static Cell createPlayer() {
        return new Cell(350, 250, 50, Color.GREEN);
    }
}

