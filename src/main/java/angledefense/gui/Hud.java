package angledefense.gui;

import angledefense.logic.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Hud displays a GUI system that displays the players Gold amount, has buttons for each tower,
 * and a Angle modification System.
 */
public class Hud extends JPanel {
	private ArrayList<JButton> buttons;
	private ArrayList<JLabel> textLabels;
	private Game game;
	private ViewField viewField;
	private ButtonBox buttonBox;

	public Hud(Game game) {
		this.game = game;
		initializeGUI();
	}


	private void initializeGUI() {
		this.setPreferredSize(new Dimension(200, 512));
		buttonBox = new ButtonBox(buttons);

		//buttonBox.setLocation(700,500);
		//this.add(buttonBox);
		this.add(viewField);


	}

	public void drawContent() {
		// TODO: I think this is how it is supposed to be?
		this.setVisible(true);
	}

	private JButton createButton() {
		JButton button = new JButton();
		return button;
	}

	public void createButtons() {

		// TODO: make this take in an image and return a button object
		// TODO: use Game.buildTower(Tower t) to build towers
	}

	public Color getColor() {
		//TODO: Make this
		return Color.blue;
	}

	public String getName() {
		return "Blarg";
	}
}
