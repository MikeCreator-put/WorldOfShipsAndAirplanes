package vehicles;

import airports.Airport;
import enums.Weapons;

public class MilitaryAirplane extends Airplane {
    private Weapons weapons;

    public MilitaryAirplane(double x, double y, int id, int amountOfStaff, double currentFuel, double maxFuel, Airport destination, Weapons weapons, double speed) {
        super(x, y, id, amountOfStaff, currentFuel, maxFuel, destination, speed);
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

    @Override
    public void callEmergency() {
    }

    public Weapons getWeapons() {
        return weapons;
    }

    public void setWeapons(Weapons weapons) {
        this.weapons = weapons;
    }
}