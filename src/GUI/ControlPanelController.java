package GUI;

import airports.Airport;
import enums.Companies;
import enums.Weapons;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
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

    public Stage getStage() {
        return thisStage;
    }

    public ControlPanelController() {
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ControlPanelFXML.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Control Panel");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.show();
    }


    //for test purposes
    private final List<Airport> emptylistairports = new ArrayList<>();
    private final List<Airplane> emptylistairplanes = new ArrayList<>();
    private final Airport nullAirport = null;

    private final Airplane p1 = new CivilianAirplane(23, 23, 1, 15, 32.32, 32.32, nullAirport, 50, 12, 15);
    private final Airplane p2 = new MilitaryAirplane(51, 51, 2, 31, 99.99, 99.99, nullAirport, Weapons.weapon1, 99);
    private final Ship s1 = new CivilianShip(7, 7, 3, 65.42, 16, 94, Companies.company1);
    private final Ship s2 = new MilitaryShip(12, 12, 4, 94.21, Weapons.weapon2);

    private Entities entities;


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
    Label newEntityLabel;
    @FXML
    VBox newEntityPropertiesVbox;
    @FXML
    Button createButton;
    @FXML
    Button cancelButton;
    @FXML
    Label informationsLabel;
    @FXML
    Line line;
    @FXML
    StackPane stackPaneForChooseTypeComboBox;
    ComboBox newEntityChooseTypeComboBox;

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


    private <T> void addChildren(TreeItem<Point> parent, List<T> listOfChildren) {
        for (T child : listOfChildren) {
            TreeItem<Point> treeItem = (TreeItem<Point>) new TreeItem<>(child);
            parent.getChildren().add(treeItem);
        }
    }

    @FXML
    private void buildTreeView() {
        myTreeView.setRoot(rootItem);
        myTreeView.setShowRoot(false);
        rootItem.getChildren().addAll(airplanes, ships, airports);
        airplanes.getChildren().addAll(civilianAirplanes, militaryAirplanes);
        airports.getChildren().addAll(civilianAirports, militaryAirports);
        ships.getChildren().addAll(civilianShips, militaryShips);
        addChildren(civilianAirplanes, entities.getListofCivilianAirplanes());
        addChildren(militaryAirplanes, entities.getListOfMilitaryAirplanes());
        addChildren(civilianAirports, entities.getListOfCivilianAirports());
        addChildren(militaryAirports, entities.getListOfMilitaryAirports());
        addChildren(civilianShips, entities.getListofCivilianShips());
        addChildren(militaryShips, entities.getListofMilitaryShips());

        handleTreeClicks();
    }

    public void setInformationsLabel(Point item) {
        if (item instanceof Vehicle || item instanceof Airport) {
            informationsLabel.setText(item.getInfo());
            if (item instanceof Vehicle) {
                selectedVehicle = item;
                deleteEntityButton.setVisible(true);
                if (item instanceof Airplane) {
                    changeRouteButton.setVisible(true);
                    callEmergencyButton.setVisible(true);
                }
            }
        } else {
            informationsLabel.setText("Here you'll see informations about chosen entity");
            deleteEntityButton.setVisible(false);
            changeRouteButton.setVisible(false);
            callEmergencyButton.setVisible(false);
        }
    }

    private void handleTreeClicks() {
        myTreeView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            setInformationsLabel(newValue.getValue());
        }));
    }


    public void deleteEntityButtonClicked() {
        if (selectedVehicle != null) {
            if (selectedVehicle instanceof Ship) {
                entities.removeShip((Ship) selectedVehicle);
            }
            if (selectedVehicle instanceof Airplane) {
                entities.removeAirplane((Airplane) selectedVehicle);
            }
            TreeItem<Point> c = myTreeView.getSelectionModel().getSelectedItem();
            c.getParent().getChildren().remove(c);
        }
    }

    public void newPlaneButtonClicked() {
        setNewEntityComboBox();
        newEntityPropertiesVbox.getChildren().clear();
        newEntityVbox.setVisible(true);
        newEntityLabel.setText("New Airplane");
        newEntityChooseTypeComboBox.setOnAction(event -> handleChooseTypeComboBoxForAirplanes());
    }

    public ComboBox initializeStartingLocationComboBox() {
        ComboBox startingLocationComboBox = new ComboBox();
        startingLocationComboBox.setPromptText("Choose starting location");
        return startingLocationComboBox;
    }

    public void handleChooseTypeComboBoxForAirplanes() {
        newEntityPropertiesVbox.getChildren().clear();
        ComboBox startingLocationComboBox = initializeStartingLocationComboBox();
        if (newEntityChooseTypeComboBox.getValue().equals("Civilian")) {
            startingLocationComboBox.getItems().addAll(entities.getListOfCivilianAirports());
            newEntityPropertiesVbox.getChildren().add(startingLocationComboBox);
            startingLocationComboBox.setOnAction(event -> handleStartingLocationComboBoxForCivilianAirplanes((Airport) startingLocationComboBox.getValue()));
        } else if (newEntityChooseTypeComboBox.getValue().equals("Military")) {
            startingLocationComboBox.getItems().addAll(entities.getListOfMilitaryAirports());
            startingLocationComboBox.getItems().addAll(entities.getListofMilitaryShips());
            newEntityPropertiesVbox.getChildren().add(startingLocationComboBox);
            startingLocationComboBox.setOnAction(event -> handleStartingLocationComboBoxForMilitaryAirplanes((Point) startingLocationComboBox.getValue()));
        }
    }

    public void clearNewEntityPropertiesVbox(int i) {
        while (newEntityPropertiesVbox.getChildren().size() > i) {
            newEntityPropertiesVbox.getChildren().remove(newEntityPropertiesVbox.getChildren().size() - 1);
        }
    }

    public ComboBox initializeDestinationComboBox() {
        ComboBox destinationComboBox = new ComboBox();
        destinationComboBox.setPromptText("Choose destination");
        return destinationComboBox;
    }

    public void handleStartingLocationComboBoxForCivilianAirplanes(Airport startingLocation) {
        clearNewEntityPropertiesVbox(1);
        ComboBox destinationComboBox = initializeDestinationComboBox();
        destinationComboBox.getItems().addAll(entities.getListOfCivilianAirports());
        destinationComboBox.getItems().remove(startingLocation);
        newEntityPropertiesVbox.getChildren().add(destinationComboBox);
    }

    public void handleStartingLocationComboBoxForMilitaryAirplanes(Point startingLocation) {
        clearNewEntityPropertiesVbox(1);
        ComboBox destinationComboBox = initializeDestinationComboBox();
        destinationComboBox.getItems().addAll(entities.getListOfMilitaryAirports());
        if (startingLocation instanceof Airport) {
            destinationComboBox.getItems().remove(startingLocation);
        }
        newEntityPropertiesVbox.getChildren().add(destinationComboBox);
    }

    public void newShipButtonClicked() {
        setNewEntityComboBox();
        newEntityPropertiesVbox.getChildren().clear();
        newEntityVbox.setVisible(true);
        newEntityLabel.setText("New Ship");
        newEntityChooseTypeComboBox.setOnAction(event -> handleComboBoxForShips());
    }

    public void handleComboBoxForShips() {
        //TODO
    }

    public void viewMapButtonClicked() {
        MapController mapController = new MapController(this);
        mapController.showStage();
    }

    public void createButtonClicked() {
        newEntityVbox.setVisible(false);
        //TODO
    }

    public void cancelButtonClicked() {
        newEntityVbox.setVisible(false);
    }

    public void resizeLine(double value) {
        double oldY = line.getStartY();
        line.setStartY(oldY + value);
        line.setEndY(0);
    }

    public Entities getEntities() {
        return this.entities;
    }

    public void setNewEntityComboBox() {
        newEntityChooseTypeComboBox = new ComboBox();
        newEntityChooseTypeComboBox.setPromptText("Choose type");
        newEntityChooseTypeComboBox.getSelectionModel().clearSelection();
        newEntityChooseTypeComboBox.getItems().clear();
        newEntityChooseTypeComboBox.getItems().addAll(
                "Civilian",
                "Military"
        );
        stackPaneForChooseTypeComboBox.getChildren().add(newEntityChooseTypeComboBox);
    }

    @FXML
    public void initialize() {
        entities = new Entities();


        newPlaneButton.setOnAction(event -> newPlaneButtonClicked());
        newShipButton.setOnAction(event -> newShipButtonClicked());
        deleteEntityButton.setOnAction(event -> deleteEntityButtonClicked());
        createButton.setOnAction(event -> createButtonClicked());
        viewMapButton.setOnAction(event -> viewMapButtonClicked());
        cancelButton.setOnAction(event -> cancelButtonClicked());

        newEntityVbox.setVisible(false);
        deleteEntityButton.setVisible(false);
        callEmergencyButton.setVisible(false);
        changeRouteButton.setVisible(false);

        //test purposes
        entities.addAirplane(p1);
        entities.addAirplane(p2);
        entities.addShip(s1);
        entities.addShip(s2);

        buildTreeView();
    }
}
