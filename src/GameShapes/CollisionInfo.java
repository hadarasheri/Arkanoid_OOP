package GameShapes;
import BasicShapes.Point;
import Interfaces.Collidable;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines Collision Info object,
 * with a collision Point and a Collidable object.
 */
public class CollisionInfo {
    private Point colPoint;
    private Collidable colObject;

    /**
     * Constructor.
     *
     * @param collisionPoint Point of collision
     * @param collisionObject Collidable-type object
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.colPoint = collisionPoint;
        this.colObject = collisionObject;
    }

    /**
     * Returns the point at which the collision occurs.
     *
     * @return Point of collision
     */
    public Point collisionPoint() {
        return this.colPoint;
    }

    /**
     * Returns the collidable object involved in the collision.
     *
     * @return Collidable-type object
     */
    public Collidable collisionObject() {
        return this.colObject;
    }
}