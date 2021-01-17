package vehicles;

import airports.Airport;
import airports.CivilianAirport;
import airports.MilitaryAirport;
import enums.AirplaneStatus;
import others.AirPathsGraph;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Airplane extends Vehicle {
    private int amountOfStaff;
    private double currentFuel;
    private double maxFuel;
    private List<Airport> path;
    private Airport nextLanding;
    private AirplaneStatus status;
    private Airport previousLocation;
    private Airport currentLocation;
    private Airport destination;
    private AirPathsGraph airPathsGraph;

    public void setCurrentLocation(Airport currentLocation) {
        this.currentLocation = currentLocation;
    }


    private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop() {
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
        setStatus(AirplaneStatus.letEnterPath);
        nextLanding = path.get(1);
        currentLocation = path.get(0);
        path.remove(0);
        path.remove(0);
        for (; ; ) {
            airPathsGraph.printMexico_Sao();
            if (!running.get()) {
                break;
            }
            switch (getStatus()) {
                case emergency -> {
                    airPathsGraph.releasePath(currentLocation, nextLanding);
                    nextLanding = path.get(0);
                    destination = path.get(0);
                    path.clear();
                    setStatus(AirplaneStatus.travelling);
                }
                case letEnterPath -> {
                    if (airPathsGraph.letAirplaneEnterPath(currentLocation, nextLanding)) {
                        setStatus(AirplaneStatus.travelling);
                    }
                }
                case travelling -> {
                    Boolean arrived = moveToPoint(getTimeFrame(), nextLanding, 5);
                    if (arrived) {
                        previousLocation = currentLocation;
                        currentLocation = nextLanding;
                        if (currentLocation == destination) {
                            setStatus(AirplaneStatus.arrivedAtDestination);
                        } else {
                            nextLanding = path.get(0);
                            path.remove(0);
                            setStatus(AirplaneStatus.waitingToBeLetIn);
                        }
                    }
                }
                case waitingToBeLetIn -> {
                    if (currentLocation.getAvailable().tryAcquire()) {
                        airPathsGraph.releasePath(previousLocation, currentLocation);
                        if ((currentLocation instanceof MilitaryAirport && this instanceof MilitaryAirplane) || (currentLocation instanceof CivilianAirport && this instanceof CivilianAirplane)) {
                            currentLocation.land(this);
                        }
                        setStatus(AirplaneStatus.arrivedAtCheckpoint);
                    }
                }
                case arrivedAtCheckpoint -> {
                    if (moveToPoint(getTimeFrame(), currentLocation, 0.1)) {
                        Boolean occupying = occupyCrossing(currentLocation);
                        if (!occupying) {
                            setStatus(AirplaneStatus.letEnterPath);
                        }
                    }
                }
                case arrivedAtDestination -> {
                    if (destination.getAvailable().tryAcquire()) {
                        airPathsGraph.releasePath(previousLocation, currentLocation);
                        this.setX(destination.getX());
                        this.setY(destination.getY());
                        destination.land(this);
                        setStatus(AirplaneStatus.waitingForDestination);
                    }
                }
                case waitingForDestination -> {
                    if (getPath().size() > 0) {
                        destination = path.get(path.size() - 1);
                        path.remove(0); //first element of path is current location which is already set up correctly
                        nextLanding = path.get(0); //get next landing
                        path.remove(0); //remove next landing from path
                        currentLocation.getAvailable().release();
                        currentLocation.depart(this);
                        setStatus(AirplaneStatus.letEnterPath);
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

    public Airplane(double x, double y, int id, int amountOfStaff, Airport destination, double maxSpeed, List<Airport> path, AirPathsGraph airPathsGraph) {
        super(x, y, id, maxSpeed);
        this.amountOfStaff = amountOfStaff;
        this.currentFuel = 1000;
        this.maxFuel = 1000;
        this.path = path;
        this.destination = destination;
        this.airPathsGraph = airPathsGraph;
    }

    @Override
    public void reduceFuel(double value) {
        this.currentFuel -= value;
    }

    @Override
    public String getInfo() {
        return
                super.getInfo() +
                        "\nStatus: " + this.getStatus() +
                        "\nDestination: " + this.getDestination() +
                        "\nNextLanding: " + this.getNextLanding() +
                        "\nFurther route: " + this.getPath() +
                        "\nAmount of staff: " + this.getAmountOfStaff() +
                        "\nMax Speed: " + this.getMaxSpeed() + " units" +
                        "\nCurrent fuel [gallons]: " + String.format("%,.2f", this.getCurrentFuel() / 12); //to make the numbers slightly more realistic
    }

    public Airport getCurrentLocation() {
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

    public AirplaneStatus getStatus() {
        return status;
    }

    public void setStatus(AirplaneStatus airplaneStatus) {
        this.status = airplaneStatus;
    }
}
