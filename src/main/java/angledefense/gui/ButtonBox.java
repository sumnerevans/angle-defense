package angledefense.gui;

import angledefense.logic.Game;
import angledefense.logic.Location;
import angledefense.logic.Player;
import angledefense.logic.towers.Tower;
import jdk.nashorn.internal.objects.NativeUint8Array;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Created by Mobius on 12/5/16.
 */
public class ButtonBox extends JPanel {

	private Game game;

	public ButtonBox(Game game) {
		this.game = game;
		this.setPreferredSize(new Dimension(185, 400));
		this.setBorder(new EtchedBorder());
		this.setLayout(new GridLayout(3, 1));


		JButton A = createButton("Anti Ground Cannon", listener);
		JButton B = createButton("Gyro Zapper", listener);
		JButton C = createButton("Big Daddy", listener);

		A.setSize(50,50);
		B.setSize(50,50);
		C.setSize(50,50);

		
		A.addActionListener(listener);
		B.addActionListener(listener);
		C.addActionListener(listener);


		this.add(A);
		this.add(B);
		this.add(C);
	}

	private JButton createButton(String text, ActionListener listener){
		JButton button = new JButton(text);
		button.addActionListener(listener);
		add (button);
		return button;
	}

	// Listener
	private class groundTowerListener implements ActionListener {
		private BiFunction<Player,Location,Tower> supplier;
		public groundTowerListener(BiFunction<Player,Location,Tower> supplier) {
			this.supplier = supplier;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Location l = game.getSelectedLocation();
			if (l != null) {
				l = l.floor();
				game.buildTower(this.supplier.apply(game.getPlayer(), l));
			}
			else {
				
			}
		}
	}
}
