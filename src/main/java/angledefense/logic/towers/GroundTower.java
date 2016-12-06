package angledefense.logic.towers;

import angledefense.draw.DrawContext;
import angledefense.logic.Game;
import angledefense.logic.Location;
import angledefense.logic.Player;
import angledefense.logic.minions.Minion;

import java.awt.event.ActionEvent;

public class GroundTower extends Tower {
	public GroundTower(Player owner, Location location) {
		super(owner, location);
		this.damage = 10;
	}

	@Override
	public void draw(DrawContext drawContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tick(Game game, float dt) {
		// TODO Auto-generated method stub

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
