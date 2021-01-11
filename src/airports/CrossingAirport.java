package airports;

import vehicles.Airplane;

import java.util.List;

public class CrossingAirport extends Airport {
    public CrossingAirport(double x, double y, String name, int maxCapacity, List<Airplane> airplanesIn) {
        super(x, y, name, maxCapacity, airplanesIn);
    }
    @Override
    public String toString(){
        return getName();
    }
}
