package AnimationAssets;
import Interfaces.Animation;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines an Animation Runner object using a GUI object,
 * an int variable for number of frames per second and a Sleeper.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    static final int FRAMES_PER_SECONDS = 60;
    static final String GAME_TITLE = "Arkanoid";

    /**
     * Constructor.
     */
    public AnimationRunner() {
        this.gui = new GUI(GAME_TITLE, WIDTH, HEIGHT);
        this.framesPerSecond = FRAMES_PER_SECONDS;
        this.sleeper = new Sleeper();
    }

    /**
     * Returns the GUI object.
     *
     * @return this GUI
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * Runs the input animation by using this GUI and Sleeper
     * until it should stop.
     *
     * @param animation Animation-type object
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        // while the input should be running:
        while (!animation.shouldStop()) {
            // timing the running frames:
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();
            // running one frame:
            animation.doOneFrame(d);
            // showing it using the GUI:
            gui.show(d);
            // timing the sleeper according to the time passed:
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}