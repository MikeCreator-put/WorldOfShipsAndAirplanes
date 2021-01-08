package others;

import java.util.List;
import java.util.concurrent.Semaphore;

public class SeaPathNode {

    private Point node;
    private List<SeaPathNode> connections;

    public SeaPathNode(SeaPathNode seaPathNode) {
        available = new Semaphore(1);
        node = seaPathNode.node;
    }

    @Override
    public String toString(){
        return String.valueOf(node.getX()) + " " + String.valueOf(node.getY());
    }

    private final Semaphore available;
    protected   Boolean used;

    public SeaPathNode(Point point, int permits) {
        this.node = point;
        this.used = false;
        this.available = new Semaphore(permits,true);
    }


    public SeaPathNode getSeaPathNode() {
        boolean acquired = available.tryAcquire();
        if(acquired){
            return getThis();
        }
        else{return null;}
    }

    protected synchronized SeaPathNode getThis(){
        if(!used){
            used = true;
            return this;
        }
        else{
            return null;
        }
    }

//    public void releaseSeaPathNode(){
//        if(this.markAsUnused()){
//            available.release();
//        }
//    }
//
//    protected synchronized Boolean markAsUnused(){
//        if(used){
//            used = false;
//            return true;
//        }else{return false;}
//    }

//    @Override
//    public String toString() {
//        return "node " + node.getX() + " " + node.getY() + " " + getConnections() + "\n";
//    }

    public Point getNode() {
        return node;
    }

    public void setNode(Point node) {
        this.node = node;
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
