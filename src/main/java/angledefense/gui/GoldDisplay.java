package angledefense.gui;

import angledefense.logic.Player;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Mobius on 12/5/16.
 */

public class GoldDisplay extends JPanel {
	private JTextField text;

	public GoldDisplay(Player player) {

		// Gold
		JPanel goldPanel = new JPanel();
		goldPanel.setBorder(new TitledBorder(new EtchedBorder(), "Gold: "));
		text = new JTextField();
		text.setLocation(0, 0);
		text.setEditable(false);
		goldPanel.add(text);
		add(goldPanel);
	}

	public void setGold(int gold) {
		text.setText("" + gold);
	}
}
