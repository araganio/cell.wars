import java.util.Random;

public class RandomMovement implements MovementStrategy {

    private Random random = new Random();

    @Override
    public void move(Cell cell) {

        double dx = random.nextDouble() * 2 - 1;
        double dy = random.nextDouble() * 2 - 1;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            cell.setPosition(
                cell.getX() + (dx / distance) * cell.getSpeed(),
                cell.getY() + (dy / distance) * cell.getSpeed()
            );
        }
    }
}
