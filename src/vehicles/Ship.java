package vehicles;

import enums.ShipStatus;
import others.Point;
import others.SeaPathNode;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents a ship.
 */
public abstract class Ship extends Vehicle {

    private final Random random = new Random();
    private final AtomicBoolean running = new AtomicBoolean(true);

    private int waitCounter;
    private ShipStatus status;
    private SeaPathNode currentLocation;
    private SeaPathNode destination;
    private SeaPathNode previousLocation = new SeaPathNode(new Point(0, 0), 1);

    /**
     * Create new ship with specified x and y coordinates, id, maximum speed and starting location.
     *
     * @param x                    The ship's X coordinate.
     * @param y                    The ship's Y coordinate.
     * @param id                   The ship's id.
     * @param maxSpeed             The ship's maximum speed.
     * @param startingLocationNode Vertex of the sea paths graph representing the ship's starting location.
     */
    public Ship(double x, double y, int id, double maxSpeed, SeaPathNode startingLocationNode) {
        super(x, y, id, maxSpeed);
        this.currentLocation = startingLocationNode;
        this.destination = getDestination(currentLocation);
        setWaitCounter();
    }

    /**
     * Gets information about the ship.
     *
     * @return String representing information about the ship.
     */
    @Override
    public String getInfo() {
        return
                super.getInfo() +
                        "\nState: " + this.getStatus() +
                        "\nSpeed: " + this.getMaxSpeed() + " units";
    }

    private void setWaitCounter() {
        waitCounter = 100;
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
        if (getStatus() == ShipStatus.arrived) {
            currentLocation.getAvailable().release();
        }
        running.set(false);
    }

    private Boolean occupyCrossing(SeaPathNode crossing) {
        if (crossing.getAvailable().availablePermits() == 0) {
            waitCounter--;
            if (waitCounter < 0) {
                crossing.getAvailable().release();
                setWaitCounter();
                return false;
            } else {
                return true;
            }
        } else {
            crossing.getAvailable().release();
            return false;
        }
    }

    private SeaPathNode getDestination(SeaPathNode currentLocation) {
        SeaPathNode newDestination;
        do {
            newDestination = currentLocation.getConnections().get(random.nextInt(currentLocation.getConnections().size()));

        } while (newDestination.getNode().equals(previousLocation.getNode()));
        this.setStatus(ShipStatus.travelling);
        return newDestination;
    }

    /**
     * Handles the ship's movement.
     */
    @Override
    public void run() {
        for (; ; ) {
            if (!running.get()) {
                System.out.println("thread stopped");
                break;
            }
            switch (getStatus()) {
                case travelling -> {
                    Boolean arrived = moveToPoint(getTimeInterval(), destination.getNode(), 5);
                    if (arrived) {
                        previousLocation = currentLocation;
                        currentLocation = destination;
                        destination = getDestination(currentLocation);
                        setStatus(ShipStatus.waiting);
                    }
                }
                case waiting -> {
                    if (currentLocation.getAvailable().tryAcquire()) {
                        setStatus(ShipStatus.arrived);
                    }
                }
                case arrived -> {
                    if (moveToPoint(getTimeInterval(), currentLocation.getNode(), 0.1)) {
                        Boolean occupying = occupyCrossing(currentLocation);
                        if (!occupying) {
                            setStatus(ShipStatus.travelling);
                        }
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
     * Gets the ship's status.
     *
     * @return ShipsStatus value representing the ship's status.
     */
    public ShipStatus getStatus() {
        return status;
    }

    /**
     * Sets the ship's status.
     *
     * @param status New status.
     */
    public void setStatus(ShipStatus status) {
        this.status = status;
    }
}
