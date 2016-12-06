package angledefense.logic.towers;

import angledefense.draw.DrawContext;
import angledefense.logic.Game;
import angledefense.logic.Location;
import angledefense.logic.Player;
import angledefense.logic.minions.GroundUnit;
import angledefense.logic.minions.Minion;

public class AirTower extends Tower {
    public AirTower(Player owner, Location location) {
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

        if (minion.getType() != Minion.Type.GROUND ) {
            minion.attacked(this, this.damage);
        }
    }

    @Override
    public void upgrade() {
        this.damage *= this.level;
        this.range += 0.5;
    }
}
