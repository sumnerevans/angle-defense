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
	private ButtonBox buttonBox;
	private GoldDisplay goldDisplay;

	public Hud(Game game) {
		this.game = game;
		game.setUIListener(g -> {
			goldDisplay.setGold(game.getPlayer().getGold());
		});
		initializeGUI();
	}

	private void initializeGUI() {
		this.setLayout(new GridLayout(3, 1));
		this.setPreferredSize(new Dimension(200, 512));
		goldDisplay = new GoldDisplay(game.getPlayer());
		buttonBox = new ButtonBox(game);

		this.add(goldDisplay);
		this.add(buttonBox);
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
