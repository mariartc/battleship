import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Point extends Rectangle {
    int x, y;
    Ship ship = null;
    boolean isShot;

    public Point(int i, int j){
        super(31, 31);
        x = i;
        y = j;
        isShot = false;
        setFill(Color.rgb(151, 222, 236));
        setStroke(Color.rgb(208, 240, 247));
    }

    //returns true if a ship is damaged, else false
    public boolean shoot(){
        isShot = true;
        if(ship != null) {
            ship.hit();
            setFill(Color.rgb(255, 128, 128));
            return true;
        }
        else
            setFill(Color.rgb(75, 112, 120));
        return false;
    }
}
