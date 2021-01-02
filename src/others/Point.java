package others;

public class Point {
    private String name;
    private int x;
    private int y;

    @Override
    public String toString() {
        return getName();
    }

    public String getInfo() {
        return "Coordinates: " + this.x + " " + this.y;
    }

    public Point(String name) {
        this.name = name;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double distanceTo(Point p) {
        int x1 = this.x;
        int y1 = this.y;
        int x2 = p.x;
        int y2 = p.y;
        double distance;
        distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        return distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
