import angleDefenseLogic.*;
import angleDefenseLogic.Minions.*;
import angleDefenseLogic.Towers.*;
import org.junit.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;

import static junit.framework.TestCase.*;

public class GameplayTests extends TestBase {
    static private Game game;
    static private Player player;

    // Always reinitialize everything for each test
    @Before
    public void before() throws FileNotFoundException {
        player = new Player("test", Color.BLUE);
        game = Game.newGame("test-config.json");
    }

    // Test that the level increments when the player beats a level
    @Test
    public void testLevelIncrement() {
        Tower boom = new GroundTower(player);

        // add a bunch of minions
        Minion one = new GroundUnit(new Location(1, 1));
        Minion two = new GroundUnit(new Location(1, 1));
        Minion three = new GroundUnit(new Location(1, 1));
        Minion four = new GroundUnit(new Location(1, 1));
        Minion five = new GroundUnit(new Location(1, 1));
        Minion six = new GroundUnit(new Location(1, 1));

        // kill all the minions
        one.attacked(boom, Integer.MAX_VALUE);
        two.attacked(boom, Integer.MAX_VALUE);
        three.attacked(boom, Integer.MAX_VALUE);
        four.attacked(boom, Integer.MAX_VALUE);
        five.attacked(boom, Integer.MAX_VALUE);
        six.attacked(boom, Integer.MAX_VALUE);

        // Ensure that the level was incremented
        assertEquals(2, game.getLevel().getLevelNum());
    }

    // Test that the lives are tracked properly
    @Test
    public void testLivesDecrement() {
        int originalLives = game.getNumLives();
        ArrayList<Minion> minions = new ArrayList<>();

        // add some minions and let them march all the way
        for (int i = 0; i < 5; i++)
            minions.add(new GroundUnit(new Location(1, 1)));

        for (Minion m : minions)
            m.moveForward(Float.MAX_VALUE);

        // Ensure that the number of lives has been decremented by the proper number
        assertEquals(originalLives - minions.size(), game.getNumLives());
    }

    // Test Tower attack mechanisms
    @Test
    public void testTowerAttackMechanisms() {
        // Place one of each type of tower
        Tower airGroundTower = new AirGroundTower(player);
        Tower airTower = new AirTower(player);
        Tower groundTower = new GroundTower(player);

        // Create a few units
        Minion groundUnit = new GroundUnit(new Location(1, 1));
        Minion airUnit = new AirUnit(new Location(1,1));

        // Ensure that the air tower doesn't kill ground units
        airTower.attack(groundUnit);
        assertFalse(groundUnit.isDead());

        // Ensure that the ground tower doesn't kill air units
        groundTower.attack(airUnit);
        assertFalse(airUnit.isDead());

        // Ensure that the ground tower kills ground units
        groundTower.attack(groundUnit);
        assertTrue(groundUnit.isDead());

        // Ensure that the air tower kills air units
        airTower.attack(airUnit);
        assertTrue(airUnit.isDead());

        // Ensure that the air-ground tower kills all units
        groundUnit = new GroundUnit(new Location(1,1));
        airUnit = new AirUnit(new Location(1,1));

        airGroundTower.attack(groundUnit);
        airGroundTower.attack(airUnit);

        assertTrue(groundUnit.isDead());
        assertTrue(airUnit.isDead());
    }

    // Test player gold reward
    @Test
    public void testPlayerGoldReward() {

        // Place a tower and minion
        Tower tower = new AirTower(player);
        Minion minion = new AirUnit(new Location(1, 1));

        // Kill the minion
        minion.attacked(tower, Integer.MAX_VALUE);

        // Ensure that the player's gold is incremented by the value of the minion
        // TODO: UPDATE TO THE CORRECT GOLD REWARD
        assertEquals(5, player.getGold());
    }

}
