import java.util.*;

public class Player {
    int TotalShots;
    int SuccessfulShots;
    int TotalPoints;
    ArrayDeque<Shot> queue;
    int queueLength;

    public Player(){
        TotalShots = 0;
        SuccessfulShots = 0;
        TotalPoints = 0;
        queue = new ArrayDeque<>();
        queueLength = 0;
    }

    public void addShot(Shot shot){
        queue.addFirst(shot);
        if(queueLength == 5) queue.removeLast();
        else queueLength++;
    }
}
