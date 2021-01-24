package vehicles;

import airports.Airport;
import enums.Weapons;
import others.AirPathsGraph;
import others.SeaPathNode;

import java.util.List;

/**
 * Represents a military ship.
 */
public class MilitaryShip extends Ship {

    private final Weapons weapons;

    /**
     * Creates new military ship with specified x and y coordinates, id, maximum speed, armament and starting location.
     *
     * @param x                    The ship's X coordinate.
     * @param y                    The ship's Y coordinate.
     * @param id                   The ship's id.
     * @param maxSpeed             The ship's maximum speed.
     * @param weapons              The ship's armament.
     * @param startingLocationNode The ship's starting location vertex of the sea connections graph.
     */
    public MilitaryShip(double x, double y, int id, double maxSpeed, Weapons weapons, SeaPathNode startingLocationNode) {
        super(x, y, id, maxSpeed, startingLocationNode);
        this.weapons = weapons;
    }

    /**
     * {@inheritDoc}
     *
     * @return String representing information about the airplane.
     */
    @Override
    public String toString() {
        return "Military Ship, id: " + getId();
    }

    @Override
    public String getInfo() {
        return "Military Ship, id: " + this.getId() +
                super.getInfo() +
                "\nArmament: " + this.getWeapons();
    }

    /**
     * Gets the ship's armament.
     *
     * @return Weapons value representing the ship's armament.
     */
    public Weapons getWeapons() {
        return weapons;
    }

    /**
     * Creates new MilitaryAirplane with specified x and y coordinates, id, destination, amount of staff, maximum speed, destination, armament and network of air connections.
     * X,Y coordinates and armament are inherited from the airplane's creator.
     *
     * @param id            The airplane's id.
     * @param destination   The airplane's destination.
     * @param amountOfStaff The airplane's amount of staff.
     * @param speed         The airplane's maximum speed.
     * @param path          The airplane's path.
     * @param airPathsGraph Network of air connections.
     * @return New Military Airplane.
     */
    public Airplane createPlane(int id, Airport destination, int amountOfStaff, double speed, List<Airport> path, AirPathsGraph airPathsGraph) {
        return new MilitaryAirplane(this.getX(), this.getY(), id, amountOfStaff, destination, this.getWeapons(), speed, path, airPathsGraph);
    }
}
