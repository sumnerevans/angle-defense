package angleDefenseLogic;

import angleDefenseLogic.Minions.*;
import com.google.gson.annotations.SerializedName;

import java.util.function.*;

public abstract class Minion implements IDrawable, ITickable {
    public enum Type {
        @SerializedName("ground")
        GROUND(GroundUnit::new),

        @SerializedName("air")
        AIR(AirUnit::new);

        private Function<Location, Minion> spawner;

        Type(Function<Location, Minion> s) {
            this.spawner = s;
        }

        public Minion create(Location l) {
            return this.spawner.apply(l);
        }
    }

    protected boolean dead = false;
    protected int health;
    protected int goldReward;
    protected Location location;

    public Minion(Location location) {
        this.location = location;
    }

    public void moveForward(float distance) {
        // TODO: Implement
    }

    abstract protected void receiveDamage(Tower tower, int amount);

    public void attacked(Tower tower, int amount) {
        this.receiveDamage(tower, amount);

        if (this.health <= 0) {
            this.dead = true;
            tower.getOwner().addGold(this.goldReward);
        }
    }

    public boolean isDead() {
        return this.dead;
    }

    public void _setLocation(int x, int y) {
        this.location.setX(x);
        this.location.setY(y);
    }

    public void _setLocation(Location l) {
        this.location = l;
    }

    public void _setLocation(float x, float y) {
        this.location.setX(x);
        this.location.setY(y);
    }

    public Location _getLocation() {
        return this.location;
    }
}
