import angleDefenseLogic.*;
import org.junit.*;

import java.util.*;

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
        Board board = game.getBoard();

        // Ensure that the width and height of the board are correct
        assertEquals(50, board.getHeight());
        assertEquals(50, board.getWidth());

        // Test a few of the squares and ensure that they are of the proper type
        assertEquals(Square.SquareType.FLAT_GROUND, board.getSquare(0, 0).getSquareType());

        // TODO: Test a few more squares and ensure that they are of the proper type

        // TODO: Test some squares and ensure that they have the correct cliff sides
    }

    // Test level configuration load: Ensure that the level configuration was loaded correctly
    @Test
    public void testLevelConfigLoad() {
        List<Level> levels = game.getLevels();

        // Ensure that the correct number of levels were loaded
        assertEquals(10, levels.size());

        // TODO: Test a few levels ensuring that their configuration was loaded correctly
    }

}
