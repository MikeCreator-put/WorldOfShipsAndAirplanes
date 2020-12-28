package vehicles;

import enums.Companies;

public class CivilianShip extends Ship {
    private int currentPassengers;
    private int maxPassengers;
    private Companies company;

    public CivilianShip(int x, int y, int id, double maxSpeed, int currentPassengers, int maxPassengers, Companies company){
        super(x, y, id, maxSpeed);
        this.currentPassengers = currentPassengers;
        this.maxPassengers = maxPassengers;
        this.company = company;
    }

    public void print(){
        super.print();
        //cos
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
