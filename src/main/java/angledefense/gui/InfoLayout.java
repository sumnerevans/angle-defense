package angledefense.gui;

import angledefense.logic.Player;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Mobius on 12/5/16.
 */

public class InfoLayout extends JPanel {
	public InfoLayout(Player player) {
		setLayout(new GridLayout(1, 3));
		setBorder(new EtchedBorder());
		setPreferredSize(new Dimension(200, 50));

		// Gold
		JPanel goldPanel = new JPanel();
		goldPanel.setBorder(new TitledBorder(new EtchedBorder(), "Gold: "));
		JTextField field = new JTextField("TODO: Get Player Gold"/*player.getGold()*/);
		field.setLocation(0,0);
		field.setEditable(false);
		goldPanel.add(field);
		add(goldPanel);
	}
}
