package AnimationAssets;

import Interfaces.Animation;
import biuoop.DrawSurface;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines an Animation-type End Screen
 * using a boolean variable for whether the screen should run,
 * a String to print the final score,
 * and two int variables to know whether it's a win.
 */
public class EndScreen implements Animation {
    private boolean stop;
    private String finalScore;
    private int remainingBlocks;
    private int winScore;
    static final int TEXT_START = 10;
    static final int TEXT_SIZE = 32;

    /**
     * Constructor.
     *
     * @param remainingBlocks int variable
     * @param winScore int variable
     */
    public EndScreen(int remainingBlocks, int winScore) {
        this.stop = false;
        this.remainingBlocks = remainingBlocks;
        this.finalScore = "Your score is " + this.remainingBlocks;
        this.winScore = winScore;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(TEXT_START, d.getHeight() / 2,
                    getTitle() + this.finalScore, TEXT_SIZE);
    }

    /**
     * Returns the correct title for the end screen
     * (win / lose) according to the game.
     *
     * @return String
     */
    public String getTitle() {
        // if all the blocks were hit - it's a win:
        if (this.remainingBlocks == this.winScore) {
            return "You Win! ";
        } else {
            return "Game Over. ";
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}