package vehicles;

import airports.Airport;
import enums.Weapons;
import others.AirPathsGraph;

import java.util.List;

public class MilitaryAirplane extends Airplane {
    private Weapons weapons;

    public MilitaryAirplane(double x, double y, int id, int amountOfStaff, Airport destination, Weapons weapons, double speed, List<Airport> path, AirPathsGraph airPathsGraph) {
        super(x, y, id, amountOfStaff, destination, speed, path, airPathsGraph);
        this.weapons = weapons;
    }

    @Override
    public String toString() {
        return "Military Airplane, id: " + getId();
    }

    @Override
    public String getInfo() {
        return "Military Airplane, id: " + this.getId() +
                super.getInfo() +
                "\nArmament: " + this.getWeapons();
    }

    public Weapons getWeapons() {
        return weapons;
    }

    public void setWeapons(Weapons weapons) {
        this.weapons = weapons;
    }
}