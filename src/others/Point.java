package others;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Point {
    private String name;
    //private DoubleProperty x = new SimpleDoubleProperty(this, "x");
    //private DoubleProperty y = new SimpleDoubleProperty(this, "y");
    private double x;
    private double y;

    @Override
    public String toString() {
        return getName();
    }

    public String getInfo() {
        return "\nCoordinates: " + String.format("%,.0f", this.getX()) + " " + String.format("%,.0f", this.getY());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point(double x, double y) {
        //this.setX(x);
        //this.setY(y);
        this.x = x;
        this.y = y;
    }

    public Point(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public double getX() {
//        return x.get();
//    }

//    public DoubleProperty xProperty() {
//        return x;
//    }
//
//    public void setX(double x) {
//        this.x.set(x);
//    }
//
//    public double getY() {
//        return y.get();
//    }
//
//    public DoubleProperty yProperty() {
//        return y;
//    }
//
//    public void setY(double y) {
//        this.y.set(y);
//    }
}
