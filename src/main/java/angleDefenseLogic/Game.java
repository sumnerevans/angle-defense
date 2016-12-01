package angleDefenseLogic;

import angleDefenseGui.*;
import com.google.gson.*;

import java.io.*;
import java.util.*;

public class Game {

    private int numLives;
    private Board board;
    private ArrayList<Level> levels;

    private transient Level currentLevel;
    private transient DrawContext context;
    private transient Hud hud;

    public Game() {
        this.hud = new Hud();
        this.context = new DrawContext();
    }

    public static Game NewGame(String configFile) throws FileNotFoundException {
        // Create a Game object from the config JSON
        Gson gson = new GsonBuilder().registerTypeAdapter(Board.class, new Board.Builder())
                .setPrettyPrinting().create();

        InputStreamReader streamReader = new InputStreamReader(Util.newFileStream(configFile));
        BufferedReader r = new BufferedReader(streamReader);

        Game g = gson.fromJson(r, Game.class);
        g.currentLevel = g.levels.get(0);
        return g;
    }

    public void loop() {
        // TODO: Implement
    }

    public void tick() {
        // TODO Auto-generated method stub

    }

    public Board getBoard() {
        return this.board;
    }

    public ArrayList<Level> getLevels() {
        return this.levels;
    }

    public Level getLevel() {
        return this.currentLevel;
    }

    public int getNumLives() {
        return this.numLives;
    }
}
