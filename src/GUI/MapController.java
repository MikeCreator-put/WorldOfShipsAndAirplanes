package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MapController {
    private Stage thisStage;

    private final ControlPanelController controlPanelController;

    public MapController(ControlPanelController controlPanelController){
        this. controlPanelController = controlPanelController;

        thisStage = new Stage();

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MapFXML.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Map");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showStage(){
        thisStage.setResizable(false);
        thisStage.show();
    }
}
