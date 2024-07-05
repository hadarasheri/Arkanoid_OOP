package GameShapes;
import BasicShapes.Line;
import BasicShapes.Point;
import BasicShapes.Rectangle;
import GameAssets.GameLevel;
import GameAssets.GameEnvironment;
import Interfaces.Collidable;
import Interfaces.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines object Ball with a Point, an int for the radius, a Color variable,
 * a velocity and a game environment.
 */
public class Ball implements Sprite {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity vel;
    private GameEnvironment gameEnv;
    static final double SCREEN_WIDTH = 800;
    static final int FRAME = 5;

    /**
     * Constructor - constructs a Ball using a Point for the center.
     *
     * @param center Point object
     * @param r int variable
     * @param color java.awt.Color variable
     * @param gameEnv Game Environment object
     */
    public Ball(Point center, int r, java.awt.Color color,
                GameEnvironment gameEnv) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.vel = new Velocity(0, 0);
        this.gameEnv = gameEnv;
    }

    /**
     * Constructor - constructs a Ball using two int values for the center.
     *
     * @param x int variable
     * @param y int variable
     * @param r int variable
     * @param color java.awt.Color variable
     * @param gameEnv GameEnvironment object
     */
    public Ball(double x, double y, int r, java.awt.Color color,
                GameEnvironment gameEnv) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
        this.vel = new Velocity(0, 0);
        this.gameEnv = gameEnv;
    }

    /**
     * Returns the x value of this center.
     *
     * @return int variable
     */
    public int getX() {
        return (int) this.center.getX();
    }
    /**
     * Returns the y value of this center.
     *
     * @return int variable
     */
    public int getY() {
        return (int) this.center.getY();
    }
    /**
     * Returns the radius of the ball.
     *
     * @return int variable
     */
    public int getSize() {
        return this.r;
    }
    /**
     * Returns the color of this ball.
     *
     * @return java.awt.Color variable
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // fill the circle:
        d.setColor(this.color);
        d.fillCircle((int) this.center.getX(),
                (int) this.center.getY(), r);
        // draw the circle:
        d.setColor(Color.black);
        d.drawCircle((int) this.center.getX(),
                (int) this.center.getY(), r);
    }

    /**
     * Sets this velocity by the given Velocity parameter.
     *
     * @param v Velocity object
     */
    public void setVelocity(Velocity v) {
        this.vel = v;
    }

    /**
     * Sets this velocity by the given Velocity values.
     *
     * @param dx double variable
     * @param dy double variable
     */
    public void setVelocity(double dx, double dy) {
        this.vel = new Velocity(dx, dy);
    }

    /**
     * Returns the Velocity of this ball.
     *
     * @return Velocity object
     */
    public Velocity getVelocity() {
        return this.vel;
    }

    /**
     * Changes the values of the center of the Ball according to the Velocity,
     * changing the velocity if the Ball is about to collide with a block
     * from the GameEnvironment.
     */
    public void moveOneStep() {
        moveOutOfRectangle();
        // the line from the current ball location to the location + velocity:
        Point movement = this.getVelocity().applyToPoint(this.center);
        Line trajectory = new Line(this.center, movement);
        // get the closest collision point to the current ball location:
        CollisionInfo closestCol = this.gameEnv.getClosestCollision(trajectory);
        // if there is a collision on the next step:
        if (closestCol != null) {
            // move the ball to just before the collision:
            this.center = rightLocation(closestCol);
            // change the velocity according to the collision:
            this.vel = closestCol.collisionObject().
                    hit(this, closestCol.collisionPoint(),
                            this.getVelocity());
        } else {
            // move the ball according to the velocity:
            this.center = this.getVelocity().applyToPoint(this.center);
        }
    }

    /**
     * Returns the right location of the ball instead of colliding with
     * an object, according to the collision info.
     *
     * @param colInfo Collision Info object
     * @return Point object
     */
    public Point rightLocation(CollisionInfo colInfo) {
        // get the values of the point of collision:
        double x = colInfo.collisionPoint().getX();
        double y = colInfo.collisionPoint().getY();
        // get the lines of the collision object:
        Line upperLine = colInfo.collisionObject().
                getCollisionRectangle().upperLine();
        Line lowerLine = colInfo.collisionObject().
                getCollisionRectangle().lowerLine();
        Line leftLine = colInfo.collisionObject().
                getCollisionRectangle().leftLine();
        Line rightLine = colInfo.collisionObject().
                getCollisionRectangle().rightLine();
        // change the location according to the direction of the collision:
        if (upperLine.isInLineRange(colInfo.collisionPoint())) {
            y = y - this.r;
        }
        if (lowerLine.isInLineRange(colInfo.collisionPoint())) {
            y = y + this.r;
        }
        if (leftLine.isInLineRange(colInfo.collisionPoint())) {
            x = x - this.r;
        }
        if (rightLine.isInLineRange(colInfo.collisionPoint())) {
            x = x + this.r;
        }
        // return the Point that holds the location values:
        return new Point(x, y);
    }

    /**
     * Sets the Game Environment according to the given object.
     *
     * @param ge Game Environment object
     */
    public void setGameEnvironment(GameEnvironment ge) {
        this.gameEnv = ge;
    }
    @Override
    public void timePassed() {
        this.moveOneStep();
    }
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Checks whether the ball has gotten inside the first Collidable object
     * in the Game Environment list (supposedly the paddle)
     * - and if so, moves it outside accordingly.
     */
    public void moveOutOfRectangle() {
        // get the paddle:
        Collidable pad = this.gameEnv.getGameEnvironment().get(0);
        Rectangle rec = pad.getCollisionRectangle();
        // if the ball is inside the paddle:
        if (this.getX() <= rec.upperRight().getX()
                && this.getX() >= rec.getUpperLeft().getX()
                && this.getY() >= rec.getUpperLeft().getY()
                && this.getY() <= rec.lowerLeft().getY()) {
            // move the ball out of the paddle in the right direction:
            //double dx = rec.getWidth() / PART_OF_REC_WIDTH;
            double dx = 2 * this.r + rec.upperRight().getX() - this.getX();
            double dy = 0;
            // if the ball is closer to the left of the paddle:
            if (this.getX() < rec.getUpperLeft().getX()
                    + (rec.getWidth() / 2)) {
                // move the ball out to the left of the paddle:
                //dx *= -1;
                dx = -1 * (this.getX() - rec.getUpperLeft().getX() - 2 * this.r);
            }
            // if moving the ball will take it out of the board's range:
            if (this.getX() + dx <= FRAME
                    || this.getX() + dx >= SCREEN_WIDTH - FRAME) {
                // move it upwards instead:
                dx = 0;
                dy = -rec.getHeight();
            }
            // move the ball according to the right velocity values:
            Velocity temp = new Velocity(dx, dy);
            this.center = temp.applyToPoint(this.center);
        }
    }

    /**
     * Remove this ball from the game.
     *
     * @param game game object
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }
}