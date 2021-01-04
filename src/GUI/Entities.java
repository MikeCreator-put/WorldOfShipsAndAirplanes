package GUI;

import airports.Airport;
import airports.CivilianAirport;
import airports.MilitaryAirport;
import others.Point;
import others.SeaPathNode;
import vehicles.Airplane;
import vehicles.CivilianAirplane;
import vehicles.CivilianShip;
import vehicles.Ship;

import java.util.ArrayList;
import java.util.List;

public class Entities {

    public Entities() {
        //initialize sea paths
        Point seaNode1 = new Point(714,396);
        Point seaNode2 = new Point(646, 338);
        Point seaNode3 = new Point(680,282);
        Point seaNode4 = new Point(589,278);
        Point seaNode5 = new Point(560, 378);
        Point seaNode6 = new Point(510,427);
        Point seaNode7 = new Point(425, 409);
        Point seaNode8 = new Point(405,305);
        Point seaNode9 = new Point(310,377);
        Point seaNode10 = new Point(316, 288);
        Point seaNode11 = new Point(310, 182);
        Point seaNode12 = new Point(338,107);
        Point seaNode13 = new Point(252, 148);
        Point seaNode14 = new Point(213, 187);
        Point seaNode15 = new Point(253,245);

        SeaPathNode seaPathNode1 = new SeaPathNode(seaNode1, new ArrayList<>(List.of(seaNode2, seaNode5)));
        SeaPathNode seaPathNode2 = new SeaPathNode(seaNode2, new ArrayList<>(List.of(seaNode1, seaNode3)));
        SeaPathNode seaPathNode3 = new SeaPathNode(seaNode3, new ArrayList<>(List.of(seaNode2, seaNode4)));
        SeaPathNode seaPathNode4 = new SeaPathNode(seaNode4, new ArrayList<>(List.of(seaNode3, seaNode5)));
        SeaPathNode seaPathNode5 = new SeaPathNode(seaNode5, new ArrayList<>(List.of(seaNode1, seaNode4)));
        SeaPathNode seaPathNode6 = new SeaPathNode(seaNode6, new ArrayList<>(List.of(seaNode5, seaNode7)));
        SeaPathNode seaPathNode7 = new SeaPathNode(seaNode7, new ArrayList<>(List.of(seaNode6, seaNode8, seaNode9)));
        SeaPathNode seaPathNode8 = new SeaPathNode(seaNode8, new ArrayList<>(List.of(seaNode7, seaNode10)));
        SeaPathNode seaPathNode9 = new SeaPathNode(seaNode9, new ArrayList<>(List.of(seaNode7, seaNode10)));
        SeaPathNode seaPathNode10 = new SeaPathNode(seaNode10, new ArrayList<>(List.of(seaNode9, seaNode8, seaNode11, seaNode15)));
        SeaPathNode seaPathNode11 = new SeaPathNode(seaNode11, new ArrayList<>(List.of(seaNode10, seaNode12)));
        SeaPathNode seaPathNode12 = new SeaPathNode(seaNode12, new ArrayList<>(List.of(seaNode11, seaNode13)));
        SeaPathNode seaPathNode13 = new SeaPathNode(seaNode13, new ArrayList<>(List.of(seaNode12, seaNode14)));
        SeaPathNode seaPathNode14 = new SeaPathNode(seaNode14, new ArrayList<>(List.of(seaNode13, seaNode15)));
        SeaPathNode seaPathNode15 = new SeaPathNode(seaNode15, new ArrayList<>(List.of(seaNode10, seaNode14)));

        this.seaPathNodes = new ArrayList<>(List.of(seaPathNode1, seaPathNode2, seaPathNode3, seaPathNode4, seaPathNode5, seaPathNode6, seaPathNode7, seaPathNode8, seaPathNode9, seaPathNode10, seaPathNode11, seaPathNode12, seaPathNode13, seaPathNode14, seaPathNode15));



        // initialize airports
        Airport tokyo = new CivilianAirport(811, 153, "Tokyo", 54, new ArrayList<>());
        Airport mexico = new CivilianAirport(111, 213, "Mexico City", 35, new ArrayList<>());
        Airport atlanta = new CivilianAirport(160, 160, "Atlanta", 78, new ArrayList<>());
        Airport buenos_aires = new CivilianAirport(239, 403, "Buenos Aires", 44, new ArrayList<>());
        Airport paris = new CivilianAirport(419, 109, "Paris", 38, new ArrayList<>());
        Airport dubai = new CivilianAirport(573, 193, "Dubai", 66, new ArrayList<>());
        Airport melbourne = new CivilianAirport(824,412, "Melbourne", 23, new ArrayList<>());
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


    private List<SeaPathNode> seaPathNodes;
    public List<SeaPathNode> getSeaPathNodes() {
        return seaPathNodes;
    }


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
}
