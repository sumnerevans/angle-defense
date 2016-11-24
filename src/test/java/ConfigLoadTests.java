import angleDefenseLogic.*;
import org.junit.*;

import java.io.FileNotFoundException;
import java.util.*;

import static junit.framework.TestCase.*;

public class ConfigLoadTests {

    static Game game;

    @Before
    public void before() throws FileNotFoundException {
        // Reinitialize the game for every test
        game = Game.NewGame("test-config.json");
    }

    // Test board configuration load: Ensure that the board configuration was loaded properly
    @Test
    public void testBoardConfigLoad() {
        Board board = game.getBoard();

        // Ensure that the width and height of the board are correct
        assertEquals(48, board.getHeight());
        assertEquals(64, board.getWidth());

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

        // Test some squares and ensure that they have the correct cliff sides
        assertTrue(board.getSquare(24, 4).getCliffSides().contains(Square.CliffSide.RIGHT));
        assertTrue(board.getSquare(27, 4).getCliffSides().contains(Square.CliffSide.LEFT));
        assertTrue(board.getSquare(33, 40).getCliffSides().contains(Square.CliffSide.TOP));
        assertTrue(board.getSquare(40, 38).getCliffSides().contains(Square.CliffSide.BOTTOM));

        List<Square.CliffSide> cliffSides4616 = board.getSquare(46, 16).getCliffSides();
        assertTrue(cliffSides4616.contains(Square.CliffSide.TOP));
        assertTrue(cliffSides4616.contains(Square.CliffSide.LEFT));
        assertTrue(cliffSides4616.size() == 2);

        List<Square.CliffSide> cliffSides2413 = board.getSquare(24, 13).getCliffSides();
        assertTrue(cliffSides2413.contains(Square.CliffSide.RIGHT));
        assertTrue(cliffSides2413.contains(Square.CliffSide.BOTTOM));
        assertTrue(cliffSides4616.size() == 2);
    }

    // Test level configuration load: Ensure that the level configuration was loaded correctly
    @Test
    public void testLevelConfigLoad() {
        List<Level> levels = game.getLevels();

        // Ensure that the correct number of levels were loaded
        assertEquals(1, levels.size());

        Level firstLevel = levels.get(0);

        // Test a few levels ensuring that their configuration was loaded correctly
        assertEquals(Minion.Type.GROUND, firstLevel.getWave(0).getMinionType());
        assertEquals(15, firstLevel.getWave(0).getCount());
        assertEquals(15, firstLevel.getWave(0).getStart());
        assertEquals(20, firstLevel.getWave(0).getEnd());
    }

}
