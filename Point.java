import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Point extends Rectangle {
    int x, y;
    Ship ship = null;
    boolean isShot;

    public Point(int i, int j){
        super(30, 30);
        x = i;
        y = j;
        isShot = false;
        setFill(Color.rgb(225, 230, 253));
        setStroke(Color.rgb(85, 103, 149));
    }

    public void shoot(){
        isShot = true;
        if(ship != null) {
            ship.hit();
            setFill(Color.rgb(233, 1, 53));
        }
        else
            setFill(Color.rgb(85, 103, 149));
    }
}
