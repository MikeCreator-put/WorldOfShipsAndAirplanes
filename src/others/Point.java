package others;

public class Point {
    private String name;
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

    public int distanceTo(Point p){
        double x1 = this.x;
        double y1 = this.y;
        double x2 = p.x;
        double y2 = p.y;
        double distance;
        distance = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
        return (int)distance;
    }
}
