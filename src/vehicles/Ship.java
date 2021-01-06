package vehicles;

import enums.ShipState;
import javafx.animation.AnimationTimer;
import others.Point;
import others.SeaPathNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Ship extends Vehicle {
    private SeaPathNode currentLocation;
    private SeaPathNode destination;
    private List<SeaPathNode> listOfSeaPathNodes;

    public ShipState getState() {
        return state;
    }

    public void setState(ShipState state) {
        this.state = state;
    }

    private ShipState state = ShipState.arrived;



    public Ship(double x, double y, int id, double maxSpeed, SeaPathNode startingLocationNode, List<SeaPathNode> listOfSeaPathNodes) {
        super(x, y, id, maxSpeed);
        this.currentLocation = startingLocationNode;
        this.listOfSeaPathNodes = listOfSeaPathNodes;

    }

    public SeaPathNode getPath(SeaPathNode currentLocation){
        Random random = new Random();
        List<Point> listOfDestinationsConnections = new ArrayList<>();
        Point destinationPoint = currentLocation.getConnections().get(random.nextInt(currentLocation.getConnections().size()));
        double destinationsX = destinationPoint.getX();
        double destinationsY = destinationPoint.getY();
        for(SeaPathNode seaPathNode : listOfSeaPathNodes){
            if(destinationsX == seaPathNode.getNode().getX() && destinationsY == seaPathNode.getNode().getY()){
                listOfDestinationsConnections.addAll(seaPathNode.getConnections());
                listOfDestinationsConnections.remove(currentLocation.getNode());
            }
        }
        return new SeaPathNode(destinationPoint, listOfDestinationsConnections);
    }

    @Override
    public void run(){
        //TODO semphores
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                switch (getState()) {
                    case arrived -> {
                        destination = getPath(currentLocation);
                        setState(ShipState.travelling);
                    }
                    case travelling -> {
                        Boolean arrived = moveToPoint(getTimeFrame(), destination.getNode());
                        if (arrived) {
                            setState(ShipState.arrived);
                            currentLocation = destination;
                        }
                    }
                }
            }
        };
        animationTimer.start();
    }



    @Override
    public String getInfo() {
        return
                super.getInfo() +
                        "\nMax speed: " + this.getMaxSpeed() + " units";
    }

}
