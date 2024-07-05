package BasicShapes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines object Rectangle with a Point object and two double variables.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Creates a new rectangle with location and width/height.
     *
     * @param upperLeft Point object
     * @param width double variable
     * @param height double variable
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Return a (possibly empty) List of intersection points
     * with the specified line.
     *
     * @param line Line object
     * @return Linked List of intersection Points, null if there are none
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        // creating a new linked list of Points:
        List<Point> pointList = new ArrayList<Point>();
        // adding the intersection points of the lines with to the list:
        Point intersection = line.intersectionWith(this.upperLine());
        if (intersection != null) {
            pointList.add(intersection);
        }
        intersection = line.intersectionWith(this.leftLine());
        if (intersection != null) {
            pointList.add(intersection);
        }
        intersection = line.intersectionWith(this.rightLine());
        if (intersection != null) {
            pointList.add(intersection);
        }
        intersection = line.intersectionWith(this.lowerLine());
        if (intersection != null) {
            pointList.add(intersection);
        }
        return pointList;
    }

    /**
     * Returns the upper-right point of the rectangle.
     *
     * @return Point object
     */
    public Point upperRight() {
        return new Point(this.upperLeft.getX() + this.width,
                this.upperLeft.getY());
    }
    /**
     * Returns the lower-left point of the rectangle.
     *
     * @return Point object
     */
    public Point lowerLeft() {
        return new Point(this.upperLeft.getX(),
                this.upperLeft.getY() + this.height);
    }
    /**
     * Returns the lower-right point of the rectangle.
     *
     * @return Point object
     */
    public Point lowerRight() {
        return new Point(this.upperRight().getX(), this.lowerLeft().getY());
    }

    /**
     * Returns the upper Line of this Rectangle.
     *
     * @return Line object
     */
    public Line upperLine() {
        return new Line(this.upperLeft, this.upperRight());
    }
    /**
     * Returns the lower Line of this Rectangle.
     *
     * @return Line object
     */
    public Line lowerLine() {
        return new Line(this.lowerLeft(), this.lowerRight());
    }
    /**
     * Returns the left Line of this Rectangle.
     *
     * @return Line object
     */
    public Line leftLine() {
        return new Line(this.upperLeft, this.lowerLeft());
    }
    /**
     * Returns the right Line of this Rectangle.
     *
     * @return Line object
     */
    public Line rightLine() {
        return new Line(this.upperRight(), this.lowerRight());
    }

    /**
     * Return the width of the rectangle.
     *
     * @return double variable
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Return the height of the rectangle.
     *
     * @return double variable
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upper-left point of the rectangle.
     *
     * @return Point object
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
}