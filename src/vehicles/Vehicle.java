package vehicles;

import others.Point;

public abstract class Vehicle extends Point implements Runnable {

    private int id;
    private double maxSpeed;

    public double getTimeFrame() {
        return timeframe;
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
