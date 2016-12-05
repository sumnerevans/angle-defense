package angledefense.logic;

import java.awt.*;
import java.security.InvalidParameterException;
import java.util.*;

/**
 * Created by Sumner on 11/21/16.
 */
public class Player {
    private int gold;
    private String name;
    private Color color;

    public Player(String name, Color color) {
        this.gold = 0;
        this.name = name;
        this.color = color;
    }

    public void addGold(int amount) {
        if (amount < 0)
            throw new InvalidParameterException("'amount' parameter cannot be negative.");

        gold += amount;
    }

    public void sendGold(Player recipient, int amount) {
        if (this.gold < amount)
            throw new InvalidParameterException("Cannot send more gold than they own.");

        this.gold -= amount;
        recipient.gold += amount;
    }

    public int getGold() {
        return gold;
    }
}
