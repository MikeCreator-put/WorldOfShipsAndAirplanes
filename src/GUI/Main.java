package GUI;

import enums.Companies;
import enums.Weapons;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import airports.*;
import others.Point;
import vehicles.*;
import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Controller controller = new Controller();
        controller.showStage();
        Stage mystage1 = controller.getStage();
        // REQUIRES FIXING
        mystage1.widthProperty().addListener(((observable, oldValue, newValue) -> {
            controller.resizeLine((double)newValue - (double)oldValue, 0);
            System.out.println((double)newValue - (double)oldValue);
        }));

        mystage1.heightProperty().addListener(((observable, oldValue, newValue) -> {
            controller.resizeLine(0,(double)newValue - (double)oldValue);
            System.out.println((double)newValue - (double)oldValue);
        }));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
