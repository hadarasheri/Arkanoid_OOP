package GameShapes;
import BasicShapes.Point;

/**
 * @author Hadar Asheri
 * <p></p>
 * Velocity specifies the change in position on the 'x' and the 'y' axes.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructor.
     *
     * @param dx double variable
     * @param dy double variable
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Constructs a velocity by calculating dx and dy from angle and speed:
     * turning them to radiant numbers and using cos(x) and sin(x) functions.
     *
     * @param angle double variable
     * @param speed double variable
     * @return Velocity object
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radAngle = Math.toRadians(angle);
        double dx = Math.sin(radAngle) * speed;
        double dy = Math.cos(radAngle) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Returns this dx.
     *
     * @return double variable
     */
    public double getDx() {
        return dx;
    }

    /**
     * Returns this dy.
     *
     * @return double variable
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Takes a point with position (x,y) and returns a new point
     * with position (x+dx, y+dy).
     *
     * @param p Point object
     * @return new Point object
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }
}