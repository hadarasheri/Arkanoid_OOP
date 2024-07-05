package GameAssets;
import AnimationAssets.AnimationRunner;
import AnimationAssets.CountdownAnimation;
import AnimationAssets.KeyPressStoppableAnimation;
import AnimationAssets.PauseScreen;
import BasicShapes.Point;
import BasicShapes.Rectangle;
import GameShapes.Ball;
import GameShapes.Block;
import GameShapes.ScoreIndicator;
import Interfaces.Animation;
import Interfaces.Collidable;
import Interfaces.LevelInformation;
import Interfaces.Sprite;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines a Game Level using a Level Information-type object,
 * a Keyboard Sensor, an Animation Runner, a boolean value for knowing
 * when the level is running, a Sprite Collection, a Game Environment,
 * Block and Ball Removers, Score Tracking Listener and 3 Counters
 * (for remaining blocks, remaining balls and score).
 */
public class GameLevel implements Animation {
    private LevelInformation level;
    private KeyboardSensor ks;
    private AnimationRunner runner;
    private boolean running;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter counterBlock;
    private BlockRemover blockRemover;
    private Counter counterBall;
    private BallRemover ballRemover;
    private Counter counterScore;
    private ScoreTrackingListener currentScore;
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    static final int FRAME = 5;
    static final int SCORE_BLOCK = 30;
    static final int BONUS_SCORE = 100;
    static final int BALLS_HEIGHT = 560;
    static final int BALLS_SIZE = 5;
    static final Color BALLS_COLOR = Color.white;

    /**
     * Constructor.
     *
     * @param level LevelInformation
     * @param ks Keyboard Sensor
     * @param ar Animation Runner
     * @param currentScore Score Tracking Listener
     */
    public GameLevel(LevelInformation level, KeyboardSensor ks,
                     AnimationRunner ar, ScoreTrackingListener currentScore) {
        this.level = level;
        this.ks = ks;
        this.running = true;
        this.runner = ar;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.counterBlock = new Counter();
        this.counterBall = new Counter();
        this.blockRemover = new BlockRemover(this, this.counterBlock);
        this.ballRemover = new BallRemover(this, this.counterBall);
        this.currentScore = currentScore;
        this.counterScore = this.currentScore.getCurrentScore();
    }

    /**
     * Returns the Block Counter.
     *
     * @return Counter object
     */
    public Counter getCounterBlock() {
        return this.counterBlock;
    }

    /**
     * Adds a Collidable object to the Colliidable list.
     *
     * @param c Collidable object
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a Sprite object to the Sprite list.
     *
     * @param s Sprite object
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Returns the number of remaining balls in the game level.
     *
     * @return int variable
     */
    public int numOfRemainingBalls() {
        return this.counterBall.getValue();
    }

    /**
     * Initialize a new game: create the Blocks and Ball and Paddle
     * and add them to the game.
     */
    public void initialize() {
        // add the level's background to the game:
        level.getBackground().addToGame(this);
        // create a score indicator that holds the counter score:
        ScoreIndicator score = new ScoreIndicator(
                this.counterScore, level.levelName());
        score.addToGame(this);
        // create balls and add them to the game:
        createBalls();
        // create and add a paddle to the game:
        level.getPad().addToGame(this);
        // create and add four frame blocks:
        createFrameBlocks();
        // add the level's blocks to the game:
        addHitBlocks();
    }

    /**
     * Creates balls and adds them to the game.
     */
    public void createBalls() {
        // create and add the number of balls with their velocities:
        for (int i = 0; i < level.numberOfBalls(); i++) {
            Ball ball = new Ball(WIDTH / 2.0, BALLS_HEIGHT,
                    BALLS_SIZE, BALLS_COLOR, this.environment);
            ball.setVelocity(level.initialBallVelocities().get(i));
            ball.addToGame(this);
        }
        // set the balls counter:
        this.counterBall.increase(level.numberOfBalls());
    }

    /**
     * Creates and adds frame blocks to the game level.
     */
    public void createFrameBlocks() {
        int i = 0;
        Rectangle[] recs = new Rectangle[4];
        // create left block:
        recs[i++] = new Rectangle(new Point(0, SCORE_BLOCK), FRAME,
                HEIGHT - SCORE_BLOCK);
        // create upper block:
        recs[i++] = new Rectangle(new Point(0, SCORE_BLOCK), WIDTH, FRAME);
        // create right block:
        recs[i++] = new Rectangle(new Point(WIDTH - FRAME, SCORE_BLOCK),
                FRAME, HEIGHT - SCORE_BLOCK);
        // create lower block:
        recs[i] = new Rectangle(new Point(0, HEIGHT - FRAME),
                WIDTH, FRAME);
        // add each block to the game level:
        for (Rectangle r : recs) {
            /*Block block;
            if (r != recs[i]) {
                block = new Block(r, Color.GRAY);
            } else {
                block = new Block(r, ((Background) this.level.getBackground()).getColor());
                block.addHitListener(this.ballRemover);
            }*/
            Block block = new Block(r, Color.GRAY);
            block.addToGame(this);
            // make the lower block a death zone:
            if (r == recs[i]) {
                block.addHitListener(this.ballRemover);
            }
        }
    }

    /**
     * Adds the level's hit blocks to the game level.
     */
    public void addHitBlocks() {
        for (Block block : level.blocks()) {
            block.addToGame(this);
            block.addHitListener(this.blockRemover);
            block.addHitListener(this.currentScore);
            // increase the block counter by 1 for each block:
            this.counterBlock.increase(1);
        }
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // drawing and moving all the sprites and collidable game objects:
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        // if the letter P is pressed:
        if ((this.runner.getGui().getKeyboardSensor().isPressed("p"))
                ||
                (this.runner.getGui().getKeyboardSensor().isPressed("P"))) {
            // run the pause screen:
            this.runner.run(new KeyPressStoppableAnimation(
                    this.runner.getGui().getKeyboardSensor(),
                    KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }
        // if there are no more balls or hit blocks:
        if ((this.counterBlock.getValue() == 0)
                || (this.counterBall.getValue() == 0)) {
            // stop running the game level:
            this.running = false;
            // if the level was won - add bonus points:
            if (this.counterBlock.getValue() == 0) {
                this.counterScore.increase(BONUS_SCORE);
                initialize();
            }
        }
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        // countdown before the level begins playing:
        this.runner.run(new CountdownAnimation(
                2, 3, this.sprites));
        this.running = true;
        // use our runner to animate the current one turn of the game:
        this.runner.run(this);
    }

    /**
     * Remove collidable from game.
     *
     * @param c collidable object
     */
    public void removeCollidable(Collidable c) {
        this.environment.getGameEnvironment().remove(c);
    }

    /**
     * Remove sprie from game.
     *
     * @param s sprite object
     */
    public void removeSprite(Sprite s) {
        this.sprites.getSpriteCollection().remove(s);
    }
}