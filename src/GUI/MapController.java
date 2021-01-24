package GUI;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import others.Entities;
import airports.Airport;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import others.SeaPathNode;
import vehicles.Airplane;
import vehicles.Ship;

import java.util.List;

/**
 * Controller class for map window.
 */
public class MapController {
    private final Stage thisStage = new Stage();
    private final Pane root = new Pane();
    private final ControlPanelController controlPanelController;

    /**
     * Creates new instance of MapController which has access to control panel's controller.
     *
     * @param controlPanelController Controller of the control panel.
     */
    public MapController(ControlPanelController controlPanelController) {
        this.controlPanelController = controlPanelController;
        root.setOnMouseClicked(event -> {
            double clickedX = event.getSceneX();
            double clickedY = event.getSceneY();
            if (!checkForAirports(clickedX, clickedY)) {
                checkForShips(clickedX, clickedY);
                checkForAirplanes(clickedX, clickedY);
            }

        });
        Scene scene = new Scene(root);
        thisStage.setScene(scene);
        refresh();
        thisStage.setTitle("Map");
    }

    private void checkForAirplanes(double x, double y) {
        for (Airplane airplane : controlPanelController.getEntities().getListOfAirplanes()) {
            if (airplane.getX() - 5 <= x && x <= airplane.getX() + 5 && airplane.getY() - 5 <= y && y <= airplane.getY() + 5) {
                controlPanelController.setInformationLabel(airplane);
                controlPanelController.setSelectedVehicle(airplane);
                controlPanelController.setButtons(airplane);
            }
        }
    }

    private void checkForShips(double x, double y) {
        for (Ship ship : controlPanelController.getEntities().getListOfShips()) {
            if (ship.getX() - 5 <= x && x <= ship.getX() + 5 && ship.getY() - 5 <= y && y <= ship.getY() + 5) {
                controlPanelController.setInformationLabel(ship);
                controlPanelController.setSelectedVehicle(ship);
                controlPanelController.setButtons(ship);
            }
        }
    }

    private Boolean checkForAirports(double x, double y) {
        for (Airport airport : controlPanelController.getEntities().getListOfAirports()) {
            if (airport.getX() - 5 <= x && x <= airport.getX() + 5 && airport.getY() - 5 <= y && y <= airport.getY() + 5) {
                controlPanelController.setInformationLabel(airport);
                controlPanelController.setButtons(airport);
                return true;
            }
        }
        return false;
    }

    /**
     * Redraws the whole map.
     */
    public void refresh() {
        createContent();
        if (controlPanelController.getInformationLabelItem() != null) {
            controlPanelController.setInformationLabel(controlPanelController.getInformationLabelItem());
        }
    }

    /**
     * Shows the stage.
     */
    public void showStage() {
        thisStage.setResizable(false);
        thisStage.show();
    }

    private void createContent() {
        root.getChildren().clear();
        root.setPrefSize(1005.0, 500.0);
        ImageView map = new ImageView("map.png");
        ImageView legend = new ImageView("mapLegend.png");
        legend.setX(0);
        legend.setY(400);
        root.getChildren().add(map);
        root.getChildren().add(legend);

        Entities entities = controlPanelController.getEntities();

        drawSeaNodes(entities.getListOfSeaPathNodes(), Color.AQUAMARINE);
        drawSeaPaths(entities, 0.5, 0.5, Color.AQUAMARINE);
        drawAirPaths(entities, 0.5, 0.5, Color.BLACK, Color.RED);
        drawAirports(entities.getListOfCivilianAirports(), Color.BLUE);
        drawAirports(entities.getListOfMilitaryAirports(), Color.RED);
        drawAirplanes(entities.getListOfCivilianAirplanes(), Color.BLUE);
        drawAirplanes(entities.getListOfMilitaryAirplanes(), Color.RED);
        drawShips(entities.getListOfCivilianShips(), Color.PURPLE);
        drawShips(entities.getListOfMilitaryShips(), Color.ORANGE);

    }

    private <T> void drawAirports(List<T> airports, Color color) {
        for (T airport : airports) {
            MapAirport mapAirport = new MapAirport(10, 10, color, (Airport) airport);
            root.getChildren().add(mapAirport);
        }
    }

    private static class MapAirport extends Rectangle {
        Airport airport;

        MapAirport(int width, int height, Color color, Airport airport) {
            super(width, height, color);
            this.airport = airport;
            double x = airport.getX();
            double y = airport.getY();
            setTranslateX(x - (double) (width / 2));
            setTranslateY(y - (double) (height / 2));
        }
    }

    private <T> void drawAirplanes(List<T> airplanes, Color color) {
        for (T airplane : airplanes) {
            MapAirplane mapAirplane = new MapAirplane(5, color, (Airplane) airplane);
            root.getChildren().add(mapAirplane);
        }
    }

    private static class MapAirplane extends Circle {
        Airplane airplane;

        MapAirplane(int radius, Color color, Airplane airplane) {
            super(airplane.getX(), airplane.getY(), radius);
            this.setRadius(radius);
            this.airplane = airplane;
            this.setFill(color);
        }
    }

    private void drawAirPaths(Entities entities, double opacity, double strokeWidth, Color color1, Color color2) {
        for (Airport start : entities.getAirPathsGraph().getAdjList().keySet()) {
            double startX = start.getX();
            double startY = start.getY();
            for (int i = 0; i < entities.getAirPathsGraph().getAdjList().get(start).size(); i++) {
                double endX = entities.getAirPathsGraph().getAdjList().get(start).get(i).getX();
                double endY = entities.getAirPathsGraph().getAdjList().get(start).get(i).getY();
                Line airPath = new Line(startX, startY, endX, endY);
                airPath.setOpacity(opacity);
                airPath.setStrokeWidth(strokeWidth);
                if (entities.getAirPathsGraph().getDrawingHelperList().get(start).get(i)) {
                    airPath.setStroke(color2);
                } else {
                    airPath.setStroke(color1);
                }
                root.getChildren().add(airPath);
            }
        }
    }

    private void drawSeaPaths(Entities entities, double opacity, double strokeWidth, Color color) {
        for (SeaPathNode startSeaPathNode : entities.getListOfSeaPathNodes()) {
            for (SeaPathNode endPoint : startSeaPathNode.getConnections()) {
                double startX = startSeaPathNode.getNode().getX();
                double startY = startSeaPathNode.getNode().getY();
                double endX = endPoint.getNode().getX();
                double endY = endPoint.getNode().getY();
                Line seaPath = new Line(startX, startY, endX, endY);
                seaPath.setOpacity(opacity);
                seaPath.setStrokeWidth(strokeWidth);
                seaPath.setStroke(color);
                root.getChildren().add(seaPath);
            }
        }
    }

    private <T> void drawSeaNodes(List<T> nodes, Color color) {
        for (T node : nodes) {
            MapSeaPathNode mapSeaPathNode = new MapSeaPathNode(2, color, 0.5, (SeaPathNode) node);
            root.getChildren().add(mapSeaPathNode);
        }
    }

    private static class MapSeaPathNode extends Circle {
        SeaPathNode seaPathNode;

        MapSeaPathNode(int radius, Color color, double opacity, SeaPathNode seaPathNode) {
            super(seaPathNode.getNode().getX(), seaPathNode.getNode().getY(), radius);
            this.seaPathNode = seaPathNode;
            this.setFill(color);
            this.setOpacity(opacity);
        }
    }

    private <T> void drawShips(List<T> ships, Color color) {
        for (T ship : ships) {
            MapShip mapShip = new MapShip(5, color, (Ship) ship);
            root.getChildren().add(mapShip);
        }
    }

    private static class MapShip extends Circle {
        Ship ship;

        MapShip(int radius, Color color, Ship ship) {
            super(ship.getX(), ship.getY(), radius);
            this.ship = ship;
            this.setFill(color);
        }
    }
}
