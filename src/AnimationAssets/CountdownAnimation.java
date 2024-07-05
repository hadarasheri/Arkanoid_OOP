package AnimationAssets;

import GameAssets.SpriteCollection;
import Interfaces.Animation;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines an Animation-type Countdown Animation screen
 * using three double variables for measuring the time,
 * two int variables for the count print,
 * and a Sprite Collection for the background.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private int print;
    private SpriteCollection gameScreen;
    private double startTime;
    private double timePassed;
    static final int COUNT_START_X = 375;
    static final int COUNT_START_Y = 50;
    static final int COUNT_WIDTH = 50;
    static final int COUNT_HEIGHT = 50;
    static final int TEXT_SIZE = 30;
    static final int COUNT_TEXT_X = 390;
    static final int COUNT_TEXT_Y = 82;

    /**
     * Constructor -
     * sets the startTime variable to the current time
     * and the timePassed variable to 0.
     *
     * @param numOfSeconds double variable
     * @param countFrom int variable
     * @param gameScreen Sprite Collection
     */
    public CountdownAnimation(double numOfSeconds,
                              int countFrom,
                              SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.print = this.countFrom;
        this.startTime = System.currentTimeMillis();
        this.timePassed = 0;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // update the time passed:
        setTime();
        // draw the background:
        gameScreen.drawAllOn(d);
        // fill the count rectangle:
        d.setColor(Color.white);
        d.fillRectangle(COUNT_START_X, COUNT_START_Y,
                COUNT_WIDTH, COUNT_HEIGHT);
        // draw the count rectangle:
        d.setColor(Color.black);
        d.drawRectangle(COUNT_START_X, COUNT_START_Y,
                COUNT_WIDTH, COUNT_HEIGHT);
        // draw the count text:
        d.setColor(Color.black);
        d.drawText(COUNT_TEXT_X, COUNT_TEXT_Y, "" + this.print, TEXT_SIZE);
    }

    /**
     * Updates the amount of time passed since the beginning and checks
     * whether it's been longer than the amount of milliseconds for every
     * counted number - if so, changing the printed number and
     * resetting the time counters.
     */
    public void setTime() {
        // update the amount of time passed since the beginning:
        this.timePassed = System.currentTimeMillis() - startTime;
        // if it's time to change the count to a different number:
        if (this.timePassed >= numOfSeconds / countFrom * 1000) {
            this.print--;
            // resetting the time counting variables:
            this.startTime = System.currentTimeMillis();
            this.timePassed = 0;
        }
    }

    @Override
    public boolean shouldStop() {
        // stop when the count reaches 0:
        return this.print == 0;
    }
}
