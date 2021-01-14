package vehicles;

import others.Point;
import others.SeaPathNode;
import others.Vector;

public abstract class Vehicle extends Point implements Runnable {

    private int id;
    private double maxSpeed;

    public double getTimeFrame() {
        return timeframe;
    }

    public Boolean moveToPoint(double time, Point node, double safeDistance) {
        Vector vector = new Vector(node.getX() - this.getX(), node.getY() - this.getY());
        Vector normalized = new Vector(vector);
        normalized.normalize();
        normalized.mult(this.getMaxSpeed() * time);
        normalized.recalculateMagnitude();
        if (normalized.getMagnitude() + safeDistance < vector.getMagnitude()) {
            this.setX(this.getX() + normalized.getX());
            this.setY(this.getY() + normalized.getY());
            return false;
        } else {
            normalized.normalize();
            normalized.mult(safeDistance);
            this.setX(node.getX() - normalized.getX());
            this.setY(node.getY() - normalized.getY());
            return true;
        }
    }

    private final double timeframe = 0.005;

    @Override
    public String getInfo() {
        return
                super.getInfo();
    }


    public Vehicle(double x, double y, int id, double maxSpeed) {
        super(x, y);
        this.id = id;
        this.maxSpeed = maxSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
