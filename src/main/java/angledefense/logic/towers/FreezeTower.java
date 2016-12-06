package angledefense.logic.towers;

import angledefense.draw.DrawContext;
import angledefense.draw.ModelHandle;
import angledefense.logic.Game;
import angledefense.logic.Location;
import angledefense.logic.Player;
import angledefense.logic.minions.Minion;
import angledefense.util.Util;

public class FreezeTower extends Tower {
    private static ModelHandle freeze = ModelHandle.create("freeze");
    private float slowAmmount;

    public FreezeTower(Player owner, Location location) {
        super(owner, location, 1);
        this.damage = 3;
        this.range = 4;
        this.fireRate = 1.5f;
        this.slowAmmount = 0.5f;
        this.lazerModel = freeze;
    }

	@Override
	public void draw(DrawContext drawContext) {
		super.draw(drawContext);
	}

    @Override
    public void attack(Minion minion) {
        minion.decreaseSpeed(slowAmmount);
        minion.attacked(this, this.damage);
    }

    @Override
    public void upgrade() {
        this.damage *= this.level;
        this.slowAmmount += 0.1;
        this.range += 1;
    }

    @Override
    protected boolean isAreaOfEffect() {
        return true;
    }
}
