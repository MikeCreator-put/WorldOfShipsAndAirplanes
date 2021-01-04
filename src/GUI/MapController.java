package GUI;

import javafx.scene.control.Tooltip;
import javafx.scene.shape.Circle;
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
import vehicles.Airplane;

import javax.tools.Tool;
import java.util.List;

public class MapController {
    private Stage thisStage;
    private Pane root = new Pane();
    private final ControlPanelController controlPanelController;

    private Parent createContent(){
        root.setPrefSize(1005.0, 500.0);
        root.getChildren().add(new ImageView(new Image("map.png")));

        Entities entities = controlPanelController.getEntities();

        drawAirports(entities.getListOfCivilianAirports(), Color.BLUE);
        drawAirports(entities.getListOfMilitaryAirports(), Color.RED);

        drawAirplanes(entities.getListofCivilianAirplanes(), Color.BLUE);
        drawAirplanes(entities.getListOfMilitaryAirplanes(), Color.RED);

        return root;
    }

    private <T> void drawAirports(List<T> airports, Color color){
        for (T airport: airports){
            MapAirport mapAirport = new MapAirport(10, 10, color, (Airport) airport);
            root.getChildren().add(mapAirport);
            mapAirport.setOnMouseClicked(event -> controlPanelController.setInformationsLabel((Point) airport));
            Tooltip tooltip = new Tooltip(((Airport) airport).getInfo());
            Tooltip.install(mapAirport, tooltip);
        }
    }

    private static class MapAirport extends Rectangle{
        Airport airport;
        MapAirport(int width, int height, Color color, Airport airport){
            super(width, height, color);
            this.airport = airport;
            int x = airport.getX();
            int y = airport.getY();
            setTranslateX(x);
            setTranslateY(y);
        }
    }

    private <T>  void drawAirplanes(List<T> airplanes, Color color){
        for(T airplane: airplanes){
            MapAirplane mapAirplane = new MapAirplane(5, color, (Airplane) airplane);
            root.getChildren().add(mapAirplane);
            mapAirplane.setOnMouseClicked(event -> controlPanelController.setInformationsLabel((Point) airplane));
            Tooltip tooltip = new Tooltip(((Airplane)airplane).getInfo());
            Tooltip.install(mapAirplane, tooltip);
        }
    }

    private static class MapAirplane extends Circle{
        Airplane airplane;
        MapAirplane(int radius, Color color, Airplane airplane){
            super(airplane.getX(), airplane.getY(), radius);
            this.airplane = airplane;
            this.setFill(color);
        }
    }

    public MapController(ControlPanelController controlPanelController) {
        this.controlPanelController = controlPanelController;
         thisStage = new Stage();
         thisStage.setScene(new Scene(createContent()));
         thisStage.setTitle("Map");
    }

    public void showStage() {
        thisStage.setResizable(false);
        thisStage.show();
    }
}
