package vehicles;

import interfaces.PlaneFactory;
import enums.Weapons;

public class MilitaryShip extends Ship implements PlaneFactory {
    private Weapons weapons;

    public MilitaryShip(int x, int y, int id, double maxSpeed, Weapons weapons){
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
    public Weapons getWeapons() {
        return weapons;
    }

    public void setWeapons(Weapons weapons) {
        this.weapons = weapons;
    }


}
