package vehicles;

import airports.Airport;
import airports.CivilianAirport;
import airports.MilitaryAirport;
import enums.AirplaneStatus;
import others.AirPathsGraph;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents an airplane.
 */
public abstract class Airplane extends Vehicle {

    private final int amountOfStaff;
    private final double maxFuel;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final AirPathsGraph airPathsGraph;

    private double currentFuel;
    private int inEmergency = 0;
    private int waitCounter;
    private List<Airport> path;
    private Airport nextLanding;
    private AirplaneStatus status;
    private Airport previousLocation;
    private Airport currentLocation;
    private Airport destination;

    /**
     * Creates new airplane with specified x and y coordinates, id, amount of staff, destination, maximum speed, path, and network of air connections.
     *
     * @param x             The airplane's X coordinate.
     * @param y             The airplane's Y coordinate.
     * @param id            The airplane's id.
     * @param amountOfStaff The airplane's amount of staff.
     * @param destination   The airplane's destination.
     * @param maxSpeed      The airplane's maximum speed.
     * @param path          The airplane's path.
     * @param airPathsGraph Network of air connections.
     */
    public Airplane(double x, double y, int id, int amountOfStaff, Airport destination, double maxSpeed, List<Airport> path, AirPathsGraph airPathsGraph) {
        super(x, y, id, maxSpeed);
        this.amountOfStaff = amountOfStaff;
        this.currentFuel = 1000;
        this.maxFuel = 1000;
        this.path = path;
        this.destination = destination;
        this.airPathsGraph = airPathsGraph;
        setWaitCounter();
    }

    /**
     * Gets information about the airplane.
     *
     * @return String representing information about the airplane.
     */
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

    private void setWaitCounter() {
        waitCounter = 23;
    }

    /**
     * Starts the thread.
     */
    public void start() {
        Thread worker = new Thread(this);
        worker.start();
    }

    /**
     * Stops the thread.
     */
    public void stop() {
        currentLocation.getAvailable().release();
        currentLocation.depart(this);
        running.set(false);
    }

    private Boolean occupyCrossing(Airport crossing) {
        if (crossing.getAvailable().availablePermits() == 0) {
            waitCounter--;
            if (waitCounter < 0) {
                crossing.getAvailable().release();
                if ((currentLocation instanceof MilitaryAirport && this instanceof MilitaryAirplane) || (currentLocation instanceof CivilianAirport && this instanceof CivilianAirplane)) {
                    crossing.depart(this);
                }
                setWaitCounter();
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

    /**
     * Handles the airplane's movement.
     */
    @Override
    public void run() {
        setStatus(AirplaneStatus.askForPermissionToEnterPath);
        nextLanding = path.get(1);
        currentLocation = path.get(0);
        path.remove(0);
        path.remove(0);
        while (running.get()) {
            switch (getStatus()) {
                case emergency -> {
                    airPathsGraph.releasePath(currentLocation, nextLanding);
                    nextLanding = path.get(0);
                    destination = path.get(0);
                    path.clear();
                    setStatus(AirplaneStatus.travelling);
                    inEmergency = 1;
                }
                case askForPermissionToEnterPath -> {
                    try {
                        if (airPathsGraph.letAirplaneEnterPath(currentLocation, nextLanding)) {
                            setStatus(AirplaneStatus.travelling);
                        }
                    } catch (NullPointerException createdAtMilitaryShip) {
                        setStatus(AirplaneStatus.travelling);
                    }
                }
                case travelling -> {
                    Boolean arrived = moveToPoint(getTimeInterval(), nextLanding, 5);
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
                        try {
                            airPathsGraph.releasePath(previousLocation, currentLocation);
                        } catch (NullPointerException ignored) {
                        } //if the airplane was created at military ship it doesnt have a path to be released
                        if ((currentLocation instanceof MilitaryAirport && this instanceof MilitaryAirplane) || (currentLocation instanceof CivilianAirport && this instanceof CivilianAirplane)) {
                            currentLocation.land(this);
                        }
                        setStatus(AirplaneStatus.arrivedAtCheckpoint);
                    }
                }
                case arrivedAtCheckpoint -> {
                    if (moveToPoint(getTimeInterval(), currentLocation, 0.1)) {
                        Boolean occupying = occupyCrossing(currentLocation);
                        if (!occupying) {
                            setStatus(AirplaneStatus.askForPermissionToEnterPath);
                        }
                    }
                }
                case arrivedAtDestination -> {
                    if (destination.getAvailable().tryAcquire()) {
                        if (inEmergency != 1) {
                            airPathsGraph.releasePath(previousLocation, currentLocation);
                        }
                        inEmergency = 0;
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
                        setStatus(AirplaneStatus.askForPermissionToEnterPath);
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

    /**
     * Reduces the airplane's fuel by a given value.
     *
     * @param value Subtrahend.
     */
    @Override
    public void reduceFuel(double value) {
        this.currentFuel -= value;
    }

    /**
     * Gets the airplane's last visited airport.
     *
     * @return Airport representing the airplane's last visited vertex from the air connections network.
     */
    public Airport getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Gets the airplane's amount of staff.
     *
     * @return Integer representing the airplane's amount of staff.
     */
    public int getAmountOfStaff() {
        return amountOfStaff;
    }

    /**
     * Gets the airplane's current amount of fuel.
     *
     * @return Double representing the airplane's current amount of fuel.
     */
    public double getCurrentFuel() {
        return currentFuel;
    }

    /**
     * Sets the airplane's current amount of fuel.
     *
     * @param currentFuel Current amount of fuel.
     */
    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    /**
     * Gets the airplane's maximum amount of fuel.
     *
     * @return Double representing the airplane's maximum amount of fuel.
     */
    public double getMaxFuel() {
        return maxFuel;
    }

    /**
     * Gets the airplane's path.
     *
     * @return List of Airports representing the airplanes path.
     */
    public List<Airport> getPath() {
        return path;
    }

    /**
     * Sets the airplane's path
     *
     * @param path List of Airports representing the airplanes path.
     */
    public void setPath(List<Airport> path) {
        this.path = path;
    }

    /**
     * Gets first Airport from the airplane's path.
     *
     * @return Airport to which the airplane is currently flying.
     */
    public Airport getNextLanding() {
        return nextLanding;
    }

    /**
     * Gets the airplane's destination.
     *
     * @return Airport being the final element of the airplane's path.
     */
    public Airport getDestination() {
        return destination;
    }

    /**
     * Sets the airplane's destination.
     *
     * @param destination The airplanes destination.
     */
    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    /**
     * Gets the airplane's status.
     *
     * @return AirplaneStatus value representing the airplane's status.
     */
    public AirplaneStatus getStatus() {
        return status;
    }

    /**
     * Sets the airplane's status.
     *
     * @param airplaneStatus New status.
     */
    public void setStatus(AirplaneStatus airplaneStatus) {
        this.status = airplaneStatus;
    }
}
