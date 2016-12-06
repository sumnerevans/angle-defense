package angledefense.logic.towers;

import angledefense.draw.DrawContext;
import angledefense.draw.ModelHandle;
import angledefense.logic.*;
import angledefense.logic.minions.Minion;
import angledefense.util.Util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public abstract class Tower implements IDrawable, ITickable {
    private static ModelHandle gun = ModelHandle.create("gun");
    private static ModelHandle lazer = ModelHandle.create("lazer");

    public final int x;
    public final int y;
    private float angle;
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

        // Random angle in [-pi, pi]
        this.setAngle((float) (new Random().nextFloat() * 2 * Math.PI - Math.PI));
    }

    @Override
    public void draw(DrawContext drawContext) {
        Location loc = this.getLocation();
        gun.setTransform(loc, 1, 0, angle);
        gun.draw();

        if (isFiring > 0) {
            lazer.setTransform(loc, range, .5f, angle);
            lazer.draw();
        }
    }

    public abstract void attack(Minion minion);

    public abstract void upgrade();

    public void setAngle(float angle) {
        this.angle = angle;
        this.owner.getGame().notifyUI();
    }

    public void tick(Game game, float dt) {
        this.isFiring--;

        if (this.lastFireTime == null) {
            this.lastFireTime = game.getNow();
            return;
        }

        float timeUntilFire = this.lastFireTime.until(game.getNow(), ChronoUnit.MILLIS) / 1000f;

        if (timeUntilFire < 1 / this.fireRate) return;

        for (Minion m : game.minions) {
            boolean angleInAngleRange = Util.angleInRange(this.angle, Location.angle(this
                    .getLocation(), m.getLocation()), (float) Math.PI / 7.5f);
            boolean angleInRange = Location.dist(this.getLocation(), m.getLocation()) < this.range;

            if (angleInAngleRange && angleInRange) {
                this.isFiring = 5;
                m.attacked(this, this.damage);
                this.lastFireTime = game.getNow();

                // Don't kill any more minions
                break;
            }
        }
    }

    private Location getLocation() {
        return new Location(x + 0.5f, y + 0.5f);
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
