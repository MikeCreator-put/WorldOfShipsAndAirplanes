package GUI;

import enums.Companies;
import enums.Weapons;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import airports.*;
import vehicles.*;
import java.util.*;

public class Main extends Application {
    List<Airport> emptylistairports = new ArrayList<>();
    List<Airplane> emptylistairplanes = new ArrayList<>();

    Airport a1 = new CivilianAirport(10, 10, "name1", 10, 0, emptylistairplanes, emptylistairports, emptylistairports);
    Airport a2 = new MilitaryAirport(20,20, "name2", 10, 0, emptylistairplanes, emptylistairports, emptylistairports);
    Airplane p1 = new CivilianAirplane(23, 23, 1, 15, 32.32, 32.32, emptylistairports, 50, 12, 15);
    Airplane p2 = new MilitaryAirplane( 51, 51, 2, 31, 99.99, 99.99, emptylistairports, Weapons.weapon1, 99);
    Ship s1 = new CivilianShip(7, 7, 3, 65.42, 16, 94, Companies.company1);
    Ship s2 = new MilitaryShip(12, 12, 4, 94.21, Weapons.weapon2);

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println(a1.toString());
        System.out.println(a2.toString());
        System.out.println(p1.toString());
        System.out.println(p2.toString());
        System.out.println(s1.toString());
        System.out.println(s2.toString());

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Control Panel");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
