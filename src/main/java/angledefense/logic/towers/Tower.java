package angledefense.logic.towers;

import angledefense.logic.IDrawable;
import angledefense.logic.ITickable;
import angledefense.logic.Location;
import angledefense.logic.Player;
import angledefense.logic.minions.Minion;

public abstract class Tower implements IDrawable, ITickable {
	public final int x;
	public final int y;
	protected float angle;
	protected float range;
	protected Player owner;
	protected int price = 1;
	protected int level;
	protected int damage;

	public Tower(Player owner, Location location) {
		this.owner = owner;
		this.x = location.intX();
		this.y = location.intY();
	}

	public abstract void attack(Minion minion);

	public abstract void upgrade();

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public Player getOwner() {
		return owner;
	}

	public boolean onSpawn() throws Exception {
		owner.spendGold(price);
		return true;
	}
}
