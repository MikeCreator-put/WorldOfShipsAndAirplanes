package GUI;

import airports.Airport;
import airports.CivilianAirport;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import vehicles.Airplane;

import java.util.ArrayList;

public class Controller {


    @FXML
    Button newPlaneButton;
    @FXML
    Button newShipButton;
    @FXML
    Button viewMapButton;
    @FXML
    Button deleteEntityButton;
    @FXML
    Button callEmergencyButton;
    @FXML
    Button changeRouteButton;
    @FXML
    TreeView myTreeView;


    public void NewPlaneButtonClicked(){
        System.out.println("NewPlaneButtonClicked");
    }

    public void NewShipButtonClicked(){
        System.out.println("NewShipButtonClicked");
    }
}
