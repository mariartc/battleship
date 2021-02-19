import javafx.scene.layout.HBox;

import java.util.Random;

public class Enemy extends Player{
    int xLastShot = -1;
    int yLastShot = -1;
    int next = -1; //1 = up, 2 = down, 3 = left, 4 = right

    public void enemyMove(Board playerBoard){
        if(xLastShot != -1){
            if(enemyNeighbourMove(xLastShot-1, yLastShot, 1, 1, 2, playerBoard)) return;
            if(enemyNeighbourMove(xLastShot+1, yLastShot, 2, 2, -1, playerBoard)) return;
            if(enemyNeighbourMove(xLastShot, yLastShot-1, 3, 3, 4, playerBoard)) return;
            if(enemyNeighbourMove(xLastShot, yLastShot+1, 4, 4, -1, playerBoard)) return;
        }
        Random rand = new Random();
        while(true) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            Point point = (Point) ((HBox) ((HBox) playerBoard.rows.getChildren().get(x + 1)).getChildren().get(1)).getChildren().get(y);
            if (!point.isShot) {
                TotalShots++;
                int points = playerBoard.placeShot(x, y, this);
                if(points > 0){
                    xLastShot = x;
                    yLastShot = y;
                    SuccessfulShots++;
                    TotalPoints += points;
                }
                break;
            }
        }
    }

    //if the last enemy's shot was successful, they'll try shooting neighbour point
    public boolean enemyNeighbourMove(int x, int y, int currentNext, int nextIfShotSuccessful, int nextIfShotNotSuccessful, Board playerBoard){
        if(isValidPoint(x, y) && (next == -1 || next == currentNext)){
            Point point = (Point) ((HBox) ((HBox) playerBoard.rows.getChildren().get(x+1)).getChildren().get(1)).getChildren().get(y);
            if (!point.isShot){
                TotalShots++;
                int playerShips = playerBoard.ships;
                int points = playerBoard.placeShot(x, y, this);
                if(points > 0){
                    xLastShot = x;
                    yLastShot = y;
                    SuccessfulShots++;
                    TotalPoints += points;
                    next = nextIfShotSuccessful;
                }
                else next = nextIfShotNotSuccessful;
                if(playerBoard.ships != playerShips){
                    next = -1;
                    xLastShot = -1;
                    yLastShot = -1;
                }
                return true;
            }
        }
        return false;
    }

    public boolean isValidPoint(int x, int y){
        return x>=0 && x<10 && y>=0 && y<10;
    }
}

