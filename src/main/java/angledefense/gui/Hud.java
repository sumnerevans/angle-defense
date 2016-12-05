package angledefense.gui;

import javax.swing.*;
import java.util.*;

/**
 * Created by Sumner on 11/21/16.
 */
public class Hud extends JFrame {
    private JPanel board;
    private ArrayList<JButton> buttons;
    private ArrayList<JLabel> textLabels;

    public Hud() {
        initializeGUI();

    }

    private void initializeGUI() {
        setSize(900, 600);
        setTitle("Angle Defence");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void drawContent() {
        // TODO: Implement
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
}
