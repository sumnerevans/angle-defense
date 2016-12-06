package angledefense.logic.towers;

import angledefense.draw.DrawContext;
import angledefense.logic.Game;
import angledefense.logic.Location;
import angledefense.logic.Player;
import angledefense.logic.minions.Minion;

public class AirTower extends Tower {
    public AirTower(Player owner, Location location) {
        super(owner, location, 1);
        this.damage = 1;
        this.range = 5;
        this.fireRate = 3;
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
