package angledefense.gui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Mobius on 12/5/16.
 */
public class ButtonBox extends JPanel {
	public ButtonBox(ArrayList<JButton> buttons) {

		this.setPreferredSize(new Dimension(200,200));
		this.setBorder(new EtchedBorder());
		this.setLayout(new GridLayout(2, 2));
		for (JButton B : buttons) {
			B.setSize(50,50);
			B.setBorder(new EtchedBorder());
		}
	}
}
