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

public class ChooseBox {
    static String scenario;

    public static String display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Choose Scenario ID");

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER);
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(25, 25, 25, 25));
        Text title = new Text("Please Type scenario ID:");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        form.add(title, 0, 0, 2, 1);
        TextField scenarioID = new TextField();
        form.add(scenarioID, 1, 1);
        Button btn = new Button("Submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        form.add(hbBtn, 1, 4);

        btn.setOnAction(e -> {
            scenario = scenarioID.getText();
            window.close();
        });

        VBox layout = new VBox(form);
        Scene scene = new Scene(layout, 400, 150);
        window.setScene(scene);
        window.showAndWait();

        return scenario;
    }
}
