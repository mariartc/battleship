import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class PlayerMoveBox {
    public static void display(String s){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Who's turn is it?");

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER);
        form.setHgap(5);
        form.setVgap(10);
        form.setPadding(new Insets(25, 25, 25, 25));
        Text title = new Text(s);
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        form.add(title, 0, 0, 2, 1);
        Button btn = new Button("Ok");
        HBox hbBtn = new HBox(5);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        form.add(hbBtn, 1, 2);

        btn.setOnAction(e -> window.close());

        VBox layout = new VBox(form);
        Scene scene = new Scene(layout, 250, 100);
        window.setScene(scene);
        window.showAndWait();
    }
}
