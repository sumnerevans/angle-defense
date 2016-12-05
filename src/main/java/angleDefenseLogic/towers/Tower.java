package angleDefenseLogic.towers;

import angleDefenseLogic.*;
import angleDefenseLogic.minions.*;

public abstract class Tower implements IDrawable, ITickable {
    protected Location location;
    protected float angle;
    protected float range;
    protected Player owner;
    protected int price;
    protected int level;
    protected int damage;

    public Tower(Player owner, Location location) {
        this.owner = owner;
        this.location = location;
    }

    public abstract void attack(Minion minion);

    public abstract void upgrade();

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setPosition(int x, int y) {
        this.location = new Location(x, y);
    }

    public Player getOwner() {
        return owner;
    }
}
