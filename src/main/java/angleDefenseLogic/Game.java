package angleDefenseLogic;

import angleDefenseLogic.minions.*;
import angleDefenseLogic.towers.*;
import com.google.gson.*;
import config.*;
import draw.*;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class Game {
    private int numLives;
    private Board board;
    private ArrayList<Level> levels;

    public transient final ArrayList<Minion> minions;
    public transient final ArrayList<Tower> towers;

    private transient Level currentLevel;
    public transient final DrawContext draw;
    public transient final Hud hud;
    public transient final JPanel display;
    private transient boolean gameOver = false;

    Model teapot;

    private Game() {
        this.hud = new Hud();
        this.draw = new DrawContext();
        this.minions = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.display = draw.new Panel();
    }

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

    public float rottest = 0;

    public void loop() throws IOException {
        // TODO: Do other stuff
        draw.init();

        draw.loadAssets();
        teapot = draw.getModel("teapot");

        draw.setMapSize(-2, -2, 4, 4);
        draw.setVerticalRange(-1, 5);

        while (!this.gameOver) {
            this.tick();
            
            draw.preDraw();

            rottest += 0.1;
            draw.setModelTransform(Matrix.gen(0, 0, 0, rottest));

            teapot.draw(draw);

            this.render();
            draw.postDraw();

            display.repaint();
        }

        draw.close();
    }

    private void tick() {
        for (Minion m : this.minions) {
            m.tick(this);
        }

        for (Tower t : this.towers) {
            t.tick(this);
        }
    }

    private void render() {
        board.draw(draw);
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

}
