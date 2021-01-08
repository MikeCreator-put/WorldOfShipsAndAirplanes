package others;

public class Vector {
    private double x;
    private double y;
    private double magnitude;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        this.magnitude = Math.sqrt(x * x + y * y);
    }

    public Vector(Vector vector) {
        this.x = vector.getX();
        this.y = vector.getY();
        this.magnitude = vector.getMagnitude();
    }

    public void recalculateMagnitude() {
        magnitude = Math.sqrt(x * x + y * y);
    }

    public void mult(double n) {
        x = x * n;
        y = y * n;
    }

    public void div(double n) {
        x = x / n;
        y = y / n;
    }

    public void normalize() {
        x = x / magnitude;
        y = y / magnitude;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
}
