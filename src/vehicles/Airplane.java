package vehicles;

import airports.Airport;

import java.util.List;

public abstract class Airplane extends Vehicle {
    private int crewAmmount;
    private double currentFuel;
    private double maxFuel;
    private List<Class<Airport>> path;
    private Class<Airport> nextLanding;
    private double speed;

    public Airplane(int x, int y, int id, int crewAmount, double currentFuel, double maxFuel, List<Class<Airport>> path, double speed){
        super(x,y,id);
        this.crewAmmount = crewAmount;
        this.currentFuel = currentFuel;
        this.maxFuel = maxFuel;
        this.path = path;
        this.nextLanding = path.get(0);
        this.speed = speed;
    }

    public void print(){
        super.print();
        //sth
    }
    public void refuel(){}
    public void stop(){}
    public abstract void callEmergency();
    public void travelTo(Airport airport){}
    public Airport findNearestAirport(List<Airport> lotniska){
        return lotniska.get(0); //temporary
    }


    public int getCrewAmmount() {
        return crewAmmount;
    }

    public void setCrewAmmount(int crewAmmount) {
        this.crewAmmount = crewAmmount;
    }

    public double getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    public double getMaxFuel() {
        return maxFuel;
    }

    public void setMaxFuel(double maxFuel) {
        this.maxFuel = maxFuel;
    }

    public List<Class<Airport>> getPath() {
        return path;
    }

    public void setPath(List<Class<Airport>> path) {
        this.path = path;
    }

    public Class<Airport> getNextLanding() {
        return nextLanding;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
