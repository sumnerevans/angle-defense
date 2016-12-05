package angledefense.gui;

import angledefense.logic.Player;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by Sumner on 11/21/16.
 */
public class Hud extends JFrame {
    private JPanel board;
    private ArrayList<JButton> buttons;
    private ArrayList<JLabel> textLabels;
    private InfoLayout infoLayout;

    public Hud() {
        initializeGUI();

    }

    private void initializeGUI() {

        setSize(900, 600);
        setTitle("Angle Defence");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Temporarily making a player until I can get an instance.
        Player p = new Player("Cash", Color.yellow);
        infoLayout = new InfoLayout(p);
        add(infoLayout, "North");
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

    // To be used only for viewing while creating the GUI
    public static void main(String[] args) {
        Hud hud = new Hud();
        hud.setVisible(true);
    }

    public Color getColor() {
        //TODO: Make this
        return Color.blue;
    }

    public String getName() {
        return "Blarg";
    }
}
