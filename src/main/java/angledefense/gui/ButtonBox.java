package angledefense.gui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Mobius on 12/5/16.
 */
public class ButtonBox extends JPanel {
	public ButtonBox() {

		this.setPreferredSize(new Dimension(185, 400));
		this.setBorder(new EtchedBorder());
		this.setLayout(new GridLayout(3, 1));
		JButton A = new JButton();
		JButton B = new JButton();
		JButton C = new JButton();

		
		// B.setSize(50, 50);
		// B.setBorder(new EtchedBorder());
	}
}
