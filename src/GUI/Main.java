package GUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        ControlPanelController controlPanelController = new ControlPanelController();
        controlPanelController.showStage();
        Stage mystage1 = controlPanelController.getStage();

        mystage1.heightProperty().addListener(((observable, oldValue, newValue) -> {
            controlPanelController.resizeLine((double)newValue - (double)oldValue);
        }));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
