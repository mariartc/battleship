import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class Board extends Parent {
    VBox rows = new VBox();
    boolean enemy = false;
    int ships = 5;
    boolean[] types = {false, false, false, false, false};


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
                //p.setOnMouseClicked(handler);
                row.setPadding(new Insets(0, 0, 0, 5));
                row.getChildren().add(p);
            }
            numberedRow.getChildren().add(row);
            rows.getChildren().add(numberedRow);
        }
        getChildren().add(rows);
    }

    //0 = all good, 1 = InvalidCountException
    public int placeShip (Ship ship, int x, int y){
        if (!isValidPoint(x, y)) return 1;
        if (ship.vertical) {
            if(types[ship.type - 1]) return 4;
            else types[ship.type - 1] = true;
            for (int i = x; i < x + ship.length; i++) {
                if(checkAdjacentTilesException(i, y, ship)) return 3;
                Point point = (Point) ((HBox) ((HBox) rows.getChildren().get(i + 1)).getChildren().get(1)).getChildren().get(y);
                if(point.ship != null) return 2;
                point.ship = ship;
                if (!this.enemy)
                    point.setFill(Color.rgb(155, 220, 88));
            }
        } else {
            if(types[ship.type - 1]) return 4;
            else types[ship.type - 1] = true;
            for (int i = y; i < y + +ship.length; i++) {
                if(checkAdjacentTilesException(x, i, ship)) return 3;
                Point point = (Point) ((HBox) ((HBox) rows.getChildren().get(x + 1)).getChildren().get(1)).getChildren().get(i);
                if(point.ship != null) return 2;
                point.ship = ship;
                if (!this.enemy)
                    point.setFill(Color.rgb(155, 220, 88));
            }
        }
        return 0;
    }

    //returns points
    public int placeShot (int x, int y){
        Point point = (Point) ((HBox) ((HBox) rows.getChildren().get(x+1)).getChildren().get(1)).getChildren().get(y);
        int returnValue = 0;
        if(point.shoot()) returnValue = point.ship.valueShot;
        if(returnValue > 0 && !point.ship.isAlive()){
            ships--;
            returnValue += point.ship.totalValue;
        }
        return returnValue;
    }

    boolean isValidPoint(int x, int y){
        return (x >= 0 && x < 10 && y >= 0 && y < 10);
    }

    //returns true if AdjacentTilesException will occur
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
