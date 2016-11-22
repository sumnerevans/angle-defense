import angleDefenseLogic.Game;
import angleDefenseLogic.Square;
import org.junit.*;

import static junit.framework.TestCase.*;

public class ConfigLoadTests {

    static Game game;

    @BeforeClass
    public static void setUp() {
        game = new Game();
    }

    @Before
    public void before() {
        game.init("default-config.json");
    }

    // Test board configuration load: Ensure that the board configuration was loaded properly
    @Test
    public void testBoardConfigLoad() {
        // Ensure that the width and height of the board are correct
        assertEquals(50, game.getBoard().getHeight());
        assertEquals(50, game.getBoard().getWidth());

        // Test a few of the squares and ensure that they are of the proper type
        assertEquals(Square.SquareType.FLAT_GROUND, game.getBoard().getSquare(0, 0).getSquareType());

        // TODO: Test a few more squares and ensure that they are of the proper type


        // TODO: Test some squares and ensure that they have the correct cliff sides
    }

    // Test level configuration load: Ensure that the level configuration was loaded correctly
    @Test
    public void testLevelConfigLoad() {
        // Ensure that the correct number of levels were loaded
        assertEquals(10, game.getLevels().size());

        // TODO: Test a few levels ensuring that their configuration was loaded correctly
    }

}
