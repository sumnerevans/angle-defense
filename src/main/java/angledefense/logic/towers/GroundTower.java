package angledefense.logic.towers;

import angledefense.draw.DrawContext;
import angledefense.logic.Game;
import angledefense.logic.Location;
import angledefense.logic.Player;
import angledefense.logic.minions.Minion;

import java.awt.event.ActionEvent;

public class GroundTower extends Tower {
    public GroundTower(Player owner, Location location) {
        super(owner, location, 1);
        this.damage = 10;
        this.range = 5;
        this.fireRate = 2;
    }

	@Override
	public void draw(DrawContext drawContext) {
		super.draw(drawContext);
	}

    @Override
    public void attack(Minion minion) {
        minion.attacked(this, 10);
    }

    @Override
    public void upgrade() {
        // TODO: Implement
    }
}
