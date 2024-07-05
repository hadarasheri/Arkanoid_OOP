package AnimationAssets;

import BasicShapes.Point;
import BasicShapes.Rectangle;
import GameShapes.Block;
import GameShapes.Paddle;
import GameShapes.Velocity;
import Interfaces.LevelInformation;
import Interfaces.Sprite;
import biuoop.DrawSurface;
import biuoop.GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines the first level of the game: Direct Hit
 * using an int for the number of balls, a String for the level name,
 * a Paddle, a list of Blocks, a list of Velocities and a background Sprite.
 */
public class Level1 implements LevelInformation {
    private int numberOfBalls;
    private String levelName;
    private Paddle pad;
    private List<Block> blocks;
    private List<Velocity> initialBallVelocities;
    private Sprite background;
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    static final int BLOCK_SIZE = 30;
    static final int BLOCK_WIDTH = 385;
    static final int BLOCK_HEIGHT = 150;
    static final int BLOCK_CENTER_X = 400;
    static final int BLOCK_CENTER_Y = 165;
    static final int INNER_CIRCLE = 40;
    static final int CIRCLE_GROWTH = 20;
    static final int LINE_LENGTH = 100;

    /**
     * Constructor.
     *
     * @param gui GUI object
     */
    public Level1(GUI gui) {
        this.numberOfBalls = 1;
        this.levelName = "Level Name: Direct Hit";
        // set a regular paddle:
        this.pad = new Paddle(gui.getKeyboardSensor(), 75, 10);
        this.blocks = new ArrayList<>();
        // add a single block:
        this.blocks.add(new Block(new Rectangle(new Point(BLOCK_WIDTH,
                BLOCK_HEIGHT), BLOCK_SIZE, BLOCK_SIZE), Color.RED));
        this.initialBallVelocities = new ArrayList<>();
        // add a velocity for the ball:
        this.initialBallVelocities.add(new Velocity(0, 5));
        // set the background:
        this.background = new Background(
                Color.BLACK, 0, 0, WIDTH, HEIGHT, this);
    }

    @Override
    public void drawBackgroundShapes(DrawSurface d) {
        // draw blue circles around the single block all over the screen:
        d.setColor(Color.blue);
        for (int i = INNER_CIRCLE + (CIRCLE_GROWTH / 2);
             i < HEIGHT; i += CIRCLE_GROWTH) {
            d.drawCircle(BLOCK_CENTER_X, BLOCK_CENTER_Y, i);
        }
        // draw pink circles and lines in an aim-like shape around the block:
        d.setColor(Color.MAGENTA);
        for (int j = 0; j < 3; j++) {
            d.drawCircle(BLOCK_CENTER_X, BLOCK_CENTER_Y,
                    INNER_CIRCLE + (j * CIRCLE_GROWTH));
        }
        d.drawLine(BLOCK_CENTER_X - LINE_LENGTH, BLOCK_CENTER_Y,
                BLOCK_CENTER_X + LINE_LENGTH, BLOCK_CENTER_Y);
        d.drawLine(BLOCK_CENTER_X, BLOCK_CENTER_Y - LINE_LENGTH,
                BLOCK_CENTER_X, BLOCK_CENTER_Y + LINE_LENGTH);
    }

    @Override
    public int numberOfBalls() {
        return this.numberOfBalls;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.initialBallVelocities;
    }

    @Override
    public Paddle getPad() {
        return this.pad;
    }

    @Override
    public int paddleSpeed() {
        return this.pad.getSpeed();
    }

    @Override
    public int paddleWidth() {
        return this.pad.getWidth();
    }

    @Override
    public String levelName() {
        return this.levelName;
    }

    @Override
    public Sprite getBackground() {
        return this.background;
    }

    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks.size();
    }
}
