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
import java.util.Random;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines the first level of the game: Green 3
 * using an int for the number of balls, a String for the level name,
 * a Paddle, a list of Blocks, a list of Velocities and a background Sprite.
 */
public class Level3 implements LevelInformation {
    private int numberOfBalls;
    private String levelName;
    private Paddle pad;
    private List<Block> blocks;
    private List<Velocity> initialBallVelocities;
    private Sprite background;
    static final int STEP = 7;
    static final int WIDTH_PAD = 75;
    static final int BLOCK_PLACE = 200;
    static final double BLOCK_WIDTH = 45;
    static final double BLOCK_HEIGHT = 20;
    static final int NUM_LINE_BLOCKS = 5;
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    static final int FRAME = 5;
    static final int NUM_BLOCKS = 7;
    static final double RANGE_ANGLES = 120;
    static final double START_ANGLE = -60;
    static final double SPEED = -5;

    /**
     * Constructor.
     *
     * @param gui GUI object
     */
    public Level3(GUI gui) {
        this.numberOfBalls = 2;
        this.levelName = "Level Name: Green 3";
        // set a regular paddle:
        this.pad = new Paddle(gui.getKeyboardSensor(), WIDTH_PAD, STEP);
        this.blocks = new ArrayList<>();
        // add stairs-shaped blocks:
        addBlocks();
        this.initialBallVelocities = new ArrayList<>();
        setRangeVelocities();
        // set the background:
        this.background = new Background(
                Color.green, 0, 0, WIDTH, HEIGHT, this);
    }

    /**
     * Adds blocks in the shape of upside-down stairs.
     */
    public void addBlocks() {
        Random rand = new Random();
        for (int i = 0; i < NUM_LINE_BLOCKS; i++) {
            // set a color for each line of blocks:
            Color color = new Color(rand.nextInt());
            for (int j = 0; j < NUM_BLOCKS + i; j++) {
                // add a decreasing number of blocks to each line:
                this.blocks.add(new Block(new Rectangle(new Point(
                        WIDTH - FRAME - (BLOCK_WIDTH * (j + 1)),
                        BLOCK_PLACE - (i * BLOCK_HEIGHT)),
                        BLOCK_WIDTH, BLOCK_HEIGHT), color));
            }
        }
    }

    /**
     * Sets the velocities so that the balls will range all over the screen.
     */
    public void setRangeVelocities() {
        // set angle and speed:
        double angle = START_ANGLE;
        // for each ball's velocity:
        for (int i = 0; i < this.numberOfBalls; i++) {
            this.initialBallVelocities.add(
                    Velocity.fromAngleAndSpeed(angle, SPEED));
            // add to the angle proportionately to the balls amount:
            angle += RANGE_ANGLES / (this.numberOfBalls - 1);
        }
    }

    @Override
    public void drawBackgroundShapes(DrawSurface d) {
        // independent of the blocks and screen, so magic numbers aren't used.
        d.setColor(Color.darkGray);
        // draw the big "building":
        d.fillRectangle(80, 200, 20, 400);
        d.fillRectangle(60, 375, 60, 225);
        d.setColor(Color.black);
        d.fillRectangle(30, 400, 120, 200);
        // draw the windows:
        d.setColor(Color.yellow);
        for (int i = 410; i < 600; i += 40) {
            for (int j = 40; j < 150; j += 22) {
                d.fillRectangle(j, i, 12, 30);
            }
        }
        // draw the top light:
        d.setColor(Color.orange);
        d.fillCircle(90, 190, 20);
        d.setColor(Color.red);
        d.fillCircle(90, 190, 15);
        d.setColor(Color.yellow);
        d.fillCircle(90, 190, 5);
        // draw the small "building":
        d.setColor(Color.darkGray);
        d.fillRectangle(205, 400, 10, 200);
        d.setColor(Color.black);
        d.fillRectangle(180, 500, 60, 100);
        // draw the small windows:
        d.setColor(Color.yellow);
        for (int i = 506; i < 600; i += 20) {
            for (int j = 186; j < 240; j += 11) {
                d.fillRectangle(j, i, 6, 15);
            }
        }
        // draw the small top light:
        d.setColor(Color.orange);
        d.fillCircle(209, 397, 11);
        d.setColor(Color.red);
        d.fillCircle(209, 397, 8);
        d.setColor(Color.yellow);
        d.fillCircle(209, 397, 3);
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
