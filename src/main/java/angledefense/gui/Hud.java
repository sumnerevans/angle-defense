package angledefense.gui;

import angledefense.logic.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Hud displays a GUI system that displays the players Gold amount, has buttons for each tower,
 * and a Angle modification System.
 */
public class Hud extends JPanel {
    private ArrayList<JButton> buttons;
    private ArrayList<JLabel> textLabels;
    private Game game;
    private ButtonBox buttonBox;
    private GoldDisplay goldDisplay;
    private TowerSettingsDisplay towerSettingsDisplay;

    public Hud(Game game) {
        this.game = game;

        game.setUIListener(g -> {
            goldDisplay.setGold(game.getPlayer().getGold());
            if (game.getSelectedLocation() != null)
                towerSettingsDisplay.setInfo(game.getTower(game.getSelectedLocation()));
            else
                towerSettingsDisplay.setInfo(null);
        });

        initializeGUI();
    }

    private void initializeGUI() {
        this.setLayout(new GridLayout(3, 1));
        this.setPreferredSize(new Dimension(200, 512));
        goldDisplay = new GoldDisplay(game.getPlayer());
        buttonBox = new ButtonBox(game);
        towerSettingsDisplay = new TowerSettingsDisplay();

        this.add(goldDisplay);
        this.add(buttonBox);
        this.add(towerSettingsDisplay);
    }
}
