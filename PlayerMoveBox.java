import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlayerMoveBox {
    public static void display(Player player){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Last five shots");

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER);
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(25, 25, 25, 25));
        Button btn = new Button("Ok");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);

        if(player.queue.isEmpty()){
            Text empty = new Text("Player hasn't placed shots yet!");
            empty.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
            form.add(empty, 0, 0, 2, 1);
            form.add(hbBtn, 1, 1);
        }
        else {
            Text x = new Text("x-coordinate   ");
            x.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15));
            form.add(x, 0, 0);
            Text y = new Text("y-coordinate   ");
            y.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15));
            form.add(y, 1, 0);
            Text wasSuccessful = new Text("Was successful   ");
            wasSuccessful.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15));
            form.add(wasSuccessful, 2, 0);
            Text ship = new Text("Type of ship hit");
            ship.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15));
            form.add(ship, 3, 0);
            for (int i = 0; i < player.queueLength; i++) {
                Shot shot = player.queue.removeFirst();

                Text xi = new Text("        " + shot.x);
                xi.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
                form.add(xi, 0, i + 1);
                Text yi = new Text("        " + shot.y);
                yi.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
                form.add(yi, 1, i + 1);
                Text wasSuccessfuli = new Text("        " + shot.wasSuccessful);
                wasSuccessfuli.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
                form.add(wasSuccessfuli, 2, i + 1);
                Text shipi = new Text();
                if(shot.wasSuccessful.equals("yes")) shipi.setText("    " + getType(shot.type));
                else shipi.setText("        -");
                shipi.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
                form.add(shipi, 3, i + 1);

                player.queue.addLast(shot);
            }
            form.add(hbBtn, 1, player.queueLength + 1);
        }


        btn.setOnAction(e -> window.close());
        form.setAlignment(Pos.CENTER);
        VBox layout = new VBox(form);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static String getType(int type){
        if (type == 1) return "Carrier";
        else if (type == 2) return "Battleship";
        else if (type == 3) return "Cruiser";
        else if (type == 4) return "Submarine";
        else return "Destroyer";
    }
}
