package others;

import airports.Airport;
import airports.CivilianAirport;
import vehicles.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores information about all of the objects which are drawn on the map and displayed at control panel.
 */
public class Entities {

    /**
     * Creates new instance of Entities class with specified network of connections between airports.
     *
     * @param airPathsGraph Network of connections between airports.
     */
    public Entities(AirPathsGraph airPathsGraph) {
        SeaPathsGraph seaPathsGraph = new SeaPathsGraph();
        this.listOfSeaPathNodes = seaPathsGraph.getListOfNodes();
        this.airPathsGraph = airPathsGraph;
        setListOfAirports(airPathsGraph.getListOfAirports());
    }

    private List<Airport> listOfAirports = new ArrayList<>();
    private final List<Airport> listOfCivilianAirports = new ArrayList<>();
    private final List<Airport> listOfMilitaryAirports = new ArrayList<>();

    private final List<Airplane> listOfAirplanes = new ArrayList<>();
    private final List<Airplane> listOfCivilianAirplanes = new ArrayList<>();
    private final List<Airplane> listOfMilitaryAirplanes = new ArrayList<>();

    private final List<Ship> listOfShips = new ArrayList<>();
    private final List<Ship> listOfCivilianShips = new ArrayList<>();
    private final List<Ship> listOfMilitaryShips = new ArrayList<>();

    private final List<SeaPathNode> listOfSeaPathNodes;
    private final AirPathsGraph airPathsGraph;

    private int id = 0;

    /**
     * Adds a new airplane to suitable lists.
     *
     * @param airplane Airplane to be added.
     */
    public void addAirplane(Airplane airplane) {
        listOfAirplanes.add(airplane);
        if (airplane instanceof CivilianAirplane) {
            listOfCivilianAirplanes.add(airplane);
        } else { // it is a military airplane
            listOfMilitaryAirplanes.add(airplane);
        }
    }

    /**
     * Removes the airplane from every list in which it is stored in.
     *
     * @param airplane Airplane to be removed.
     */
    public void removeAirplane(Airplane airplane) {
        listOfAirplanes.remove(airplane);
        if (airplane instanceof CivilianAirplane) {
            listOfCivilianAirplanes.remove(airplane);
        } else { // it is a military airplane
            listOfMilitaryAirplanes.remove(airplane);
        }
    }

    /**
     * Adds a new ship to suitable lists.
     *
     * @param ship Ship to be added.
     */
    public void addShip(Ship ship) {
        listOfShips.add(ship);
        if (ship instanceof CivilianShip) {
            listOfCivilianShips.add(ship);
        } else {
            listOfMilitaryShips.add(ship);
        }
    }

    /**
     * Removes the ship from every list in which it is stored in.
     *
     * @param ship Ship to be removed.
     */
    public void removeShip(Ship ship) {
        listOfShips.remove(ship);
        if (ship instanceof CivilianShip) {
            listOfCivilianShips.remove(ship);
        } else {
            listOfMilitaryShips.remove(ship);
        }
    }

    private void setListOfAirports(List<Airport> listOfAirports) {
        this.listOfAirports = listOfAirports;
        for (Airport airport : listOfAirports) {
            if (airport instanceof CivilianAirport) {
                this.listOfCivilianAirports.add(airport);
            } else {
                this.listOfMilitaryAirports.add(airport);
            }
        }
    }

    /**
     * Gets an id that hasn't been used yet.
     *
     * @return Integer representing new id.
     */
    public int getNewId() {
        id += 1;
        return id;
    }

    /**
     * Gets the network of connections between airports.
     *
     * @return Graph representing network of connections between airports.
     */
    public AirPathsGraph getAirPathsGraph() {
        return airPathsGraph;
    }

    /**
     * Gets every airport.
     *
     * @return List including every airport.
     */
    public List<Airport> getListOfAirports() {
        return listOfAirports;
    }

    /**
     * Gets every civilian airport.
     *
     * @return List including every civilian airport.
     */
    public List<Airport> getListOfCivilianAirports() {
        return listOfCivilianAirports;
    }

    /**
     * Gets every military airport.
     *
     * @return List including every military airport.
     */
    public List<Airport> getListOfMilitaryAirports() {
        return listOfMilitaryAirports;
    }

    /**
     * Gets every airplane.
     *
     * @return List including every airplane.
     */
    public List<Airplane> getListOfAirplanes() {
        return listOfAirplanes;
    }

    /**
     * Gets every civilian airplane.
     *
     * @return List including every civilian airplane.
     */
    public List<Airplane> getListOfCivilianAirplanes() {
        return listOfCivilianAirplanes;
    }

    /**
     * Gets every military airplane.
     *
     * @return List including every military airplane.
     */
    public List<Airplane> getListOfMilitaryAirplanes() {
        return listOfMilitaryAirplanes;
    }

    /**
     * Gets every ship.
     *
     * @return List including every ship.
     */
    public List<Ship> getListOfShips() {
        return listOfShips;
    }

    /**
     * Gets every civilian ship.
     *
     * @return List including every civilian ship.
     */
    public List<Ship> getListOfCivilianShips() {
        return listOfCivilianShips;
    }

    /**
     * Gets every military ship.
     *
     * @return List including every military ship.
     */
    public List<Ship> getListOfMilitaryShips() {
        return listOfMilitaryShips;
    }

    /**
     * Gets every vertex from the graph representing network of sea connections.
     *
     * @return List including every vertex from the graph representing network of sea connections.
     */
    public List<SeaPathNode> getListOfSeaPathNodes() {
        return listOfSeaPathNodes;
    }
}
