package angledefense.logic;

import angledefense.logic.minions.*;
import angledefense.logic.towers.*;
import com.google.gson.*;
import angledefense.config.*;
import angledefense.draw.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;

public class Game {
    private final Player player;
    private int numLives;
    private Board board;
    private ArrayList<Level> levels;
    private float gameStart;
    private float timeOfLastTick;
    private boolean forTest = false;
    float testTime = 0;
    private Location selected;
    private ArrayList<BiConsumer<Integer, Location>> onclick = new ArrayList<>();

    public transient final ArrayList<Minion> minions;
    public transient final ArrayList<Tower> towers;

    private transient Level currentLevel;
    public transient final DrawContext draw;
    private transient boolean gameOver = false;

    private transient ModelHandle selector = ModelHandle.create("selector");

    private Game() {
        this.player = new Player("Player", Color.BLUE);
        this.draw = new DrawContext(this);
        this.minions = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.gameStart = new Date().getTime() / 1000;
    }

    public static Game newGame(String configFile) throws FileNotFoundException, JsonParseException {
        // Create a Game object from the angledefense.config JSON
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

    public void loop() throws IOException {
        draw.init();

        draw.setVerticalRange(-2, 0.8f * (board.width + board.height));

        while (!this.gameOver) {
            this.tick();

            draw.preDraw();
            this.render();
            draw.postDraw();
        }

        draw.close();
    }

    private void tick() {
        float currentTime = this.timeSinceGameStart();
        float dt = currentTime - timeOfLastTick;

        this.getLevel().spawnMinions(
                new TimeRange(this.timeOfLastTick, currentTime),
                this);

        for (Tower t : this.towers) {
            t.tick(this, dt);
        }

        ArrayList<Minion> forRemoval = new ArrayList<>();

        for (Minion m : this.minions) {
            m.tick(this, dt);

            if (m.gotToCastle()) this.looseLife();

            if (m.shouldRemove()) forRemoval.add(m);
        }

        forRemoval.forEach(this.minions::remove);

        if (!this.currentLevel.hasMoreMinions(currentTime) && this.minions.size() == 0) {
            this.incrementLevel();
        }

        this.timeOfLastTick = currentTime;
    }

    private float timeSinceGameStart() {
        if (this.forTest) return testTime;
        return new Date().getTime() / 1000 - this.gameStart;
    }

    private void incrementLevel() {
        int nextLevelIndex = this.levels.indexOf(this.currentLevel) + 1;

        if (nextLevelIndex == this.levels.size()) {
            this.gameOver = true;
            return;
        }

        this.currentLevel = this.levels.get(nextLevelIndex);
    }

    private void render() {
        if (selected != null) {
            selector.setTransform(selected.floor(), 1, -.8f, 0);
            selector.draw();
        }

        board.draw(draw);
        towers.forEach(t -> t.draw(draw));
        minions.forEach(m -> m.draw(draw));
    }

    private void looseLife() {
        this.numLives--;
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

    public void setGameOver() {
        this.gameOver = true;
    }

    public Player getPlayer() {
        return player;
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

    // Testing only
    public ArrayList<Minion> _getMinions() {
        return this.minions;
    }

    public void simulateSeconds(int seconds) {
        this.forTest = true;
        for (int i = 0; i < seconds; i++) {
            this.tick();
            this.testTime++;
        }
    }
}
