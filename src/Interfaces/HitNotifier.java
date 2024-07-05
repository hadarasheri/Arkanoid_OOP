package Interfaces;

/**
 * @author Hadar Asheri
 * <p></p>
 * Notifies when a hit occurs.
 */
public interface HitNotifier {

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl HitListener type object
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl HitListener type object
     */
    void removeHitListener(HitListener hl);
}