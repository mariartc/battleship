public class Ship {
    int type; //1 => Carrier, 2 => Battleship, 3 => Cruiser , 4 => Submarine, 5 => Destroyer
    boolean vertical;
    private int health;

    public Ship(int type, boolean vertical){
        this.type = type;
        this.vertical = vertical;
        this.health = 6 - type;
    }

    public void hit(){
        this.health--;
    }

    public boolean isAlive(){
        if(this.health > 0) return true;
        return false;
    }
}
