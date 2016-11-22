import angleDefenseLogic.Game;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ResourceLoadTests {

    static Game game;

    @BeforeClass
    public static void setUp() {
        game = new Game();
    }

    @Before
    public void before() {
        game.init("default.config");
    }

    // Test board configuration load
    @Test
    public void testBoardConfigLoad() {
        // TODO: Ensure that the board configuration was loaded correctly
    }

    // Test level configuration load
    @Test
    public void testLevelConfigLoad() {
        // TODO: Ensure that the level configuration was loaded correctly
    }

}
