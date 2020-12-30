package airports;

import vehicles.Airplane;
import interfaces.PlaneFactory;

import java.util.List;

public class CivilianAirport extends Airport implements PlaneFactory {

    public CivilianAirport(int x, int y, String name, int maxCapacity, int currentCapacity, List<Airplane> airplanesIn, List<Airport> oneWayConnections, List<Airport> twoWayConnections) {
        super(x, y, name, maxCapacity, currentCapacity, airplanesIn, oneWayConnections, twoWayConnections);
    }

    @Override
    public String toString(){
        return "Civilian Airport " + this.getName() +
                super.toString();
    }

    @Override
    public void createPlane() {

    }
}
