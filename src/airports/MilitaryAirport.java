package airports;

import enums.Weapons;
import others.AirPathsGraph;
import vehicles.Airplane;
import vehicles.MilitaryAirplane;

import java.util.List;

/**
 * Represents a military airport.
 */
public class MilitaryAirport extends Airport {

    /**
     * Creates military airport with specified x and y coordinates, name and maximum capacity.
     *
     * @param x           The airport's X coordinate.
     * @param y           The airport's Y coordinate.
     * @param name        The airport's name.
     * @param maxCapacity The airport's maximum capacity.
     */
    public MilitaryAirport(double x, double y, String name, int maxCapacity) {
        super(x, y, name, maxCapacity);
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * {@inheritDoc}
     *
     * @return A string representing information about the airport.
     */
    @Override
    public String getInfo() {
        return this.getName() + " Military Airport" +
                super.getInfo();
    }

    /**
     * Creates new <i>military airplane</>.
     *
     * @param id            The airplane's id.
     * @param destination   The airplane's destination.
     * @param amountOfStaff The airplane's amount of staff.
     * @param weapons       The airplane's armament.
     * @param speed         The airplane's speed.
     * @param path          The airplane's path.
     * @param airPathsGraph Graph of connections between airports.
     * @return New MilitaryAirplane entity describing military airplane with provided parameters.
     * @see MilitaryAirplane
     */
    public Airplane createPlane(int id, Airport destination, int amountOfStaff, Weapons weapons, double speed, List<Airport> path, AirPathsGraph airPathsGraph) {
        return new MilitaryAirplane(this.getX(), this.getY(), id, amountOfStaff, destination, weapons, speed, path, airPathsGraph);
    }
}
