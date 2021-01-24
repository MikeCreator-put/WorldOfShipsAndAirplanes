package others;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Represent a vertex of sea paths graph.
 */
public class SeaPathNode {

    private final Point node;
    private final Semaphore available;
    private List<SeaPathNode> connections;

    /**
     * Creates new vertex at specified point and given amount of permits.
     *
     * @param point   The vertexes point.
     * @param permits The vertexes amount of permits.
     */
    public SeaPathNode(Point point, int permits) {
        this.node = point;
        this.available = new Semaphore(permits, true);
    }

    /**
     * Gets the point stored in vertex.
     *
     * @return Point stored in vertex.
     */
    public Point getNode() {
        return node;
    }

    /**
     * Gets successors of the vertex.
     *
     * @return List of SeaPathNodes representing successors of the vertex.
     */
    public List<SeaPathNode> getConnections() {
        return connections;
    }

    /**
     * Gets the vertex's semaphore
     *
     * @return Semaphore whose permits represent the amount of available spots for airplanes.
     */
    public Semaphore getAvailable() {
        return available;
    }

    /**
     * Sets the vertex's successors.
     *
     * @param connections List of SeaPathNodes representing successors of the vertex.
     */
    public void setConnections(List<SeaPathNode> connections) {
        this.connections = connections;
    }
}
