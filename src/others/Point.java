package others;

/**
 * Represent a point on the map.
 */
public class Point {
    private String name;
    private double x;
    private double y;

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Gets information about the point.
     *
     * @return String representing information about the point.
     */
    public String getInfo() {
        return "\nCoordinates: " + String.format("%,.0f", this.getX()) + " " + String.format("%,.0f", this.getY());
    }

    /**
     * Creates a point with specified x and y coordinates.
     *
     * @param x The point's X coordinate.
     * @param y The point's Y coordinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a point with specified name.
     *
     * @param name The point's name.
     */
    public Point(String name) {
        this.name = name;
    }

    /**
     * Calculates distance between two points.
     *
     * @param p The point to which you want to calculate distance.
     * @return Integer representing distance between two points.
     */
    public int distanceTo(Point p) {
        double x1 = this.x;
        double y1 = this.y;
        double x2 = p.x;
        double y2 = p.y;
        double distance;
        distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        return (int) distance;
    }

    /**
     * Gets the point's X coordinate.
     *
     * @return Double representing the point's X coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the point's X coordinate.
     *
     * @param x The point's X coordinate.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the point's Y coordinate
     *
     * @return Double representing the point's Y coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the point's Y coordinate.
     *
     * @param y The point's Y coordinate.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the point's name.
     *
     * @return String representing the point's name.
     */
    public String getName() {
        return name;
    }
}