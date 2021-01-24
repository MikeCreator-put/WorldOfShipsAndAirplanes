package others;

/**
 * Represents a 2D vector.
 */
public class Vector {
    private double x;
    private double y;
    private double magnitude;

    /**
     * Create a new vector with specified x and y.
     *
     * @param x The vector's X value.
     * @param y The vector's Y value.
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        this.magnitude = Math.sqrt(x * x + y * y);
    }

    /**
     * Deepcopy given vector.
     *
     * @param vector The vector to be copied.
     */
    public Vector(Vector vector) {
        this.x = vector.getX();
        this.y = vector.getY();
        this.magnitude = vector.getMagnitude();
    }

    /**
     * Calulate the vector's magnitude.
     */
    public void recalculateMagnitude() {
        magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    /**
     * Multiply the vector by constant value.
     * @param n Multiplier.
     */
    public void mult(double n) {
        x = x * n;
        y = y * n;
    }

    /**
     * Normalize the vector.
     */
    public void normalize() {
        x = x / magnitude;
        y = y / magnitude;
    }

    /**
     * Gets the vector's X value.
     * @return Double representing the vector's X value.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the vector's X value.
     * @param x Double representing the vector's X value.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the vector's Y value.
     * @return Double representing the vector's Y value.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the vector's Y value.
     * @param y Double representing the vector's Y value.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the vector's magnitude.
     * @return Double representing the vector's magnitude.
     */
    public double getMagnitude() {
        return magnitude;
    }
}
