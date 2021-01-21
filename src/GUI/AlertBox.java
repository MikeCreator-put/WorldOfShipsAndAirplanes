package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Represents alert box popping up after an error.
 */
public class AlertBox {
    /**
     * Creates new popup window with an error message provided in parameter.
     *
     * @param message The window's error message.
     */
    public static void display(String message) {
        Stage thisStage = new Stage();

        thisStage.initModality(Modality.APPLICATION_MODAL);
        thisStage.setTitle("error");
        thisStage.setMinWidth(250);
        thisStage.setMinWidth(150);


        Label label = new Label();
        label.setText(message);

        Button button = new Button();
        button.setText("Close");
        button.setOnAction(event -> thisStage.close());

        VBox vBox = new VBox(10);
        vBox.setStyle("-fx-padding: 16");

        vBox.getChildren().addAll(label, button);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        thisStage.setScene(scene);
        thisStage.showAndWait();
    }
}
