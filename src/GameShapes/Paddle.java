package GameShapes;
import BasicShapes.Line;
import BasicShapes.Point;
import BasicShapes.Rectangle;
import GameAssets.GameLevel;
import Interfaces.Collidable;
import Interfaces.Sprite;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines object Paddle with a Rectangle and a Keyboard sensor,
 * and two int variables for the width and speed.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle pad;
    private int step;
    private int width;
    static final int HEIGHT = 20;
    static final double SCREEN_WIDTH = 800;
    static final double SCREEN_HEIGHT = 600;
    static final int FRAME = 5;
    static final int START_ANGLE = -60;
    static final int MOVE_ANGLE = 30;
    static final double REGIONS = 5;

    /**
     * Constructor - initializes the keyboard sensor and the Rectangle.
     *
     * @param keyboard Keyboard sensor
     * @param width int variable
     * @param step int variable
     */
    public Paddle(biuoop.KeyboardSensor keyboard, int width, int step) {
        this.keyboard = keyboard;
        this.width = width;
        this.step = step;
        this.pad = new Rectangle(new Point((SCREEN_WIDTH / 2)
                - (width / 2.0), SCREEN_HEIGHT - HEIGHT - FRAME),
                width, HEIGHT);
    }

    /**
     * Returns this paddle's speed -
     * the length of step it makes each press of the keyboard.
     *
     * @return int variable
     */
    public int getSpeed() {
        return this.step;
    }

    /**
     * Returns this paddle's width.
     *
     * @return int variable
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Moving the Rectangle a step to the left.
     */
    public void moveLeft() {
        double step = this.pad.getUpperLeft().getX() - this.step;
        // if the paddle moves out of frame - stopping it before:
        if (step < FRAME) {
            this.pad = new Rectangle(new Point(FRAME,
                    this.pad.getUpperLeft().getY()),
                    this.pad.getWidth(), this.pad.getHeight());
        } else {
            this.pad = new Rectangle(new Point(step,
                    this.pad.getUpperLeft().getY()),
                    this.pad.getWidth(), this.pad.getHeight());
        }
    }

    /**
     * Moving the Rectangle a step to the right.
     */
    public void moveRight() {
        double step = this.pad.getUpperLeft().getX() + this.step;
        // if the paddle moves out of frame - stopping it before:
        if (step > SCREEN_WIDTH - FRAME - this.pad.getWidth()) {
            this.pad = new Rectangle(new Point(
                    SCREEN_WIDTH - FRAME - this.pad.getWidth(),
                    this.pad.getUpperLeft().getY()),
                    this.pad.getWidth(), this.pad.getHeight());
        } else {
            this.pad = new Rectangle(new Point(step,
                    this.pad.getUpperLeft().getY()),
                    this.pad.getWidth(), this.pad.getHeight());
        }
    }

    @Override
    public void timePassed() {
        // if the left key was pressed and there's room to the left:
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        // if the right key was pressed and there's room to the right:
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }
    @Override
    public void drawOn(DrawSurface d) {
        // fill the paddle:
        d.setColor(Color.yellow);
        d.fillRectangle((int) this.pad.getUpperLeft().getX(),
                (int) this.pad.getUpperLeft().getY(),
                (int) this.pad.getWidth(), (int) this.pad.getHeight());
        // draw the paddle:
        d.setColor(Color.black);
        d.drawRectangle((int) this.pad.getUpperLeft().getX(),
                (int) this.pad.getUpperLeft().getY(),
                (int) this.pad.getWidth(), (int) this.pad.getHeight());
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.pad;
    }
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint,
                        Velocity currentVelocity) {
        // get the current velocity values:
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        // if the collision point is on the upper line of paddle:
        if (this.pad.upperLine().isInLineRange(collisionPoint)) {
            // change the velocity value to angle and speed:
            double speed = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            double angle = START_ANGLE;
            // create a line from the first region of the upper line of the pad:
            Point startOfPart = this.pad.getUpperLeft();
            Point endOfPart = new Point(startOfPart.getX()
                    + (this.width / REGIONS), startOfPart.getY());
            Line partOfPaddle = new Line(startOfPart, endOfPart);
            // if the collision point is on it - change the angle accordingly:
            if (partOfPaddle.isInLineRange(collisionPoint)) {
                return Velocity.fromAngleAndSpeed(-angle, -speed);
            }
            // go over the rest of the regions of the upper line of the pad:
            for (int i = 1; i < REGIONS; i++) {
                angle += MOVE_ANGLE;
                startOfPart = endOfPart;
                endOfPart = new Point(endOfPart.getX()
                        + (this.width / REGIONS), endOfPart.getY());
                partOfPaddle = new Line(startOfPart, endOfPart);
                // check whether the collision point is on the current region:
                if (partOfPaddle.isInLineRange(collisionPoint)) {
                    return Velocity.fromAngleAndSpeed(-angle, -speed);
                }
            }
        }
        // if the collision point is on the left or right paddle line:
        if (this.pad.leftLine().isInLineRange(collisionPoint)
                || this.pad.rightLine().isInLineRange(collisionPoint)) {
            // change the dx velocity value:
            dx = -1 * currentVelocity.getDx();
        }
        return new Velocity(dx, dy);
    }

    /**
     * Add this paddle to the game.
     *
     * @param g Game object
     */
    public void addToGame(GameLevel g) {
        // paddle is a collidable object:
        g.addCollidable(this);
        // paddle is a sprite object:
        g.addSprite(this);
    }
}