import angleDefenseLogic.*;
import org.junit.*;

import java.io.FileNotFoundException;
import java.util.*;

import static junit.framework.TestCase.*;

public class GameplayTests {
    static private Game game;

    @Before
    public void before() throws FileNotFoundException {
        game = Game.NewGame("default-config.json");
    }

    // Test that the level increments when the player beats a level
    @Test
    public void testLevelIncrement() {
        // TODO: add a bunch of minions
        // TODO: kill all the minions

        // Ensure that the level was incremented
        assertEquals(2, game.getLevel());
    }

    // Test that the lives are tracked properly
    @Test
    public void testLivesDecrement() {
        int originalLives = game.getNumLives();
        ArrayList<Minion> minions = new ArrayList<>();

        // TODO: add some minions and let them march all the way

        // Ensure that the number of lives has been decremented by the proper number
        assertEquals(originalLives - minions.size(), game.getNumLives());
    }

    // Test Minion advance
    @Test
    public void testMinionAdvance() {
        // TODO: Ensure that the minions advance at the correct speeds

        // TODO: Ensure that the minions stay on the paths as they advance.
    }

    // Test tower fire angle
    @Test
    public void testTowerFileAngle() {
        // TODO: DO THESE TESTS FOR ALL TOWER TYPES
        // TODO: Place a tower and set its angle
        // TODO: Place a minion and place it on a spot where the tower can hit it
        // TODO: Have the tower attack the minion and ensure that the minion dies

        // TODO: Place a tower and set its angle
        // TODO: Place a minion within range of the tower, but at the wrong angle
        // TODO: Ensure that the tower does not attack the minion
    }

    // Test Tower attack mechanisms
    @Test
    public void testTowerAttackMechanisms() {
        // TODO: DO THESE TESTS FOR ALL TOWER TYPES
        // TODO: Place one of each type of tower
        // TODO: Place a few minions of the correct type in the fire area of the tower.
        // TODO: Ensure that the minions die

        // TODO: Place a few minions of the incorrect type in the fire area of the tower.
        // TODO: Ensure that the minions do not die
    }

    // Test player gold reward
    @Test
    public void testPlayerGoldReward() {
        // TODO: Place a tower
        // TODO: Kill a minion
        // TODO: Ensure that the player's gold is incremented by the value of the minion
    }

}
