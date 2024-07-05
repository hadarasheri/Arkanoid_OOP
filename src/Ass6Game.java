// Hadar Asheri 212072359
import AnimationAssets.AnimationRunner;
import AnimationAssets.GameFlow;
import AnimationAssets.Level1;
import AnimationAssets.Level2;
import AnimationAssets.Level3;
import Interfaces.LevelInformation;

import java.util.ArrayList;

/**
 * @author Hadar Asheri
 * <p></p>
 * Runs the arkanoid game.
 */
public class Ass6Game {
    /**
     * Creates a new game,
     * initializes the levels according to the arguments
     * and runs the game.
     *
     * @param argc null
     */
    public static void main(String[] argc) {
        AnimationRunner ar = new AnimationRunner();
        GameFlow gf = new GameFlow(ar, ar.getGui().getKeyboardSensor());
        ArrayList<LevelInformation> levels = new ArrayList<>();
        boolean noCorrectArgs = true;
        // if there are arguments:
        for (String s : argc) {
            // add each level by order:
            switch (s) {
                case "1" -> {
                    levels.add(new Level1(ar.getGui()));
                    noCorrectArgs = false;
                }
                case "2" -> {
                    levels.add(new Level2(ar.getGui()));
                    noCorrectArgs = false;
                }
                case "3" -> {
                    levels.add(new Level3(ar.getGui()));
                    noCorrectArgs = false;
                }
                default -> {
                }
            }
        }
        // if there are no correct arguments:
        if ((argc.length == 0) || (noCorrectArgs)) {
            // add the three levels by order:
            levels.add(new Level1(ar.getGui()));
            levels.add(new Level2(ar.getGui()));
            levels.add(new Level3(ar.getGui()));
        }
        // run the game using the levels:
        gf.runLevels(levels);
    }
}
