import angleDefenseLogic.Game;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameplayTests {
    static Game game;

    @BeforeClass
    public static void setUp() {
        game = new Game();
    }

    @Before
    public void before() {
        game.init("default-config.json");
    }

    // Test that the level increments when the player beats a level
    @Test
    public void testLevelIncrement() {

    }

    // Test that the lives are tracked properly
    @Test
    public void testLivesDecrement() {

    }

    // Test Minion advance
    @Test
    public void testMinionAdvance() {
        // TODO: Ensure that the minions advance at the correct speeds and that they stay on the
        // paths.
    }

    // Test tower fire angle
    @Test
    public void testTowerFileAngle() {

    }

    // Test Tower attack mechanisms
    @Test
    public void testTowerAttackMechanisms() {
        // Ensure that the towers only attack and kill minions of the type that they are capable of
        // killing
    }

    // Test player gold reward
    @Test
    public void testPlayerGoldReward() {
        // Ensure that when one of the player's towers kills a minion, the minion gives money to
        // the player.
    }

}
