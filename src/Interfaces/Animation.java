package Interfaces;
import biuoop.DrawSurface;

/**
 * @author Hadar Asheri
 * <p></p>
 * Animation interface.
 */
public interface Animation {
    /**
     * Responsible for the functions of each separate frame.
     *
     * @param d DrawSurface object
     */
    void doOneFrame(DrawSurface d);

    /**
     * Returns true if the game should stop, false otherwise.
     *
     * @return true or false
     */
    boolean shouldStop();
}