package airports;

import vehicles.Airplane;
import vehicles.CivilianAirplane;

import java.util.List;

public class CivilianAirport extends Airport {

    public CivilianAirport(double x, double y, String name, int maxCapacity, List<Airplane> airplanesIn) {
        super(x, y, name, maxCapacity, airplanesIn);
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getInfo() {
        return this.getName() + " Civilian Airport" +
                super.getInfo();
    }

    public Airplane createPlane(int id, Airport destination, int amountOfStaff, int maxPassengers, int currentPassengers, double speed, double currentFuel, double maxFuel) {
        return new CivilianAirplane(this.getX(), this.getY(), id, amountOfStaff, currentFuel, maxFuel, destination, maxPassengers, currentPassengers, speed);
    }
}
