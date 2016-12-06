package angledefense.logic;

import angledefense.config.Board;
import angledefense.config.Level;
import angledefense.config.Square;
import angledefense.draw.DrawContext;
import angledefense.draw.ModelHandle;
import angledefense.logic.minions.Minion;
import angledefense.logic.towers.Tower;
import angledefense.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Game {
    // Global Constants
    public static final int INITIAL_HEIGHT = 700;
    public static final int INITIAL_WIDTH = 700;
    public static final long MS_PER_FRAME = 1000 / 30;

    private transient final Player player;

    // These are not loaded via the Gson JSON deserializer, so they have to be transient.S
    public transient final ArrayList<Minion> minions;
    public transient final HashMap<Integer, Tower> towers;
    public transient final DrawContext draw;

    private transient Level currentLevel;
    private transient boolean gameOver = false;
    private transient boolean playerWon = false;

    private transient ModelHandle towerSelector = ModelHandle.create("tower_selector");
    private transient ModelHandle selector = ModelHandle.create("selector");

    // Private instance variables loaded from JSON
    private int numLives;
    private Board board;
    private ArrayList<Level> levels;
    private float playRate = 1;

    // Other private instance variables
    private float gameStart;
    private Location selected;
    private ArrayList<BiConsumer<Integer, Location>> onclick = new ArrayList<>();
    private Instant levelStartTime;
    private Instant now;

    private transient Consumer<Game> uilisten;

    // For tests
    public boolean preventGui = false;

    /**
     * Create a game object. Called by Gson somehow.
     */
    private Game() {
        this.player = new Player(this, "Player", Color.BLUE);
        this.draw = new DrawContext(this);
        this.minions = new ArrayList<>();
        this.towers = new HashMap<>();
    }

    /**
     * Create a new Game from a config file
     *
     * @param configFile
     * @return the new game
     * @throws FileNotFoundException thrown if the config can't be found
     * @throws JsonParseException    throw if the config can't be parsed
     */
    public static Game newGame(String configFile) throws FileNotFoundException, JsonParseException {
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

    public void setUIListener(Consumer<Game> listener) {
        uilisten = listener;
    }

    public void notifyUI() {
        if (this.preventGui) return;
        uilisten.accept(this);
    }

    /**
     * Implements the game loop.
     *
     * @throws IOException
     */
    public void loop() throws IOException {
        draw.init();

        draw.setVerticalRange(-2, 0.8f * (board.width + board.height));

        Instant last = Instant.now();
        levelStartTime = last;

        while (!gameOver) {
            Instant now = Instant.now();

            tick(last, now);

            draw.preDraw();
            render();
            draw.postDraw();

            long towait = MS_PER_FRAME - now.until(Instant.now(), ChronoUnit.MILLIS);
            if (towait > MS_PER_FRAME) towait = MS_PER_FRAME;
            if (towait > 0) {
                try {
                    Thread.sleep(towait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            last = now;
        }

        this.playerWon = this.numLives > 0;

        draw.close();
    }

    /**
     * Tick function. This is called on every tick and then calls tick on the other things that
     * need to tick.
     *
     * @param last
     * @param now
     */
    private void tick(Instant last, Instant now) {
        this.getLevel().spawnMinions(new TimeRange(levelStartTime, last, now, this.playRate), this);
        this.now = Instant.now();
        float dt = TimeRange.relativeSecs(last, now) * this.playRate;

        for (Tower t : this.towers.values()) {
            t.tick(this, dt);
        }

        ArrayList<Minion> forRemoval = new ArrayList<>();

        for (Minion m : this.minions) {
            m.tick(this, dt);

            if (m.gotToCastle()) this.looseLife();

            if (m.shouldRemove()) forRemoval.add(m);
        }

        forRemoval.forEach(this.minions::remove);

        // If they've won, increment the level
        if (!this.currentLevel.hasMoreMinions(TimeRange.relativeSecs(levelStartTime, now)) &&
                this.minions.size() == 0) {
            this.incrementLevel();
        }
    }

    /**
     * Increments the level.
     */
    private void incrementLevel() {
        int nextLevelIndex = this.levels.indexOf(this.currentLevel) + 1;

        if (nextLevelIndex == this.levels.size()) {
            this.gameOver = true;
            return;
        }

        this.currentLevel = this.levels.get(nextLevelIndex);
        levelStartTime = Instant.now();
    }

    private void render() {
        if (selected != null) {
            ModelHandle sel;
            if (getTower(selected) != null) sel = towerSelector;
            else sel = selector;
            sel.setTransform(selected.floor(), 1, -.8f, 0);
            sel.draw();
        }

        board.draw(draw);
        towers.forEach((p, t) -> t.draw(draw));
        minions.forEach(m -> m.draw(draw));
    }

    private void looseLife() {
        this.numLives--;

        if (this.numLives <= 0) {
            this.gameOver = true;
        }
    }

    public void spawnMinion(Minion m) {
        this.minions.add(m);
    }

    public Tower getTower(Location l) {
        return getTower(l.intX(), l.intY());
    }

    public Tower getTower(int x, int y) {
        int i = y * board.width + x;
        return towers.get(i);
    }

    public boolean buildTower(Tower t) throws Exception {
        t.onSpawn();
        int i = t.y * board.width + t.x;
        if (this.towers.containsKey(i)) throw new Exception("There is already a tower there!");
        if (board.getSquare(t.x, t.y).getSquareType() != Square.SquareType.GROUND)
            throw new Exception("Towers can not be placed there!");
        this.towers.put(i, t);
        return true;
    }

    public void onBoardClick(Location loc, int button, boolean pressed) {
        if (pressed) {
            if (loc.getX() < 0 || loc.getX() >= board.width + 1 || loc.getY() < 0 || loc.getY() >= board.height + 1) {
                selected = null;
            } else {
                selected = loc;
                onclick.forEach(c -> c.accept(button, loc));
            }
        }
    }

    public void addClickListener(BiConsumer<Integer, Location> listener) {
        onclick.add(listener);
    }

    public Location getSelectedLocation() {
        return selected;
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

    public void setGameOver() {
        this.gameOver = true;
    }

    public Player getPlayer() {
        return player;
    }

    // Testing only
    public ArrayList<Minion> _getMinions() {
        return this.minions;
    }

    public void simulateSeconds(float seconds) {
        Instant current = Instant.now();
        this.levelStartTime = current;
        long ms = (long) (seconds * 1000);

        while (ms > 0) {
            Instant next = current.plus(MS_PER_FRAME, ChronoUnit.MILLIS);
            this.tick(current, next);
            current = next;
            ms -= MS_PER_FRAME;
        }
    }

    public boolean didPlayerWin() {
        return playerWon;
    }

    public Instant getNow() {
        return now;
    }
}
