package vehicles;

import airports.Airport;

import java.util.ArrayList;
import java.util.List;

public abstract class Airplane extends Vehicle {
    private int crewAmmount;
    private double currentFuel;
    private double maxFuel;
    private List<Airport> path;
    private Airport nextLanding;
    private double speed;

    private Airport destination;

    public Airplane(int x, int y, int id, int crewAmount, double currentFuel, double maxFuel, Airport destination, double speed){
        super(x,y,id);
        this.crewAmmount = crewAmount;
        this.currentFuel = currentFuel;
        this.maxFuel = maxFuel;
        this.path = new ArrayList<>(); //to be changed
        if (path.isEmpty()) {
            this.nextLanding = null;
        }else{
            this.nextLanding = path.get(0);
        }
        this.speed = speed;
        this.destination = destination;
    }

    @Override
    public String getInfo(){
        return
                super.getInfo() +
                "\nNumber of staff: " + this.getCrewAmmount() +
                "\nCurrent fuel: " + this.getCurrentFuel() +
                "\nMax fuel: " + this.getMaxFuel() +
                "\nRoute: " + this.getPath() +
                "\nNextLanding: " + this.getNextLanding() +
                "\nSpeed: " + this.getSpeed() + " km/h" +
                "\nDestination: " + this.getDestination();
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

    public List<Airport> getPath() {
        return path;
    }

    public void setPath(List<Airport> path) {
        this.path = path;
    }

    public Airport getNextLanding() {
        return nextLanding;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Airport getDestination() { return destination; }

    public void setDestination(Airport destination) { this.destination = destination; }
}
