package angledefense.gui;

import angledefense.logic.Game;
import angledefense.logic.Location;
import angledefense.logic.Player;
import angledefense.logic.towers.AirGroundTower;
import angledefense.logic.towers.AirTower;
import angledefense.logic.towers.GroundTower;
import angledefense.logic.towers.Tower;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.BiFunction;

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

        JButton A = createButton("Anti Ground Cannon", GroundTower::new);
        JButton B = createButton("Gyro Zapper", AirTower::new);
        JButton C = createButton("Big Daddy", AirGroundTower::new);

        A.setSize(50, 50);
        B.setSize(50, 50);
        C.setSize(50, 50);

        this.add(A);
        this.add(B);
        this.add(C);
    }

    private JButton createButton(String text, BiFunction<Player, Location, Tower> listener) {
        JButton button = new JButton(text);
        button.addActionListener(new BuildListener(listener));
        add(button);
        return button;
    }

    // Listener
    private class BuildListener implements ActionListener {
        private BiFunction<Player, Location, Tower> supplier;

        public BuildListener(BiFunction<Player, Location, Tower> supplier) {
            this.supplier = supplier;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Location l = game.getSelectedLocation();
            if (l != null) {
                l = l.floor();

                try {
                    game.buildTower(this.supplier.apply(game.getPlayer(), l));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ButtonBox.this, ex.getMessage(), "Place Tower", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(ButtonBox.this, "Can not place tower, no location selected.", "Place Tower", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
