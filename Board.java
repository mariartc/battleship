import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class Board extends Parent {
    VBox rows = new VBox();
    boolean enemy;
    int ships = 5;
    String[] conditions = {null, null, null, null, null};

    /**
     * Class constructor.
     * Creates a new board with ten rows consisting of ten cells each,
     * with suitable padding.
     * @param enemy a boolean that determines whether
     *              this player is the user or the enemy
     */
    public Board(boolean enemy) {
        this.enemy = enemy;
        Text first = new Text("        0        1        2        3        4        5        6        7        8        9");
        rows.getChildren().add(first);
        for (int y = 0; y < 10; y++) {
            Text rowNumber = new Text(Integer.toString(y));
            HBox row = new HBox();
            HBox numberedRow = new HBox();
            numberedRow.getChildren().add(rowNumber);
            for (int x = 0; x < 10; x++) {
                Point p = new Point(x, y);
                row.setPadding(new Insets(0, 0, 0, 5));
                row.getChildren().add(p);
            }
            numberedRow.getChildren().add(row);
            rows.getChildren().add(numberedRow);
        }
        getChildren().add(rows);
    }

    /**
     * Places given ship to board's cells with starting cell the one
     * represented by given coordinates depending on ship's orientation.
     * Also checks for possible exceptions, if this type of ship is already
     * placed using the conditions array, if a cell has already a ship placed
     * on it, if a point is invalid, or if a point abuts on cell with ship
     * using checkAdjacentTilesException.
     * @param ship  ship to be placed on board
     * @param x     int that corresponds to x-coordinate
     * @param y     int that corresponds to y-coordinate
     * @return      0 if ship is placed successfully
     *              1 if OversizeException is provoked
     *              2 if OverlapTilesException is provoked
     *              3 if AdjacentTilesException is provoked
     *              4 if InvalidCountException is provoked
     */
    public int placeShip (Ship ship, int x, int y){
        if (!isValidPoint(x, y)) return 1;
        if(conditions[ship.type - 1] != null) return 4;
        else conditions[ship.type - 1] = "intact";
        if (ship.vertical) {
            for (int i = x; i < x + ship.length; i++) {
                if (!isValidPoint(i, y)) return 1;
                Point point = (Point) ((HBox) ((HBox) rows.getChildren().get(i + 1)).getChildren().get(1)).getChildren().get(y);
                if(point.ship != null) return 2;
                if(checkAdjacentTilesException(i, y, ship)) return 3;
                point.ship = ship;
                if (!this.enemy)
                    point.setFill(Color.rgb(155, 220, 88));
            }
        } else {
            for (int i = y; i < y + +ship.length; i++) {
                if (!isValidPoint(x, i)) return 1;
                Point point = (Point) ((HBox) ((HBox) rows.getChildren().get(x + 1)).getChildren().get(1)).getChildren().get(i);
                if(point.ship != null) return 2;
                if(checkAdjacentTilesException(x, i, ship)) return 3;
                point.ship = ship;
                if (!this.enemy)
                    point.setFill(Color.rgb(155, 220, 88));
            }
        }
        return 0;
    }

    /**
     * Places shot to cell represented by given coordinates by given player.
     * The shot gets added to players queue of shots by method addShot of Player class.
     * If the shot is successful, the type of ship hit is added to addShot, the total points
     * earned are calculated and the conditions array is refreshed.
     * @param x      int that corresponds to x-coordinate
     * @param y      int that corresponds to y-coordinate
     * @param player player that places the shot
     * @return       int of points earned by shot
     */
    public int placeShot (int x, int y, Player player){
        Point point = (Point) ((HBox) ((HBox) rows.getChildren().get(x+1)).getChildren().get(1)).getChildren().get(y);
        int returnValue = 0;
        if(point.shoot()){
            returnValue = point.ship.valueShot;
            conditions[point.ship.type - 1] = "damaged";
            player.addShot(new Shot(x, y, "yes", point.ship.type));
        }
        else player.addShot(new Shot(x, y, "no", 0));
        if(returnValue > 0 && !point.ship.isAlive()){
            ships--;
            conditions[point.ship.type - 1] = "sunken";
            returnValue += point.ship.totalValue;
        }
        return returnValue;
    }

    /**
     * Checks if given coordinates represent a valid cell of a board.
     * @param x int that corresponds to x-coordinate
     * @param y int that corresponds to y-coordinate
     * @return  true if the point is within the boards limits
     *              false otherwise
     */
    boolean isValidPoint(int x, int y){
        return (x >= 0 && x < 10 && y >= 0 && y < 10);
    }

    /**
     * Checks if an AdjacentTilesException occurs by placing a ship to cell
     * represented by given coordinates. An AdjacentTilesException occurs if
     * a neighbour cell has a ship of different type than given ship's placed on it.
     * @param x     int that corresponds to x-coordinate
     * @param y     int that corresponds to y-coordinate
     * @param ship  ship to be placed on board
     * @return      true if AdjacentTilesException occurs
     */
    boolean checkAdjacentTilesException(int x, int y, Ship ship){
        if(isValidPoint(x-1, y)){
            Point point = (Point) ((HBox) ((HBox) rows.getChildren().get(x)).getChildren().get(1)).getChildren().get(y);
            if(point.ship != null && !ship.equals(point.ship)) return true;
        }
        if(isValidPoint(x+1, y)){
            Point point = (Point) ((HBox) ((HBox) rows.getChildren().get(x+2)).getChildren().get(1)).getChildren().get(y);
            if(point.ship != null && !ship.equals(point.ship)) return true;
        }
        if(isValidPoint(x, y-1)){
            Point point = (Point) ((HBox) ((HBox) rows.getChildren().get(x+1)).getChildren().get(1)).getChildren().get(y-1);
            if(point.ship != null && !ship.equals(point.ship)) return true;
        }
        if(isValidPoint(x, y+1)){
            Point point = (Point) ((HBox) ((HBox) rows.getChildren().get(x+1)).getChildren().get(1)).getChildren().get(y+1);
            return point.ship != null && !ship.equals(point.ship);
        }
        return false;
    }
}
