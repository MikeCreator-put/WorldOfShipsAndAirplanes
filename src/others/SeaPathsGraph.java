package others;

import java.util.ArrayList;
import java.util.List;

public class SeaPathsGraph {

    public SeaPathsGraph(){
        setNodes();
    }

    //testing semaphores
//    private final Point seaNode1 = new Point(300,300);
//    private final Point seaNode2 = new Point(300, 400);
//    private final Point seaNode3 = new Point(400,300);
//    private final Point seaNode4 = new Point(400, 400);
//    private final Point seaNode5 = new Point(350, 350);
//
//    private final SeaPathNode seaPathNode1 = new SeaPathNode(seaNode1);
//    private final SeaPathNode seaPathNode2 = new SeaPathNode(seaNode2);
//    private final SeaPathNode seaPathNode3 = new SeaPathNode(seaNode3);
//    private final SeaPathNode seaPathNode4 = new SeaPathNode(seaNode4);
//    private final SeaPathNode seaPathNode5 = new SeaPathNode(seaNode5);
//
//    public void setNodes() {
//        seaPathNode1.setConnections(new ArrayList<>(List.of(seaPathNode3, seaPathNode5)));
//        seaPathNode2.setConnections(new ArrayList<>(List.of(seaPathNode4, seaPathNode5)));
//        seaPathNode3.setConnections(new ArrayList<>(List.of(seaPathNode1, seaPathNode5)));
//        seaPathNode4.setConnections(new ArrayList<>(List.of(seaPathNode2, seaPathNode5)));
//        seaPathNode5.setConnections(new ArrayList<>(List.of(seaPathNode1, seaPathNode2, seaPathNode3, seaPathNode4)));
//    }
//
//    public List<SeaPathNode> getListOfNodes(){
//        return new ArrayList<>(List.of(seaPathNode1, seaPathNode2, seaPathNode3, seaPathNode4, seaPathNode5));
//    }


    private final SeaPathNode seaPathNode1 = new SeaPathNode(new Point(714, 396),1000);
    private final SeaPathNode seaPathNode2 = new SeaPathNode(new Point(646, 338), 1000);
    private final SeaPathNode seaPathNode3 = new SeaPathNode(new Point(680, 282), 1000);
    private final SeaPathNode seaPathNode4 = new SeaPathNode(new Point(589, 278), 1000);
    private final SeaPathNode seaPathNode5 = new SeaPathNode(new Point(560, 378), 1);
    private final SeaPathNode seaPathNode6 = new SeaPathNode(new Point(510, 427), 1000);
    private final SeaPathNode seaPathNode7 = new SeaPathNode(new Point(425, 409), 1);
    private final SeaPathNode seaPathNode8 = new SeaPathNode(new Point(405, 305), 1000);
    private final SeaPathNode seaPathNode9 = new SeaPathNode(new Point(310, 377),1000);
    private final SeaPathNode seaPathNode10 = new SeaPathNode(new Point(316, 288),1);
    private final SeaPathNode seaPathNode11 = new SeaPathNode(new Point(310, 182), 1000);
    private final SeaPathNode seaPathNode12 = new SeaPathNode(new Point(338, 107), 1000);
    private final SeaPathNode seaPathNode13 = new SeaPathNode(new Point(252, 148),1000);
    private final SeaPathNode seaPathNode14 = new SeaPathNode(new Point(213, 187),1000);
    private final SeaPathNode seaPathNode15 = new SeaPathNode(new Point(253, 245),1000);

    void setNodes() {
        seaPathNode1.setConnections(new ArrayList<>(List.of(seaPathNode2, seaPathNode5)));
        seaPathNode2.setConnections(new ArrayList<>(List.of(seaPathNode1, seaPathNode3)));
        seaPathNode3.setConnections(new ArrayList<>(List.of(seaPathNode2, seaPathNode4)));
        seaPathNode4.setConnections(new ArrayList<>(List.of(seaPathNode3, seaPathNode5)));
        seaPathNode5.setConnections(new ArrayList<>(List.of(seaPathNode1, seaPathNode4, seaPathNode6)));
        seaPathNode6.setConnections(new ArrayList<>(List.of(seaPathNode5, seaPathNode7)));
        seaPathNode7.setConnections(new ArrayList<>(List.of(seaPathNode6, seaPathNode8, seaPathNode9)));
        seaPathNode8.setConnections(new ArrayList<>(List.of(seaPathNode7, seaPathNode10)));
        seaPathNode9.setConnections(new ArrayList<>(List.of(seaPathNode7, seaPathNode10)));
        seaPathNode10.setConnections(new ArrayList<>(List.of(seaPathNode9, seaPathNode8, seaPathNode11, seaPathNode15)));
        seaPathNode11.setConnections(new ArrayList<>(List.of(seaPathNode10, seaPathNode12)));
        seaPathNode12.setConnections(new ArrayList<>(List.of(seaPathNode11, seaPathNode13)));
        seaPathNode13.setConnections(new ArrayList<>(List.of(seaPathNode12, seaPathNode14)));
        seaPathNode14.setConnections(new ArrayList<>(List.of(seaPathNode13, seaPathNode15)));
        seaPathNode15.setConnections(new ArrayList<>(List.of(seaPathNode10, seaPathNode14)));
    }
    public List<SeaPathNode> getListOfNodes(){
        return new ArrayList<>(List.of(seaPathNode1, seaPathNode2, seaPathNode3, seaPathNode4, seaPathNode5, seaPathNode6, seaPathNode7, seaPathNode8, seaPathNode9, seaPathNode10, seaPathNode11, seaPathNode12, seaPathNode13, seaPathNode14, seaPathNode15));
    }


}
