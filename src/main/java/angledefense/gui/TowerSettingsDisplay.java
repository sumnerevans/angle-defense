package angledefense.gui;

import angledefense.logic.Player;
import angledefense.logic.towers.Tower;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Created by sumner on 12/5/16.
 */
public class TowerSettingsDisplay extends JPanel {
    private JTextField text = new JTextField();

    public TowerSettingsDisplay() {

        // Angle
        JPanel anglePanel = new JPanel();
        anglePanel.setBorder(new TitledBorder(new EtchedBorder(), "Angle: "));
        text.setLocation(0, 0);
        anglePanel.add(text);
        add(anglePanel);
    }

    public void setInfo(Tower angle) {
        if (angle == null) {
            text.setText("");
            text.setEnabled(false);
        } else {
            text.setText("" + angle.getAngle());
            text.setEnabled(true);
        }
    }
}
