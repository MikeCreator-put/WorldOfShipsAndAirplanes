package GUI;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import others.Point;
import vehicles.Ship;

public class Main extends Application {
    ControlPanelController controlPanelController;


    @Override
    public void start(Stage primaryStage) {

        controlPanelController = new ControlPanelController();
        controlPanelController.showStage();
        Stage mystage1 = controlPanelController.getStage();

        mystage1.heightProperty().addListener(((observable, oldValue, newValue) -> {
            controlPanelController.resizeLine((double) newValue - (double) oldValue);
        }));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
