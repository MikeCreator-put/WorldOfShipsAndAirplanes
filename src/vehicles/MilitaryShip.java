package vehicles;

import airports.Airport;
import enums.Weapons;

public class MilitaryShip extends Ship {
    private Weapons weapons;

    public MilitaryShip(int x, int y, int id, double maxSpeed, Weapons weapons) {
        super(x, y, id, maxSpeed);
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

    public Airplane createPlane(int id, Airport destination, int amountOfStaff, Weapons weapons, double speed, double currentFuel, double maxFuel) {
        return new MilitaryAirplane(this.getX(), this.getY(), id, amountOfStaff, currentFuel, maxFuel, destination, weapons, speed);
    }

    public Weapons getWeapons() {
        return weapons;
    }

    public void setWeapons(Weapons weapons) {
        this.weapons = weapons;
    }
}
