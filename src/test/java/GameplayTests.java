import angleDefenseLogic.*;
import angleDefenseLogic.Minions.*;
import angleDefenseLogic.Towers.*;
import org.junit.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;

import static junit.framework.TestCase.*;

public class GameplayTests {
    static private Game game;

    @Before
    public void before() throws FileNotFoundException {
        game = Game.NewGame("test-config.json");
    }

    // Test that the level increments when the player beats a level
    @Test
    public void testLevelIncrement() {
        Tower boom = new GroundTower();

        // add a bunch of minions
        Minion one = new GroundUnit();
        Minion two = new GroundUnit();
        Minion three = new GroundUnit();
        Minion four = new GroundUnit();
        Minion five = new GroundUnit();
        Minion six = new GroundUnit();

        // kill all the minions
        one.attacked(boom, Integer.MAX_VALUE);
        two.attacked(boom, Integer.MAX_VALUE);
        three.attacked(boom, Integer.MAX_VALUE);
        four.attacked(boom, Integer.MAX_VALUE);
        five.attacked(boom, Integer.MAX_VALUE);
        six.attacked(boom, Integer.MAX_VALUE);

        // Ensure that the level was incremented
        assertEquals(2, game.getLevel());
    }

    // Test that the lives are tracked properly
    @Test
    public void testLivesDecrement() {
        int originalLives = game.getNumLives();
        ArrayList<Minion> minions = new ArrayList<>();

        // add some minions and let them march all the way
        for (int i = 0; i < 5; i++)
            minions.add(new GroundUnit());

        for (Minion m : minions)
            m.moveForward(Float.MAX_VALUE);

        // Ensure that the number of lives has been decremented by the proper number
        assertEquals(originalLives - minions.size(), game.getNumLives());
    }

    // Test Minion advance
    @Test
    public void testMinionAdvance() {
        Minion ground = new GroundUnit();
        Minion air = new AirUnit();

        // level takes in minion, random start

        ground._setLocation(13, 2);
        air._setLocation(14, 2);

        ground.moveForward(20);
        air.moveForward(20);

        // TODO: PHASE 2: Ensure that the minions advance at the correct speeds

        // Ensure that the minions stay on the paths as they advance.
        assertTrue(ground._getLocation().equals(new Location(18, 16)));
        assertTrue(air._getLocation().equals(new Location(18, 16)));
    }

    // Test tower fire angle
    @Test
    public void testTowerFileAngle() {
        Board board = new Board(64, 48);

        // Place a tower and set its angle
        Tower airGroundTower = new AirGroundTower();
        Tower airTower = new AirTower();
        Tower groundTower = new GroundTower();

        airGroundTower.setAngle(0);
        airTower.setAngle(0);
        groundTower.setAngle(0);

        airGroundTower.setPosition(24, 13);
        airTower.setPosition(24, 12);
        groundTower.setPosition(24, 11);

        // Place a minion and place it on a spot where the tower can hit it
        Minion groundUnit = new GroundUnit();
        groundUnit._setLocation(26, 13);

        // TODO: PHASE 2: Have the tower attack the minion and ensure that the minion dies

        // TODO: PHASE 2: Place a tower and set its angle
        // TODO: PHASE 2: Place a minion within range of the tower, but at the wrong angle
        // TODO: PHASE 2: Ensure that the tower does not attack the minion
    }

    // Test Tower attack mechanisms
    @Test
    public void testTowerAttackMechanisms() {
        // Place one of each type of tower
        Tower airGroundTower = new AirGroundTower();
        Tower airTower = new AirTower();
        Tower groundTower = new GroundTower();

        airGroundTower.setAngle(0);
        airTower.setAngle(0);
        groundTower.setAngle(-1);

        airGroundTower.setPosition(24, 13);
        airTower.setPosition(13, 35);
        groundTower.setPosition(48, 21);

        // TODO: PHASE 2: Place a few minions of the correct type in the fire area of the tower.
        Minion groundUnit = new GroundUnit();
        groundUnit._setLocation(45, 21);

        // TODO: PHASE 2: Ensure that the minions die


        // TODO: PHASE 2: Place a few minions of the incorrect type in the fire area of the tower.
        // TODO: PHASE 2: Ensure that the minions do not die
    }

    // Test player gold reward
    @Test
    public void testPlayerGoldReward() {
        Player player = new Player("test", Color.BLUE);

        // Place a tower and minion
        Tower tower = new AirTower();
        Minion minion = new AirUnit();

        // Kill the minion
        minion.attacked(tower, Integer.MAX_VALUE);

        // Ensure that the player's gold is incremented by the value of the minion
        assertEquals(5, player.getGold());
    }

}
