package GameShapes;

import GameAssets.Counter;
import GameAssets.GameLevel;
import Interfaces.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines a Sprite-type Score Indicator using a Counter for the
 * current score and a string for the level's name.
 */
public class ScoreIndicator implements Sprite {
    private Counter currentScore;
    private String levelName;
    static final int SCORE_WIDTH = 800;
    static final int SCORE_HEIGHT = 30;
    static final int TEXT_SIZE = 18;
    static final int TEXT_HEIGHT_Y = 22;
    static final int LIVES_TEXT_X = 100;
    static final int SCORE_TEXT_X = 372;
    static final int LEVEL_TEXT_X = 560;

    /**
     * Constructor.
     *
     * @param scoreCounter Counter object
     * @param levelName String
     */
    public ScoreIndicator(Counter scoreCounter, String levelName) {
        this.currentScore = scoreCounter;
        this.levelName = levelName;
    }

    /**
     * Returns the Score in string.
     *
     * @return String
     */
    public String getScore() {
        return "Score: " + currentScore.getValue();
    }

    @Override
    public void drawOn(DrawSurface d) {
        // fill the Block:
        d.setColor(Color.white);
        d.fillRectangle(0, 0, SCORE_WIDTH, SCORE_HEIGHT);
        // draw the Block:
        d.setColor(Color.black);
        d.drawRectangle(0, 0, SCORE_WIDTH, SCORE_HEIGHT);
        // draw the Lives text:
        d.setColor(Color.black);
        d.drawText(LIVES_TEXT_X, TEXT_HEIGHT_Y, "Lives: 1", TEXT_SIZE);
        // draw the Score text:
        d.setColor(Color.black);
        d.drawText(SCORE_TEXT_X, TEXT_HEIGHT_Y, this.getScore(), TEXT_SIZE);
        // draw the Level Name text:
        d.setColor(Color.black);
        d.drawText(LEVEL_TEXT_X, TEXT_HEIGHT_Y, this.levelName, TEXT_SIZE);
    }

    @Override
    public void timePassed() {
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
