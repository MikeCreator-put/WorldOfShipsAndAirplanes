package vehicles;

import airports.Airport;

import java.util.List;

public class MilitaryAirplane extends Airplane {
    private String weapons;

    public MilitaryAirplane(int x, int y, int id, int crewAmount, double currentFuel, double maxFuel, List<Class<Airport>> path, String weapons, double speed){
        super(x, y, id, crewAmount, currentFuel, maxFuel, path, speed);
        this.weapons = weapons;
    }

    public void print(){
        super.print();
        //cos
    }
    @Override
    public void callEmergency() {}

    public String getWeapons() {
        return weapons;
    }

    public void setWeapons(String weapons) {
        this.weapons = weapons;
    }
}