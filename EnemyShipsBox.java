import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EnemyShipsBox {
    public static void display(Board board){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Enemy's ships conditions");

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER);
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(25, 25, 25, 25));

        Text carrier = new Text("Carrier:");
        Text carrierCondition = new Text(board.conditions[0]);
        carrier.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
        carrierCondition.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC,15));
        form.add(carrier, 0, 0, 2, 1);
        form.add(carrierCondition, 2, 0, 2, 1);

        Text battleship = new Text("Battleship:");
        Text battleshipCondition = new Text(board.conditions[1]);
        battleship.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
        battleshipCondition.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC,15));
        form.add(battleship, 0, 1, 2, 1);
        form.add(battleshipCondition, 2, 1, 2, 1);

        Text cruiser = new Text("Cruiser:");
        Text cruiserCondition = new Text(board.conditions[2]);
        cruiser.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
        cruiserCondition.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC,15));
        form.add(cruiser, 0, 2, 2, 1);
        form.add(cruiserCondition, 2, 2, 2, 1);

        Text submarine = new Text("Submarine:");
        Text submarineCondition = new Text(board.conditions[3]);
        submarine.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
        submarineCondition.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC,15));
        form.add(submarine, 0, 3, 2, 1);
        form.add(submarineCondition, 2, 3, 2, 1);

        Text destroyer = new Text("Destroyer:");
        Text destroyerCondition = new Text(board.conditions[4]);
        destroyer.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
        destroyerCondition.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC,15));
        form.add(destroyer, 0, 4, 2, 1);
        form.add(destroyerCondition, 2, 4, 2, 1);

        Button btn = new Button("Ok");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        form.add(hbBtn, 2, 5);

        btn.setOnAction(e -> window.close());

        VBox layout = new VBox(form);
        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void display(String s){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Enemy's ships conditions");

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER);
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(25, 25, 25, 25));

        Text carrier = new Text(s);
        carrier.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
        form.add(carrier, 0, 0, 2, 1);


        Button btn = new Button("Ok");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        form.add(hbBtn, 1, 1);

        btn.setOnAction(e -> window.close());

        VBox layout = new VBox(form);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
