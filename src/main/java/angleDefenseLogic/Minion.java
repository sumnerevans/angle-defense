package angleDefenseLogic;

import angleDefenseGui.*;

public abstract class Minion implements IDrawable, ITickable {
    float x, y;
    int health;

    public void moveForward(float distance) {
        // TODO: Implement
    }

    abstract public void attacked(Tower tower, int amount);
}
