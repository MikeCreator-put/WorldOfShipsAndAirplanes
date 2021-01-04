package vehicles;

import airports.Airport;

import java.util.List;

public class CivilianAirplane extends Airplane {
    private int maxPassengers;
    private int currentPassengers;

    public CivilianAirplane(int x, int y, int id, int amountOfStaff, double currentFuel, double maxFuel, Airport destination, int maxPassengers, int currentPassengers, double speed) {
        super(x, y, id, amountOfStaff, currentFuel, maxFuel, destination, speed);
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
    }

    @Override
    public void callEmergency() {
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
