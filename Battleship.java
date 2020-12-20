import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.text.*;
import java.io.*;
import java.lang.*;
import java.util.*;
import javafx.scene.paint.Color;

public class Battleship extends Application{
    Stage window;
    BorderPane layout;
    Board enemyBoard, playerBoard;
    int xLastShot = -1;
    int yLastShot = -1;
    int next = -1; //1 = up, 2 = down, 3 = left, 4 = right
    Text actiontarget = new Text();
    Text initialMessage = new Text();
    GridPane form;
    VBox topBar;
    Text ShipsPlayer, ShipsPlayerValue, PointsPlayer, PointsPlayerValue, ShotsPlayer, ShotsPlayerValue, ShipsEnemy, ShipsEnemyValue, PointsEnemy, PointsEnemyValue, ShotsEnemy, ShotsEnemyValue;
    int playerTotalShots = 0;
    int playerSuccessfulShots = 0;
    int enemyTotalShots = 0;
    int enemySuccessfulShots = 0;
    int playerTotalPoints = 0;
    int enemyTotalPoints = 0;
    boolean loaded = false;
    String scenario;
    boolean firstPlayer; //true for player, false for enemy;

    public void load(){
        try {
            BufferedReader enemy = new BufferedReader(new FileReader("C:\\Users\\marak\\Documents\\Multimedia\\src\\enemy_" + scenario + ".txt"));
            enemyBoard = new Board(true);
            String lineEnemy = enemy.readLine();
            while(lineEnemy!=null){
                String[] a = lineEnemy.split("");
                int e = enemyBoard.placeShip(new Ship(Integer.parseInt(a[0]), Integer.parseInt(a[6])), Integer.parseInt(a[2]), Integer.parseInt(a[4]));
                if(e == 1) throw new OversizeException();
                if(e == 2) throw new OverlapTilesException();
                if(e == 3) throw new AdjacentTilesException();
                if(e == 4) throw new InvalidCountException();
                lineEnemy = enemy.readLine();
            }

            BufferedReader player = new BufferedReader(new FileReader("C:\\Users\\marak\\Documents\\Multimedia\\src\\player_" + scenario + ".txt"));
            playerBoard = new Board(false);
            String linePlayer = player.readLine();
            while(linePlayer!=null){
                String[] a = linePlayer.split("");
                int e = playerBoard.placeShip(new Ship(Integer.parseInt(a[0]), Integer.parseInt(a[6])), Integer.parseInt(a[2]), Integer.parseInt(a[4]));
                if(e == 1) throw new OversizeException();
                if(e == 2) throw new OverlapTilesException();
                if(e == 3) throw new AdjacentTilesException();
                if(e == 4) throw new InvalidCountException();
                linePlayer = player.readLine();
            }

            loaded = true;
            initialMessage.setText("All set!\nPlease go to Application->Start to start the game.");
            initialMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.rgb(0, 150, 201));
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
        } catch (OversizeException e1) {
            initialMessage.setText("Some of the ships caused Oversize Exception!");
            initialMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
            loaded = false;
        } catch (OverlapTilesException e2) {
            initialMessage.setText("Some of the ships caused Overlap Tiles Exception!");
            initialMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
            loaded = false;
        }catch (AdjacentTilesException e3) {
            initialMessage.setText("Some of the ships caused Adjacent Tiles Exception!");
            initialMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
            loaded = false;
        }catch (InvalidCountException e4) {
            initialMessage.setText("Some of the ships caused Invalid Count Exception!");
            initialMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
            loaded = false;
        }catch (FileNotFoundException e5) {
            initialMessage.setText("The file you chose cannot be found!");
            initialMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
            loaded = false;
        }catch (Exception e) {
            initialMessage.setText("Some exception occurred!\n"+e);
            initialMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
            loaded = false;
        }
    }

    public void start() {
        if(!loaded){
            initialMessage.setText("You need to place your ships first!\nPlease go to Application->Load and choose scenario ID.");
            initialMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
        }
        else {
            restart();
            if(choosePlayer()){
                PlayerMoveBox.display("It's your turn!");
                firstPlayer = true;
            }
            else{
                PlayerMoveBox.display("It's your enemy's turn!");
                firstPlayer = false;
            }
            //place top bar
            ShipsPlayer = new Text("    Your ships:  ");
            ShipsPlayerValue = new Text(Integer.toString(playerBoard.ships));
            ShipsPlayer.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            ShipsPlayerValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            PointsPlayer = new Text("    Your points:  ");
            PointsPlayerValue = new Text("0");
            PointsPlayer.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            PointsPlayerValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            ShotsPlayer = new Text("    Your successful shots:  ");
            ShotsPlayerValue = new Text("0%");
            ShotsPlayer.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            ShotsPlayerValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            HBox playerDetails = new HBox(ShipsPlayer, ShipsPlayerValue, PointsPlayer, PointsPlayerValue, ShotsPlayer, ShotsPlayerValue);
            playerDetails.setPadding(new Insets(10, 25, 5, 25));
            ShipsEnemy = new Text("    Enemy's ships:  ");
            ShipsEnemyValue = new Text(Integer.toString(enemyBoard.ships));
            ShipsEnemy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            ShipsEnemyValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            PointsEnemy = new Text("    Enemy's points:  ");
            PointsEnemyValue = new Text("0");
            PointsEnemy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            PointsEnemyValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            ShotsEnemy = new Text("    Enemy's successful shots:  ");
            ShotsEnemyValue = new Text(0 + "%");
            ShotsEnemy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            ShotsEnemyValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            HBox enemyDetails = new HBox(ShipsEnemy, ShipsEnemyValue, PointsEnemy, PointsEnemyValue, ShotsEnemy, ShotsEnemyValue);
            enemyDetails.setPadding(new Insets(10, 25, 0, 25));

            //place boards
            HBox boards = new HBox(50, enemyBoard, playerBoard);
            boards.setAlignment(Pos.CENTER);
            boards.setPadding(new Insets(25, 25, 5, 25));
            topBar = new VBox(playerDetails, enemyDetails, boards);
            layout.setCenter(topBar);

            //place form
            form = new GridPane();
            form.setAlignment(Pos.CENTER);
            form.setHgap(10);
            form.setVgap(10);
            form.setPadding(new Insets(0, 25, 5, 25));
            Text title = new Text("Place a new shot!");
            title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
            form.add(title, 0, 0, 2, 1);

            Label row = new Label("Row number");
            form.add(row, 0, 1);
            TextField rowTextField = new TextField();
            form.add(rowTextField, 1, 1);

            Label column = new Label("Column number");
            form.add(column, 0, 2);

            TextField columnTextField = new TextField();
            form.add(columnTextField, 1, 2);

            Button btn = new Button("Shoot!");
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
            form.add(hbBtn, 1, 4);

            form.add(actiontarget, 1, 6);

            layout.setBottom(form);
            EventHandler<ActionEvent> event = e -> {
                try {
                    actiontarget.setText("");
                    int x = Integer.parseInt(rowTextField.getText());
                    int y = Integer.parseInt(columnTextField.getText());
                    if (!isValidPoint(x, y)) throw new NonValidPoint();
                    Point point = (Point) ((HBox) ((HBox) enemyBoard.rows.getChildren().get(x + 1)).getChildren().get(1)).getChildren().get(y);
                    if (point.isShot) throw new PointAlreadyShot();
                    playerMove(x, y);
                } catch (NonValidPoint e1) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Points need to be inside board's range!");
                } catch (PointAlreadyShot e2) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Point already shot!");
                } catch (Exception e1) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Please insert valid point!");
                }
            };
            btn.setOnAction(event);
            if(!firstPlayer) enemyMove();
        }
    }

    public void restart(){
        if(loaded) load();
        playerTotalShots = 0;
        playerSuccessfulShots = 0;
        enemyTotalShots = 0;
        enemySuccessfulShots = 0;
        playerTotalPoints = 0;
        enemyTotalPoints = 0;
    }

    public void enemyMove(){
        if(xLastShot != -1){
            if(isValidPoint(xLastShot-1, yLastShot) && (next == -1 || next == 1)){
                Point point = (Point) ((HBox) ((HBox) playerBoard.rows.getChildren().get(xLastShot)).getChildren().get(1)).getChildren().get(yLastShot);
                if (!point.isShot){
                    enemyTotalShots++;
                    if(enemyTotalShots == 40) displayWinner(3);
                    int playerShips = playerBoard.ships;
                    int points = playerBoard.placeShot(xLastShot-1, yLastShot);
                    if(points > 0){
                        xLastShot--;
                        enemySuccessfulShots++;
                        enemyTotalPoints += points;
                        PointsEnemyValue.setText(Integer.toString(enemyTotalPoints));
                        next = 1;
                    }
                    else next = 2;
                    if(playerBoard.ships != playerShips){
                        ShipsPlayerValue.setText(Integer.toString(playerBoard.ships));
                        if(playerBoard.ships == 0) displayWinner(2);
                        next = -1;
                        xLastShot = -1;
                        yLastShot = -1;
                    }
                    ShotsEnemyValue.setText(enemySuccessfulShots*100/enemyTotalShots+"%");
                    return;
                }
            }
            if(isValidPoint(xLastShot+1, yLastShot) && (next == -1 || next == 2)){
                Point point = (Point) ((HBox) ((HBox) playerBoard.rows.getChildren().get(xLastShot+2)).getChildren().get(1)).getChildren().get(yLastShot);
                if (!point.isShot){
                    enemyTotalShots++;
                    if(enemyTotalShots == 40) displayWinner(3);
                    int playerShips = playerBoard.ships;
                    int points = playerBoard.placeShot(xLastShot+1, yLastShot);
                    if(points > 0){
                        xLastShot++;
                        enemySuccessfulShots++;
                        enemyTotalPoints += points;
                        PointsEnemyValue.setText(Integer.toString(enemyTotalPoints));
                        next = 2;
                    }
                    else next = -1;
                    if(playerBoard.ships != playerShips){
                        ShipsPlayerValue.setText(Integer.toString(playerBoard.ships));
                        if(playerBoard.ships == 0) displayWinner(2);
                        next = -1;
                        xLastShot = -1;
                        yLastShot = -1;
                    }
                    ShotsEnemyValue.setText(enemySuccessfulShots*100/enemyTotalShots+"%");
                    return;
                }
            }
            if(isValidPoint(xLastShot, yLastShot-1) && (next == -1 || next == 3)){
                Point point = (Point) ((HBox) ((HBox) playerBoard.rows.getChildren().get(xLastShot+1)).getChildren().get(1)).getChildren().get(yLastShot-1);
                if (!point.isShot){
                    enemyTotalShots++;
                    if(enemyTotalShots == 40) displayWinner(3);
                    int playerShips = playerBoard.ships;
                    int points = playerBoard.placeShot(xLastShot, yLastShot-1);
                    if(points > 0){
                        yLastShot--;
                        enemySuccessfulShots++;
                        enemyTotalPoints += points;
                        PointsEnemyValue.setText(Integer.toString(enemyTotalPoints));
                        next = 3;
                    }
                    else next = 4;
                    if(playerBoard.ships != playerShips){
                        ShipsPlayerValue.setText(Integer.toString(playerBoard.ships));
                        if(playerBoard.ships == 0) displayWinner(2);
                        next = -1;
                        xLastShot = -1;
                        yLastShot = -1;
                    }
                    ShotsEnemyValue.setText(enemySuccessfulShots*100/enemyTotalShots+"%");
                    return;
                }
            }
            if(isValidPoint(xLastShot, yLastShot+1) && (next == -1 || next == 4)){
                Point point = (Point) ((HBox) ((HBox) playerBoard.rows.getChildren().get(xLastShot+1)).getChildren().get(1)).getChildren().get(yLastShot+1);
                if (!point.isShot){
                    enemyTotalShots++;
                    if(enemyTotalShots == 40) displayWinner(3);
                    int playerShips = playerBoard.ships;
                    int points = playerBoard.placeShot(xLastShot, yLastShot+1);
                    if(points > 0){
                        yLastShot++;
                        enemySuccessfulShots++;
                        enemyTotalPoints += points;
                        PointsEnemyValue.setText(Integer.toString(enemyTotalPoints));
                        next = 4;
                    }
                    else next = -1;
                    if(playerBoard.ships != playerShips){
                        ShipsPlayerValue.setText(Integer.toString(playerBoard.ships));
                        if(playerBoard.ships == 0) displayWinner(2);
                        next = -1;
                        xLastShot = -1;
                        yLastShot = -1;
                    }
                    ShotsEnemyValue.setText(enemySuccessfulShots*100/enemyTotalShots+"%");
                    return;
                }
            }
        }
        Random rand = new Random();
        while(true) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            Point point = (Point) ((HBox) ((HBox) playerBoard.rows.getChildren().get(x + 1)).getChildren().get(1)).getChildren().get(y);
            if (!point.isShot) {
                enemyTotalShots++;
                if(enemyTotalShots == 40) displayWinner(3);
                int playerShips = playerBoard.ships;
                int points = playerBoard.placeShot(x, y);
                if(points > 0){
                    xLastShot = x;
                    yLastShot = y;
                    enemySuccessfulShots++;
                    enemyTotalPoints += points;
                    PointsEnemyValue.setText(Integer.toString(enemyTotalPoints));
                }
                if(playerBoard.ships != playerShips){
                    ShipsPlayerValue.setText(Integer.toString(playerBoard.ships));
                    if(playerBoard.ships == 0) displayWinner(2);
                }
                ShotsEnemyValue.setText(enemySuccessfulShots*100/enemyTotalShots+"%");
                break;
            }
        }
    }

    public boolean isValidPoint(int x, int y){
        return x>=0 && x<10 && y>=0 && y<10;
    }

    public void playerMove(int x, int y){
        playerTotalShots++;
        if(playerTotalShots == 40) displayWinner(3);
        int enemyShips = enemyBoard.ships;
        int points = enemyBoard.placeShot(x, y);
        if(points > 0){
            playerSuccessfulShots++;
            playerTotalPoints += points;
            PointsPlayerValue.setText(Integer.toString(playerTotalPoints));
        }
        ShotsPlayerValue.setText(playerSuccessfulShots*100/playerTotalShots+"%");
        if(enemyBoard.ships != enemyShips){
            ShipsEnemyValue.setText(Integer.toString(enemyBoard.ships));
            if(enemyBoard.ships == 0) displayWinner(1);
        }
        enemyMove();
    }

    //returns true if player starts first, enemy otherwise
    public boolean choosePlayer(){
        return Math.random() < 0.5;
    }

    //1 = player won, 2 = enemy won, 3 = draw
    public void displayWinner(int player){
        if(player == 1 || (player == 3 && playerTotalPoints > enemyTotalPoints)){
            initialMessage.setText("Congratulations! You won!");
            initialMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
            initialMessage.setFill(Color.rgb(0, 150, 201));
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
        }
        else if(player == 2 || (player == 3 && playerTotalPoints < enemyTotalPoints)){
            initialMessage.setText("Oh no! You lost!");
            initialMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
        }
        else {
            initialMessage.setText("Draw!");
            initialMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
            initialMessage.setFill(Color.rgb(0, 150, 201));
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
        }
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        layout = new BorderPane();
        //stage
        window = primaryStage;
        window.setTitle("MediaLab Battleship");

        //menu
        MenuItem start = new MenuItem("Start");
        start.setOnAction(e -> start());
        MenuItem load = new MenuItem("Load");
        load.setOnAction(e -> {
            scenario = ChooseBox.display();
            load();
        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> window.close());

        Menu menu = new Menu("Application");
        menu.getItems().add(start);
        menu.getItems().add(load);
        menu.getItems().add(new SeparatorMenuItem());
        menu.getItems().add(exit);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu);
        layout.setTop(menuBar);

        initialMessage.setText("Welcome! First, you need to load your ships.\nPlease go to Application->Load and choose scenario ID.");
        initialMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        initialMessage.setFill(Color.rgb(0, 150, 201));
        layout.setCenter(initialMessage);
        //scene
        Scene scene = new Scene(layout, 900, 650);
        window.setScene(scene);
        window.show();
    }
}
