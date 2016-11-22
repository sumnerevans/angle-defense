package angleDefenseLogic;

import angleDefenseGui.*;
import com.google.gson.*;
import com.google.gson.stream.*;

import java.beans.Transient;
import java.io.*;
import java.util.*;

public class Game {

    private String gameConfigSource;
    private int numLives;
    private Board board;
    private ArrayList<Level> levels;

    private transient  Level currentLevel;
    private transient DrawContext context;
    private transient Hud hud;

    public Game() {}

    private static InputStream newFileStream(String path) throws FileNotFoundException {
        ClassLoader cl = Board.class.getClassLoader();
        InputStream stream = cl.getResourceAsStream(path);

        if (stream == null) {
            try {
                stream = new FileInputStream(new File(new File("data/"), path));
            } catch (FileNotFoundException ex) {
                throw new FileNotFoundException(String.format("Could not find file %s in jar or data/", path));
            }
        }

        return stream;
    }

    public static Game NewGame(String configFile) throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader r = new BufferedReader(new InputStreamReader(newFileStream(configFile)));
        return gson.fromJson(r, Game.class);
    }

    public void init(String gameConfigSource) {
        // TODO: This function needs to clear out all old configuration and reinitialize everything
        this.gameConfigSource = gameConfigSource;

        this.hud = new Hud();
        this.context = new DrawContext();
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

    public int getLevel() {
        return this.currentLevel.getLevelNum();
    }

    public int getNumLives() {
        return this.numLives;
    }
}
