package others;

import java.util.List;
import java.util.concurrent.Semaphore;

public class SeaPathNode {

    private final Point node;
    private List<SeaPathNode> connections;

    private final Semaphore available;

    public SeaPathNode(Point point, int permits) {
        this.node = point;
        this.available = new Semaphore(permits,true);
    }

    public Point getNode() {
        return node;
    }

    public List<SeaPathNode> getConnections() {
        return connections;
    }

    public void setConnections(List<SeaPathNode> connections) {
        this.connections = connections;
    }

    public Semaphore getAvailable() {
        return available;
    }
}
