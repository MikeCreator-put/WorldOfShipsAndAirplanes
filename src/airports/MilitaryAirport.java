package airports;

import enums.Weapons;
import jdk.jfr.Percentage;
import vehicles.Airplane;
import vehicles.MilitaryAirplane;

import java.util.List;

public class MilitaryAirport extends Airport {

    public MilitaryAirport(int x, int y, String name, int maxCapacity, List<Airplane> airplanesIn) {
        super(x, y, name, maxCapacity, airplanesIn);
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getInfo() {
        return this.getName() + " Military Airport" +
                super.getInfo();
    }

    public Airplane createPlane(int id, Airport destination, int amountOfStaff, Weapons weapons, double speed, double currentFuel, double maxFuel) {
        return new MilitaryAirplane(this.getX(), this.getY(), id, amountOfStaff, currentFuel, maxFuel, destination, weapons, speed);
    }
}
