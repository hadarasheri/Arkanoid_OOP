package GameAssets;
import Interfaces.Sprite;
import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines a Sprite Collection using a list of Sprite objects.
 */
public class SpriteCollection {
    private List<Sprite> list;

    /**
     * Constructor - initiates the Sprite list.
     */
    public SpriteCollection() {
        this.list = new ArrayList<>();
    }

    /**
     * Return this list of sprites.
     *
     * @return list of sprites
     */
    public List<Sprite> getSpriteCollection() {
        return this.list;
    }

    /**
     * Adds a given Sprite to this list.
     *
     * @param s Sprite object
     */
    public void addSprite(Sprite s) {
        this.list.add(s);
    }

    /**
     * Calls the method timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        for (int i = 0; i < this.list.size(); i++) {
            this.list.get(i).timePassed();
        }
    }

    /**
     * Calls the method drawOn(d) on all sprites.
     *
     * @param d DrawSurface object
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.list) {
            s.drawOn(d);
        }
    }
}