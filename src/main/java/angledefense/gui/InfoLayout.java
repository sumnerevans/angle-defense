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
public class InfoLayout extends JPanel{
	public InfoLayout(Player player) {
		setLayout(new GridLayout(1, 3));
		setBorder(new EtchedBorder());
		setSize(900, 50);

		// Gold
		JPanel goldPanel = new JPanel();
		goldPanel.setLayout(new GridLayout(1, 0));
		goldPanel.setBorder(new TitledBorder(new EtchedBorder(), "Gold: "));
		JTextField field = new JTextField(player.getGold());
		field.setEditable(false);
		goldPanel.add(field);
		add(goldPanel);
	}
}
