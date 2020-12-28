package vehicles;

import airports.Airport;

import java.util.List;

public class CivilianAirplane extends Airplane {
    private int maxPassengers;
    private int currentPassengers;

    public CivilianAirplane(int x, int y, int id, int crewAmount, double currentFuel, double maxFuel, List<Class<Airport>> path, int maxPassengers, int currentPassengers, double speed){
        super(x, y, id, crewAmount, currentFuel, maxFuel, path, speed);
        this.maxPassengers = maxPassengers;
        this.currentPassengers = currentPassengers;
    }

    public void print(){
        super.print();
        //sth
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
