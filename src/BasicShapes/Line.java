package BasicShapes;
import java.util.List;
/**
 * @author Hadar Asheri
 * <p></p>
 * Defines the object Line with two Point objects.
 */
public class Line {
    private Point start;
    private Point end;
    static final double MID_DIVIDER = 2;
    static final double CMP_THRESHOLD = 0.00001;

    /**
     * Constructor - constructs a Line using two Point objects.
     *
     * @param start Point object
     * @param end Point object
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructor - constructs a Line using four double variables.
     *
     * @param x1 x value of Point 1
     * @param y1 y value of Point 1
     * @param x2 x value of Point 2
     * @param y2 y value of Point 2
     */
    public Line(double x1, double y1, double x2, double y2) {
        Point start = new Point(x1, y1);
        Point end = new Point(x2, y2);
        this.start = start;
        this.end = end;
    }

    /**
     * Length - returns the length of the line.
     *
     * @return the distance between this start and this end (double)
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Middle - returns the middle point of the line.
     *
     * @return Point object in the middle of this start and this end
     */
    public Point middle() {
        double x = start.getX();
        double x2 = end.getX();
        double y = start.getY();
        double y2 = end.getY();
        x += x2;
        y += y2;
        return new Point(x / MID_DIVIDER, y / MID_DIVIDER);
    }

    /**
     * Returns this start point of the line.
     *
     * @return Point start
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return Point end
     */
    public Point end() {
        return this.end;
    }

    /**
     * Checks whether the lines touch if they both have gradient = 0.
     *
     * @param other Line object
     * @return true if they touch, false otherwise
     */
    public boolean gradsAre0(Line other) {
        // if this line is parallel to the y-axis:
        if (Math.abs(this.start.getX() - this.end.getX()) < CMP_THRESHOLD) {
            // if the other line is parallel to the y-axis:
            if (Math.abs(other.start.getX() - other.end.getX())
                    < CMP_THRESHOLD) {
                // return true if the lines touch, false otherwise:
                return (isInLineRange(other.start) || isInLineRange(other.end)
                        || other.isInLineRange(this.start)
                        || other.isInLineRange(this.end));
            }
            // since the other line is parallel to the x-axis:
            Point inter = new Point(this.end.getX(), other.end.getY());
            return (isInLineRange(inter) && other.isInLineRange(inter));
        }
        // since this line is parallel to the x-axis:
        // if the other line is parallel to the x-axis:
        if (Math.abs(other.start.getY() - other.end.getY()) < CMP_THRESHOLD) {
            return (isInLineRange(other.start) || isInLineRange(other.end)
                    || other.isInLineRange(this.start)
                    || other.isInLineRange(this.end));
        }
        // since the other line is parallel to the y-axis:
        Point inter = new Point(other.end.getX(), this.end.getY());
        return (isInLineRange(inter) && other.isInLineRange(inter));
    }

    /**
     * Returns true if the lines intersect, false otherwise.
     * <p></p>
     * Finds the gradients and Y intercepts of the two lines and uses them to
     * compare the lines' equations and find the intersection Point,
     * then checks if it's in the range of the two lines.
     *
     * @param other Line object
     * @return boolean variable
     */
    public boolean isIntersecting(Line other) {
        // if the lines share a point:
        if (this.start.equals(other.start) || this.start.equals(other.end)
            || this.end.equals(other.start) || this.end.equals(other.end)) {
            return true;
        }
        double grad1 = findGradient();
        double grad2 = other.findGradient();
        double interY1 = findYIntercept(grad1);
        double interY2 = other.findYIntercept(grad2);
        // if both gradients are 0:
        if ((Math.abs(grad1) < CMP_THRESHOLD)
                && (Math.abs(grad2) < CMP_THRESHOLD)) {
            return gradsAre0(other);
        }
        // if one of the gradients is 0:
        if (Math.abs(grad1) < CMP_THRESHOLD) {
            if (Math.abs(this.start.getX() - this.end.getX()) < CMP_THRESHOLD) {
                return other.isInLineRange(this.end.getX(),
                        (this.end.getX() * grad2) + interY2);
            } else {
                return other.isInLineRange((this.end.getY() - interY2) / grad2,
                        this.end.getY());
            }
        }
        if (Math.abs(grad2) < CMP_THRESHOLD) {
            if (Math.abs(other.start.getX() - other.end.getX())
                    < CMP_THRESHOLD) {
                return isInLineRange(other.end.getX(),
                        (other.end.getX() * grad1) + interY1);
            } else {
                return isInLineRange((other.end.getY() - interY1) / grad1,
                        other.end.getY());
            }
        }
        // if the lines are parts of the same infinite line:
        if ((Math.abs(grad1 - grad2) < CMP_THRESHOLD)
                && (Math.abs(interY1 - interY2) < CMP_THRESHOLD)) {
            // returns true if this line is touching the other, false otherwise:
            return (isInLineRange(other.start.getX(), other.start.getY()))
                    || (isInLineRange(other.end.getX(), other.end.getY()));
        }
        double xInter = (interY2 - interY1) / (grad1 - grad2);
        double yInter = grad1 * xInter + interY1;
        return (isInLineRange(xInter, yInter))
                && (other.isInLineRange(xInter, yInter));
    }

    /**
     * Returns the intersection point if the lines intersect,
     * and null otherwise.
     * <p></p>
     * Calculates the intersection by finding the gradiant and Y intercept of
     * each line then using them to compare the infinite lines to check if
     * the intersection point found is in the range of the two lines.
     *
     * @param other Line object
     * @return Point object or null
     */
    public Point intersectionWith(Line other) {
        if (equals(other)) {
            return null;
        }
        double grad1 = findGradient();
        double grad2 = other.findGradient();
        // if the gradients are the same:
        if (Math.abs(grad1 - grad2) < CMP_THRESHOLD) {
            return ifEqualGrads(other, grad1);
        }
        // if the lines aren't parallel but share a point:
        if (this.start.equals(other.start) || this.start.equals(other.end)) {
            return this.start;
        }
        if (this.end.equals(other.start) || this.end.equals(other.end)) {
            return this.end;
        }
        double interY1 = findYIntercept(grad1);
        double interY2 = other.findYIntercept(grad2);
        Point inter;
        // if one of the gradients is 0:
        if (Math.abs(grad1) < CMP_THRESHOLD) {
            if (Math.abs(this.start.getX() - this.end.getX()) < CMP_THRESHOLD) {
                inter = new Point(this.end.getX(),
                        (this.end.getX() * grad2) + interY2);
            } else {
                inter = new Point((this.end.getY() - interY2) / grad2,
                        this.end.getY());
            }
            if (isInLineRange(inter) && other.isInLineRange(inter)) {
                return inter;
            } else {
                return null;
            }
        } else if (Math.abs(grad2) < CMP_THRESHOLD) {
            if (Math.abs(other.start.getX() - other.end.getX())
                    < CMP_THRESHOLD) {
                inter = new Point(other.end.getX(),
                        (other.end.getX() * grad1) + interY1);
            } else {
                inter = new Point((other.end.getY() - interY1) / grad1,
                        other.end.getY());
            }
            if (isInLineRange(inter) && other.isInLineRange(inter)) {
                return inter;
            } else {
                return null;
            }
        }
        double xInter = (interY2 - interY1) / (grad1 - grad2);
        double yInter = grad1 * xInter + interY1;
        if (isInLineRange(xInter, yInter)
                && other.isInLineRange(xInter, yInter)) {
            return new Point(xInter, yInter);
        }
        return null;
    }

    /**
     * Deals with the extreme case of equal gradients when searching the
     * intersection point of two lines.
     * <p></p>
     * Returns a Point object if the lines share only it, or if both lines are
     * parallel to the opposite axes and the intersection is in their range.
     * Otherwise - null.
     *
     * @param other Line object
     * @param grad one of the equal gradients, double
     * @return Point of intersection
     */
    public Point ifEqualGrads(Line other, double grad) {
        // if the lines share only one point - return it:
        if (!other.isInLineRange(this.end)) {
            if ((this.start.equals(other.start) && !isInLineRange(other.end))
                    || (this.start.equals(other.end)
                    && !isInLineRange(other.start))) {
                return this.start;
            }
        }
        if (!other.isInLineRange(this.start)) {
            if ((this.end.equals(other.start) && !isInLineRange(other.end))
                    || (this.end.equals(other.end)
                    && !isInLineRange(other.start))) {
                return this.end;
            }
        }
        // if the lines don't share a point but their gradients are 0:
        if (Math.abs(grad) < CMP_THRESHOLD) {
            // if this line is parallel to the y-axis:
            if (Math.abs(this.start.getX() - this.end.getX()) < CMP_THRESHOLD) {
                // if the other line is parallel to the x-axis:
                if (Math.abs(other.start.getY() - other.end.getY())
                        < CMP_THRESHOLD) {
                    Point inter = new Point(this.end.getX(), other.end.getY());
                    // returning the intersection if it's in range:
                    if (isInLineRange(inter) && other.isInLineRange(inter)) {
                        return inter;
                    }
                }
            // if this line is parallel to the x-axis:
            } else {
                // if the other line is parallel to the y-axis:
                if (Math.abs(other.start.getX() - other.end.getX())
                        < CMP_THRESHOLD) {
                    Point inter = new Point(other.end.getX(), this.end.getY());
                    // returning the intersection if it's in range:
                    if (isInLineRange(inter) && other.isInLineRange(inter)) {
                        return inter;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the gradient of the line,
     * calculating it with the start and end points.
     *
     * @return double variable
     */
    public double findGradient() {
        double y = this.start.getY() - this.end.getY();
        double x = this.start.getX() - this.end.getX();
        // if the gradient is not 0:
        if (x != 0) {
            return y / x;
        }
        // if the line is parallel to y=0:
        return 0;
    }

    /**
     * Returns the Y intercept value of the line,
     * by using the start and end points and the gradient.
     *
     * @param gradient double variable
     * @return double variable
     */
    public double findYIntercept(double gradient) {
        return this.start.getY() - (gradient * this.start.getX());
    }

    /**
     * Returns true if both of the given values are between this line's points,
     * false otherwise.
     *
     * @param x double variable
     * @param y double variable
     * @return boolean variable
     */
    public boolean isInLineRange(double x, double y) {
        double max = Math.max(this.start.getX(), this.end.getX());
        double min = Math.min(this.start.getX(), this.end.getX());
        if ((x > max) || (x < min)) {
            return false;
        }
        max = Math.max(this.start.getY(), this.end.getY());
        min = Math.min(this.start.getY(), this.end.getY());
        return ((y <= max) && (y >= min));
    }

    /**
     * Returns true if the given Point's values are between this line's points,
     * false otherwise.
     *
     * @param p Point object
     * @return boolean variable
     */
    public boolean isInLineRange(Point p) {
        if (p.equals(this.start) || p.equals(this.end)) {
            return true;
        }
        return isInLineRange(p.getX(), p.getY());
    }

    /**
     * Equals - return true is the lines are equal, false otherwise.
     *
     * @param other Line object
     * @return boolean variable
     */
    public boolean equals(Line other) {
        return (this.start.equals(other.start) && this.end.equals(other.end))
                ||
                (this.start.equals(other.end) && this.end.equals(other.start));
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     *
     * @param rect Rectangle object
     * @return Point of the closest intersection, null if there isn't one
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // get the list of intersection points:
        List<Point> pointList = rect.intersectionPoints(this);
        if (pointList.isEmpty()) {
            return null;
        }
        // return the closest one:
        return closestToStart(pointList);
    }

    /**
     * Returns the closest point in the list to this start.
     *
     * @param pointList List of Points
     * @return Point object
     */
    public Point closestToStart(java.util.List<Point> pointList) {
        // if there are no Points in the list - return null:
        if (pointList.isEmpty()) {
            return null;
        }
        // if there is one point in the list - return it:
        if (pointList.size() == 1) {
            return pointList.get(0);
        }
        // find which of the first two Points in the list is closest to start:
        Point closest;
        double currentDistance = this.start.distance(pointList.get(0));
        double smallestDistance = this.start.distance(pointList.get(1));
        smallestDistance = Math.min(currentDistance, smallestDistance);
        if (Math.abs(smallestDistance - currentDistance) < CMP_THRESHOLD) {
            closest = pointList.get(0);
        } else {
            closest = pointList.get(1);
        }
        // if there are more Points - check if one of them is closer:
        for (int i = 2; i < pointList.size(); i++) {
            currentDistance = this.start.distance(pointList.get(i));
            // if one is closer to start than the previous points:
            if (currentDistance < smallestDistance) {
                // save it and the distance:
                smallestDistance = currentDistance;
                closest = pointList.get(i);
            }
        }
        return closest;
    }
}