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
    public String toString(){
        return "Military Ship, id: " + getId();
    }

    @Override
    public String getInfo(){
        return "Military Ship, id: " + this.getId() +
                super.getInfo() +
                "\nArmament: " + this.getWeapons();
    }
    @Override
    public void createPlane() {

    }

    public Weapons getWeapons() {
        return weapons;
    }

    public void setWeapons(Weapons weapons) {
        this.weapons = weapons;
    }


}
