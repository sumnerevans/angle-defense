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
	public GoldDisplay(Player player) {

		// Gold
		JPanel goldPanel = new JPanel();
		goldPanel.setBorder(new TitledBorder(new EtchedBorder(), "Gold: "));
		JTextField field = new JTextField("TODO: Get Player Gold"/*player.getGold()*/);
		field.setLocation(0, 0);
		field.setEditable(false);
		goldPanel.add(field);
		add(goldPanel);
	}
}