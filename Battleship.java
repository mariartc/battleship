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
import javafx.scene.paint.Color;

import exceptions.*;

public class Battleship extends Application{
    Stage window;
    BorderPane layout;
    public Board enemyBoard, playerBoard;
    Player player = new Player();
    Enemy enemy = new Enemy();
    Text actiontarget = new Text();
    Text initialMessage = new Text();
    GridPane form;
    VBox topBar;
    Text ShipsPlayer, ShipsPlayerValue, PointsPlayer, PointsPlayerValue, ShotsPlayer, ShotsPlayerValue, ShipsEnemy, ShipsEnemyValue, PointsEnemy, PointsEnemyValue, ShotsEnemy, ShotsEnemyValue;
    boolean loaded = false;
    String scenario;
    boolean firstPlayer; //true for player, false for enemy;

    public void load(){
        try {
            String currentDirectory = System.getProperty("user.dir");
            BufferedReader enemy = new BufferedReader(new FileReader(currentDirectory + "\\medialab\\enemy_" + scenario + ".txt"));
            enemyBoard = new Board(true);
            String lineEnemy = enemy.readLine();
            while(lineEnemy!=null){
                String[] a = lineEnemy.split(",");
                int e = enemyBoard.placeShip(new Ship(Integer.parseInt(a[0]), Integer.parseInt(a[3])), Integer.parseInt(a[1]), Integer.parseInt(a[2]));
                if(e == 1) throw new OversizeException();
                if(e == 2) throw new OverlapTilesException();
                if(e == 3) throw new AdjacentTilesException();
                if(e == 4) throw new InvalidCountException();
                lineEnemy = enemy.readLine();
            }

            BufferedReader player = new BufferedReader(new FileReader(currentDirectory + "\\medialab\\player_" + scenario + ".txt"));
            playerBoard = new Board(false);
            String linePlayer = player.readLine();
            while(linePlayer!=null){
                String[] a = linePlayer.split(",");
                int e = playerBoard.placeShip(new Ship(Integer.parseInt(a[0]), Integer.parseInt(a[3])), Integer.parseInt(a[1]), Integer.parseInt(a[2]));
                if(e == 1) throw new OversizeException();
                if(e == 2) throw new OverlapTilesException();
                if(e == 3) throw new AdjacentTilesException();
                if(e == 4) throw new InvalidCountException();
                linePlayer = player.readLine();
            }

            loaded = true;
            initialMessage.setText("All set!\nPlease go to Application->Start to start the game.");
            initialMessage.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.rgb(0, 150, 201));
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
        } catch (OversizeException e1) {
            initialMessage.setText("Some of the ships caused Oversize Exception!");
            initialMessage.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
            loaded = false;
        } catch (OverlapTilesException e2) {
            initialMessage.setText("Some of the ships caused Overlap Tiles Exception!");
            initialMessage.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
            loaded = false;
        }catch (AdjacentTilesException e3) {
            initialMessage.setText("Some of the ships caused Adjacent Tiles Exception!");
            initialMessage.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
            loaded = false;
        }catch (InvalidCountException e4) {
            initialMessage.setText("Some of the ships caused Invalid Count Exception!");
            initialMessage.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
            loaded = false;
        }catch (FileNotFoundException e5) {
            initialMessage.setText("The file you chose cannot be found!");
            initialMessage.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
            loaded = false;
        }catch (Exception e) {
            initialMessage.setText("Some exception occurred!\n"+e);
            initialMessage.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
            loaded = false;
        }
    }

    public void start() {
        if(!loaded){
            initialMessage.setText("You need to place your ships first!\nPlease go to Application->Load and choose scenario ID.");
            initialMessage.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
        }
        else {
            restart();
            if(choosePlayer()){
                PlayerTurnBox.display("You start first!");
                firstPlayer = true;
            }
            else{
                PlayerTurnBox.display("Your enemy starts first!");
                firstPlayer = false;
            }
            //place top bar
            ShipsPlayer = new Text("    Your ships:  ");
            ShipsPlayerValue = new Text(Integer.toString(playerBoard.ships));
            ShipsPlayer.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
            ShipsPlayerValue.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
            PointsPlayer = new Text("    Your points:  ");
            PointsPlayerValue = new Text("0");
            PointsPlayer.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
            PointsPlayerValue.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
            ShotsPlayer = new Text("    Your successful shots:  ");
            ShotsPlayerValue = new Text("0%");
            ShotsPlayer.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
            ShotsPlayerValue.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
            HBox playerDetails = new HBox(ShipsPlayer, ShipsPlayerValue, PointsPlayer, PointsPlayerValue, ShotsPlayer, ShotsPlayerValue);
            playerDetails.setPadding(new Insets(10, 25, 5, 25));
            ShipsEnemy = new Text("    Enemy's ships:  ");
            ShipsEnemyValue = new Text(Integer.toString(enemyBoard.ships));
            ShipsEnemy.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
            ShipsEnemyValue.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
            PointsEnemy = new Text("    Enemy's points:  ");
            PointsEnemyValue = new Text("0");
            PointsEnemy.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
            PointsEnemyValue.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
            ShotsEnemy = new Text("    Enemy's successful shots:  ");
            ShotsEnemyValue = new Text(0 + "%");
            ShotsEnemy.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
            ShotsEnemyValue.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
            HBox enemyDetails = new HBox(ShipsEnemy, ShipsEnemyValue, PointsEnemy, PointsEnemyValue, ShotsEnemy, ShotsEnemyValue);
            enemyDetails.setPadding(new Insets(10, 25, 0, 25));

            //place boards
            HBox boards = new HBox(50, playerBoard, enemyBoard);
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
            title.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
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

            form.add(actiontarget, 1, 6, 2, 1);

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
        player.TotalShots = 0;
        player.SuccessfulShots = 0;
        enemy.TotalShots = 0;
        enemy.SuccessfulShots = 0;
        player.TotalPoints = 0;
        enemy.TotalPoints = 0;
    }

    public void enemyMove(){
        int playerShips = playerBoard.ships;
        enemy.enemyMove(playerBoard);
        if(enemy.TotalShots == 40) displayWinner(3);
        PointsEnemyValue.setText(Integer.toString(enemy.TotalPoints));
        if(playerBoard.ships != playerShips){
            ShipsPlayerValue.setText(Integer.toString(playerBoard.ships));
            if(playerBoard.ships == 0) displayWinner(2);
        }
        ShotsEnemyValue.setText(enemy.SuccessfulShots*100/enemy.TotalShots+"%");
    }

    public boolean isValidPoint(int x, int y){
        return x>=0 && x<10 && y>=0 && y<10;
    }

    public void playerMove(int x, int y){
        int enemyShips = enemyBoard.ships;
        player.playerMove(x, y, enemyBoard);
        if(player.TotalShots == 40) displayWinner(3);
        PointsPlayerValue.setText(Integer.toString(player.TotalPoints));
        ShotsPlayerValue.setText(player.SuccessfulShots*100/player.TotalShots+"%");
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
    public void displayWinner(int winner){
        if(winner == 1 || (winner == 3 && player.TotalPoints > enemy.TotalPoints)){
            initialMessage.setText("Congratulations! You won!");
            initialMessage.setFont(Font.font("Verdana", FontWeight.NORMAL, 25));
            initialMessage.setFill(Color.rgb(0, 150, 201));
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
        }
        else if(winner == 2 || (winner == 3 && player.TotalPoints < enemy.TotalPoints)){
            initialMessage.setText("Oh no! You lost!");
            initialMessage.setFont(Font.font("Verdana", FontWeight.NORMAL, 25));
            initialMessage.setFill(Color.FIREBRICK);
            layout.setCenter(initialMessage);
            layout.setBottom(actiontarget);
        }
        else {
            initialMessage.setText("Draw!");
            initialMessage.setFont(Font.font("Verdana", FontWeight.NORMAL, 25));
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
        MenuItem start = new MenuItem("Start       ");
        start.setOnAction(e -> start());
        MenuItem load = new MenuItem("Load       ");
        load.setOnAction(e -> {
            scenario = ChooseBox.display();
            load();
        });
        MenuItem exit = new MenuItem("Exit       ");
        exit.setOnAction(e -> window.close());

        Menu application = new Menu("Application");
        application.getItems().add(start);
        application.getItems().add(load);
        application.getItems().add(new SeparatorMenuItem());
        application.getItems().add(exit);

        MenuItem enemyShips = new MenuItem("Enemy ships");
        enemyShips.setOnAction(e -> {
            if(loaded) EnemyShipsBox.display(enemyBoard);
            else EnemyShipsBox.display("You need to place you ships first!");
        });
        MenuItem playerShots = new MenuItem("Player Shots");
        playerShots.setOnAction(e -> PlayerMoveBox.display(player));
        MenuItem enemyShots = new MenuItem("Enemy Shots");
        enemyShots.setOnAction(e -> PlayerMoveBox.display(enemy));
        Menu details = new Menu("Details");
        details.getItems().add(enemyShips);
        details.getItems().add(playerShots);
        details.getItems().add(enemyShots);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(application, details);
        layout.setTop(menuBar);

        //initial welcome message
        initialMessage.setText("Welcome! First, you need to load your ships.\nPlease go to Application->Load and choose scenario ID.");
        initialMessage.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        initialMessage.setFill(Color.rgb(0, 150, 201));
        layout.setCenter(initialMessage);

        //scene
        Scene scene = new Scene(layout, 900, 650);
        window.setScene(scene);
        window.show();
    }
}
