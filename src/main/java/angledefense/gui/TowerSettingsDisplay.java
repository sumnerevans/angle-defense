package angledefense.gui;

import angledefense.logic.Game;
import angledefense.logic.Location;
import angledefense.logic.towers.Tower;
import angledefense.util.Util;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by sumner on 12/5/16.
 */
public class TowerSettingsDisplay extends JPanel {
    private JTextField text = new JTextField();
    private Game game;
    private ArrayList<JButton> buttons = new ArrayList<>();

    public TowerSettingsDisplay(Game game) {
        this.game = game;

        this.setPreferredSize(new Dimension(100, 50));
        this.setBorder(new TitledBorder(new EtchedBorder(), "Tower: "));

        JButton incrementButton = createButton("+", (float) Math.PI / 36f);
        this.buttons.add(incrementButton);
        this.add(incrementButton);

        // Angle text
        text.setLocation(0, 0);
        text.setPreferredSize(new Dimension(70, 20));
        text.setEnabled(false);
        text.setEditable(false);
        this.add(text);

        JButton decrementButton = createButton("-", -(float) Math.PI / 36f);
        this.buttons.add(decrementButton);
        this.add(decrementButton);

        // Upgrade button
        JButton upgradeButton = new JButton("Upgrade");
        upgradeButton.addActionListener(new UpgradeListener());
        this.buttons.add(upgradeButton);
        this.add(upgradeButton);

    }

    private JButton createButton(String text, float incrementAmount) {
        JButton button = new JButton(text);
        button.addActionListener(new ButtonListener(incrementAmount));
        button.setPreferredSize(new Dimension(50, 20));
        return button;
    }

    // Listener
    private class ButtonListener implements ActionListener {
        private float incrementAmount;

        public ButtonListener(float incrementAmount) {
            this.incrementAmount = incrementAmount;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Location l = game.getSelectedLocation();

            if (l != null) {
                Tower t = game.getTower(l);
                t.setAngle(t.getAngle() + this.incrementAmount);
            }
        }
    }

    public void setInfo(Tower t) {
        text.setText(t == null ? "" : "" + Util.toDegrees(t.getAngle()));
        text.setEnabled(t != null);

        for (JButton b : this.buttons)
            b.setEnabled(t != null);
    }

    private class UpgradeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Tower t = game.getTower(game.getSelectedLocation());

            try {
                t.upgrade();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(TowerSettingsDisplay.this, ex.getMessage(),
                        "Place Tower", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
