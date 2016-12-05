package angledefense.logic;

import angledefense.gui.Hud;
import angledefense.logic.minions.*;
import angledefense.logic.towers.*;
import com.google.gson.*;
import angledefense.config.*;
import angledefense.draw.*;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class Game {
    private final Player player;
    private int numLives;
    private Board board;
    private ArrayList<Level> levels;

    public transient final ArrayList<Minion> minions;
    public transient final ArrayList<Tower> towers;

    private transient Level currentLevel;
    public transient final DrawContext draw;
    public transient final Hud hud;
    private transient boolean gameOver = false;

    private transient ModelHandle teapot = ModelHandle.create("teapot");

    private Game() {
        this.hud = new Hud();
        this.player = new Player(hud.getName(), hud.getColor());
        this.draw = new DrawContext(this);
        this.minions = new ArrayList<>();
        this.towers = new ArrayList<>();
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

        draw.setVerticalRange(-1, 0.8f * (board.width + board.height));

        while (!this.gameOver) {
            this.tick();

            draw.preDraw();
            this.render();
            draw.postDraw();
        }

        draw.close();
    }

    private void tick() {
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

    private void render() {
        for (int x = 2; x < board.width; x += 5) {
            for (int y = 0; y < board.height; y += 5) {
                teapot.setTransform(new Location(x, y), 3, 0, 3.1416f / 4);
                teapot.draw();
            }
        }

        board.draw(draw);
        towers.forEach(t -> t.draw(draw));
        minions.forEach(m -> m.draw(draw));
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

}
