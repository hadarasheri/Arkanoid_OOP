package GameAssets;

import GameShapes.Ball;
import GameShapes.Block;
import Interfaces.HitListener;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines a HitListener-type Score Tracking Listener using a Counter object.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;
    static final int HIT_SCORE = 5;

    /**
     * Constructor.
     *
     * @param scoreCounter Counter object
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Returns the score counter.
     *
     * @return Counter object
     */
    public Counter getCurrentScore() {
        return this.currentScore;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
       this.currentScore.increase(HIT_SCORE);
       beingHit.removeHitListener(this);
    }
}
