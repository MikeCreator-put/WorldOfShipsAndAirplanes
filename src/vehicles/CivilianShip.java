package vehicles;

import GUI.MapController;
import enums.Companies;
import others.SeaPathNode;

import java.util.List;

public class CivilianShip extends Ship {
    private int currentPassengers;
    private int maxPassengers;
    private Companies company;

    public CivilianShip(double x, double y, int id, double maxSpeed, int currentPassengers, int maxPassengers, Companies company, SeaPathNode startingLocationNode) {
        super(x, y, id, maxSpeed, startingLocationNode);
        this.currentPassengers = currentPassengers;
        this.maxPassengers = maxPassengers;
        this.company = company;
    }

    @Override
    public String toString() {
        return "Civilian Ship, id: " + getId();
    }

    @Override
    public String getInfo() {
        return "Civilian Ship, id: " + this.getId() +
                super.getInfo() +
                "\nCurrent amount of passengers: " + this.getCurrentPassengers() +
                "\nMaximum amount of passengers: " + this.getMaxPassengers() +
                "\nCompany name: " + this.getCompany();
    }


    public int getCurrentPassengers() {
        return currentPassengers;
    }

    public void setCurrentPassengers(int currentPassengers) {
        this.currentPassengers = currentPassengers;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public Companies getCompany() {
        return company;
    }

    public void setCompany(Companies company) {
        this.company = company;
    }
}
