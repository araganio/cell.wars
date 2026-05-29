import java.util.ArrayList;

public class SmartBotMovement implements MovementStrategy {

    private ArrayList<Food> foods;
    private ArrayList<Cell> bots;
    private Cell player;

    public SmartBotMovement(ArrayList<Food> foods,
                            ArrayList<Cell> bots,
                            Cell player) {

        this.foods = foods;
        this.bots = bots;
        this.player = player;
    }

    @Override
    public void move(Cell cell) {

        // Distancia al jugador (desde el centro)
        double playerDX = (player.getX() + player.getSize() / 2)
                - (cell.getX() + cell.getSize() / 2);

        double playerDY = (player.getY() + player.getSize() / 2)
                - (cell.getY() + cell.getSize() / 2);

        double playerDistance = Math.sqrt(
                playerDX * playerDX + playerDY * playerDY
        );

        //  HUIR si el jugador es mucho más grande y está cerca
        if (player.getSize() > cell.getSize() * 1.3
                && playerDistance < 200) {

            moveTowards(cell, -playerDX, -playerDY);

            return;
        }

        //  ATACAR si el bot es mucho más grande
        if (cell.getSize() > player.getSize() * 1.3
                && playerDistance < 250) {

            moveTowards(cell, playerDX, playerDY);

            return;
        }

        //  BUSCAR COMIDA
        Food closestFood = null;

        double closestDistance = Double.MAX_VALUE;

        for (Food food : foods) {

            //  Distancia desde centros
            double dx = (food.getX() + food.getSize() / 2)
                    - (cell.getX() + cell.getSize() / 2);

            double dy = (food.getY() + food.getSize() / 2)
                    - (cell.getY() + cell.getSize() / 2);

            double distance = Math.sqrt(dx * dx + dy * dy);

            //  Encontrar comida más cercana
            if (distance < closestDistance) {

                closestDistance = distance;
                closestFood = food;
            }
        }

        //  Ir hacia comida
        if (closestFood != null) {

            double dx = (closestFood.getX() + closestFood.getSize() / 2)
                    - (cell.getX() + cell.getSize() / 2);

            double dy = (closestFood.getY() + closestFood.getSize() / 2)
                    - (cell.getY() + cell.getSize() / 2);

            moveTowards(cell, dx, dy);
        }
    }

    //  Movimiento reutilizable
    private void moveTowards(Cell cell, double dx, double dy) {

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 1) {

            cell.setPosition(
                    cell.getX() + (dx / distance) * cell.getSpeed(),
                    cell.getY() + (dy / distance) * cell.getSpeed()
            );
        }
    }
}