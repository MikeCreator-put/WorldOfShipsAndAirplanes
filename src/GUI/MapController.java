package GUI;

import javafx.application.Platform;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import others.Point;
import airports.Airport;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import others.SeaPathNode;
import vehicles.Airplane;
import vehicles.Ship;

import java.util.List;


public class MapController {
    private final Stage thisStage = new Stage();
    private Pane root = new Pane();
    private Scene scene = new Scene(root);
    private final ControlPanelController controlPanelController;

    public MapController(ControlPanelController controlPanelController) {
        this.controlPanelController = controlPanelController;
        thisStage.setScene(scene);
        refresh();
        thisStage.setTitle("Map");
    }


    public void refresh() {
        scene.setRoot(createContent());
        //thisStage.setScene(new Scene(createContent()));

    }


    public void showStage() {
        thisStage.setResizable(false);
        thisStage.show();
        //thisStage.initModality(Modality.NONE);
    }

    private Parent createContent() {

        root = new Pane();
        root.setPrefSize(1005.0, 500.0);
        ImageView map = new ImageView(new Image("map.png"));
        ImageView legend = new ImageView(new Image("mapLegend.png"));
        legend.setX(0);
        legend.setY(400);
        root.getChildren().add(map);
        root.getChildren().add(legend);

        Entities entities = controlPanelController.getEntities();

        try {
            drawSeaNodes(entities.getListOfSeaPathNodes(), Color.AQUAMARINE);
            drawSeaPaths(entities, 0.5, 0.5, Color.AQUAMARINE);
            drawAirports(entities.getListOfCivilianAirports(), Color.BLUE);
            drawAirports(entities.getListOfMilitaryAirports(), Color.RED);
            drawAirplanes(entities.getListofCivilianAirplanes(), Color.BLUE);
            drawAirplanes(entities.getListOfMilitaryAirplanes(), Color.RED);
            drawShips(entities.getListofCivilianShips(), Color.PURPLE);
            drawShips(entities.getListofMilitaryShips(), Color.ORANGE);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    private <T> void drawAirports(List<T> airports, Color color) {
        for (T airport : airports) {
            MapAirport mapAirport = new MapAirport(10, 10, color, (Airport) airport);
            root.getChildren().add(mapAirport);
            mapAirport.setOnMouseClicked(event -> controlPanelController.setInformationsLabel((Point) airport));
            Tooltip tooltip = new Tooltip(((Airport) airport).getInfo());
            Tooltip.install(mapAirport, tooltip);
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
            mapAirplane.setOnMouseClicked(event -> controlPanelController.setInformationsLabel((Point) airplane));
            Tooltip tooltip = new Tooltip(((Airplane) airplane).getInfo());
            Tooltip.install(mapAirplane, tooltip);
        }
    }

    private static class MapAirplane extends Circle {
        Airplane airplane;

        MapAirplane(int radius, Color color, Airplane airplane) {
            super(airplane.getX(),airplane.getY(),radius);
            //this.centerXProperty().bind(airplane.xProperty());
            //this.centerYProperty().bind(airplane.yProperty());
            this.setRadius(radius);
            this.airplane = airplane;
            this.setFill(color);
        }
    }

    private void drawSeaPaths(Entities entities, double opacity, double strokeWidth, Color color) {
        for (SeaPathNode startSeaPathNode : entities.getListOfSeaPathNodes()) {
            for (SeaPathNode endPoint : startSeaPathNode.getConnections()) {
                double startX = startSeaPathNode.getNode().getX();
                double startY = startSeaPathNode.getNode().getY();
                double endX = endPoint.getNode().getX();
                double endY = endPoint.getNode().getY();
                Line seaPath = new Line();
                seaPath.setStartX(startX);
                seaPath.setStartY(startY);
                seaPath.setEndX(endX);
                seaPath.setEndY(endY);
                seaPath.setOpacity(opacity);
                seaPath.setStrokeWidth(strokeWidth);
                seaPath.setStroke(color);
                root.getChildren().add(seaPath);
            }
        }
    }

    private <T> void drawSeaNodes(List<T> nodes, Color color) {
        for (T node : nodes) {
            MapSeapathNode mapSeaPathNode = new MapSeapathNode(2, color, 0.5, (SeaPathNode) node);
            root.getChildren().add(mapSeaPathNode);
        }
    }

    private static class MapSeapathNode extends Circle {
        SeaPathNode seaPathNode;

        MapSeapathNode(int radius, Color color, double opacity, SeaPathNode seaPathNode) {
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
            mapShip.setOnMouseClicked(event -> controlPanelController.setInformationsLabel((Point) ship));
            Tooltip tooltip = new Tooltip(((Ship) ship).getInfo());
            Tooltip.install(mapShip, tooltip);
        }
    }

    private static class MapShip extends Circle {
        Ship ship;

        MapShip(int radius, Color color, Ship ship) {
            super(ship.getX(),ship.getY(),radius);
            //this.centerXProperty().bind(ship.xProperty());
            //this.centerYProperty().bind(ship.yProperty());
            //this.setRadius(radius);
            this.ship = ship;
            this.setFill(color);
        }
    }
}
