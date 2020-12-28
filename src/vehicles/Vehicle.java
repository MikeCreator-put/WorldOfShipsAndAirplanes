package vehicles;
import others.Point;

public abstract class Vehicle extends Point {
    private int id;
    private String type;

    public void print(){}
    public void moveTo(Point p){}
    public void avoidCollision(){}

    public Vehicle(int x, int y, int id) {
        super(x,y);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
