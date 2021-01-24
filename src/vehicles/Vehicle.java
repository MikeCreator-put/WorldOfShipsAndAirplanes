package vehicles;

import others.Point;
import others.Vector;

/**
 * Represents a vehicle.
 */
public abstract class Vehicle extends Point implements Runnable {

    private final double maxSpeed;

    private int id;

    /**
     * Create new vehicle with specified x and y coordinates, id and maximum speed.
     *
     * @param x        The vehicle's X coordinate.
     * @param y        The vehicle's Y coordinate.
     * @param id       The vehicle's id.
     * @param maxSpeed The vehicle's maximum speed.
     */
    public Vehicle(double x, double y, int id, double maxSpeed) {
        super(x, y);
        this.id = id;
        this.maxSpeed = maxSpeed;
    }

    /**
     * Gets information about the vehicle.
     *
     * @return String representing information about the vehicle.
     */
    @Override
    public String getInfo() {
        return
                super.getInfo();
    }

    /**
     * Move the vehicle towards a specified Point by calculated distance.
     * The method will be called in a loop until it returns true, which means that the vehicle is in safeDistance away from the destination.
     *
     * @param timeInterval Time interval for the move.
     * @param point        Destination of the move.
     * @param safeDistance Distance from the destination which upon entering will result in returning True.
     * @return Boolean informing if the move would result in entering safe distance.
     */
    public Boolean moveToPoint(double timeInterval, Point point, double safeDistance) {
        Vector vector = new Vector(point.getX() - this.getX(), point.getY() - this.getY());
        Vector normalized = new Vector(vector);
        normalized.normalize();
        normalized.mult(this.getMaxSpeed() * timeInterval);
        normalized.recalculateMagnitude();
        if (normalized.getMagnitude() + safeDistance < vector.getMagnitude()) {
            this.setX(this.getX() + normalized.getX());
            this.setY(this.getY() + normalized.getY());
            reduceFuel(normalized.getMagnitude());
            return false;
        } else {
            normalized.normalize();
            normalized.mult(safeDistance);
            this.setX(point.getX() - normalized.getX());
            this.setY(point.getY() - normalized.getY());
            return true;
        }
    }

    /**
     * Template of the reduceFuel method which is going to be used only in the Airplane class.
     *
     * @param value here - doesn't matter, body of the method is empty.
     */
    public void reduceFuel(double value) {
    }

    /**
     * Gets the vehicle's maximum speed.
     *
     * @return Double representing the vehicle's maximum speed.
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Gets the vehicle's id.
     *
     * @return Integer representing the vehicle's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the vehicle's id.
     *
     * @param id The vehicle's id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the time interval used in methods responsible for moving the vehicle.
     *
     * @return Double representing the time interval.
     */
    public double getTimeInterval() {
        return 0.005;
    }
}
