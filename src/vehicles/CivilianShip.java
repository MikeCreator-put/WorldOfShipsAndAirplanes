package vehicles;

public class CivilianShip extends Ship {
    private int currentPassengers;
    private int maxPassengers;
    private String company;

    public CivilianShip(int x, int y, int id, double maxSpeed, int currentPassengers, int maxPassengers, String company){
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
