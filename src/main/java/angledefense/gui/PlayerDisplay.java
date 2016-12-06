package angledefense.gui;

import angledefense.logic.Game;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Mobius on 12/5/16.
 */

public class PlayerDisplay extends JPanel {
    private JTextField goldText;
    private JTextField livesText;

    public PlayerDisplay() {
        JPanel goldPanel = new JPanel();
        goldPanel.setPreferredSize(new Dimension(100, 50));
        goldPanel.setBorder(new TitledBorder(new EtchedBorder(), "Gold: "));

        goldText = new JTextField();
        goldText.setLocation(0, 0);
        goldText.setEditable(false);
        goldText.setPreferredSize(new Dimension(80, 20));
        goldPanel.add(goldText);

        JPanel livesPanel = new JPanel();
        livesPanel.setPreferredSize(new Dimension(100, 50));
        livesPanel.setBorder(new TitledBorder(new EtchedBorder(), "Lives: "));

        livesText = new JTextField();
        livesText.setLocation(0, 0);
        livesText.setEditable(false);
        livesText.setPreferredSize(new Dimension(80, 20));
        livesPanel.add(livesText);

        this.add(goldPanel);
        this.add(livesPanel);
    }

    public void setInfo(Game game) {
        goldText.setText("" + game.getPlayer().getGold());
        livesText.setText("" + game.getNumLives());
    }
}
