package angledefense.logic;

import java.awt.*;
import java.security.InvalidParameterException;

/**
 * Created by Sumner on 11/21/16.
 */
public class Player {
    private int gold = 100;
    private String name;
    private Color color;
    private Game game;

    public Player(Game game, String name, Color color) {
        this.game = game;
        this.name = name;
        this.color = color;
    }

    public void spendGold(int amount) throws Exception {
        if (amount > gold)
            throw new Exception("You are too poor! NO TOWER FOR YOU!");
        gold -= amount;
        game.notifyUI();
    }

    public void addGold(int amount) {
        gold += amount;
        game.notifyUI();
    }

    public int getGold() {
        return gold;
    }

    public Game getGame() {
        return game;
    }
}
