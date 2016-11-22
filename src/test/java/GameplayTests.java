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
        game.init("default.config");
    }

    // Test that the level increments when the player beats a level
    @Test
    public void testLevelIncrement() {

    }

    // Test that the lives are tracked properly
    @Test
    public void testLivesDecrement() {

    }

}
