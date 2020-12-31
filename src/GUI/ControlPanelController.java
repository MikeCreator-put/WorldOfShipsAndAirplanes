package GUI;

import airports.Airport;
import airports.CivilianAirport;
import airports.MilitaryAirport;
import enums.Companies;
import enums.Weapons;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import others.Point;
import vehicles.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControlPanelController {
    private final Stage thisStage;

    public Stage getStage(){ return thisStage; }

    public ControlPanelController(){
        thisStage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ControlPanelFXML.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Control Panel");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void showStage(){
        thisStage.show();
    }


    //for test purposes
    private final List<Airport> emptylistairports = new ArrayList<>();
    private final List<Airplane> emptylistairplanes = new ArrayList<>();
    private final Airport nullAirport = null;

    private final Airport a1 = new CivilianAirport(10, 10, "name1", 10, 0, emptylistairplanes, emptylistairports, emptylistairports);
    private final Airport a2 = new MilitaryAirport(20,20, "name2", 10, 0, emptylistairplanes, emptylistairports, emptylistairports);
    private final Airplane p1 = new CivilianAirplane(23, 23, 1, 15, 32.32, 32.32, nullAirport, 50, 12, 15);
    private final Airplane p2 = new MilitaryAirplane( 51, 51, 2, 31, 99.99, 99.99, nullAirport, Weapons.weapon1, 99);
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
    VBox newEntityVbox;
    @FXML
    ComboBox<String> newEntityComboBox;
    @FXML
    VBox newEntityPropertiesVbox;
    @FXML
    Button createButton;
    @FXML
    Label informationsLabel;
    @FXML
    Line line;




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
    private Point selectedVehicle = null;


    private <T> void addChildren(TreeItem<Point> parent, List<T> listOfChildren){
        for(T child : listOfChildren){
            TreeItem<Point> treeItem = (TreeItem<Point>) new TreeItem<>(child);
            parent.getChildren().add(treeItem);
        }
    }

    @FXML
    private void buildTreeView(){
        //myTreeView = new TreeView<>();
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

        handleTreeClicks();
    }
    private void handleTreeClicks(){
        myTreeView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.getValue() instanceof Vehicle || newValue.getValue() instanceof Airport){
                informationsLabel.setText(newValue.getValue().getInfo());
                if(newValue.getValue() instanceof Vehicle){
                    selectedVehicle = newValue.getValue();
                    deleteEntityButton.setVisible(true);
                    if(newValue.getValue() instanceof  Airplane){
                        changeRouteButton.setVisible(true);
                        callEmergencyButton.setVisible(true);
                    }
                }
            }else{
                informationsLabel.setText("Here you'll see informations about chosen entity");
                deleteEntityButton.setVisible(false);
                changeRouteButton.setVisible(false);
                callEmergencyButton.setVisible(false);
            }
        }));
    }



    public void deleteEntityButtonClicked(){
        if(selectedVehicle!=null){
            if(selectedVehicle instanceof Ship){
                controlPanel.removeShip((Ship)selectedVehicle);
            }
            if(selectedVehicle instanceof Airplane){
                controlPanel.removeAirplane((Airplane)selectedVehicle);
            }
            TreeItem<Point> c = myTreeView.getSelectionModel().getSelectedItem();
            c.getParent().getChildren().remove(c);
        }
    }

    public void newPlaneButtonClicked(){
        newEntityVbox.setVisible(true);
        System.out.println("NewPlaneButtonClicked");
    }

    public void newShipButtonClicked(){
        newEntityVbox.setVisible(true);
        System.out.println("NewShipButtonClicked");
    }

    public void viewMapButtonClicked(){
        MapController mapController = new MapController(this);
        mapController.showStage();
    }

    public void createButtonClicked(){
        newEntityVbox.setVisible(false);
    }

    public void resizeLine(double value){
        double oldY = line.getStartY();
        line.setStartY(oldY+value);
        line.setEndY(0);
    }

    @FXML
    public void initialize() {
        newPlaneButton.setOnAction(event -> newPlaneButtonClicked());
        newShipButton.setOnAction(event -> newShipButtonClicked());
        deleteEntityButton.setOnAction(event -> deleteEntityButtonClicked());
        createButton.setOnAction(event -> createButtonClicked());
        viewMapButton.setOnAction(event -> viewMapButtonClicked());

        newEntityVbox.setVisible(false);
        deleteEntityButton.setVisible(false);
        callEmergencyButton.setVisible(false);
        changeRouteButton.setVisible(false);

        //test purposes
        controlPanel.addAirplane(p1);
        controlPanel.addAirplane(p2);
        controlPanel.addAirport(a1);
        controlPanel.addAirport(a2);
        controlPanel.addShip(s1);
        controlPanel.addShip(s2);

        buildTreeView();
    }
}
