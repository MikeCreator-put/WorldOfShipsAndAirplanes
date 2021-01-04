package others;

import java.util.ArrayList;
import java.util.List;

public class SeaPathNode {
    private Point node;
    private List<Point> connections = new ArrayList<>();
    public SeaPathNode(Point node, List<Point> connections){
        this.node = node;
        this.connections.addAll(connections);
    }

    @Override
    public String toString(){
        return "node " + node.getX() + " " + node.getY() + "\n";
    }

    public Point getNode() {
        return node;
    }

    public void setNode(Point node) {
        this.node = node;
    }

    public List<Point> getConnections() {
        return connections;
    }

    public void setConnections(List<Point> connections) {
        this.connections = connections;
    }
}
