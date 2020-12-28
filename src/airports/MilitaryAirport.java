package airports;

import vehicles.Airplane;
import interfaces.PlaneFactory;

import java.util.List;

public class MilitaryAirport extends Airport implements PlaneFactory {

    public MilitaryAirport(int x, int y, String type, String name, int maxCapacity, int currentCapacity, List<Airplane> airplanesIn, List<Airport> oneWayConnections, List<Airport> twoWayConnections) {
        super(x, y, type, name, maxCapacity, currentCapacity, airplanesIn, oneWayConnections, twoWayConnections);
    }

    @Override
    public void createPlane() {
    }
}
