package airports;

/**
 * Represents crossing of paths in the air.
 */
public class CrossingAirport extends Airport {
    /**
     * Creates path's crossing with specified x and y coordinates, name and maximum capacity.
     *
     * @param x           The crossing's X coordinate.
     * @param y           The crossing's Y coordinate.
     * @param name        The crossing's name.
     * @param maxCapacity The crossing's maximum capacity.
     */
    public CrossingAirport(double x, double y, String name, int maxCapacity) {
        super(x, y, name, maxCapacity);
    }

    @Override
    public String toString() {
        return getName();
    }
}
