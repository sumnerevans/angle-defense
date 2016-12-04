package angleDefenseLogic;

import javax.swing.*;
import java.util.*;

/**
 * Created by Sumner on 11/21/16.
 */
public class Hud {
    private JPanel board;
    private ArrayList<JButton> buttons;
    private ArrayList<JLabel> textLabels;

    public Hud() {
        board = new JPanel();
        buttons = new ArrayList<>();
        textLabels = new ArrayList<>();


    }

    public void drawContent() {
        // TODO: Implement
    }

    public void createButton() {
        // TODO: make this take in an image and return a button object
        // TODO: use Game.buildTower(Tower t) to build towers
    }
}
