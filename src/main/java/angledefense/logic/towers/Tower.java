package angledefense.logic.towers;

import angledefense.logic.*;
import angledefense.logic.minions.Minion;
import angledefense.util.Util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public abstract class Tower implements IDrawable, ITickable {
    protected Location location;
    protected float angle;
    protected float range;
    protected int isFiring = 0;
    protected Player owner;
    protected int price;
    protected int level = 1;
    protected float fireRate;
    protected int damage;
    protected Instant lastFireTime;

    public Tower(Player owner, Location location) {
        this.owner = owner;
        this.location = location;
    }

    public void tick(Game game, float dt) {
        this.isFiring--;

        float timeUntilFire = this.lastFireTime.until(game.getNow(), ChronoUnit.MILLIS) / 1000f;

        if (timeUntilFire > 0) return;

        for (Minion m : game.minions) {
            if (Util.angleInRange(this.angle,
                    Location.angle(this.location, m.getLocation()),
                    12)) {

                this.isFiring = 5;
                m.attacked(this, this.damage);
                this.lastFireTime = game.getNow();
            }
        }
    }

    public abstract void attack(Minion minion);

    public abstract void upgrade();

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Player getOwner() {
        return owner;
    }
}
