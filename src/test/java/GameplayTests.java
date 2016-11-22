import angleDefenseLogic.*;
import angleDefenseLogic.Minions.AirUnit;
import angleDefenseLogic.Minions.GroundUnit;
import angleDefenseLogic.Towers.AirGroundTower;
import angleDefenseLogic.Towers.AirTower;
import angleDefenseLogic.Towers.GroundTower;
import org.junit.*;

import java.awt.*;
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
        Tower boom = new GroundTower();

        // TODO: add a bunch of minions
        Minion one = new GroundUnit();
        Minion two = new GroundUnit();
        Minion three = new GroundUnit();
        Minion four = new GroundUnit();
        Minion five = new GroundUnit();
        Minion six = new GroundUnit();
        // TODO: kill all the minions

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

        // TODO: add some minions and let them march all the way
        Minion one = new GroundUnit();

        minions.add(one);
        minions.add(one);
        minions.add(one);

        for (Minion m : minions) {
            m.moveForward(Float.MAX_VALUE);
        }

        // Ensure that the number of lives has been decremented by the proper number
        assertEquals(originalLives - minions.size(), game.getNumLives());
    }

    // Test Minion advance
    @Test
    public void testMinionAdvance() {
        Minion one = new GroundUnit();
        Minion two = new AirUnit();


        // TODO: Ensure that the minions advance at the correct speeds

        // TODO: Ensure that the minions stay on the paths as they advance.

    }

    // Test tower fire angle
    @Test
    public void testTowerFileAngle() {
        Board board = new Board(64,48);
        // TODO: DO THESE TESTS FOR ALL TOWER TYPES
        // TODO: Place a tower and set its angle
        Tower bang = new AirGroundTower();
        Tower pew = new AirTower();
        Tower kaboom = new GroundTower();

        bang.setAngle(0);
        pew.setAngle(0);
        kaboom.setAngle(0);

        bang.setPosition(24, 13);
        pew.setPosition(24,12);
        kaboom.setPosition(24,11);

        // TODO: Place a minion and place it on a spot where the tower can hit it
        Minion ded = new GroundUnit();
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
        Tower bang = new AirGroundTower();
        Tower pew = new AirTower();
        Tower kaboom = new GroundTower();

        bang.setAngle(0);
        pew.setAngle(0);
        kaboom.setAngle(0);

        bang.setPosition(24, 13);
        pew.setPosition(24,12);
        kaboom.setPosition(24,11);
        // TODO: Place a few minions of the correct type in the fire area of the tower.
        // TODO: Ensure that the minions die

        // TODO: Place a few minions of the incorrect type in the fire area of the tower.
        // TODO: Ensure that the minions do not die
    }

    // Test player gold reward
    @Test
    public void testPlayerGoldReward() {
        Player test = new Player("test", Color.BLUE);
        // TODO: Place a tower
        Tower pew = new AirTower();
        // TODO: Place a minion
        Minion eek = new AirUnit();
        // TODO: Kill a minion
        eek.attacked(pew, Integer.MAX_VALUE);
        // TODO: Ensure that the player's gold is incremented by the value of the minion
        assertEquals(test.getGold(),3); // TODO: change the 3 to whatever gold amount it should be
    }

}
