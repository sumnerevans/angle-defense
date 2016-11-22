package tests;

import static org.junit.Assert.*;

import org.junit.*;

import angleDefenseLogic.*;

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
