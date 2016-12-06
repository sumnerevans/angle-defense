package angledefense.logic.towers;

import angledefense.draw.DrawContext;
import angledefense.draw.ModelHandle;
import angledefense.logic.*;
import angledefense.logic.minions.Minion;
import angledefense.util.Util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public abstract class Tower implements IDrawable, ITickable {
	private static ModelHandle gun = ModelHandle.create("gun");

	public final int x;
	public final int y;
	protected float angle;
	protected float range;
	protected Player owner;
	protected int price = 1;
	protected int level;
	protected int isFiring = 0;
	protected float fireRate;
	protected int damage;
	protected Instant lastFireTime;

	public Tower(Player owner, Location location, int price) {
		this.owner = owner;
		this.x = location.intX();
		this.y = location.intY();
		this.price = price;
	}

	@Override
	public void draw(DrawContext drawContext) {
		gun.setTransform(new Location(x + .5f, y + .5f), 1, 0, angle);
		gun.draw();
	}

	public abstract void attack(Minion minion);

	public abstract void upgrade();

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void tick(Game game, float dt) {
		this.isFiring--;

		if (this.lastFireTime == null) {
			this.lastFireTime = game.getNow();
			return;
		}

		float timeUntilFire = this.lastFireTime.until(game.getNow(), ChronoUnit.MILLIS) / 1000f;

		if (timeUntilFire > 0) return;

		for (Minion m : game.minions) {
			if (Util.angleInRange(this.angle,
					Location.angle(this.getLocation(), m.getLocation()),
					12)) {

				this.isFiring = 5;
				m.attacked(this, this.damage);
				this.lastFireTime = game.getNow();
			}
		}
	}

	private Location getLocation() {
		return new Location(x, y);
	}

	public Player getOwner() {
		return owner;
	}

	public boolean onSpawn() throws Exception {
		owner.spendGold(price);
		return true;
	}

    public float getAngle() {
        return angle;
    }
}
