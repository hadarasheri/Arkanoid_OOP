package AnimationAssets;

import Interfaces.Animation;
import biuoop.DrawSurface;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines an Animation-type Pause Screen using a boolean variable
 * for when the screen should stop running.
 */
public class PauseScreen implements Animation {
    private boolean stop;
    static final int TEXT_START = 10;
    static final int TEXT_SIZE = 32;

    /**
     * Constructor.
     */
    public PauseScreen() {
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(TEXT_START, d.getHeight() / 2,
                "paused -- press space to continue", TEXT_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}