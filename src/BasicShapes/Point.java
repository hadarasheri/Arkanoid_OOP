package BasicShapes;
/**
 * @author Hadar Asheri
 * <p></p>
 * Defines object Point with two double variables.
 */
public class Point {
    private double x;
    private double y;
    static final double CMP_THRESHOLD = 0.00001;

    /**
     * Constructor.
     *
     * @param x double variable
     * @param y double variable
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Distance - returns the distance of this point to the other point.
     *
     * @param other Line object
     * @return double variable
     */
    public double distance(Point other) {
        double disX = (this.x - other.x) * (this.x - other.x);
        double disY = (this.y - other.y) * (this.y - other.y);
        return Math.sqrt(disX + disY);
    }

    /**
     * Equals - returns true if the points are equal, false otherwise.
     *
     * @param other Line object
     * @return boolean variable
     */
    public boolean equals(Point other) {
        return (Math.abs(this.x - other.x) < CMP_THRESHOLD)
                && (Math.abs(this.y - other.y) < CMP_THRESHOLD);
    }

    /**
     * Returns the x value of this point.
     *
     * @return this x (double)
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the y value of this point.
     *
     * @return this y (double)
     */
    public double getY() {
        return this.y;
    }
}