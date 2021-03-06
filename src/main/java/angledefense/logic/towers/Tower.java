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
    public static final ModelHandle gun = ModelHandle.create("gun");
    public static final ModelHandle lazer = ModelHandle.create("lazer");
    public static final ModelHandle marker = ModelHandle.create("marker");

    public final int x;
    public final int y;
    private float angle;
    protected float range;
    protected Player owner;
    protected int price;
    protected int level;
    protected int isFiring = 0;
    protected float fireRate;
    protected int damage;
    protected int xp = 0;
    protected Instant lastFireTime;

    protected ModelHandle towerModel = gun;
    protected ModelHandle lazerModel = lazer;

    public Tower(Player owner, Location location) {
        this.owner = owner;
        this.x = location.intX();
        this.y = location.intY();

        // Random angle in [-pi, pi]
        this.setAngle((float) (new Random().nextFloat() * 2 * Math.PI - Math.PI));
    }

    @Override
    public void draw(DrawContext drawContext) {
        Location loc = this.getLocation();
        towerModel.setTransform(loc, 1, 0, angle);
        towerModel.draw();

        if (isFiring > 0) {

            lazerModel.setTransform(loc, range, .5f, angle);
            lazerModel.draw();
        }

        for (int i = 0; i < level; i++) {
            marker.setTransform(loc, 1, 0, i * (float) Math.PI / 4);
            marker.draw();
        }
    }

    public boolean attack(Minion minion) {
        return minion.attacked(this, this.damage);
    }


    public abstract void upgrade() throws Exception;

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

        boolean hit = false;

        for (Minion m : game.minions) {
            boolean angleInAngleRange = Util.angleInRange(this.angle, Location.angle(this
                    .getLocation(), m.getLocation()), (float) Math.PI / 7.5f);
            boolean angleInRange = Location.dist(this.getLocation(), m.getLocation()) < this.range;

            if (angleInAngleRange && angleInRange) {
                boolean hitthis = m.attacked(this, this.damage);
                hit |= hitthis;
                if (hitthis) {
                    this.isFiring = 5;
                    if (!isAreaOfEffect()) break;
                }
            }
        }

        this.lastFireTime = game.getNow();
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

    protected boolean isAreaOfEffect() {
        return false;
    }
}
