package airports;

import vehicles.Airplane;
import others.Point;

import java.awt.geom.Arc2D;
import java.util.List;


public abstract class Airport extends Point {
    private String name;
    private int maxCapacity;
    private int currentCapacity;
    private List<Airplane> airplanesIn;

    public Airport(double x, double y, String name, int maxCapacity, List<Airplane> airplanesIn) {
        super(x, y);
        this.currentCapacity = 0;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.airplanesIn = airplanesIn;
    }

    @Override
    public String getInfo() {
        return super.getInfo() +
                "\nMaximum capacity: " + this.getMaxCapacity() +
                "\nSpots taken: " + this.getCurrentCapacity() +
                "\nAirplanes in: " + this.getAirplanesIn();
    }

    public void addAirplaneToAirplanesIn(Airplane airplane){
        if(currentCapacity <= maxCapacity){
            airplanesIn.add(airplane);
            currentCapacity += 1;
        }
        // TODO airplane.waitToBeletIn???
    }

    public void removeAirplaneFromAirplanesIn(Airplane airplane){
        currentCapacity-=1;
        airplanesIn.remove(airplane);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public List<Airplane> getAirplanesIn() {
        return airplanesIn;
    }

    public void setAirplanesIn(List<Airplane> airplanesIn) {
        this.airplanesIn = airplanesIn;
    }
}
