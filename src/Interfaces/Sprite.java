package Interfaces;
import GameAssets.GameLevel;
import biuoop.DrawSurface;

/**
 * @author Hadar Asheri
 * <p></p>
 * Sprite objects on screen.
 */
public interface Sprite {

    /**
     * draw the sprite to the screen.
     *
     * @param d DrawSurafce object
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     */
    void timePassed();

    /**
     * add the sprite to the given Game's sprite collection.
     *
     * @param g Game object
     */
    void addToGame(GameLevel g);
}