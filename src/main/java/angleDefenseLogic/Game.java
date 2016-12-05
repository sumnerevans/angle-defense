package angleDefenseLogic;

import angleDefenseLogic.minions.*;
import angleDefenseLogic.towers.*;
import com.google.gson.*;
import config.*;
import draw.DrawContext;
import draw.OBJLoader;
import draw.VertexBuffer;
import org.omg.CORBA.OBJ_ADAPTER;

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

    VertexBuffer teapot;

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

    public void loop() {
        // TODO: Do other stuff
        draw.init();

        try {
            teapot = OBJLoader.load(Util.newFileStream("assets/teapot.obj"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (!this.gameOver) {
            this.tick();
            
            draw.preDraw();
            teapot.draw();
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
