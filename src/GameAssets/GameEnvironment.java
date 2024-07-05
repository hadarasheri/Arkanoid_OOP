package GameAssets;
import BasicShapes.Line;
import BasicShapes.Point;
import GameShapes.CollisionInfo;
import Interfaces.Collidable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines a Game Environment using a list of Collidable objects.
 */
public class GameEnvironment {
    private List<Collidable> objects;

    /**
     * Constructor - initiates the list of collidable objects.
     */
    public GameEnvironment() {
        objects = new ArrayList<Collidable>();
    }

    /**
     * Constructor - initiates the list with a given list of Collidables.
     *
     * @param c Collidable Array List
     */
    public GameEnvironment(ArrayList<Collidable> c) {
        this.objects = c;
    }

    /**
     * Adds the given collidable to the environment.
     *
     * @param c Collidable-type object
     */
    public void addCollidable(Collidable c) {
        this.objects.add(c);
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     *
     * @param trajectory Line object of the ball's movement
     * @return CollisionInfo
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closest = null;
        Collidable collisionObj = null;
        // go over the list of collidable objects:
        for (Collidable c: objects) {
            Point col = trajectory.closestIntersectionToStartOfLine(
                    c.getCollisionRectangle());
            // if one of the objects collides with the trajectory:
            if (col != null) {
                // save the closest collision point and object to the start:
                closest = closestPointToStart(trajectory, closest, col);
                if (closest == col) {
                    collisionObj = c;
                }
            }
        }
        // if there weren't collision objects - return null:
        if (collisionObj == null) {
            return null;
        }
        return new CollisionInfo(closest, collisionObj);
    }

    /**
     * Returns the closest Point to the start of the trajectory Line.
     *
     * @param trajectory Line object
     * @param a Point object
     * @param b Point object
     * @return closest Point object to the start
     */
   public Point closestPointToStart(Line trajectory, Point a, Point b) {
       // if one of the points is null - return the other:
       if (a == null) {
           return b;
       }
       if (b == null) {
           return a;
       }
       // make a list of the two Points and find the closest one to start:
       List<Point> collisions = new ArrayList<>();
       collisions.add(a);
       collisions.add(b);
       return trajectory.closestToStart(collisions);
    }

    /**
     * Returns this Game Environment.
     *
     * @return Game Environment object.
     */
    public ArrayList<Collidable> getGameEnvironment() {
        return (ArrayList<Collidable>) this.objects;
    }
}