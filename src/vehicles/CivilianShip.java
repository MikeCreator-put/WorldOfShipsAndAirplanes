package vehicles;

import enums.Companies;
import others.SeaPathNode;

/**
 * Represents a civilian ship.
 */
public class CivilianShip extends Ship {
    private final int currentPassengers;
    private final int maxPassengers;
    private final Companies company;

    /**
     * Creates new civilian ship with specified x and y coordinates, id, maximum speed, current amount of passengers, maximum amount of passengers, company, and starting location.
     *
     * @param x                    The ship's X coordinate.
     * @param y                    The ship's Y coordinate.
     * @param id                   The ship's id.
     * @param maxSpeed             The ship's maximum speed.
     * @param currentPassengers    The ship's current amount of passengers.
     * @param maxPassengers        The ship's maximum amount of passengers.
     * @param company              Name of the ship's company.
     * @param startingLocationNode The ship's starting location vertex of the sea connections graph.
     */
    public CivilianShip(double x, double y, int id, double maxSpeed, int currentPassengers, int maxPassengers, Companies company, SeaPathNode startingLocationNode) {
        super(x, y, id, maxSpeed, startingLocationNode);
        this.currentPassengers = currentPassengers;
        this.maxPassengers = maxPassengers;
        this.company = company;
    }

    @Override
    public String toString() {
        return "Civilian Ship, id: " + getId();
    }

    /**
     * {@inheritDoc}
     *
     * @return String representing information about the ship.
     */
    @Override
    public String getInfo() {
        return "Civilian Ship, id: " + this.getId() +
                super.getInfo() +
                "\nCurrent amount of passengers: " + this.getCurrentPassengers() +
                "\nMaximum amount of passengers: " + this.getMaxPassengers() +
                "\nCompany name: " + this.getCompany();
    }

    /**
     * Gets current amount of passengers.
     *
     * @return Integer representing current amount of passengers on the ship.
     */
    public int getCurrentPassengers() {
        return currentPassengers;
    }

    /**
     * Gets maximum amount of passengers.
     *
     * @return Integer representing maximum amount of passengers on the ship.
     */
    public int getMaxPassengers() {
        return maxPassengers;
    }

    /**
     * Gets name of the ship's company.
     *
     * @return Companies value representing the name of the ship's company.
     */
    public Companies getCompany() {
        return company;
    }
}
