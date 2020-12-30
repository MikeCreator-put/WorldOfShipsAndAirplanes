package airports;

import vehicles.Airplane;
import others.Point;

import java.util.List;


public abstract class Airport extends Point {
    private String name;
    private int maxCapacity;
    private int currentCapacity;
    private List<Airplane> airplanesIn;
    private List<Airport> oneWayConnections;
    private List<Airport> twoWayConnections;

    public Airport(int x, int y, String name, int maxCapacity, int currentCapacity, List<Airplane> airplanesIn, List<Airport> oneWayConnections, List<Airport> twoWayConnections){
        super(x,y);
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
        this.airplanesIn = airplanesIn;
        this.oneWayConnections = oneWayConnections;
        this.twoWayConnections = twoWayConnections;
    }

    @Override
    public String toString(){
        return "\nCoordinates: " + this.getX() + " " + this.getY() +
                "\nMaximum capacity: " + this.getMaxCapacity() +
                "\nSpots taken: " + this.getCurrentCapacity();
    }

    public void addOneWayConnection(Airport airport){}
    public void addTwoWayConnection(Airport airport){}

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

    public List<Airport> getOneWayConnections() {
        return oneWayConnections;
    }

    public void setOneWayConnections(List<Airport> oneWayConnections) {
        this.oneWayConnections = oneWayConnections;
    }

    public List<Airport> getTwoWayConnections() {
        return twoWayConnections;
    }

    public void setTwoWayConnections(List<Airport> twoWayConnections) {
        this.twoWayConnections = twoWayConnections;
    }
}
