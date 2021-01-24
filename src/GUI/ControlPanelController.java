package GUI;

import airports.Airport;
import airports.CivilianAirport;
import airports.MilitaryAirport;
import enums.AirplaneStatus;
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
import others.AirPathsGraph;
import others.Entities;
import others.Point;
import others.SeaPathNode;
import vehicles.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

/**
 * Controller class for control panel window.
 */
public class ControlPanelController {
    private final Stage thisStage;
    private final Entities entities;
    private MapController mapController;

    /**
     * Creates new instance of ControlPanelController.
     */
    public ControlPanelController() {
        entities = new Entities(new AirPathsGraph());
        this.thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ControlPanelFXML.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Control Panel");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    Button newAirplaneButton;
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
    ComboBox<Airport> changeRouteComboBox;
    @FXML
    Button cancelButton;
    @FXML
    Label informationLabel;
    @FXML
    Line line;
    @FXML
    StackPane stackPaneForChooseTypeComboBox;
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
    private final List<TreeItem<Point>> itemsList = new ArrayList<>();
    private Point selectedVehicle = null;

    private <T> void addChildren(TreeItem<Point> parent, List<T> listOfChildren) {
        for (T child : listOfChildren) {
            TreeItem<Point> treeItem = (TreeItem<Point>) new TreeItem<>(child);
            parent.getChildren().add(treeItem);
            itemsList.add(treeItem);
        }
    }

    private <T> void addChild(TreeItem<Point> parent, T child) {
        TreeItem<Point> treeItem = (TreeItem<Point>) new TreeItem<>(child);
        parent.getChildren().add(treeItem);
        itemsList.add(treeItem);
    }

    @FXML
    private void buildTreeView() {
        myTreeView.setRoot(rootItem);
        myTreeView.setShowRoot(false);
        rootItem.getChildren().addAll(airplanes, ships, airports);
        airplanes.getChildren().addAll(civilianAirplanes, militaryAirplanes);
        airports.getChildren().addAll(civilianAirports, militaryAirports);
        ships.getChildren().addAll(civilianShips, militaryShips);
        addChildren(civilianAirplanes, entities.getListOfCivilianAirplanes());
        addChildren(militaryAirplanes, entities.getListOfMilitaryAirplanes());
        addChildren(civilianAirports, entities.getListOfCivilianAirports());
        addChildren(militaryAirports, entities.getListOfMilitaryAirports());
        addChildren(civilianShips, entities.getListOfCivilianShips());
        addChildren(militaryShips, entities.getListOfMilitaryShips());

        handleTreeClicks();
    }

    //objects used to create new entities and show information about currently selected ones
    private final int maxWidth = 220;
    private ComboBox<String> newEntityChooseTypeComboBox;
    private ComboBox<Point> startingLocationComboBox;
    private ComboBox<Airport> destinationComboBox;
    private TextField amountOfStaffTextField;
    private TextField maxPassengersTextField;
    private TextField currentPassengersTextField;
    private TextField speedTextField;
    private ComboBox<Weapons> weaponsComboBox;
    private Weapons chosenWeapon;
    private ComboBox<Companies> companiesComboBox;
    private Companies chosenCompany;
    private Airport newDestination;
    private Point informationLabelItem = null;

    /**
     * Hides unnecessary buttons and sets up needed ones based on an entity clicked by user.
     *
     * @param item The entity chosen by user.
     */
    public void setButtons(Point item) {
        newDestination = null;
        changeRouteButton.setVisible(false);
        callEmergencyButton.setVisible(false);
        changeRouteComboBox.setVisible(false);
        if (item instanceof Vehicle) {
            deleteEntityButton.setVisible(true);
            if (item instanceof Airplane) {
                setupChangeRouteComboBox((Airplane) item);
                changeRouteButton.setVisible(true);
                changeRouteButton.setDisable(true);
                changeRouteComboBox.setVisible(true);
                callEmergencyButton.setVisible(true);
            }
        }
    }

    /**
     * Sets up the label showing information about currently chosen entity.
     *
     * @param item Entity chosen by the user.
     */
    public void setInformationLabel(Point item) {
        if (item instanceof Vehicle || item instanceof Airport) {
            informationLabel.setVisible(true);
            informationLabel.setText(item.getInfo());
            informationLabelItem = item;
            if (item instanceof Vehicle) {
                selectedVehicle = item;
            } else {
                changeRouteButton.setVisible(false);
                callEmergencyButton.setVisible(false);
                changeRouteComboBox.setVisible(false);
            }
        } else {
            deleteEntityButton.setVisible(false);
            informationLabelItem = null;
            informationLabel.setText("Here you'll see information about chosen entity");
        }
    }

    private void handleTreeClicks() {
        myTreeView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            changeRouteComboBox.getSelectionModel().clearSelection();
            newDestination = null;
            setInformationLabel(newValue.getValue());
            setButtons(newValue.getValue());
        }));
    }

    private void deleteEntityButtonClicked() {
        if (selectedVehicle != null) {
            if (selectedVehicle instanceof Ship) {
                ((Ship) selectedVehicle).stop();
                entities.removeShip((Ship) selectedVehicle);
            }
            if (selectedVehicle instanceof Airplane) {
                entities.getAirPathsGraph().releasePath(((Airplane) selectedVehicle).getCurrentLocation(), ((Airplane) selectedVehicle).getNextLanding());
                ((Airplane) selectedVehicle).stop();
                entities.removeAirplane((Airplane) selectedVehicle);
            }
            TreeItem<Point> c = new TreeItem<>(selectedVehicle);
            try {
                for (TreeItem<Point> item : itemsList) {
                    if (c.getValue().equals(item.getValue())) {
                        item.getParent().getChildren().remove(item);
                        itemsList.remove(item);
                    }
                }
            } catch (ConcurrentModificationException ignored) {
            }
            selectedVehicle = null;
            informationLabelItem = null;
            mapController.refresh();
            setInformationLabel(null);
            deleteEntityButton.setVisible(false);
            changeRouteButton.setVisible(false);
            callEmergencyButton.setVisible(false);
            changeRouteComboBox.setVisible(false);
        }
    }

    private void setNewDestination() {
        newDestination = changeRouteComboBox.getValue();
        changeRouteButton.setDisable(false);
    }

    private void setupNewEntityChooseTypeComboBox() {
        newEntityChooseTypeComboBox = new ComboBox<>();
        newEntityChooseTypeComboBox.setPromptText("Choose type");
        newEntityChooseTypeComboBox.getSelectionModel().clearSelection();
        newEntityChooseTypeComboBox.getItems().clear();
        newEntityChooseTypeComboBox.setMaxWidth(maxWidth);
        newEntityChooseTypeComboBox.getItems().addAll(
                "Civilian",
                "Military"
        );
        stackPaneForChooseTypeComboBox.getChildren().add(newEntityChooseTypeComboBox);
    }

    private void setChosenWeapon(int flag) { // flag == 1 - airplane, else - ship
        chosenWeapon = weaponsComboBox.getValue();
        if (flag == 1) {
            unlockCreateButtonForMilitaryAirplane();
        } else {
            unlockCreateButtonForMilitaryShip();
        }
    }

    private void setChosenCompany() {
        chosenCompany = companiesComboBox.getValue();
        unlockCreateButtonForCivilianShip();
    }

    private void setupChangeRouteComboBox(Airplane airplane) {
        List<Airport> possibleDestinations;
        if (airplane instanceof CivilianAirplane) {
            possibleDestinations = new ArrayList<>(entities.getListOfCivilianAirports());
        } else {
            possibleDestinations = new ArrayList<>(entities.getListOfMilitaryAirports());
        }
        possibleDestinations.remove(airplane.getDestination());
        changeRouteComboBox.getItems().clear();
        changeRouteComboBox.getItems().addAll(possibleDestinations);
    }

    private ComboBox<Point> initializeStartingLocationComboBox() {
        startingLocationComboBox = new ComboBox<>();
        startingLocationComboBox.setMaxWidth(maxWidth);
        startingLocationComboBox.setPromptText("Choose starting location");
        return startingLocationComboBox;
    }

    private TextField initializeAmountOfStaffTextField() {
        amountOfStaffTextField = new TextField();
        amountOfStaffTextField.setPromptText("Set amount of staff");
        amountOfStaffTextField.setMaxWidth(maxWidth);
        return amountOfStaffTextField;
    }

    private TextField initializeMaxPassengersTextField() {
        maxPassengersTextField = new TextField();
        maxPassengersTextField.setPromptText("Set maximum amount of passengers");
        maxPassengersTextField.setMaxWidth(maxWidth);
        return maxPassengersTextField;
    }

    private TextField initializeCurrentPassengersTextField() {
        currentPassengersTextField = new TextField();
        currentPassengersTextField.setPromptText("Set current amount of passengers");
        currentPassengersTextField.setMaxWidth(maxWidth);
        return currentPassengersTextField;
    }

    private TextField initializeSpeedTextField(int flag) { //flag == 1 - civilian, else - military
        speedTextField = new TextField();
        speedTextField.setMaxWidth(maxWidth);
        if (flag == 1) {
            speedTextField.setPromptText("Set airplane's speed (double)");
        } else {
            speedTextField.setPromptText("Set ship's speed (double)");
        }
        return speedTextField;
    }

    private ComboBox<Airport> initializeDestinationComboBox() {
        destinationComboBox = new ComboBox<>();
        destinationComboBox.setMaxWidth(maxWidth);
        destinationComboBox.setPromptText("Choose destination");
        return destinationComboBox;
    }

    private void clearNewEntityPropertiesVbox(int i) {
        while (newEntityPropertiesVbox.getChildren().size() > i) {
            newEntityPropertiesVbox.getChildren().remove(newEntityPropertiesVbox.getChildren().size() - 1);
        }
    }

    private ComboBox<Weapons> initializeWeaponsComboBox(int flag) { //flag 1 - airplane, else - ship
        weaponsComboBox = new ComboBox<>();
        weaponsComboBox.setPromptText("Choose armament");
        weaponsComboBox.setMaxWidth(maxWidth);
        weaponsComboBox.getItems().addAll(Weapons.values());
        weaponsComboBox.setOnAction(event -> setChosenWeapon(flag));
        return weaponsComboBox;
    }

    private ComboBox<Companies> initializeCompaniesComboBox() {
        companiesComboBox = new ComboBox<>();
        companiesComboBox.setPromptText("Choose company");
        companiesComboBox.setMaxWidth(maxWidth);
        companiesComboBox.getItems().addAll(Companies.values());
        companiesComboBox.setOnAction(event -> setChosenCompany());
        return companiesComboBox;
    }

    private void unlockCreateButtonForCivilianAirplane() {
        createButton.setDisable(amountOfStaffTextField.getText().equals("") || maxPassengersTextField.getText().equals("") || currentPassengersTextField.getText().equals("") || speedTextField.getText().equals(""));
    }

    private void unlockCreateButtonForMilitaryAirplane() {
        createButton.setDisable(amountOfStaffTextField.getText().equals("") || speedTextField.getText().equals("") || chosenWeapon == null);
    }

    private void unlockCreateButtonForCivilianShip() {
        createButton.setDisable(maxPassengersTextField.getText().equals("") || currentPassengersTextField.getText().equals("") || speedTextField.getText().equals("") || chosenCompany == null);
    }

    private void unlockCreateButtonForMilitaryShip() {
        createButton.setDisable(speedTextField.getText().equals("") || chosenWeapon == null);
    }

    private void newAirplaneButtonClicked() {
        createButton.setDisable(true);
        setupNewEntityChooseTypeComboBox();
        newEntityPropertiesVbox.getChildren().clear();
        newEntityVbox.setVisible(true);
        newEntityLabel.setText("New Airplane");
        newEntityChooseTypeComboBox.setOnAction(event -> handleChooseTypeComboBoxForAirplanes());
    }

    private void handleChooseTypeComboBoxForAirplanes() {
        newEntityPropertiesVbox.getChildren().clear();
        startingLocationComboBox = initializeStartingLocationComboBox();
        if (newEntityChooseTypeComboBox.getValue().equals("Civilian")) {
            startingLocationComboBox.getItems().addAll(entities.getListOfCivilianAirports());
            newEntityPropertiesVbox.getChildren().add(startingLocationComboBox);
            startingLocationComboBox.setOnAction(event -> handleStartingLocationComboBoxForCivilianAirplanes());
        } else { //it equals military
            startingLocationComboBox.getItems().addAll(entities.getListOfMilitaryAirports());
            startingLocationComboBox.getItems().addAll(entities.getListOfMilitaryShips());
            newEntityPropertiesVbox.getChildren().add(startingLocationComboBox);
            startingLocationComboBox.setOnAction(event -> handleStartingLocationComboBoxForMilitaryAirplanes());
        }
    }

    private void handleStartingLocationComboBoxForCivilianAirplanes() {
        clearNewEntityPropertiesVbox(1);
        destinationComboBox = initializeDestinationComboBox();
        destinationComboBox.getItems().addAll(entities.getListOfCivilianAirports());
        destinationComboBox.getItems().remove(startingLocationComboBox.getValue());
        newEntityPropertiesVbox.getChildren().add(destinationComboBox);
        destinationComboBox.setOnAction(event -> handleDestinationComboBoxForCivilianAirplane());
    }

    private void handleStartingLocationComboBoxForMilitaryAirplanes() {
        clearNewEntityPropertiesVbox(1);
        destinationComboBox = initializeDestinationComboBox();
        destinationComboBox.getItems().addAll(entities.getListOfMilitaryAirports());
        if (startingLocationComboBox.getValue() instanceof Airport) {
            destinationComboBox.getItems().remove(startingLocationComboBox.getValue());
        }
        newEntityPropertiesVbox.getChildren().add(destinationComboBox);
        destinationComboBox.setOnAction(event -> handleDestinationComboBoxForMilitaryAirplane());
    }

    private void addListenerToFieldsForCivilianAirplane(TextField textField) {
        textField.textProperty().addListener(((observable, oldValue, newValue) -> unlockCreateButtonForCivilianAirplane()));
    }

    private void handleDestinationComboBoxForCivilianAirplane() {
        clearNewEntityPropertiesVbox(2);
        amountOfStaffTextField = initializeAmountOfStaffTextField();
        maxPassengersTextField = initializeMaxPassengersTextField();
        currentPassengersTextField = initializeCurrentPassengersTextField();
        speedTextField = initializeSpeedTextField(1);
        addListenerToFieldsForCivilianAirplane(amountOfStaffTextField);
        addListenerToFieldsForCivilianAirplane(maxPassengersTextField);
        addListenerToFieldsForCivilianAirplane(currentPassengersTextField);
        addListenerToFieldsForCivilianAirplane(speedTextField);
        newEntityPropertiesVbox.getChildren().addAll(amountOfStaffTextField, maxPassengersTextField, currentPassengersTextField, speedTextField);
    }

    private void addListenerToFieldsForMilitaryAirplane(TextField textField) {
        textField.textProperty().addListener(((observable, oldValue, newValue) -> unlockCreateButtonForMilitaryAirplane()));
    }

    private void handleDestinationComboBoxForMilitaryAirplane() {
        clearNewEntityPropertiesVbox(2);
        amountOfStaffTextField = initializeAmountOfStaffTextField();
        speedTextField = initializeSpeedTextField(1);
        addListenerToFieldsForMilitaryAirplane(amountOfStaffTextField);
        addListenerToFieldsForMilitaryAirplane(speedTextField);
        newEntityPropertiesVbox.getChildren().addAll(amountOfStaffTextField, speedTextField);
        weaponsComboBox = initializeWeaponsComboBox(1);
        if (startingLocationComboBox.getValue() instanceof MilitaryShip) {
            chosenWeapon = ((MilitaryShip) startingLocationComboBox.getValue()).getWeapons();
        } else {
            newEntityPropertiesVbox.getChildren().add(weaponsComboBox);
        }
    }

    private void addListenerToFieldsForCivilianShip(TextField textField) {
        textField.textProperty().addListener(((observable, oldValue, newValue) -> unlockCreateButtonForCivilianShip()));
    }

    private void addListenerToFieldsForMilitaryShip(TextField textField) {
        textField.textProperty().addListener(((observable, oldValue, newValue) -> unlockCreateButtonForMilitaryShip()));
    }

    private void newShipButtonClicked() {
        createButton.setDisable(true);
        setupNewEntityChooseTypeComboBox();
        newEntityPropertiesVbox.getChildren().clear();
        newEntityVbox.setVisible(true);
        newEntityLabel.setText("New Ship");
        newEntityChooseTypeComboBox.setOnAction(event -> handleChooseTypeComboBoxForShips());
    }

    private void handleChooseTypeComboBoxForShips() {
        newEntityPropertiesVbox.getChildren().clear();
        speedTextField = initializeSpeedTextField(2);
        if (newEntityChooseTypeComboBox.getValue().equals("Civilian")) {
            maxPassengersTextField = initializeMaxPassengersTextField();
            currentPassengersTextField = initializeCurrentPassengersTextField();
            companiesComboBox = initializeCompaniesComboBox();
            addListenerToFieldsForCivilianShip(maxPassengersTextField);
            addListenerToFieldsForCivilianShip(currentPassengersTextField);
            addListenerToFieldsForCivilianShip(speedTextField);
            newEntityPropertiesVbox.getChildren().addAll(companiesComboBox, maxPassengersTextField, currentPassengersTextField, speedTextField);
        } else { //it equals military
            weaponsComboBox = initializeWeaponsComboBox(2);
            addListenerToFieldsForMilitaryShip(speedTextField);
            newEntityPropertiesVbox.getChildren().addAll(weaponsComboBox, speedTextField);
        }
    }


    private void viewMapButtonClicked() {
        mapController.showStage();
    }

    private int checkIntValue(TextField textField, String stringValue) {
        try {
            int value = Integer.parseInt(textField.getText());
            if (value < 0) {
                AlertBox.display("Please provide positive number for " + stringValue);
                textField.clear();
                return -1;
            } else {
                return value;
            }
        } catch (NumberFormatException e) {
            AlertBox.display("Please provide valid " + stringValue + " and try again");
            textField.clear();
            return -1;
        }
    }

    private double checkDoubleValue(TextField textField, String stringValue) {
        try {
            double value = Double.parseDouble(textField.getText());
            if (value < 0) {
                AlertBox.display("Please provide positive number for " + stringValue);
                textField.clear();
                return -1;
            } else if (value > 300) {
                AlertBox.display("That's way too fast, please try lower value for " + stringValue);
                textField.clear();
                return -1;
            } else {
                return value;
            }
        } catch (NumberFormatException e) {
            AlertBox.display("Please provide valid " + stringValue + " and try again");
            textField.clear();
            return -1;
        }
    }

    private Boolean checkMaxAndCurrentPassengers(int maxPassengers, int currentPassengers) {
        if (currentPassengers > maxPassengers) {
            AlertBox.display("Current amount of passengers can't be greater than maximum");
            return false;
        } else {
            return true;
        }
    }

    private void createCivilianAirplane(Airport destination, int amountOfStaff, double speed) {
        int maxPassengers = checkIntValue(maxPassengersTextField, "maximum amount of passengers");
        int currentPassengers = checkIntValue(currentPassengersTextField, "current amount of passengers");
        if (amountOfStaff != -1 && speed != -1 && maxPassengers != -1 && currentPassengers != -1) {
            if (checkMaxAndCurrentPassengers(maxPassengers, currentPassengers)) {
                int id = entities.getNewId();
                CivilianAirport creator = (CivilianAirport) startingLocationComboBox.getValue();
                List<Airport> path = entities.getAirPathsGraph().getPathDijkstra(creator, destination);
                Airplane newAirplane = creator.createPlane(id, destination, amountOfStaff, maxPassengers, currentPassengers, speed, path, entities.getAirPathsGraph());
                entities.addAirplane(newAirplane);
                addChild(civilianAirplanes, newAirplane);
                newEntityVbox.setVisible(false);
                mapController.refresh();
                newAirplane.start();
            }
        }
    }

    private void createMilitaryAirplane(Airport destination, int amountOfStaff, double speed) {
        Weapons weapons = chosenWeapon;
        Airplane newAirplane;
        if (amountOfStaff != -1 && speed != -1) {
            int id = entities.getNewId();
            if (startingLocationComboBox.getValue() instanceof Airport) {
                MilitaryAirport creator = (MilitaryAirport) startingLocationComboBox.getValue();
                List<Airport> path = entities.getAirPathsGraph().getPathDijkstra(creator, destination);
                newAirplane = creator.createPlane(id, destination, amountOfStaff, weapons, speed, path, entities.getAirPathsGraph());
            } else { //it is a Military Ship
                MilitaryShip creator = (MilitaryShip) startingLocationComboBox.getValue();
                List<Airport> path = entities.getAirPathsGraph().getPathDijkstra(findNearestAirport(creator, entities.getListOfAirports()), destination);
                path.add(0, new MilitaryAirport(creator.getX(), creator.getY(), "Military Ship, id: " + creator.getId(), 1));
                newAirplane = creator.createPlane(id, destination, amountOfStaff, speed, path, entities.getAirPathsGraph());
            }
            entities.addAirplane(newAirplane);
            addChild(militaryAirplanes, newAirplane);
            newEntityVbox.setVisible(false);
            mapController.refresh();
            newAirplane.start();
            chosenWeapon = null;
        }
    }

    private void createCivilianShip(SeaPathNode shipsStartingLocationNode, double speed) {
        double x = shipsStartingLocationNode.getNode().getX();
        double y = shipsStartingLocationNode.getNode().getY();
        int maxPassengers = checkIntValue(maxPassengersTextField, "maximum amount of passengers");
        int currentPassengers = checkIntValue(currentPassengersTextField, "current amount of passengers");
        Companies company = chosenCompany;
        if (speed != -1 && maxPassengers != -1 && currentPassengers != -1) {
            if (checkMaxAndCurrentPassengers(maxPassengers, currentPassengers)) {
                int id = entities.getNewId();
                Ship newShip = new CivilianShip(x, y, id, speed, currentPassengers, maxPassengers, company, shipsStartingLocationNode);
                entities.addShip(newShip);
                addChild(civilianShips, newShip);
                newEntityVbox.setVisible(false);
                mapController.refresh();
                newShip.start();
                chosenCompany = null;
            }
        }
    }

    private void createMilitaryShip(SeaPathNode shipsStartingLocationNode, double speed) {
        double x = shipsStartingLocationNode.getNode().getX();
        double y = shipsStartingLocationNode.getNode().getY();
        Weapons weapons = chosenWeapon;
        if (speed != -1) {
            int id = entities.getNewId();
            Ship newShip = new MilitaryShip(x, y, id, speed, weapons, shipsStartingLocationNode);
            entities.addShip(newShip);
            addChild(militaryShips, newShip);
            newEntityVbox.setVisible(false);
            mapController.refresh();
            newShip.start();
            chosenWeapon = null;
        }
    }

    private void createButtonClicked() {
        double speed = checkDoubleValue(speedTextField, "speed");
        if (newEntityLabel.getText().equals("New Airplane")) {
            Airport destination = destinationComboBox.getValue();
            int amountOfStaff = checkIntValue(amountOfStaffTextField, "amount of staff");
            if (newEntityChooseTypeComboBox.getValue().equals("Civilian")) {
                createCivilianAirplane(destination, amountOfStaff, speed);
            } else { //it equals "Military"
                createMilitaryAirplane(destination, amountOfStaff, speed);
            }
        } else {  //it equals "New Ship"
            Random random = new Random();
            SeaPathNode shipsStartingLocationNode = entities.getListOfSeaPathNodes().get(random.nextInt(entities.getListOfSeaPathNodes().size()));
            if (newEntityChooseTypeComboBox.getValue().equals("Civilian")) {
                createCivilianShip(shipsStartingLocationNode, speed);
            } else {   //it equals "Military"
                createMilitaryShip(shipsStartingLocationNode, speed);
            }
        }
    }

    private Airport findNearestAirport(Point point, List<Airport> airports) {
        double minDist = Double.MAX_VALUE;
        Airport closest = null;
        for (Airport airport : airports) {
            double tmpDist = airport.distanceTo(point);
            if (tmpDist < minDist) {
                minDist = tmpDist;
                closest = airport;
            }
        }
        return closest;
    }

    private void callEmergencyButtonClicked() {
        if (((Airplane) selectedVehicle).getStatus() == AirplaneStatus.travelling) {
            double posX = selectedVehicle.getX();
            double posY = selectedVehicle.getY();
            List<Airport> path = new ArrayList<>();
            if (selectedVehicle instanceof MilitaryAirplane) {
                path.add(findNearestAirport(new Point(posX, posY), entities.getListOfMilitaryAirports()));
            } else { //instanceof CivilianAirplane
                path.add(findNearestAirport(new Point(posX, posY), entities.getListOfCivilianAirports()));
            }
            ((Airplane) selectedVehicle).setPath(path);
            ((Airplane) selectedVehicle).setStatus(AirplaneStatus.emergency);
        } else {
            AlertBox.display("Emergency can be called only while airplane is travelling.");
        }
    }

    private void changeRouteButtonClicked() {
        Airplane selectedAirplane = ((Airplane) selectedVehicle);
        if (selectedAirplane.getPath().size() == 0 && selectedAirplane.getStatus() == AirplaneStatus.waitingForDestination) {
            selectedAirplane.setPath(entities.getAirPathsGraph().getPathDijkstra(selectedAirplane.getCurrentLocation(), newDestination));
        } else if (selectedAirplane.getStatus() == AirplaneStatus.travelling) {
            List<Airport> newPath = entities.getAirPathsGraph().getPathDijkstra(selectedAirplane.getNextLanding(), newDestination);
            newPath.remove(0);
            selectedAirplane.setDestination(newPath.get(newPath.size() - 1));
            selectedAirplane.setPath(newPath);
        } else if (selectedAirplane.getStatus() != AirplaneStatus.travelling && selectedAirplane.getStatus() != AirplaneStatus.waitingForDestination) {
            AlertBox.display("Can't change route right now, try again in a moment");
        }
        newDestination = null;
        setButtons(selectedAirplane);
        changeRouteComboBox.getSelectionModel().clearSelection();
    }

    private void cancelButtonClicked() {
        newEntityVbox.setVisible(false);
    }

    /**
     * Resizes the line which separates information about the entity from new entities creator to fit the size of the Pane.
     *
     * @param value Length by which the line should be extended or shortened.
     */
    public void resizeLine(double value) {
        double oldY = line.getStartY();
        line.setStartY(oldY + value);
        line.setEndY(0);
    }

    /**
     * Shows the stage.
     */
    public void showStage() {
        thisStage.show();
    }

    /**
     * Gets an object storing information about every entity on the map.
     *
     * @return Instance of Entities object storing information about every entity on the map.
     */
    public Entities getEntities() {
        return this.entities;
    }

    /**
     * Sets currently selected vehicle.
     *
     * @param point Currently selected Vehicle.
     */
    public void setSelectedVehicle(Point point) {
        selectedVehicle = point;
    }

    /**
     * Gets the controller of map window.
     *
     * @return Instance of MapController.
     */
    public MapController getMapController() {
        return mapController;
    }

    /**
     * Gets the stage of control panel window.
     *
     * @return The Stage of control panel window.
     */
    public Stage getStage() {
        return thisStage;
    }

    /**
     * Gets the item which details are currently being displayed.
     *
     * @return The item stored by InformationLabel.
     */
    public Point getInformationLabelItem() {
        return informationLabelItem;
    }

    @FXML
    public void initialize() {
        mapController = new MapController(this);

        newAirplaneButton.setOnAction(event -> newAirplaneButtonClicked());
        newShipButton.setOnAction(event -> newShipButtonClicked());
        deleteEntityButton.setOnAction(event -> deleteEntityButtonClicked());
        createButton.setOnAction(event -> createButtonClicked());
        viewMapButton.setOnAction(event -> viewMapButtonClicked());
        cancelButton.setOnAction(event -> cancelButtonClicked());
        callEmergencyButton.setOnAction(event -> callEmergencyButtonClicked());
        changeRouteButton.setOnAction(event -> changeRouteButtonClicked());
        changeRouteComboBox.setOnAction(event -> setNewDestination());

        newEntityVbox.setVisible(false);
        deleteEntityButton.setVisible(false);
        callEmergencyButton.setVisible(false);
        changeRouteButton.setVisible(false);
        changeRouteComboBox.setVisible(false);

        mapController.refresh();
        buildTreeView();
    }
}