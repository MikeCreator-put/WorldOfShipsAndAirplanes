package vehicles;

import airports.Airport;
import others.AirPathsGraph;

import java.util.List;
import java.util.Random;

/**
 * Represents a civilian airplane.
 */
public class CivilianAirplane extends Airplane {
    private final int maxPassengers;
    private int currentPassengers;

    /**
     * Create new civilian airplane with specified x and y coordinates, id, amount of staff, destination, maximum amount of passengers, current amount of passengers, maximum speed, path and network of air connections.
     *
     * @param x                 The airplane's X coordinate.
     * @param y                 The airplane's Y coordinate.
     * @param id                The airplane's id.
     * @param amountOfStaff     The airplane's amount of staff.
     * @param destination       The airplane's destination.
     * @param maxPassengers     The airplane's maximum amount of passengers.
     * @param currentPassengers The airplane's current amount of passengers.
     * @param speed             The airplane's maximum speed.
     * @param path              The airplane's path.
     * @param airPathsGraph     Network of air connections.
     */
    public CivilianAirplane(double x, double y, int id, int amountOfStaff, Airport destination, int maxPassengers, int currentPassengers, double speed, List<Airport> path, AirPathsGraph airPathsGraph) {
        super(x, y, id, amountOfStaff, destination, speed, path, airPathsGraph);
        this.maxPassengers = maxPassengers;
        this.currentPassengers = currentPassengers;
    }

    @Override
    public String toString() {
        return "Civilian Airplane, id: " + getId();
    }

    /**
     * {@inheritDoc}
     *
     * @return String representing information about the airplane.
     */
    @Override
    public String getInfo() {
        return "Civilian Airplane, id: " + this.getId() +
                super.getInfo() +
                "\nCurrent amount of passengers: " + this.getCurrentPassengers() +
                "\nMaximum amount of passengers: " + this.getMaxPassengers();
    }

    /**
     * Changes the amount of passengers.
     */
    public void exchangePassengers() {
        Random random = new Random();
        currentPassengers = random.nextInt(maxPassengers);
    }

    /**
     * Gets the airplane's maximum amount of passengers.
     *
     * @return Integer representing the airplane's maximum amount amount of passengers.
     */
    public int getMaxPassengers() {
        return maxPassengers;
    }

    /**
     * Gets the airplane's current amount of passengers.
     *
     * @return Integer representing the airplane's current amount of passengers.
     */
    public int getCurrentPassengers() {
        return currentPassengers;
    }
}
