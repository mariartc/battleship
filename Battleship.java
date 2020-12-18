import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import javafx.scene.paint.Color;

public class Battleship extends Application{
    Stage window;
    BorderPane layout;
    Board enemyBoard, playerBoard;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        layout = new BorderPane();
        //stage
        window = primaryStage;
        window.setTitle("MediaLab Battleship");

        //menu
        MenuItem start = new MenuItem("Start");
        MenuItem load = new MenuItem("Load");
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

        //boards
        enemyBoard = new Board(true);
        playerBoard = new Board(false);
        HBox boards = new HBox(50, enemyBoard, playerBoard);
        boards.setAlignment(Pos.CENTER);
        boards.setPadding(new Insets(25, 25, 25, 25));
        layout.setCenter(boards);

        //form
        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER);
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(0, 25, 25, 25));
        Text title = new Text("Place a new shot!");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        form.add(title, 0, 0, 2, 1);

        Label row = new Label("Row number");
        form.add(row, 0, 1);

        TextField userTextField = new TextField();
        form.add(userTextField, 1, 1);

        Label column = new Label("Column number");
        form.add(column, 0, 2);

        Button btn = new Button("Shoot!");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        form.add(hbBtn, 1, 4);

        PasswordField pwBox = new PasswordField();
        form.add(pwBox, 1, 2);
        layout.setBottom(form);

        //for the event handling later..
        final Text actiontarget = new Text();
        form.add(actiontarget, 1, 6);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("SHOTS!!!!!!");
            }
        });


        //scene
        Scene scene = new Scene(layout, 900, 600);
        window.setScene(scene);
        window.show();
    }
}
