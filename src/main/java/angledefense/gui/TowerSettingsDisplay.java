package angledefense.gui;

import angledefense.logic.Player;
import angledefense.logic.towers.Tower;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by sumner on 12/5/16.
 */
public class TowerSettingsDisplay extends JPanel {
    private JTextField text = new JTextField();

    public TowerSettingsDisplay() {
        this.setPreferredSize(new Dimension(100, 50));
        this.setBorder(new TitledBorder(new EtchedBorder(), "Angle: "));

        // Angle
        text.setLocation(0, 0);
        text.setPreferredSize(new Dimension(100, 20));
        this.add(text);
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
