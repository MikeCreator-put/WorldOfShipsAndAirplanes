package vehicles;

import airports.Airport;
import java.util.List;
import enums.Weapons;

public class MilitaryAirplane extends Airplane {
    private Weapons weapons;

    public MilitaryAirplane(int x, int y, int id, int crewAmount, double currentFuel, double maxFuel, List<Airport> path, Weapons weapons, double speed){
        super(x, y, id, crewAmount, currentFuel, maxFuel, path, speed);
        this.weapons = weapons;
    }

    @Override
    public String toString(){
        return "Military Airplane, id: " + getId();
    }

    @Override
    public String getInfo(){
        return "Military Airplane, id: " + this.getId() +
                super.getInfo() +
                "\nArmament: " + this.getWeapons();
    }
    @Override
    public void callEmergency() {}

    public Weapons getWeapons() {
        return weapons;
    }

    public void setWeapons(Weapons weapons) {
        this.weapons = weapons;
    }
}