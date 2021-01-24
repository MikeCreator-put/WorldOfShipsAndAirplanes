package GUI;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * <h1>
 * Word of ships and airplanes
 * </h1>
 * <p>
 * The WorldOfShipsAndAirplanes implements an application that tries to model the world of ships and airplanes.
 * </p>
 * <p>
 * Airports, paths for airplanes and paths for ships are predefined.
 * Each sea path is bidirectional, while some of the air paths are one-way (marked by red color).
 * </p>
 * <p>
 * The user is able to create new ships and airplanes by pressing new ship and new airplane buttons and then providing
 * all of the required by text fields and combo boxes.
 * Ships swim around choosing random paths at each crossing while planes use Dijkstra's algorithm to find shortest
 * path to their destination.
 * </p>
 * <p>
 * Both of the vehicles mentioned above can be divided into two categories: military and civilian, where military ships
 * can create military airplanes and airplanes can land only on airports they share category with.
 * </p>
 * <p>
 * The user is also able to view the map with ships and airplanes moving at 'real' time at any moment simply
 * by pressing view map button at the top right corner of the control panel.
 * </p>
 * <p>
 * The user can select any entity by either clicking on its label in the tree view visible on the left side of the
 * control panel or by clicking it on the map.
 * </p>
 * <p>
 * While any object is selected, its live information will be displayed at the control panel.
 * While any ship is selected, there will also be a button allowing the user to delete it.
 * While an airplane is selected, two extra buttons will be displayed. One will allow the user to call an
 * emergency, which will cause the airplane to abort its path and fly to the nearest airport that it shares
 * category with. Second one will allow the user to change the airplane's destination. In order to do so
 * the user has to select new location from the combo box and confirm the choice by pressing 'New destination' button.
 * </p>
 * <p>
 * It can be easily noticed that airplanes and ships stop at crossings and do nothing for a short period of time.
 * It is a deliberate implementation made in order to make it easier to observe the work of Semaphores which were required
 * in the specification of the project. The duration of anticipation for the plane to release permit on the semaphore and
 * leave the crossing can be changed by modifying the method setWaitCounter() in both Ship and Airplane classes separately.
 * </p>
 *
 * @author Mikołaj Skrzypczak
 * @version 1.0
 * @since 21.01.2021
 */
public class WorldOfShipsAndAirplanes extends Application {
    ControlPanelController controlPanelController;

    /**
     * Creates application thread.
     *
     * @param primaryStage Primary stage for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        controlPanelController = new ControlPanelController();

        controlPanelController.showStage();
        Stage myStage = controlPanelController.getStage();


        myStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        myStage.heightProperty().addListener(((observable, oldValue, newValue) -> {
            controlPanelController.resizeLine((double) newValue - (double) oldValue);
        }));

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (controlPanelController.getEntities().getListOfShips().size() != 0 || controlPanelController.getEntities().getListOfAirplanes().size() != 0) {
                    controlPanelController.getMapController().refresh();
                }
            }
        };
        animationTimer.start();

    }

    /**
     * Launches the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
