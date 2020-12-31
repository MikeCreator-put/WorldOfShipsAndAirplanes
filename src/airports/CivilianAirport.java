package airports;

import vehicles.Airplane;
import vehicles.CivilianAirplane;

import java.util.List;

public class CivilianAirport extends Airport {

    public CivilianAirport(int x, int y, String name, int maxCapacity, int currentCapacity, List<Airplane> airplanesIn, List<Airport> oneWayConnections, List<Airport> twoWayConnections) {
        super(x, y, name, maxCapacity, currentCapacity, airplanesIn, oneWayConnections, twoWayConnections);
    }

    @Override
    public String toString(){
        return getName();
    }

    @Override
    public String getInfo(){
        return "Civilian Airport " + this.getName() +
                super.getInfo();
    }

    public Airplane createPlane(int id, Airport destination, int amountOfStaff, int maxPassengers, int currentPassengers, double speed, double currentFuel, double maxFuel) {
        return new CivilianAirplane(this.getX(), this.getY(), id, amountOfStaff, currentFuel, maxFuel, destination, maxPassengers, currentPassengers, speed);
    }
}
