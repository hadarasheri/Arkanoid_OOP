package GameShapes;
import BasicShapes.Point;
import BasicShapes.Rectangle;
import GameAssets.GameLevel;
import Interfaces.Collidable;
import Interfaces.HitListener;
import Interfaces.HitNotifier;
import Interfaces.Sprite;
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines a Block object with a Rectangle object, a color
 * and a list of Listeners to hits.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private Color color;
    private List<HitListener> hitListeners;

    /**
     * Constructor.
     *
     * @param rect Rectangle object
     * @param color color
     */
    public Block(Rectangle rect, Color color) {
        this.rect = rect;
        this.color = color;
        this.hitListeners = new ArrayList<>();
    }
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Notifies all the registered HitListener objects by
     * calling their hitEvent method.
     *
     * @param hitter Ball object
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList
                <HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint,
                        Velocity currentVelocity) {
        this.notifyHit(hitter);
        // get the current velocity values:
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        // if the collision point is on the left or right rectangle line:
        if (this.rect.leftLine().isInLineRange(collisionPoint)
                || this.rect.rightLine().isInLineRange(collisionPoint)) {
            // change the dx velocity value:
            dx = -1 * currentVelocity.getDx();
        }
        // if the collision point is on the upper or lower rectangle line:
        if (this.rect.upperLine().isInLineRange(collisionPoint)
                || this.rect.lowerLine().isInLineRange(collisionPoint)) {
            // change the dy velocity value:
            dy = -1 * currentVelocity.getDy();
        }
        return new Velocity(dx, dy);
    }
    @Override
    public void drawOn(DrawSurface d) {
        // draw the Block:
        d.setColor(this.color);
        d.fillRectangle((int) this.rect.getUpperLeft().getX(),
                (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(), (int) this.rect.getHeight());
        // draw the Block's frame:
        d.setColor(Color.black);
        d.drawRectangle((int) this.rect.getUpperLeft().getX(),
                (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(), (int) this.rect.getHeight());
    }
    @Override
    public void timePassed() {
        return;
    }
    @Override
    public void addToGame(GameLevel g) {
        // the Block is a Sprite:
        g.addSprite(this);
        // the Block is also Collidable:
        g.addCollidable(this);
    }

    /**
     * Remove this block from the game.
     *
     * @param game game object
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}
