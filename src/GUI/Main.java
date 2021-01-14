package GUI;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;



public class Main extends Application {
    ControlPanelController controlPanelController;


    @Override
    public void start(Stage primaryStage) {
        controlPanelController = new ControlPanelController();

        controlPanelController.showStage();
        Stage mystage1 = controlPanelController.getStage();


        mystage1.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        mystage1.heightProperty().addListener(((observable, oldValue, newValue) -> {
            controlPanelController.resizeLine((double) newValue - (double) oldValue);
        }));

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                controlPanelController.getMapController().refresh();
                //System.out.println(controlPanelController.getEntities().getListOfAirports().get(7).getAvailable().availablePermits());
            }
        };
        animationTimer.start();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
