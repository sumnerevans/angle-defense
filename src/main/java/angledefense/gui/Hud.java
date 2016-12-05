package angledefense.gui;

import angledefense.logic.Game;
import angledefense.logic.Player;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by Sumner on 11/21/16.
 */
public class Hud extends JPanel {
    private JPanel board;
    private ArrayList<JButton> buttons;
    private ArrayList<JLabel> textLabels;
    private InfoLayout infoLayout;
    private Game game;
    private ViewField viewField;

    public Hud(Game game) {
        this.game = game;
        initializeGUI();
    }

    private void initializeGUI() {
        this.setPreferredSize(new Dimension(900,700));
        infoLayout = new InfoLayout(game.getPlayer());
        viewField = new ViewField(game);
        this.add(infoLayout);
        this.add(viewField);
    }

    public void drawContent() {
        // TODO: I think this is how it is suposed to be?
        this.setVisible(true);
    }

    private JButton createButton(){
        JButton button = new JButton();
        return button;
    }

    public void createButtons() {
        // TODO: make this take in an image and return a button object
        // TODO: use Game.buildTower(Tower t) to build towers
    }

    public Color getColor() {
        //TODO: Make this
        return Color.blue;
    }

    public String getName() {
        return "Blarg";
    }
}
