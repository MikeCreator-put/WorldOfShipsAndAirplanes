package vehicles;

import GUI.MapController;
import enums.ShipState;
import others.Point;
import others.SeaPathNode;
import others.Vector;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Ship extends Vehicle {


    private SeaPathNode currentLocation;
    private SeaPathNode destination;
    private SeaPathNode previousLocation = new SeaPathNode(new Point(0, 0), 1);
    private List<SeaPathNode> listOfSeaPathNodes;

    public ShipState getState() {
        return state;
    }

    public void setState(ShipState state) {
        this.state = state;
    }

    private ShipState state = ShipState.arrived;
    private MapController mapController;


    public Ship(double x, double y, int id, double maxSpeed, SeaPathNode startingLocationNode, List<SeaPathNode> listOfSeaPathNodes, MapController mapController) {
        super(x, y, id, maxSpeed);
        this.currentLocation = startingLocationNode;
        this.destination = getDestination(currentLocation);
        this.listOfSeaPathNodes = listOfSeaPathNodes;
        this.mapController = mapController;

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

        } while (newDestination.getNode().equals(previousLocation.getNode()) || newDestination.getNode().equals(currentLocation.getNode()));
        this.setState(ShipState.travelling);
        return newDestination;
    }


    public Boolean moveToSeaNode(double time, SeaPathNode node, double safeDistance) {

        Vector vector = new Vector(node.getNode().getX() - this.getX(), node.getNode().getY() - this.getY());
        Vector normalized = new Vector(vector);
        normalized.normalize();
        normalized.mult(this.getMaxSpeed() * time);
        normalized.recalculateMagnitude();
        if (normalized.getMagnitude() + safeDistance < vector.getMagnitude()) {
            this.setX(this.getX() + normalized.getX());
            this.setY(this.getY() + normalized.getY());
            return false;
        } else {
            normalized.normalize();
            normalized.mult(safeDistance);
            this.setX(node.getNode().getX() - normalized.getX());
            this.setY(node.getNode().getY() - normalized.getY());
            return true;
        }
    }

    private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public void start(){
        worker = new Thread(this);
        worker.start();
    }

    public void stop(){
        running.set(false);
    }

//    @Override
//    public void run() {
//        running.set(true);
//        while (running.get()) {
//                AnimationTimer animationTimer = new AnimationTimer() {
//                    @Override
//                    public void handle(long now) {
//                        switch (getState()) {
//                            case travelling -> {
//                                Boolean arrived = moveToSeaNode(getTimeFrame(), destination, 3);
//                                if (arrived) {
//                                    previousLocation = currentLocation;
//                                    currentLocation = destination;
//                                    destination = getDestination(currentLocation);
//                                    setState(ShipState.waiting);
//                                }
//                            }
//                            case waiting -> {
//                                if (currentLocation.getAvailable().tryAcquire()) {
//                                    moveToSeaNode(getTimeFrame(), currentLocation, 0);
//                                    setState(ShipState.arrived);
//                                }
//                            }
//                            case arrived -> {
//                                Boolean occupying = occupyCrossing(currentLocation);
//                                if (!occupying) {
//                                    setState(ShipState.travelling);
//                                }
//                            }
//                        }
//                    }
//                };
//                animationTimer.start();
//            }
//
//
    @Override
    public void run() {
        for (;;) {
            System.out.println("running");
            // 1: mapController.refresh();
            // 2: Platform.runLater(() -> mapController.refresh());
            if(!running.get()){
                System.out.println("here");
                break;
            }
            switch (getState()) {
                case travelling -> {
                    Boolean arrived = moveToSeaNode(getTimeFrame(), destination, 5);
                    if (arrived) {
                        previousLocation = currentLocation;
                        currentLocation = destination;
                        destination = getDestination(currentLocation);
                        setState(ShipState.waiting);
                    }
                }
                case waiting -> {
                    if (currentLocation.getAvailable().tryAcquire()) {
                        setState(ShipState.arrived);
                    }
                }
                case arrived -> {
                    if(moveToSeaNode(getTimeFrame(), currentLocation, 0.1)) {
                        Boolean occupying = occupyCrossing(currentLocation);
                        if (!occupying) {
                            setState(ShipState.travelling);
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
                        "\nMax speed: " + this.getMaxSpeed() + " units";
    }

}
