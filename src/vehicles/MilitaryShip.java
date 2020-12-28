package vehicles;

import airports.Airport;
import interfaces.PlaneFactory;

import java.util.List;

public class MilitaryShip extends Ship implements PlaneFactory {
    private String weapons;

    public MilitaryShip(int x, int y, int id, double maxSpeed, String weapons){
        super(x, y, id, maxSpeed);
        this.weapons = weapons;
    }
    @Override
    public void createPlane() {

    }

    public void print() {
        super.print();
        //cos
    }
    public String getWeapons() {
        return weapons;
    }

    public void setWeapons(String weapons) {
        this.weapons = weapons;
    }


}
