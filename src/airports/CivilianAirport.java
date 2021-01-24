package airports;

import others.AirPathsGraph;
import vehicles.Airplane;
import vehicles.CivilianAirplane;

import java.util.List;

/**
 * Represents a civilian airport.
 */
public class CivilianAirport extends Airport {

    /**
     * Creates civilian airport with specified x and y coordinates, name and maximum capacity.
     *
     * @param x           The airport's X coordinate.
     * @param y           The airport's Y coordinate.
     * @param name        The airport's name.
     * @param maxCapacity The airport's maximum capacity.
     */
    public CivilianAirport(double x, double y, String name, int maxCapacity) {
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
        return this.getName() + " Civilian Airport" +
                super.getInfo();
    }

    /**
     * Creates new <i>civilian airplane</i>.
     *
     * @param id                The airplanes id.
     * @param destination       The airplanes destination.
     * @param amountOfStaff     The airplane's amount of staff.
     * @param maxPassengers     The airplane's maximum amount of passengers.
     * @param currentPassengers The airplane's current amount of passengers.
     * @param speed             The airplane's speed.
     * @param path              The airplane's path.
     * @param airPathsGraph     Graph of connections between airports.
     * @return New CivilianAirplane entity describing civilian airplane with provided parameters.
     * @see CivilianAirplane
     */
    public Airplane createPlane(int id, Airport destination, int amountOfStaff, int maxPassengers, int currentPassengers, double speed, List<Airport> path, AirPathsGraph airPathsGraph) {
        return new CivilianAirplane(this.getX(), this.getY(), id, amountOfStaff, destination, maxPassengers, currentPassengers, speed, path, airPathsGraph);
    }
}
