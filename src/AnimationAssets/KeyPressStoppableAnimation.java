package AnimationAssets;

import Interfaces.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * @author Hadar Asheri
 * <p></p>
 * Defines an Animation-type Key Press Stoppable Animation decorator
 * using a keyboard sensor, a String for the key that stops the animation,
 * an Animation-type object that holds the screen
 * and two boolean variables to stop running when the key is pressed.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * Constructor.
     *
     * @param sensor Keyboard Sensor
     * @param key String
     * @param animation Animation
     */
    public KeyPressStoppableAnimation(
            KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        // assume the key is already pressed:
        this.isAlreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // do one frame of the animation:
        this.animation.doOneFrame(d);
        // if the key is pressed and wasn't already pressed:
        if (this.sensor.isPressed(this.key)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            // assume the key isn't pressed anymore:
            this.isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
