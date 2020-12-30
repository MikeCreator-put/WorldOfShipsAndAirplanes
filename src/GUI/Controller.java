package GUI;

import airports.Airport;
import airports.CivilianAirport;
import airports.MilitaryAirport;
import com.sun.source.tree.Tree;
import enums.Companies;
import enums.Weapons;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import others.Point;
import vehicles.*;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Controller {

    //for test purposes
    private final List<Airport> emptylistairports = new ArrayList<>();
    private final List<Airplane> emptylistairplanes = new ArrayList<>();

    private final Airport a1 = new CivilianAirport(10, 10, "name1", 10, 0, emptylistairplanes, emptylistairports, emptylistairports);
    private final Airport a2 = new MilitaryAirport(20,20, "name2", 10, 0, emptylistairplanes, emptylistairports, emptylistairports);
    private final Airplane p1 = new CivilianAirplane(23, 23, 1, 15, 32.32, 32.32, emptylistairports, 50, 12, 15);
    private final Airplane p2 = new MilitaryAirplane( 51, 51, 2, 31, 99.99, 99.99, emptylistairports, Weapons.weapon1, 99);
    private final Ship s1 = new CivilianShip(7, 7, 3, 65.42, 16, 94, Companies.company1);
    private final Ship s2 = new MilitaryShip(12, 12, 4, 94.21, Weapons.weapon2);

    private final ControlPanel controlPanel = new ControlPanel();


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
    TreeView<Point> myTreeView;
    private final TreeItem<Point> airplanes = new TreeItem<>(new Point("Airplanes"));
    private final TreeItem<Point> ships = new TreeItem<>(new Point("Ships"));
    private final TreeItem<Point> airports = new TreeItem<>(new Point("Airports"));
    private final TreeItem<Point> rootItem = new TreeItem<>(new Point("root"));
    private final TreeItem<Point> militaryAirplanes = new TreeItem<>(new Point("Military Airplanes"));
    private final TreeItem<Point> civilianAirplanes = new TreeItem<>(new Point("Civilian Airplanes"));
    private final TreeItem<Point> militaryAirports = new TreeItem<>(new Point("Military Airports"));
    private final TreeItem<Point> civilianAirports = new TreeItem<>(new Point("Civilian Airports"));
    private final TreeItem<Point> militaryShips = new TreeItem<>(new Point("Military Ships"));
    private final TreeItem<Point> civilianShips = new TreeItem<>(new Point("Civilian Ships"));


    private <T> void addChildren(TreeItem<Point> parent, List<T> listOfChildren){
        for(T child : listOfChildren){
            TreeItem<Point> treeItem = (TreeItem<Point>) new TreeItem<>(child);
            parent.getChildren().add(treeItem);
        }
    }

    private void buildTreeView(){
        myTreeView.setRoot(rootItem);
        myTreeView.setShowRoot(false);
        rootItem.getChildren().addAll(airplanes, ships, airports);
        airplanes.getChildren().addAll(civilianAirplanes, militaryAirplanes);
        airports.getChildren().addAll(civilianAirports, militaryAirports);
        ships.getChildren().addAll(civilianShips, militaryShips);
        addChildren(civilianAirplanes, controlPanel.getListofCivilianAirplanes());
        addChildren(militaryAirplanes, controlPanel.getListOfMilitaryAirplanes());
        addChildren(civilianAirports, controlPanel.getListOfCivilianAirports());
        addChildren(militaryAirports, controlPanel.getListOfMilitaryAirports());
        addChildren(civilianShips, controlPanel.getListofCivilianShips());
        addChildren(militaryShips, controlPanel.getListofMilitaryShips());
    }



    @FXML
    public void initialize() {

        //test purposes
        controlPanel.addAirplane(p1);
        controlPanel.addAirplane(p2);
        controlPanel.addAirport(a1);
        controlPanel.addAirport(a2);
        controlPanel.addShip(s1);
        controlPanel.addShip(s2);

        buildTreeView();
    }

    public void NewPlaneButtonClicked(){
        System.out.println("NewPlaneButtonClicked");
    }

    public void NewShipButtonClicked(){
        System.out.println("NewShipButtonClicked");
    }
}
