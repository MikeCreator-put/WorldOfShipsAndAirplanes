package vehicles;

import others.Point;

public abstract class Vehicle extends Point {
    private int id;

    @Override
    public String getInfo() {
        return
                "\nCoordinates: " + this.getX() + " " + this.getY();
    }

    public void moveTo(Point p) {
    }

    public void avoidCollision() {
    }

    public Vehicle(int x, int y, int id) {
        super(x, y);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
