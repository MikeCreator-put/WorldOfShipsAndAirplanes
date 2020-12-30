package vehicles;

public abstract class Ship extends Vehicle {
    private double maxSpeed;
    public Ship(int x, int y, int id, double maxSpeed){
        super(x,y,id);
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString(){
        return
                super.toString() +
                "\nMax speed: " + this.getMaxSpeed() + " km/h";
    }
    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
