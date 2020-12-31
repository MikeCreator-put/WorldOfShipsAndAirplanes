package GUI;

import airports.Airport;
import airports.CivilianAirport;
import vehicles.Airplane;
import vehicles.CivilianAirplane;
import vehicles.CivilianShip;
import vehicles.Ship;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel {

    private List<Airport> listOfAirports = new ArrayList<>();
    private List<Airport> listOfCivilianAirports = new ArrayList<>();
    private List<Airport> listOfMilitaryAirports = new ArrayList<>();

    private List<Airplane> listofAirplanes = new ArrayList<>();
    private List<Airplane> listofCivilianAirplanes = new ArrayList<>();
    private List<Airplane> listOfMilitaryAirplanes = new ArrayList<>();

    private List<Ship> listOfShips = new ArrayList<>();
    private List<Ship> listofCivilianShips = new ArrayList<>();
    private List<Ship> listofMilitaryShips = new ArrayList<>();

    private int id = 0;

    public int getNewId(){
        id += 1;
        return id;
    }

    public void addAirport(Airport airport){
        listOfAirports.add(airport);
        if(airport instanceof CivilianAirport){
            listOfCivilianAirports.add(airport);
        }else{
            listOfMilitaryAirports.add(airport);
        }
    }

    public void removeAirport(Airport airport){
        listOfAirports.remove(airport);
        if(airport instanceof CivilianAirport) {
            listOfCivilianAirports.remove(airport);
        }else{
            listOfMilitaryAirports.remove(airport);
        }
    }

    public void addAirplane(Airplane airplane){
        listofAirplanes.add(airplane);
        if(airplane instanceof CivilianAirplane){
            listofCivilianAirplanes.add(airplane);
        }else{
            listOfMilitaryAirplanes.add(airplane);
        }
    }

    public void removeAirplane(Airplane airplane){
        listofAirplanes.remove(airplane);
        if(airplane instanceof CivilianAirplane){
            listofCivilianAirplanes.remove(airplane);
        }else{
            listOfMilitaryAirplanes.remove(airplane);
        }
    }

    public void addShip(Ship ship){
        listOfShips.add(ship);
        if(ship instanceof CivilianShip){
            listofCivilianShips.add(ship);
        }else{
            listofMilitaryShips.add(ship);
        }
    }

    public void removeShip(Ship ship){
        listOfShips.remove(ship);
        if(ship instanceof CivilianShip){
            listofCivilianShips.remove(ship);
        }else{
            listofMilitaryShips.remove(ship);
        }
    }

    public void setListOfAirports(List<Airport> listOfAirports) {
        this.listOfAirports = listOfAirports;
        for(Airport airport : listOfAirports){
            if(airport instanceof CivilianAirport){
                this.listOfCivilianAirports.add(airport);
            }else{
                this.listOfMilitaryAirports.add(airport);
            }
        }
    }

    public void setListofAirplanes(List<Airplane> listofAirplanes) {
        this.listofAirplanes = listofAirplanes;
        for(Airplane airplane : listofAirplanes){
            if(airplane instanceof CivilianAirplane){
                this.listofCivilianAirplanes.add(airplane);
            }else{
                this.listOfMilitaryAirplanes.add(airplane);
            }
        }
    }

    public void setListOfShips(List<Ship> listOfShips) {
        this.listOfShips = listOfShips;
        for(Ship ship : listOfShips){
            if(ship instanceof CivilianShip){
                this.listofCivilianShips.add(ship);
            }else{
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
