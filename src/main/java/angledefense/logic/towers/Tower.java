package angledefense.logic.towers;

import angledefense.logic.*;
import angledefense.logic.minions.*;

public abstract class Tower implements IDrawable, ITickable {
    protected Location location;
    protected float angle;
    protected float range;
    protected Player owner;
    protected int price;

    public Tower(Player owner) {
        this.owner = owner;
    }

    public abstract void attack(Minion minion);

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
