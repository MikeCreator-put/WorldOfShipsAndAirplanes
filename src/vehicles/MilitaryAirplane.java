package vehicles;

import airports.Airport;
import enums.Weapons;
import others.AirPathsGraph;

import java.util.List;

/**
 * Represents a military airplane.
 */
public class MilitaryAirplane extends Airplane {
    private final Weapons weapons;

    /**
     * Creates new military airplane with specified x and y coordinates, id, amount of staff, destination, armament, maximum speed, path and network of air connections.
     *
     * @param x             The airplane's X coordinate.
     * @param y             The airplane's Y coordinate.
     * @param id            The airplane's id.
     * @param amountOfStaff The airplane's amount of staff.
     * @param destination   The airplane's destination.
     * @param weapons       The airplane's armament.
     * @param speed         The airplane's maximum speed.
     * @param path          The airplane's path.
     * @param airPathsGraph Network of air connections.
     */
    public MilitaryAirplane(double x, double y, int id, int amountOfStaff, Airport destination, Weapons weapons, double speed, List<Airport> path, AirPathsGraph airPathsGraph) {
        super(x, y, id, amountOfStaff, destination, speed, path, airPathsGraph);
        this.weapons = weapons;
    }

    @Override
    public String toString() {
        return "Military Airplane, id: " + getId();
    }

    /**
     * {@inheritDoc}
     *
     * @return String representing information about the airplane.
     */
    @Override
    public String getInfo() {
        return "Military Airplane, id: " + this.getId() +
                super.getInfo() +
                "\nArmament: " + this.getWeapons();
    }

    /**
     * Gets the airplane's armament.
     *
     * @return Weapons value representing the airplane's armament.
     */
    public Weapons getWeapons() {
        return weapons;
    }
}