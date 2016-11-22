import angleDefenseLogic.*;
import org.junit.*;

import java.io.FileNotFoundException;
import java.util.*;

import static junit.framework.TestCase.*;

public class ConfigLoadTests {

    static Game game;

    @Before
    public void before() throws FileNotFoundException {
        game = Game.NewGame("test-config.json");
    }

    // Test board configuration load: Ensure that the board configuration was loaded properly
    @Test
    public void testBoardConfigLoad() {
        Board board = game.getBoard();

        // Ensure that the width and height of the board are correct
        assertEquals(50, board.getHeight());
        assertEquals(50, board.getWidth());

        // Test a few of the squares and ensure that they are of the proper type
        assertEquals(Square.SquareType.GROUND, board.getSquare(0, 0).getSquareType());
        assertEquals(Square.SquareType.GROUND, board.getSquare(11, 0).getSquareType());
        assertEquals(Square.SquareType.GROUND, board.getSquare(14, 13).getSquareType());
        assertEquals(Square.SquareType.GROUND, board.getSquare(36, 15).getSquareType());
        assertEquals(Square.SquareType.GROUND, board.getSquare(50, 24).getSquareType());

        assertEquals(Square.SquareType.TRENCH, board.getSquare(13, 2).getSquareType());
        assertEquals(Square.SquareType.TRENCH, board.getSquare(15, 23).getSquareType());
        assertEquals(Square.SquareType.TRENCH, board.getSquare(44, 23).getSquareType());
        assertEquals(Square.SquareType.TRENCH, board.getSquare(48, 38).getSquareType());
        assertEquals(Square.SquareType.TRENCH, board.getSquare(31, 38).getSquareType());

        // Ensure that the correct squares have decorations
        assertTrue(board.getSquare(30, 30).getDecorations().contains(Square.Decoration.PILLAR));
        assertTrue(board.getSquare(33, 30).getDecorations().contains(Square.Decoration.PILLAR));
        assertTrue(board.getSquare(32, 28).getDecorations().contains(Square.Decoration.PILLAR));

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
