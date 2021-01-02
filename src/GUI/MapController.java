package GUI;

import others.Point;
import airports.Airport;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.util.List;

public class MapController {
    private Stage thisStage;
    private Pane root = new Pane();
    private final ControlPanelController controlPanelController;

    private Parent createContent(){
        root.setPrefSize(1005.0, 500.0);
        root.getChildren().add(new ImageView(new Image("map.png")));

        Entities entities = controlPanelController.getControlPanel();

        drawAirports(entities.getListOfCivilianAirports(), Color.BLUE);
        drawAirports(entities.getListOfMilitaryAirports(), Color.RED);

        return root;
    }

    private <T> void drawAirports(List<T> airports, Color color){
        for (T airport: airports){
            MapAirport mapAirport = new MapAirport(10, 10, color, (Airport) airport);
            root.getChildren().add(mapAirport);
            mapAirport.setOnMouseClicked(event -> controlPanelController.setInformationsLabel((Point) airport));
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
