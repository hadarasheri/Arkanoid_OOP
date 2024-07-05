package Interfaces;

import GameShapes.Ball;
import GameShapes.Block;

/**
 * @author Hadar Asheri
 * <p></p>
 * Listens to hits.
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit Block object
     * @param hitter Ball object
     */
    void hitEvent(Block beingHit, Ball hitter);
}
