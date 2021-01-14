package vehicles;

import airports.Airport;

import java.util.List;
import java.util.Random;


public class CivilianAirplane extends Airplane {
    private int maxPassengers;
    private int currentPassengers;

    public CivilianAirplane(double x, double y, int id, int amountOfStaff, double currentFuel, double maxFuel, Airport destination, int maxPassengers, int currentPassengers, double speed, List<Airport> path) {
        super(x, y, id, amountOfStaff, currentFuel, maxFuel, destination, speed, path);
        this.maxPassengers = maxPassengers;
        this.currentPassengers = currentPassengers;
    }

    @Override
    public String toString() {
        return "Civilian Airplane, id: " + getId();
    }

    @Override
    public String getInfo() {
        return "Civilian Airplane, id: " + this.getId() +
                super.getInfo() +
                "\nCurrent amount of passengers: " + this.getCurrentPassengers() +
                "\nMaximum amount of passengers: " + this.getMaxPassengers();
    }

    public void exchangePassengers() {
        Random random = new Random();
        currentPassengers = random.nextInt(maxPassengers);
    }


    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public int getCurrentPassengers() {
        return currentPassengers;
    }

    public void setCurrentPassengers(int currentPassengers) {
        this.currentPassengers = currentPassengers;
    }
}
