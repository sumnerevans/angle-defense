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
    private PlayerDisplay playerDisplay;
    private TowerSettingsDisplay towerSettingsDisplay;

    public Hud(Game game) {
        this.game = game;

        game.setUIListener(g -> {
            playerDisplay.setInfo(game);
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
        playerDisplay = new PlayerDisplay();
        buttonBox = new ButtonBox(game);
        towerSettingsDisplay = new TowerSettingsDisplay(this.game);

        this.add(playerDisplay);
        this.add(buttonBox);
        this.add(towerSettingsDisplay);
    }
}
