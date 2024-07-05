package Interfaces;
import BasicShapes.Point;
import BasicShapes.Rectangle;
import GameShapes.Ball;
import GameShapes.Velocity;
import biuoop.DrawSurface;

/**
 * @author Hadar Asheri
 * <p></p>
 * Collidable objects on screen.
 */
public interface Collidable {

    /**
     * Return the "collision shape" of the object.
     *
     * @return Rectangle object
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity. Notify all its listeners.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param collisionPoint Point object
     * @param currentVelocity Velocity object before the collision
     * @param hitter Ball object
     * @return Velocity object after the collision
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * Draw - draws the Collidable object on the given DrawSurface.
     *
     * @param d DrawSurface object
     */
    void drawOn(DrawSurface d);
}