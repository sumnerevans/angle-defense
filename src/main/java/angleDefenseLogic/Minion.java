package angleDefenseLogic;

import com.google.gson.annotations.SerializedName;

public abstract class Minion implements IDrawable, ITickable {
    public enum Type {
        @SerializedName("ground")
        GROUND,
        @SerializedName("air")
        AIR
    }

    protected int health;
    protected int goldReward;
    protected Location location;

    public Minion(float x, float y) {
        this.location = new Location(x, y);
    }

    public void moveForward(float distance) {
        // TODO: Implement
    }

    abstract protected void receiveDamage(Tower tower, int amount);

    public void attacked(Tower tower, int amount) {
        this.receiveDamage(tower, amount);

        if (this.health <= 0) {
            tower.getOwner().addGold(this.goldReward);
        }
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
