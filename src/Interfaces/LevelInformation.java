package Interfaces;

import GameShapes.Block;
import GameShapes.Paddle;
import GameShapes.Velocity;
import biuoop.DrawSurface;

import java.util.List;

/**
 * @author Hadar Asheri
 * <p></p>
 * Level Information interface.
 */
public interface LevelInformation {
    /**
     * Returns the number of balls in this level.
     *
     * @return int variable
     */
    int numberOfBalls();

    /**
     * The initial velocity of each ball in this level.
     * initialBallVelocities().size() == numberOfBalls()
     *
     * @return List of Velocities for each ball in the game.
     */
    List<Velocity> initialBallVelocities();

    /**
     * Returns this paddle.
     *
     * @return Paddle object
     */
    Paddle getPad();

    /**
     * Returns the speed of the paddle.
     *
     * @return int variable
     */
    int paddleSpeed();

    /**
     * Returns the width of the paddle.
     *
     * @return int variable
     */
    int paddleWidth();

    /**
     * Returns the level name which is displayed at the top of the screen.
     *
     * @return String
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     *
     * @return Sprite object
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return List of Blocks
     */
    List<Block> blocks();

    /**
     * Returns the number of blocks that should be removed
     * before the level is considered to be "cleared".
     * This number is <= blocks.size().
     *
     * @return int variable
     */
    int numberOfBlocksToRemove();

    /**
     * Draws the background shapes of the level.
     *
     * @param d Draw Surface
     */
    void drawBackgroundShapes(DrawSurface d);
}