package angledefense.gui;

import angledefense.logic.*;
import com.google.gson.JsonParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * Created by sumner on 12/1/16.
 */
public class Main extends JFrame {
    private Main() {
        this.setSize(900,600);
        this.setTitle("Angle Defense");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            // Load the game
            Game game = Game.newGame("default-config.json");
            Hud hud = new Hud(game);

            this.add(hud, BorderLayout.CENTER);

            // Show the window
            this.pack();
            this.setVisible(true);

            // Play the game
            game.loop();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Something went terribly wrong. We were unable to load the game " +
                            "configuration:\n\n" +
                            e.getMessage(),
                    "Configuration Load Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (JsonParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Something went terribly wrong. We were unable to parse the JSON.\n\n" +
                            e.getMessage(),
                    "Configuration JSON Parse Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Something went terribly wrong.\n\n" + e.getMessage(),
                    "Unknown Error", JOptionPane.ERROR_MESSAGE);
        }

        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public static void main(String[] args) {
        new Main();
    }
}
