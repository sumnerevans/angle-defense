package angledefense.gui;

import angledefense.logic.Game;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * Created by Mobius on 12/5/16.
 */
public class ViewField extends JPanel {
	public ViewField(Game game) {
		setPreferredSize(new Dimension(850, 550));
		setBorder(new EtchedBorder());

	}
}
