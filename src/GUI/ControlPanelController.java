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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import others.Point;
import vehicles.*;

import java.io.IOException;
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


    private final Airplane p1 = new CivilianAirplane(23, 23, 101, 15, 32.32, 32.32, null, 50, 12, 15);
    private final Airplane p2 = new MilitaryAirplane(51, 51, 102, 31, 99.99, 99.99, null, Weapons.weapon1, 99);
    private final Ship s1 = new CivilianShip(7, 7, 103, 65.42, 16, 94, Companies.company1);
    private final Ship s2 = new MilitaryShip(12, 12, 104, 94.21, Weapons.weapon2);

    private Entities entities = new Entities();


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

    private <T> void addChild(TreeItem<Point> parent, T child){
        TreeItem<Point> treeItem = (TreeItem<Point>) new TreeItem<>(child);
        parent.getChildren().add(treeItem);
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
        myTreeView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> setInformationsLabel(newValue.getValue())));
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
            mapController.refresh();
        }
    }

    //objects needed to create new entity
    private final int MAXWIDTH = 220;
    private ComboBox<String> newEntityChooseTypeComboBox;
    private ComboBox<Point> startingLocationComboBox;
    private ComboBox<Airport> destinationComboBox;
    private TextField amountOfStaffTextField;
    private TextField maxPassengersTextField;
    private TextField currentPassengersTextField;
    private TextField speedTextField;
    private ComboBox<Weapons> weaponsComboBox;
    private Weapons chosenWeapon;

    public void setChosenWeapon() {
        chosenWeapon = weaponsComboBox.getValue();
        unlockCreateButtonForMilitaryAirplane();
    }

    public ComboBox<Point> initializeStartingLocationComboBox() {
        startingLocationComboBox = new ComboBox<>();
        startingLocationComboBox.setMaxWidth(MAXWIDTH);
        startingLocationComboBox.setPromptText("Choose starting location");
        return startingLocationComboBox;
    }


    public TextField initializeAmountOfStaffTextField() {
        amountOfStaffTextField = new TextField();
        amountOfStaffTextField.setPromptText("Set amount of staff");
        amountOfStaffTextField.setMaxWidth(MAXWIDTH);
        return amountOfStaffTextField;
    }

    public TextField initializeMaxPassengersTextField() {
        maxPassengersTextField = new TextField();
        maxPassengersTextField.setPromptText("Set maximum amount of passengers");
        maxPassengersTextField.setMaxWidth(MAXWIDTH);
        return maxPassengersTextField;
    }

    public TextField initializeCurrentPassengersTextField() {
        currentPassengersTextField = new TextField();
        currentPassengersTextField.setPromptText("Set current amount of passengers");
        currentPassengersTextField.setMaxWidth(MAXWIDTH);
        return currentPassengersTextField;
    }

    public TextField initializeSpeedTextField() {
        speedTextField = new TextField();
        speedTextField.setPromptText("Set airplane's speed (double)");
        speedTextField.setMaxWidth(MAXWIDTH);
        return speedTextField;
    }

    public ComboBox<Airport> initializeDestinationComboBox() {
        destinationComboBox = new ComboBox<>();
        destinationComboBox.setMaxWidth(MAXWIDTH);
        destinationComboBox.setPromptText("Choose destination");
        return destinationComboBox;
    }

    public void clearNewEntityPropertiesVbox(int i) {
        while (newEntityPropertiesVbox.getChildren().size() > i) {
            newEntityPropertiesVbox.getChildren().remove(newEntityPropertiesVbox.getChildren().size() - 1);
        }
    }

    public ComboBox<Weapons> initializeWeaponsComboBox() {
        weaponsComboBox = new ComboBox<>();
        weaponsComboBox.setPromptText("Choose armament");
        weaponsComboBox.setMaxWidth(MAXWIDTH);
        weaponsComboBox.getItems().addAll(Weapons.values());
        weaponsComboBox.setOnAction(event -> setChosenWeapon());
        return weaponsComboBox;
    }

    public void unlockCreateButtonForCivilianAirplane(){
        createButton.setDisable(amountOfStaffTextField.getText().equals("") || maxPassengersTextField.getText().equals("") || currentPassengersTextField.getText().equals("") || speedTextField.getText().equals(""));
    }

    public void unlockCreateButtonForMilitaryAirplane(){
        createButton.setDisable(amountOfStaffTextField.getText().equals("") || speedTextField.getText().equals("") || chosenWeapon == null);
    }

    public void newPlaneButtonClicked() {
        createButton.setDisable(true);
        setNewEntityComboBox();
        newEntityPropertiesVbox.getChildren().clear();
        newEntityVbox.setVisible(true);
        newEntityLabel.setText("New Airplane");
        newEntityChooseTypeComboBox.setOnAction(event -> handleChooseTypeComboBoxForAirplanes());
    }

    public void handleChooseTypeComboBoxForAirplanes() {
        newEntityPropertiesVbox.getChildren().clear();
        startingLocationComboBox = initializeStartingLocationComboBox();
        if (newEntityChooseTypeComboBox.getValue().equals("Civilian")) {
            startingLocationComboBox.getItems().addAll(entities.getListOfCivilianAirports());
            newEntityPropertiesVbox.getChildren().add(startingLocationComboBox);
            startingLocationComboBox.setOnAction(event -> handleStartingLocationComboBoxForCivilianAirplanes());
        } else if (newEntityChooseTypeComboBox.getValue().equals("Military")) {
            startingLocationComboBox.getItems().addAll(entities.getListOfMilitaryAirports());
            startingLocationComboBox.getItems().addAll(entities.getListofMilitaryShips());
            newEntityPropertiesVbox.getChildren().add(startingLocationComboBox);
            startingLocationComboBox.setOnAction(event -> handleStartingLocationComboBoxForMilitaryAirplanes());
        }
    }

    public void handleStartingLocationComboBoxForCivilianAirplanes() {
        clearNewEntityPropertiesVbox(1);
        destinationComboBox = initializeDestinationComboBox();
        destinationComboBox.getItems().addAll(entities.getListOfCivilianAirports());
        destinationComboBox.getItems().remove(startingLocationComboBox.getValue());
        newEntityPropertiesVbox.getChildren().add(destinationComboBox);
        destinationComboBox.setOnAction(event -> handleDestinationComboBoxForCivilianAirplane());
    }

    public void handleStartingLocationComboBoxForMilitaryAirplanes() {
        clearNewEntityPropertiesVbox(1);
        destinationComboBox = initializeDestinationComboBox();
        destinationComboBox.getItems().addAll(entities.getListOfMilitaryAirports());
        if (startingLocationComboBox.getValue() instanceof Airport) {
            destinationComboBox.getItems().remove(startingLocationComboBox.getValue());
        }
        newEntityPropertiesVbox.getChildren().add(destinationComboBox);
        destinationComboBox.setOnAction(event -> handleDestinationComboBoxForMilitaryAirplane());
    }

    public void addListnerToFieldsForCivilianAirplane(TextField textField){
        textField.textProperty().addListener(((observable, oldValue, newValue) -> unlockCreateButtonForCivilianAirplane()));
    }

    public void handleDestinationComboBoxForCivilianAirplane() {
        clearNewEntityPropertiesVbox(2);
        amountOfStaffTextField = initializeAmountOfStaffTextField();
        maxPassengersTextField = initializeMaxPassengersTextField();
        currentPassengersTextField = initializeCurrentPassengersTextField();
        speedTextField = initializeSpeedTextField();
        addListnerToFieldsForCivilianAirplane(amountOfStaffTextField);
        addListnerToFieldsForCivilianAirplane(maxPassengersTextField);
        addListnerToFieldsForCivilianAirplane(currentPassengersTextField);
        addListnerToFieldsForCivilianAirplane(speedTextField);
        newEntityPropertiesVbox.getChildren().add(amountOfStaffTextField);
        newEntityPropertiesVbox.getChildren().add(maxPassengersTextField);
        newEntityPropertiesVbox.getChildren().add(currentPassengersTextField);
        newEntityPropertiesVbox.getChildren().add(speedTextField);
    }

    public void addListnerToFieldsForMilitaryAirplane(TextField textField){
        textField.textProperty().addListener(((observable, oldValue, newValue) -> unlockCreateButtonForMilitaryAirplane()));
    }

    public void handleDestinationComboBoxForMilitaryAirplane() {
        clearNewEntityPropertiesVbox(2);
        amountOfStaffTextField = initializeAmountOfStaffTextField();
        speedTextField = initializeSpeedTextField();
        addListnerToFieldsForMilitaryAirplane(amountOfStaffTextField);
        addListnerToFieldsForMilitaryAirplane(speedTextField);
        newEntityPropertiesVbox.getChildren().add(amountOfStaffTextField);
        newEntityPropertiesVbox.getChildren().add(speedTextField);
        weaponsComboBox = initializeWeaponsComboBox();
        if (startingLocationComboBox.getValue() instanceof MilitaryShip) {
            chosenWeapon = ((MilitaryShip) startingLocationComboBox.getValue()).getWeapons();
        } else {
            newEntityPropertiesVbox.getChildren().add(weaponsComboBox);
        }
    }

    public void newShipButtonClicked() {
        createButton.setDisable(true);
        setNewEntityComboBox();
        newEntityPropertiesVbox.getChildren().clear();
        newEntityVbox.setVisible(true);
        newEntityLabel.setText("New Ship");
        newEntityChooseTypeComboBox.setOnAction(event -> handleComboBoxForShips());
    }

    public void handleComboBoxForShips() {
        //TODO
    }

    MapController mapController = new MapController(this);


    public void viewMapButtonClicked() {
        mapController.showStage();
    }

    public int checkIntValue(TextField textField, String stringValue ){
        try{
            int value = Integer.parseInt(textField.getText());
            if(value<0){
                AlertBox.display("Please provide positive number for " + stringValue);
                return -1;
            }else{
                return value;
            }
        }catch (NumberFormatException e){
            AlertBox.display("Please provide valid " + stringValue + " and try again");
            return -1;
        }
    }
    
    public double checkDoubleValue(TextField textField, String stringValue){
        try{
            double value = Double.parseDouble(textField.getText());
            if(value<0){
                AlertBox.display("Please provide positive number for " + stringValue);
                return -1;
            }else{
                return value;
            }
        }catch (NumberFormatException e){
            AlertBox.display("Please provide valid " + stringValue + " and try again");
            return -1;
        }
    }

    public void createCivilianAirplane(int id, Airport destination, int amountOfStaff, double speed, double maxFuel, double currentFuel){
        int maxPassengers = checkIntValue(maxPassengersTextField, "maximum amount of passengers");
        int currentPassengers = checkIntValue(currentPassengersTextField, "current amount of passengers");
        if(amountOfStaff!=-1 && speed != -1 && maxPassengers != -1 && currentPassengers != -1) {
            CivilianAirport creator = (CivilianAirport) startingLocationComboBox.getValue();
            Airplane newAirplane = creator.createPlane(id, destination, amountOfStaff, maxPassengers, currentPassengers, speed, currentFuel, maxFuel);
            entities.addAirplane(newAirplane);
            addChild(civilianAirplanes, newAirplane);
            newEntityVbox.setVisible(false);
            mapController.refresh();
        }
    }

    public void createMilitaryAirplane(int id, Airport destination, int amountOfStaff, double speed, double maxFuel, double currentFuel){
        Weapons weapons = chosenWeapon;
        Airplane newAirplane;
        if(amountOfStaff!=-1 && speed != -1) {
            if (startingLocationComboBox.getValue() instanceof Airport) {
                MilitaryAirport creator = (MilitaryAirport) startingLocationComboBox.getValue();
                newAirplane = creator.createPlane(id, destination, amountOfStaff, weapons, speed, currentFuel, maxFuel);
            } else { //it is a Military Ship
                MilitaryShip creator = (MilitaryShip) startingLocationComboBox.getValue();
                newAirplane = creator.createPlane(id, destination, amountOfStaff, speed, currentFuel, maxFuel);
            }
            entities.addAirplane(newAirplane);
            addChild(militaryAirplanes, newAirplane);
            newEntityVbox.setVisible(false);
            mapController.refresh();
        }
    }

    public void createButtonClicked() {
        int id = entities.getNewId();
        if(newEntityLabel.getText().equals("New Airplane")){
            Airport destination = destinationComboBox.getValue();
            int amountOfStaff = checkIntValue(amountOfStaffTextField, "amount of staff");
            double speed = checkDoubleValue(speedTextField, "speed");
            double maxFuel = 1000; //TODO TODO TODO TODO TODO TODO TODO TODO
            double currentFuel = 1000;
            if(newEntityChooseTypeComboBox.getValue().equals("Civilian")){
                createCivilianAirplane(id, destination, amountOfStaff, speed, maxFuel, currentFuel);
            }else{ //it equals "Military"
                createMilitaryAirplane(id, destination, amountOfStaff, speed, maxFuel, currentFuel);
            }
        }else{  //it equals "New Ship"
            if(newEntityChooseTypeComboBox.getValue().equals("Civilian")){
                //TODO
            }else{   //it equals "Military"
                //TODO
            }
        }
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
        newEntityChooseTypeComboBox = new ComboBox<>();
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
        mapController.refresh();

        buildTreeView();
    }
}