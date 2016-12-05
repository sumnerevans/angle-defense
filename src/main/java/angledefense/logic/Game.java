package angledefense.logic;

import angledefense.gui.*;
import angledefense.logic.minions.*;
import angledefense.logic.towers.*;
import com.google.gson.*;
import angledefense.config.*;

import java.io.*;
import java.util.*;

public class Game {

    private int numLives;
    private Board board;
    private ArrayList<Level> levels;

    private ArrayList<Minion> minions;
    private ArrayList<Tower> towers;

    private transient Level currentLevel;
    private transient DrawContext context;
    private transient Hud hud;
    private boolean gameOver = false;

    private Game() {
        this.hud = new Hud();
        this.context = new DrawContext();
        this.minions = new ArrayList<>();
        this.towers = new ArrayList<>();
    }

    public static Game NewGame(String configFile) throws FileNotFoundException, JsonParseException {
        // Create a Game object from the config JSON
        Gson gson = new GsonBuilder().registerTypeAdapter(Board.class, new Board.Builder())
                .setPrettyPrinting().create();

        InputStreamReader streamReader = new InputStreamReader(Util.newFileStream(configFile));
        BufferedReader r = new BufferedReader(streamReader);

        Game g = gson.fromJson(r, Game.class);

        if (g == null) {
            throw new JsonParseException("Invalid JSON");
        }

        g.currentLevel = g.levels.get(0);
        return g;
    }

    public void loop() {
        // TODO: Do other stuff
        while (!this.gameOver) {
            this.tick();
        }
    }

    public void tick() {
        ArrayList<Minion> forRemoval = new ArrayList<>();

        for (Minion m : this.minions) {
            m.tick(this);

            if (m.shouldRemove()) {
                forRemoval.add(m);
            }
        }

        for (Minion m : forRemoval) {
            this.minions.remove(m);
        }

        for (Tower t : this.towers) {
            t.tick(this);
        }
    }

    public void spawnMinion(Minion m) {
        this.minions.add(m);
    }

    public void buildTower(Tower t) {
        this.towers.add(t);
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

    // Testing only
    public ArrayList<Minion> _getMinions() {
        return this.minions;
    }
}
