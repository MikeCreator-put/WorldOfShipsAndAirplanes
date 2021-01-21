package airports;

import vehicles.Airplane;
import others.Point;
import vehicles.CivilianAirplane;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Represents an airport.
 */
public abstract class Airport extends Point {
    private String name;
    private final int maxCapacity;
    private int currentCapacity;
    private final List<Airplane> airplanesIn;
    private final Semaphore available;

    /**
     * Creates an airport with specified x and y coordinates, name and maximum capacity.
     *
     * @param x           The airport's X coordinate.
     * @param y           The airport's Y coordinate.
     * @param name        The airport's name.
     * @param maxCapacity The airport's maximum capacity.
     */
    public Airport(double x, double y, String name, int maxCapacity) {
        super(x, y);
        this.currentCapacity = 0;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.airplanesIn = new ArrayList<>();
        available = new Semaphore(maxCapacity, true);
    }

    /**
     * Gets information about the airport.
     *
     * @return A string representing information about the airport.
     */
    @Override
    public String getInfo() {
        return super.getInfo() +
                "\nMaximum capacity: " + this.getMaxCapacity() +
                "\nSpots taken: " + this.getCurrentCapacity() +
                "\nSpots available: " + this.getAvailable().availablePermits() +
                "\nAirplanes waiting for departure:\n" + this.getAirplanesIn();
    }

    /**
     * Gets the airport's semaphore
     *
     * @return A semaphore which permits represent the amount of available spots for airplanes.
     */
    public Semaphore getAvailable() {
        return available;
    }

    /**
     * Handles the situation of an airplane arriving at the airport.
     *
     * @param airplane which is landing at the airport.
     */
    public void land(Airplane airplane) {
        currentCapacity += 1;
        getAirplanesIn().add(airplane);
        if (airplane instanceof CivilianAirplane) {
            ((CivilianAirplane) airplane).exchangePassengers();
        }
        airplane.setCurrentFuel(airplane.getMaxFuel());
    }

    /**
     * Handles the situation of an airplane departing from the airport.
     *
     * @param airplane which is departing from the airport.
     */
    public void depart(Airplane airplane) {
        currentCapacity -= 1;
        getAirplanesIn().remove(airplane);
    }

    /**
     * Gets name of the airport.
     *
     * @return A string representing aiport's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of the airport.
     *
     * @param name The airport's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the airport's maximum capacity.
     *
     * @return an integer representing the airport's maximum capacity.
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Gets the airport's current capacity.
     *
     * @return an integer representing the airport's current capacity.
     */
    public int getCurrentCapacity() {
        return currentCapacity;
    }

    /**
     * Gets the list of Airplanes currently being stationary at the airport.
     *
     * @return a list representing airplanes currently being stationary at the airport.
     */
    public List<Airplane> getAirplanesIn() {
        return airplanesIn;
    }

}
