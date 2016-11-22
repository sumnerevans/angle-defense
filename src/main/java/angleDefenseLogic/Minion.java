package angleDefenseLogic;

import angleDefenseGui.*;

public abstract class Minion implements IDrawable, ITickable {
    protected float x, y;
    protected int health;
    protected int goldReward;

    public void moveForward(float distance) {
        // TODO: Implement
    }

    abstract public void attacked(Tower tower, int amount);


}
