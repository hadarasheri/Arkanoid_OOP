package AnimationAssets;

import BasicShapes.Point;
import BasicShapes.Rectangle;
import GameAssets.GameLevel;
import Interfaces.LevelInformation;
import Interfaces.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines a Sprite-type Background object using a Color,
 * a Rectangle for screen-size and a Level Information type object.
 */
public class Background implements Sprite {
    private Color color;
    private Rectangle rect;
    private LevelInformation level;

    /**
     * Constructor.
     *
     * @param color Color
     * @param x int variable
     * @param y int variable
     * @param width int variable
     * @param height int variable
     * @param level Level Information
     */
    public Background(Color color, int x, int y, int width, int height,
                      LevelInformation level) {
        this.color = color;
        this.rect = new Rectangle(new Point(x, y), width, height);
        this.level = level;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.rect.getUpperLeft().getX(),
                (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(), (int) this.rect.getHeight());
        // draw the level's background shapes:
        level.drawBackgroundShapes(d);
    }

    @Override
    public void timePassed() {
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
