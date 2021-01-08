package vehicles;

import airports.Airport;
import others.Point;

import java.util.ArrayList;
import java.util.List;

public abstract class Airplane extends Vehicle {
    private int amountOfStaff;
    private double currentFuel;
    private double maxFuel;
    private List<Airport> path;
    private Airport nextLanding;

    private Airport destination;

    @Override
    public void run() {//TODO
    }


    public Airplane(double x, double y, int id, int amountOfStaff, double currentFuel, double maxFuel, Airport destination, double maxSpeed) {
        super(x, y, id, maxSpeed);
        this.amountOfStaff = amountOfStaff;
        this.currentFuel = currentFuel;
        this.maxFuel = maxFuel;
        this.path = new ArrayList<>(); //TODO to be changed
        if (path.isEmpty()) {
            this.nextLanding = null;
        } else {
            this.nextLanding = path.get(0);
        }
        this.destination = destination;
    }

    @Override
    public String getInfo() {
        return
                super.getInfo() +
                        "\nNumber of staff: " + this.getAmountOfStaff() +
                        "\nCurrent fuel: " + this.getCurrentFuel() +
                        "\nMax fuel: " + this.getMaxFuel() +
                        "\nRoute: " + this.getPath() +
                        "\nNextLanding: " + this.getNextLanding() +
                        "\nSpeed: " + this.getMaxSpeed() + " units" +
                        "\nDestination: " + this.getDestination();
    }

    public void refuel() {
    }

    public void stop() {
    }

    public abstract void callEmergency();

    public void travelTo(Airport airport) {
    }

    public Airport findNearestAirport(List<Airport> lotniska) {
        return lotniska.get(0); //temporary
    }


    public int getAmountOfStaff() {
        return amountOfStaff;
    }

    public void setAmountOfStaff(int amountOfStaff) {
        this.amountOfStaff = amountOfStaff;
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


    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }
}
