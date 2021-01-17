package vehicles;

import enums.ShipStatus;
import others.Point;
import others.SeaPathNode;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Ship extends Vehicle {


    private SeaPathNode currentLocation;
    private SeaPathNode destination;
    private SeaPathNode previousLocation = new SeaPathNode(new Point(0, 0), 1);

    public ShipStatus getStatus() {
        return status;
    }

    public void setStatus(ShipStatus status) {
        this.status = status;
    }

    private ShipStatus status;


    public Ship(double x, double y, int id, double maxSpeed, SeaPathNode startingLocationNode) {
        super(x, y, id, maxSpeed);
        this.currentLocation = startingLocationNode;
        this.destination = getDestination(currentLocation);
    }

    private int waitCounter = 100;

    public Boolean occupyCrossing(SeaPathNode crossing) {
        if (crossing.getAvailable().availablePermits() == 0) {
            waitCounter--;
            if (waitCounter < 0) {
                crossing.getAvailable().release();
                waitCounter = 100;
                return false;
            } else {
                return true;
            }
        } else {
            crossing.getAvailable().release();
            return false;
        }
    }

    private final Random random = new Random();

    public SeaPathNode getDestination(SeaPathNode currentLocation) {
        SeaPathNode newDestination;
        do {
            newDestination = currentLocation.getConnections().get(random.nextInt(currentLocation.getConnections().size()));

        } while (newDestination.getNode().equals(previousLocation.getNode()));
        this.setStatus(ShipStatus.travelling);
        return newDestination;
    }

    private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public void start(){
        worker = new Thread(this);
        worker.start();
    }

    public void stop(){
        if(getStatus() == ShipStatus.arrived){
            currentLocation.getAvailable().release();
        }
        running.set(false);
    }

    @Override
    public void run() {
        for (;;) {
            System.out.println(worker.getId());
            //System.out.println("running");
            if(!running.get()){
                System.out.println("thread stopped");
                break;
            };
            switch (getStatus()) {
                case travelling -> {
                    Boolean arrived = moveToPoint(getTimeFrame(), destination.getNode(), 5);
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
                    if(moveToPoint(getTimeFrame(), currentLocation.getNode(), 0.1)) {
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

    @Override
    public String getInfo() {
        return
                super.getInfo() +
                        "\nState: " + this.getStatus() +
                        "\nSpeed: " + this.getMaxSpeed() + " units";
    }

}
