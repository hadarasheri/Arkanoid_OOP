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
 * Defines the second level of the game: Wide Easy
 * using an int for the number of balls, a String for the level name,
 * a Paddle, a list of Blocks, a list of Velocities and a background Sprite.
 */
public class Level2 implements LevelInformation {
    private int numberOfBalls;
    private String levelName;
    private Paddle pad;
    private List<Block> blocks;
    private List<Velocity> initialBallVelocities;
    private Sprite background;
    static final double WIDTH = 800;
    static final double HEIGHT = 600;
    static final int FRAME = 5;
    static final int NUM_BLOCKS = 15;
    static final double RANGE_ANGLES = 120;
    static final double START_ANGLE = -60;
    static final double SPEED = -5;
    static final int START_RADIUS = 70;
    static final int RADIUS_CHANGE = 15;
    static final int SCORE_BLOCK = 30;

    /**
     * Constructor.
     *
     * @param gui GUI object
     */
    public Level2(GUI gui) {
        this.numberOfBalls = 10;
        this.levelName = "Level Name: Wide Easy";
        // set a wide paddle:
        this.pad = new Paddle(gui.getKeyboardSensor(), 550, 3);
        this.blocks = new ArrayList<>();
        // add a line of paired blocks:
        addBlockLine();
        this.initialBallVelocities = new ArrayList<>();
        // add different velocities:
        setRangeVelocities();
        // set the background:
        this.background = new Background(
                Color.white, 0, 0, (int) WIDTH, (int) HEIGHT, this);
    }

    /**
     * Adds a line of identical blocks, with a different color
     * for each pair and for the middle three.
     */
    public void addBlockLine() {
        // set the start of the line and width of every block:
        double x = FRAME, width = (WIDTH - FRAME - FRAME) / NUM_BLOCKS;
        Random rand = new Random();
        Color color = null;
        // go over the blocks:
        for (int i = 0; i < NUM_BLOCKS; i++) {
            // set a new color for every pair and the middle trio:
            if ((i < NUM_BLOCKS / 2) && (i % 2 == 0)
                    || (i > NUM_BLOCKS / 2) && (i % 2 != 0)) {
                color = new Color(rand.nextInt());
            }
            // add a new identical block:
            this.blocks.add(new Block(new Rectangle(
                    new Point(x, WIDTH / 2), width,
                    NUM_BLOCKS * 2), color));
            // set the next block's start to the end of the current one:
            x += width;
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
        int r = START_RADIUS;
        // draw an outer sun:
        d.setColor(Color.pink);
        d.fillCircle((int) WIDTH / 2, (int) HEIGHT / 2, r);
        r -= RADIUS_CHANGE;
        d.setColor(Color.orange);
        d.fillCircle((int) WIDTH / 2, (int) HEIGHT / 2, r);
        r -= RADIUS_CHANGE;
        // draw lines from the sun to the frame of the screen:
        for (int i = FRAME; i <= WIDTH - FRAME; i += FRAME * 2) {
            d.drawLine((int) WIDTH / 2, (int) HEIGHT / 2,
                    i, FRAME + SCORE_BLOCK);
        }
        for (int i = FRAME; i <= WIDTH - FRAME; i += FRAME * 2) {
            d.drawLine((int) WIDTH / 2, (int) HEIGHT / 2,
                    i, (int) HEIGHT - FRAME);
        }
        for (int i = FRAME + SCORE_BLOCK; i <= HEIGHT - FRAME; i += FRAME * 2) {
            d.drawLine((int) WIDTH / 2, (int) HEIGHT / 2, FRAME, i);
        }
        for (int i = FRAME + SCORE_BLOCK; i <= HEIGHT - FRAME; i += FRAME * 2) {
            d.drawLine((int) WIDTH / 2, (int) HEIGHT / 2,
                    (int) WIDTH - FRAME, i);
        }
        // draw an inner sun:
        d.setColor(Color.yellow);
        d.fillCircle((int) WIDTH / 2, (int) HEIGHT / 2, r);
        // draw lines from it to the line of blocks:
        for (int i = FRAME; i <= WIDTH - FRAME; i += FRAME + 3) {
            d.drawLine((int) WIDTH / 2, (int) HEIGHT / 2,
                    i, (int) WIDTH / 2);
        }
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
