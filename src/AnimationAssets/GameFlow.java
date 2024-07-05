package AnimationAssets;

import GameAssets.Counter;
import GameAssets.GameLevel;
import GameAssets.ScoreTrackingListener;
import Interfaces.LevelInformation;
import biuoop.KeyboardSensor;

import java.util.List;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines the Game Flow which runs the game levels
 * using an Animation Runner, a Keyboard Sensor, a Score Tracking Listener
 * and an int variable that keeps the maximum score possible.
 */
public class GameFlow {
    private AnimationRunner ar;
    private KeyboardSensor ks;
    private ScoreTrackingListener currentScore;
    private int winScore;
    static final int BONUS_WIN_LEVEL = 100;
    static final int POINTS_PER_BLOCK = 5;

    /**
     * Constructor.
     *
     * @param ar Animation Runner
     * @param ks Keyboard Sensor
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks) {
        this.ar = ar;
        this.ks = ks;
        this.currentScore = new ScoreTrackingListener(new Counter());
        this.winScore = 0;
    }

    /**
     * Runs the input game levels.
     * <p></p>
     * Creates each level, initializes and runs them one after one,
     * then closes the game.
     *
     * @param levels List of Level Information objects
     */
    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation levelInfo : levels) {
            // create each level:
            GameLevel level = new GameLevel(levelInfo,
                    this.ks, this.ar, this.currentScore);
            level.initialize();
            // add the max score of the level to the win score:
            winScore += BONUS_WIN_LEVEL
                    + (POINTS_PER_BLOCK * level.getCounterBlock().getValue());
            //while the level has more blocks and balls - run the game level:
            while (!level.shouldStop()) {
                level.run();
            }
            //if there are no more balls - stop the game entirely:
            if (level.numOfRemainingBalls() == 0) {
                break;
            }
        }
        //show the end screen before closing:
        this.ar.run(new KeyPressStoppableAnimation(
                this.ar.getGui().getKeyboardSensor(),
                KeyboardSensor.SPACE_KEY, new EndScreen(
                this.currentScore.getCurrentScore().getValue(), winScore)));
        this.ar.getGui().close();
    }
}
