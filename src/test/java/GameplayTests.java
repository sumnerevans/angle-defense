import angleDefenseLogic.*;
import angleDefenseLogic.minions.*;
import angleDefenseLogic.towers.*;
import config.*;
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
        game = Game.NewGame("test-config.json");
    }

    // Test that the level increments when the player beats a level
    @Test
    public void testLevelIncrement() {
        Tower boom = new GroundTower(player, new Location(0, 0));

        // add a bunch of minions
        Minion one = Minion.Type.GROUND.create(new Node(new Location(1, 1), null));
        Minion two = Minion.Type.GROUND.create(new Node(new Location(1, 1), null));
        Minion three = Minion.Type.GROUND.create(new Node(new Location(1, 1), null));
        Minion four = Minion.Type.GROUND.create(new Node(new Location(1, 1), null));
        Minion five = Minion.Type.GROUND.create(new Node(new Location(1, 1), null));
        Minion six = Minion.Type.GROUND.create(new Node(new Location(1, 1), null));

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
            minions.add(Minion.Type.GROUND.create(new Node(new Location(1, 1), null)));

        for (Minion m : minions)
            m.moveForward(Float.MAX_VALUE);

        // Ensure that the number of lives has been decremented by the proper number
        assertEquals(originalLives - minions.size(), game.getNumLives());
    }

    @Test
    public void testMinionSpawn() {
        game.getLevel().spawnMinions(new TimeRange(0, 1), game);

        assertEquals(1, game._getMinions().size());
    }

    // Test minion move
    @Test
    public void testMinionMove() {
        // Create a node chain
        Node node4 = new Node(new Location(9, 9), null);
        Node node3 = new Node(new Location(9, 4), node4);
        Node node2 = new Node(new Location(2, 4), node3);
        Node node1 = new Node(new Location(2, 1), node2);

        Minion minion = Minion.Type.AIR.create(node1);

        // Ensure that the minion moves properly
        minion.moveForward(1);
        assertEquals(new Location(2, 2), minion._getLocation());

        minion.moveForward(3);
        assertEquals(new Location(3, 4), minion._getLocation());

        minion.moveForward(6);
        assertEquals(new Location(9, 4), minion._getLocation());

        minion.moveForward(7);
        assertEquals(new Location(9, 9), minion._getLocation());
    }

    // Test Tower attack mechanisms
    @Test
    public void testTowerAttackMechanisms() {
        // Place one of each type of tower
        Tower airGroundTower = new AirGroundTower(player, new Location(0, 0));
        Tower airTower = new AirTower(player, new Location(0, 0));
        Tower groundTower = new GroundTower(player, new Location(0, 0));

        // Create a few units
        Minion groundUnit = Minion.Type.GROUND.create(new Node(new Location(1, 1), null));
        Minion airUnit = Minion.Type.AIR.create(new Node(new Location(1, 1), null));

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
        groundUnit = Minion.Type.GROUND.create(new Node(new Location(1, 1), null));
        airUnit = Minion.Type.AIR.create(new Node(new Location(1, 1), null));

        airGroundTower.attack(groundUnit);
        airGroundTower.attack(airUnit);

        assertTrue(groundUnit.isDead());
        assertTrue(airUnit.isDead());
    }

    // Test player gold reward
    @Test
    public void testPlayerGoldReward() {

        // Place a tower and minion
        Tower tower = new AirTower(player, new Location(0, 0));
        Minion minion = Minion.Type.AIR.create(new Node(new Location(1, 1), null));

        // Kill the minion
        minion.attacked(tower, Integer.MAX_VALUE);

        // Ensure that the player's gold is incremented by the value of the minion
        // TODO: UPDATE TO THE CORRECT GOLD REWARD
        assertEquals(5, player.getGold());
    }

}
