public class Ship {
    int type; //1 => Carrier, 2 => Battleship, 3 => Cruiser , 4 => Submarine, 5 => Destroyer
    int length;
    boolean vertical;
    private int health;
    int valueShot;
    int totalValue;

    public Ship(int type, int vertical){
        this.type = type;
        this.vertical = vertical == 2;
        if(type == 1){
            this.length = 5;
            this.valueShot = 350;
            this.totalValue = 1000;
        }
        else if(type == 2){
            this.length = 4;
            this.valueShot = 250;
            this.totalValue = 500;
        }
        else if(type == 3){
            this.length = 3;
            this.valueShot = 100;
            this.totalValue = 250;
        }
        else if(type == 4){
            this.length = 3;
            this.valueShot = 100;
            this.totalValue = 0;
        }
        else if(type == 5){
            this.length = 2;
            this.valueShot = 50;
            this.totalValue = 0;
        }
        this.health = this.length;
    }

    public void hit(){
        this.health--;
    }

    public boolean isAlive(){
        return this.health > 0;
    }
}
