package others;

import airports.Airport;
import airports.CivilianAirport;
import airports.CrossingAirport;
import airports.MilitaryAirport;

import java.util.*;

//Dijkstra algorithm from https://stackoverflow.com/questions/17480022/java-find-shortest-path-between-2-points-in-a-distance-weighted-map
//Classes modified for purposes of my project

/**
 * Represents the vertex of the air paths graph, where the vertex is an airport.
 */
class Vertex implements Comparable<Vertex> {
    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public Airport airport;

    /**
     * Creates new vertex representing a given airport.
     *
     * @param airport Airport to be represented by the vertex.
     */
    public Vertex(Airport airport) {
        this.airport = airport;
        this.name = airport.getName();
    }

    /**
     * Compares the minDistance variable of two vertexes.
     * First one is taken from the vertex this method is called upon and the second one taken from the vertex passed as parameter.
     *
     * @param other Vertex whose minDistance value will be compared.
     * @return Integer representing information which minDistance is greater.
     */
    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }

    /**
     * Gets the airport of a vertex.
     *
     * @return Airport which is represented by the vertex.
     */
    public Airport getAirport() {
        return airport;
    }
}

/**
 * Represents a connection between two vertexes (airports).
 */
class Edge {
    public final Vertex target;
    public final double weight;
    public Boolean occupied = false;
    public Boolean oneWay;
    public Boolean isStart = false;
    public int airplanesOnIt = 0;

    /**
     * Creates a bidirectional connection to a given vertexes (airport).
     *
     * @param argTarget Destination of the path.
     * @param argWeight Distance needed to travelled in order to reach the destination.
     */
    public Edge(Vertex argTarget, double argWeight) {
        this.target = argTarget;
        this.weight = argWeight;
        this.oneWay = false;
    }

    /**
     * Creates a connection to a given vertex (airport) which can be either bidirectional or one-way.
     *
     * @param argTarget Destination of the path.
     * @param argWeight Distance needed to be travelled in order to reach the destination.
     * @param oneWay    Boolean variable which decides if the path is bidirectional or one-way.
     */
    public Edge(Vertex argTarget, double argWeight, Boolean oneWay) {
        this.target = argTarget;
        this.weight = argWeight;
        this.oneWay = oneWay;
    }

    /**
     * Gets the destination of the path.
     *
     * @return The vertex representing destination of the path.
     */
    public Vertex getTarget() {
        return target;
    }
}

/**
 * Represents the network of connections between airports.
 */
public class AirPathsGraph {
    private final Airport tokyo = new CivilianAirport(811, 153, "Tokyo", 54);
    private final Airport mexico = new CivilianAirport(111, 213, "Mexico City", 3);
    private final Airport atlanta = new CivilianAirport(160, 160, "Atlanta", 78);
    private final Airport buenos_aires = new CivilianAirport(239, 403, "Buenos Aires", 44);
    private final Airport paris = new CivilianAirport(419, 109, "Paris", 38);
    private final Airport dubai = new CivilianAirport(573, 193, "Dubai", 66);
    private final Airport melbourne = new CivilianAirport(824, 412, "Melbourne", 23);
    private final Airport cape_town = new MilitaryAirport(463, 396, "Cape Town", 8);
    private final Airport sao_louis = new MilitaryAirport(257, 285, "Sao Louis", 10);
    private final Airport moscow = new MilitaryAirport(524, 88, "Moscow", 31);
    private final Airport crossing1 = new CrossingAirport(285, 183, "crossing1", 1);
    private final Airport crossing2 = new CrossingAirport(401, 285, "crossing2", 1);
    private final Airport crossing3 = new CrossingAirport(641, 214, "crossing3", 1);

    private final Vertex tokyoV = new Vertex(tokyo);
    private final Vertex mexicoV = new Vertex(mexico);
    private final Vertex atlantaV = new Vertex(atlanta);
    private final Vertex buenos_airesV = new Vertex(buenos_aires);
    private final Vertex parisV = new Vertex(paris);
    private final Vertex dubaiV = new Vertex(dubai);
    private final Vertex melbourneV = new Vertex(melbourne);
    private final Vertex cape_townV = new Vertex(cape_town);
    private final Vertex sao_louisV = new Vertex(sao_louis);
    private final Vertex moscowV = new Vertex(moscow);
    private final Vertex crossing1V = new Vertex(crossing1);
    private final Vertex crossing2V = new Vertex(crossing2);
    private final Vertex crossing3V = new Vertex(crossing3);

    private final Map<Airport, Vertex> mapAirportsToVertexes;
    private final Map<Airport, List<Airport>> adjList;
    private final Map<Airport, List<Boolean>> drawingHelperList;

    private final List<Vertex> listOfVertexes = new ArrayList<>(List.of(tokyoV, mexicoV, atlantaV, buenos_airesV, parisV, dubaiV, melbourneV, cape_townV, sao_louisV, moscowV, crossing1V, crossing2V, crossing3V));

    /**
     * Creates new network of connections between airports.
     */
    public AirPathsGraph() {
        tokyoV.adjacencies = new Edge[]{
                new Edge(melbourneV, tokyo.distanceTo(melbourne), true),
                new Edge(crossing3V, tokyo.distanceTo(crossing3))};
        mexicoV.adjacencies = new Edge[]{
                new Edge(atlantaV, mexico.distanceTo(atlanta)),
                new Edge(crossing1V, mexico.distanceTo(crossing1)),
                new Edge(sao_louisV, mexico.distanceTo(sao_louis), true)};
        atlantaV.adjacencies = new Edge[]{
                new Edge(mexicoV, atlanta.distanceTo(mexico)),
                new Edge(crossing1V, atlanta.distanceTo(crossing1))};
        buenos_airesV.adjacencies = new Edge[]{
                new Edge(cape_townV, buenos_aires.distanceTo(cape_town)),
                new Edge(crossing2V, buenos_aires.distanceTo(crossing2)),
                new Edge(sao_louisV, buenos_aires.distanceTo(sao_louis))};
        parisV.adjacencies = new Edge[]{
                new Edge(crossing1V, paris.distanceTo(crossing1)),
                new Edge(dubaiV, paris.distanceTo(dubai)),
                new Edge(moscowV, paris.distanceTo(moscow))};
        dubaiV.adjacencies = new Edge[]{
                new Edge(crossing2V, dubai.distanceTo(crossing2)),
                new Edge(parisV, dubai.distanceTo(paris)),
                new Edge(moscowV, dubai.distanceTo(moscow), true),
                new Edge(crossing3V, dubai.distanceTo(crossing3))};
        melbourneV.adjacencies = new Edge[]{
                new Edge(tokyoV, melbourne.distanceTo(tokyo), true),
                new Edge(crossing3V, melbourne.distanceTo(crossing3))};
        cape_townV.adjacencies = new Edge[]{
                new Edge(buenos_airesV, cape_town.distanceTo(buenos_aires)),
                new Edge(crossing2V, cape_town.distanceTo(crossing2))};
        sao_louisV.adjacencies = new Edge[]{
                new Edge(buenos_airesV, sao_louis.distanceTo(buenos_aires)),
                new Edge(crossing2V, sao_louis.distanceTo(crossing2)),
                new Edge(mexicoV, sao_louis.distanceTo(mexico), true)};
        moscowV.adjacencies = new Edge[]{
                new Edge(parisV, moscow.distanceTo(paris)),
                new Edge(dubaiV, moscow.distanceTo(dubai), true)};
        crossing1V.adjacencies = new Edge[]{
                new Edge(atlantaV, crossing1.distanceTo(atlanta)),
                new Edge(mexicoV, crossing1.distanceTo(mexico)),
                new Edge(crossing2V, crossing1.distanceTo(crossing2)),
                new Edge(parisV, crossing1.distanceTo(paris))};
        crossing2V.adjacencies = new Edge[]{
                new Edge(cape_townV, crossing2.distanceTo(cape_town)),
                new Edge(dubaiV, crossing2.distanceTo(dubai)),
                new Edge(crossing1V, crossing2.distanceTo(crossing1)),
                new Edge(sao_louisV, crossing2.distanceTo(sao_louis)),
                new Edge(buenos_airesV, crossing2.distanceTo(buenos_aires))};
        crossing3V.adjacencies = new Edge[]{
                new Edge(dubaiV, crossing3.distanceTo(dubai)),
                new Edge(tokyoV, crossing3.distanceTo(tokyo)),
                new Edge(melbourneV, crossing3.distanceTo(melbourne))};

        adjList = new HashMap<>();
        drawingHelperList = new HashMap<>();

        for (Vertex vertex : listOfVertexes) {
            adjList.putIfAbsent(vertex.getAirport(), new ArrayList<>());
            drawingHelperList.putIfAbsent(vertex.getAirport(), new ArrayList<>());
        }

        for (Vertex start : listOfVertexes) {
            for (Edge destination : start.adjacencies) {
                adjList.get(start.getAirport()).add(destination.getTarget().getAirport());
                drawingHelperList.get(start.getAirport()).add(destination.oneWay);
            }
        }

        mapAirportsToVertexes = new HashMap<>();
        mapAirportsToVertexes.put(tokyo, tokyoV);
        mapAirportsToVertexes.put(mexico, mexicoV);
        mapAirportsToVertexes.put(atlanta, atlantaV);
        mapAirportsToVertexes.put(buenos_aires, buenos_airesV);
        mapAirportsToVertexes.put(paris, parisV);
        mapAirportsToVertexes.put(dubai, dubaiV);
        mapAirportsToVertexes.put(melbourne, melbourneV);
        mapAirportsToVertexes.put(cape_town, cape_townV);
        mapAirportsToVertexes.put(sao_louis, sao_louisV);
        mapAirportsToVertexes.put(moscow, moscowV);
        mapAirportsToVertexes.put(crossing1, crossing1V);
        mapAirportsToVertexes.put(crossing2, crossing2V);
        mapAirportsToVertexes.put(crossing3, crossing3V);
    }

    /**
     * Determines if an airplane can enter the path from one airport to another.
     *
     * @param start The airport from which the airplane departs.
     * @param end   The airport to which the airplane wants to travel.
     * @return Boolean value representing the allowance to either enter the path or not.
     */
    public Boolean letAirplaneEnterPath(Airport start, Airport end) {
        Edge edgeFromStart = null;
        Edge edgeFromEnd = null;
        Vertex startV = mapAirportsToVertexes.get(start);
        Vertex endV = mapAirportsToVertexes.get(end);
        for (Edge edge : startV.adjacencies) {
            if (edge.getTarget() == endV) {
                edgeFromStart = edge;
            }
        }
        for (Edge edge : endV.adjacencies) {
            if (edge.getTarget() == startV) {
                edgeFromEnd = edge;
            }
        }
        assert edgeFromStart != null : "Start Edge not found";
        assert edgeFromEnd != null : "End Edge not found";
        if (edgeFromStart.oneWay) {
            if (!edgeFromStart.occupied) {
                edgeFromStart.isStart = true;
                edgeFromEnd.occupied = true;
                edgeFromStart.occupied = true;
                edgeFromStart.airplanesOnIt += 1;
                edgeFromEnd.airplanesOnIt += 1;
                return true;
            } else {
                if (edgeFromStart.isStart) {
                    edgeFromStart.airplanesOnIt += 1;
                    edgeFromEnd.airplanesOnIt += 1;
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return true;
        }
    }

    /**
     * Modifies certain fields to inform the program that an airplane has already left the path.
     *
     * @param start The airport from which the airplane departed.
     * @param end   The airport to which the airplane arrived.
     */
    public void releasePath(Airport start, Airport end) {
        Edge edgeFromStart = null;
        Edge edgeFromEnd = null;
        Vertex startV = mapAirportsToVertexes.get(start);
        Vertex endV = mapAirportsToVertexes.get(end);
        for (Edge edge : startV.adjacencies) {
            if (edge.getTarget() == endV) {
                edgeFromStart = edge;
            }
        }
        for (Edge edge : endV.adjacencies) {
            if (edge.getTarget() == startV) {
                edgeFromEnd = edge;
            }
        }
        assert edgeFromStart != null : "Start Edge not found";
        assert edgeFromEnd != null : "End Edge not found";
        edgeFromStart.airplanesOnIt -= 1;
        edgeFromEnd.airplanesOnIt -= 1;
        if (edgeFromStart.oneWay) {
            if (edgeFromStart.airplanesOnIt == 0) {
                edgeFromStart.occupied = false;
                edgeFromEnd.occupied = false;
                edgeFromStart.isStart = false;
                edgeFromStart.isStart = false;
            }
        }
    }

    private void computePaths(Vertex source) {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);

                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    private List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    /**
     * Computes the shortest path between starting airport and chosen destination.
     *
     * @param start       The airport which the airplane departs.
     * @param destination The airport to which the airplane wants to fly.
     * @return List of airports representing the path between start and destination including both of them.
     * @see <a href="https://stackoverflow.com/questions/17480022/java-find-shortest-path-between-2-points-in-a-distance-weighted-map">https://stackoverflow.com/questions/17480022/java-find-shortest-path-between-2-points-in-a-distance-weighted-map</a>
     */
    public List<Airport> getPathDijkstra(Airport start, Airport destination) {
        computePaths(mapAirportsToVertexes.get(start));
        List<Vertex> path = getShortestPathTo(mapAirportsToVertexes.get(destination));
        List<Airport> pathOfAirports = new ArrayList<>();
        for (Vertex vertex : path) {
            Airport airport = vertex.getAirport();
            pathOfAirports.add(airport);
        }
        for (Vertex vertex : listOfVertexes) {
            vertex.minDistance = Double.POSITIVE_INFINITY;
            vertex.previous = null;
        }
        return pathOfAirports;
    }

    /**
     * Gets all of the airports from the network of connections.
     *
     * @return List including every airport from the network of connections.
     */
    public List<Airport> getListOfAirports() {
        return new ArrayList<>(List.of(tokyo, mexico, atlanta, buenos_aires, paris, dubai, melbourne, cape_town, sao_louis, moscow));
    }

    /**
     * Gets an adjacency list of the graph (network of connections).
     *
     * @return Map of airports (vertexes) and list of their successors.
     */
    public Map<Airport, List<Airport>> getAdjList() {
        return adjList;
    }

    /**
     * Gets a different type of adjacency list, corresponding to the original one, which is a helping tool to later draw one-way paths in different color than the bidirectional ones.
     *
     * @return Map of airports and list of Boolean values representing if a path to their successor is bidirectional or one-way.
     */
    public Map<Airport, List<Boolean>> getDrawingHelperList() {
        return drawingHelperList;
    }
}