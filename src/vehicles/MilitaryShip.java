package vehicles;

import GUI.MapController;
import airports.Airport;
import enums.Weapons;
import others.SeaPathNode;

import java.util.List;

public class MilitaryShip extends Ship {
    private Weapons weapons;

    public MilitaryShip(double x, double y, int id, double maxSpeed, Weapons weapons, SeaPathNode startingLocationNode) {
        super(x, y, id, maxSpeed, startingLocationNode);
        this.weapons = weapons;
    }

    @Override
    public String toString() {
        return "Military Ship, id: " + getId();
    }

    @Override
    public String getInfo() {
        return "Military Ship, id: " + this.getId() +
                super.getInfo() +
                "\nArmament: " + this.getWeapons();
    }

    public Airplane createPlane(int id, Airport destination, int amountOfStaff, double speed, double maxFuel, double currentFuel, List<Airport> path) {
        return new MilitaryAirplane(this.getX(), this.getY(), id, amountOfStaff, currentFuel, maxFuel, destination, this.getWeapons(), speed, path);
    }

    public Weapons getWeapons() {
        return weapons;
    }

    public void setWeapons(Weapons weapons) {
        this.weapons = weapons;
    }
}
