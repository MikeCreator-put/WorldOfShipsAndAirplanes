package vehicles;

import airports.Airport;
import airports.CivilianAirport;
import airports.MilitaryAirport;
import enums.AirplaneState;
import others.AirPathsGraph;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Airplane extends Vehicle {
    private int amountOfStaff;
    private double currentFuel;
    private double maxFuel;
    private List<Airport> path;
    private Airport nextLanding;
    private AirplaneState airplaneState = AirplaneState.waitingToBeLetIn;
    private Airport currentLocation;
    private Airport destination;
    private AirPathsGraph airPathsGraph = new AirPathsGraph();

    public void setCurrentLocation(Airport currentLocation){
        this.currentLocation = currentLocation;
    }


    private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public void start(){
        worker = new Thread(this);
        worker.start();
    }

    public void stop(){
        currentLocation.getAvailable().release();
        currentLocation.depart(this);
        running.set(false);
    }

    private int waitCounter = 23;

    public Boolean occupyCrossing(Airport crossing) {
        if (crossing.getAvailable().availablePermits() == 0) {
            waitCounter--;
            if (waitCounter < 0) {
                crossing.getAvailable().release();
                if ((currentLocation instanceof MilitaryAirport && this instanceof MilitaryAirplane) || (currentLocation instanceof CivilianAirport && this instanceof CivilianAirplane)) {
                    crossing.depart(this);
                }
                waitCounter = 23;
                return false;
            } else {
                return true;
            }
        } else {
            crossing.getAvailable().release();
            if ((currentLocation instanceof MilitaryAirport && this instanceof MilitaryAirplane) || (currentLocation instanceof CivilianAirport && this instanceof CivilianAirplane)) {
                crossing.depart(this);
            }
            return false;
        }
    }

    @Override
    public void run() {
        setState(AirplaneState.travelling);
        nextLanding = path.get(1);
        currentLocation = path.get(0);
        path.remove(0);
        path.remove(0);
        for (;;) {
            System.out.println(airPathsGraph.getListOfAirports().get(1).getAvailable().availablePermits());
            System.out.println(getState());
            //System.out.println("running");
            if(!running.get()){
                System.out.println("thread stopped");
                break;
            }
            switch (getState()) {
                case emergency -> {
                    System.out.println("emergency");
                    nextLanding = path.get(0);
                    destination = path.get(0);
                    path.clear();
                    setState(AirplaneState.travelling);
                }
                case travelling -> {
                    Boolean arrived = moveToPoint(getTimeFrame(), nextLanding, 5);
                    if (arrived) {
                        currentLocation = nextLanding;
                        if (currentLocation == destination){
                            setState(AirplaneState.arrivedAtDestination);
                        }else{
                            nextLanding=path.get(0);
                            path.remove(0);
                            setState(AirplaneState.waitingToBeLetIn);
                        }
                    }
                }
                case waitingToBeLetIn -> {
                    if (currentLocation.getAvailable().tryAcquire()) {
                        if ((currentLocation instanceof MilitaryAirport && this instanceof MilitaryAirplane) || (currentLocation instanceof CivilianAirport && this instanceof CivilianAirplane)) {
                            currentLocation.land(this);
                        }
                        setState(AirplaneState.arrived);
                    }
                }
                case arrived -> {
                    if(moveToPoint(getTimeFrame(), currentLocation, 0.1)) {
                        Boolean occupying = occupyCrossing(currentLocation);
                        if (!occupying) {
                            setState(AirplaneState.travelling);
                        }
                    }
                }
                case arrivedAtDestination -> {
                    if(destination.getAvailable().tryAcquire()) {
                        System.out.println("acquired from: ");
                        System.out.println(destination.getAvailable().availablePermits());
                        System.out.println(destination);
                        this.setX(destination.getX());
                        this.setY(destination.getY());
                        destination.land(this);
                        setState(AirplaneState.waitingForDestination);
                    }
                }
                case waitingForDestination ->{
                    if(getPath().size()>0){
                        destination = path.get(path.size()-1);
                        path.remove(0); //first element of path is current location which is already set up correctly
                        nextLanding = path.get(0); //get next landing
                        path.remove(0); //remove next landing from path
                        currentLocation.getAvailable().release();
                        currentLocation.depart(this);
                        setState(AirplaneState.travelling);
                    }
                }
            }
            try {
                Thread.sleep(33);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

    }

    public Airplane(double x, double y, int id, int amountOfStaff, double currentFuel, double maxFuel, Airport destination, double maxSpeed, List<Airport> path) {
        super(x, y, id, maxSpeed);
        this.amountOfStaff = amountOfStaff;
        this.currentFuel = currentFuel;
        this.maxFuel = maxFuel;
        this.path = path;
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
                        "\nState: " + this.getState() +
                        "\nNumber of staff: " + this.getAmountOfStaff() +
                        "\nCurrent fuel: " + this.getCurrentFuel() +
                        "\nMax fuel: " + this.getMaxFuel() +
                        "\nRoute: " + this.getPath() +
                        "\nNextLanding: " + this.getNextLanding() +
                        "\nSpeed: " + this.getMaxSpeed() + " units" +
                        "\nDestination: " + this.getDestination();
    }

    public Airport getCurrentLocation(){
        return currentLocation;
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

    public AirplaneState getState() {
        return airplaneState;
    }

    public void setState(AirplaneState airplaneState) {
        this.airplaneState = airplaneState;
    }
}
