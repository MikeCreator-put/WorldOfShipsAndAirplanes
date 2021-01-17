package airports;

import others.AirPathsGraph;
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

    public Airplane createPlane(int id, Airport destination, int amountOfStaff, int maxPassengers, int currentPassengers, double speed, List<Airport> path, AirPathsGraph airPathsGraph) {
        return new CivilianAirplane(this.getX(), this.getY(), id, amountOfStaff, destination, maxPassengers, currentPassengers, speed, path, airPathsGraph);
    }
}
