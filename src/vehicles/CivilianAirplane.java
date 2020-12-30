package vehicles;

import airports.Airport;

import java.util.List;

public class CivilianAirplane extends Airplane {
    private int maxPassengers;
    private int currentPassengers;

    public CivilianAirplane(int x, int y, int id, int crewAmount, double currentFuel, double maxFuel, List<Airport> path, int maxPassengers, int currentPassengers, double speed){
        super(x, y, id, crewAmount, currentFuel, maxFuel, path, speed);
        this.maxPassengers = maxPassengers;
        this.currentPassengers = currentPassengers;
    }

    @Override
    public String toString(){
        return "Civilian Airplane, id: " + this.getId() +
                super.toString() +
                "\nCurrent amount of passengers: " + this.getCurrentPassengers() +
                "\nMaximum amount of passengers: " + this.getMaxPassengers();
    }

    public void exchangePassengers(){}

    @Override
    public void callEmergency() {}

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
