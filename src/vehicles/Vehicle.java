package vehicles;
import others.Point;
import others.Vector;

public abstract class Vehicle extends Point implements Runnable {
    private int id;
    private double maxSpeed;

    public double getTimeFrame() {
        return timeframe;
    }

    public void setTimeframe(double timeframe) {
        this.timeframe = timeframe;
    }

    private double timeframe = 0.005;

    @Override
    public String getInfo() {
        return
                super.getInfo();
    }

    public Boolean moveToPoint(double time, Point point) {
        Vector vector = new Vector(point.getX() - this.getX(), point.getY() - this.getY());
        Vector normalized = new Vector(vector);
        normalized.normalize();
        normalized.mult(this.getMaxSpeed()*time);
        normalized.recalculateMagnitude();
        if(normalized.getMagnitude() < vector.getMagnitude()){
            this.setX(this.getX()+normalized.getX());
            this.setY(this.getY()+normalized.getY());
            return false;
        }else{
            this.setX(point.getX());
            this.setY(point.getY());
            return true;
        }
    }


    public void avoidCollision() {
        //TODO
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
