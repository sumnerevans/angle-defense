package angleDefenseLogic;

import angleDefenseGui.DrawContext;

import java.util.*;

public class Game  {

    private String gameConfigSource;
    private int lives;
    private Board board;
    private ArrayList<Level> levels;
    private Level currentLevel;
    private DrawContext context;
    private Hud hud;

    public void init(String gameConfigSource) {
        // TODO: This function needs to clear out all old configuration and reinitialize everything
        this.gameConfigSource = gameConfigSource;
    }

    public void loop() {
        // TODO: Implement
    }

    public void tick() {
        // TODO Auto-generated method stub

    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }
}
