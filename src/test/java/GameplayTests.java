import angledefense.logic.*;
import angledefense.logic.minions.*;
import angledefense.logic.towers.*;
import angledefense.config.*;
import org.junit.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    public void testLevelIncrement() throws IOException, InterruptedException {
        game.simulateSeconds(60);

        Tower boom = new GroundTower(player, new Location(0, 0));
        for (Minion m : game._getMinions()) {
            m.attacked(boom, Integer.MAX_VALUE);
        }

        game.simulateSeconds(1);

        // Ensure that the level was incremented
        assertEquals(2, game.getLevel().getLevelNum());
    }

    // Test that the lives are tracked properly
    @Test
    public void testLivesDecrement() {
        // Ensure that the number of lives is decremented properly
        game.simulateSeconds(100);

        assertEquals(9, game.getNumLives());
    }

    @Test
    public void testMinionSpawn() {
        game.getLevel().spawnMinions(new TimeRange(15.0f, 15.4f), game);
        assertEquals(1, game._getMinions().size());

        game.getLevel().spawnMinions(new TimeRange(15.4f, 16.4f), game);
        assertEquals(4, game._getMinions().size());
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
        assertEquals(5, player.getGold());
    }

}
