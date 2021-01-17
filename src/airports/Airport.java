package airports;

import vehicles.Airplane;
import others.Point;
import vehicles.CivilianAirplane;

import java.util.List;
import java.util.concurrent.Semaphore;


public abstract class Airport extends Point {
    private String name;
    private int maxCapacity;
    private int currentCapacity;
    private List<Airplane> airplanesIn;
    private Semaphore available;

    public Airport(double x, double y, String name, int maxCapacity, List<Airplane> airplanesIn) {
        super(x, y);
        this.currentCapacity = 0;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.airplanesIn = airplanesIn;
        available = new Semaphore(maxCapacity, true);
    }

    public Semaphore getAvailable(){
        return available;
    }

    public void land(Airplane airplane){
        currentCapacity+=1;
        getAirplanesIn().add(airplane);
        if(airplane instanceof CivilianAirplane){
            ((CivilianAirplane) airplane).exchangePassengers();
        }
        airplane.setCurrentFuel(airplane.getMaxFuel());
    }

    public void depart(Airplane airplane){
        currentCapacity-=1;
        getAirplanesIn().remove(airplane);
    }

    @Override
    public String getInfo() {
        return super.getInfo() +
                "\nMaximum capacity: " + this.getMaxCapacity() +
                "\nSpots taken: " + this.getCurrentCapacity() +
                "\nSpots available: " + this.getAvailable().availablePermits() +
                "\nAirplanes waiting for departure:\n" + this.getAirplanesIn();
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
