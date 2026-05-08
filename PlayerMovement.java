public class PlayerMovement implements MovementStrategy {

    private MouseHandler mouseH;

    public PlayerMovement(MouseHandler mouseH) {
        this.mouseH = mouseH;
    }

    @Override
    public void move(Cell cell) {

        double dx = mouseH.mouseX - cell.getX();
        double dy = mouseH.mouseY - cell.getY();

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 1) {
            cell.setPosition(
                cell.getX() + (dx / distance) * cell.getSpeed(),
                cell.getY() + (dy / distance) * cell.getSpeed()
            );
        }
    }
}