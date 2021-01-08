package GUI;

import airports.Airport;
import airports.CivilianAirport;
import airports.MilitaryAirport;
import enums.Weapons;
import others.Point;
import others.SeaPathNode;
import others.SeaPathsGraph;
import vehicles.*;

import java.util.ArrayList;
import java.util.List;

public class Entities {

    public Entities() {
        SeaPathsGraph seaPathsGraph = new SeaPathsGraph();
        this.listOfSeaPathNodes =seaPathsGraph.getListOfNodes();
//        Ship s1 = new MilitaryShip(listOfSeaPathNodes.get(0).getNode().getX(),listOfSeaPathNodes.get(0).getNode().getY(),1,100, Weapons.NuclearWarhead, listOfSeaPathNodes.get(0), listOfSeaPathNodes);
//        Ship s2 = new MilitaryShip(listOfSeaPathNodes.get(1).getNode().getX(),listOfSeaPathNodes.get(1).getNode().getY(), 2, 100, Weapons.BrowningGun, listOfSeaPathNodes.get(1), listOfSeaPathNodes);
//        addShip(s1);
//        addShip(s2);
//        s1.run();
//        s2.run();

        // initialize airports
        Airport tokyo = new CivilianAirport(811, 153, "Tokyo", 54, new ArrayList<>());
        Airport mexico = new CivilianAirport(111, 213, "Mexico City", 35, new ArrayList<>());
        Airport atlanta = new CivilianAirport(160, 160, "Atlanta", 78, new ArrayList<>());
        Airport buenos_aires = new CivilianAirport(239, 403, "Buenos Aires", 44, new ArrayList<>());
        Airport paris = new CivilianAirport(419, 109, "Paris", 38, new ArrayList<>());
        Airport dubai = new CivilianAirport(573, 193, "Dubai", 66, new ArrayList<>());
        Airport melbourne = new CivilianAirport(824, 412, "Melbourne", 23, new ArrayList<>());
        Airport cape_town = new MilitaryAirport(463, 396, "Cape Town", 11, new ArrayList<>());
        Airport sao_louis = new MilitaryAirport(257, 285, "Sao Louis", 10, new ArrayList<>());
        Airport moscow = new MilitaryAirport(524, 88, "Moscow", 31, new ArrayList<>());
        setListOfAirports(new ArrayList<>(List.of(tokyo, mexico, atlanta, buenos_aires, paris, dubai, melbourne, cape_town, sao_louis, moscow)));
    }

    private List<Airport> listOfAirports = new ArrayList<>();
    private List<Airport> listOfCivilianAirports = new ArrayList<>();
    private List<Airport> listOfMilitaryAirports = new ArrayList<>();

    private List<Airplane> listofAirplanes = new ArrayList<>();
    private List<Airplane> listofCivilianAirplanes = new ArrayList<>();
    private List<Airplane> listOfMilitaryAirplanes = new ArrayList<>();

    private List<Ship> listOfShips = new ArrayList<>();
    private List<Ship> listofCivilianShips = new ArrayList<>();
    private List<Ship> listofMilitaryShips = new ArrayList<>();


    private List<SeaPathNode> listOfSeaPathNodes;


    private int id = 0;

    public int getNewId() {
        id += 1;
        return id;
    }

    public void addAirport(Airport airport) {
        listOfAirports.add(airport);
        if (airport instanceof CivilianAirport) {
            listOfCivilianAirports.add(airport);
        } else {
            listOfMilitaryAirports.add(airport);
        }
    }

    public void removeAirport(Airport airport) {
        listOfAirports.remove(airport);
        if (airport instanceof CivilianAirport) {
            listOfCivilianAirports.remove(airport);
        } else {
            listOfMilitaryAirports.remove(airport);
        }
    }

    public void addAirplane(Airplane airplane) {
        listofAirplanes.add(airplane);
        if (airplane instanceof CivilianAirplane) {
            listofCivilianAirplanes.add(airplane);
        } else {
            listOfMilitaryAirplanes.add(airplane);
        }
    }

    public void removeAirplane(Airplane airplane) {
        listofAirplanes.remove(airplane);
        if (airplane instanceof CivilianAirplane) {
            listofCivilianAirplanes.remove(airplane);
        } else {
            listOfMilitaryAirplanes.remove(airplane);
        }
    }

    public void addShip(Ship ship) {
        listOfShips.add(ship);
        if (ship instanceof CivilianShip) {
            listofCivilianShips.add(ship);
        } else {
            listofMilitaryShips.add(ship);
        }
    }

    public void removeShip(Ship ship) {
        listOfShips.remove(ship);
        if (ship instanceof CivilianShip) {
            listofCivilianShips.remove(ship);
        } else {
            listofMilitaryShips.remove(ship);
        }
    }

    public void setListOfAirports(List<Airport> listOfAirports) {
        this.listOfAirports = listOfAirports;
        for (Airport airport : listOfAirports) {
            if (airport instanceof CivilianAirport) {
                this.listOfCivilianAirports.add(airport);
            } else {
                this.listOfMilitaryAirports.add(airport);
            }
        }
    }

    public void setListofAirplanes(List<Airplane> listofAirplanes) {
        this.listofAirplanes = listofAirplanes;
        for (Airplane airplane : listofAirplanes) {
            if (airplane instanceof CivilianAirplane) {
                this.listofCivilianAirplanes.add(airplane);
            } else {
                this.listOfMilitaryAirplanes.add(airplane);
            }
        }
    }

    public void setListOfShips(List<Ship> listOfShips) {
        this.listOfShips = listOfShips;
        for (Ship ship : listOfShips) {
            if (ship instanceof CivilianShip) {
                this.listofCivilianShips.add(ship);
            } else {
                this.listofMilitaryShips.add(ship);
            }
        }
    }


    public List<Airport> getListOfAirports() {
        return listOfAirports;
    }

    public List<Airport> getListOfCivilianAirports() {
        return listOfCivilianAirports;
    }

    public List<Airport> getListOfMilitaryAirports() {
        return listOfMilitaryAirports;
    }

    public List<Airplane> getListofAirplanes() {
        return listofAirplanes;
    }

    public List<Airplane> getListofCivilianAirplanes() {
        return listofCivilianAirplanes;
    }

    public List<Airplane> getListOfMilitaryAirplanes() {
        return listOfMilitaryAirplanes;
    }

    public List<Ship> getListOfShips() {
        return listOfShips;
    }

    public List<Ship> getListofCivilianShips() {
        return listofCivilianShips;
    }

    public List<Ship> getListofMilitaryShips() {
        return listofMilitaryShips;
    }

    public List<SeaPathNode> getListOfSeaPathNodes() {
        return listOfSeaPathNodes;
    }

    public void setListOfSeaPathNodes(List<SeaPathNode> listOfSeaPathNodes) {
        this.listOfSeaPathNodes = listOfSeaPathNodes;
    }
}
