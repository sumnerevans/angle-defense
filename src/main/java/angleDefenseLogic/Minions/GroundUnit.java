package angleDefenseLogic.Minions;

import angleDefenseGui.*;
import angleDefenseLogic.*;

public class GroundUnit extends Minion {

    public GroundUnit(float x, float y) {
        super(x, y);
        this.goldReward = 3;
        this.health = 5;
    }

    @Override
    public void draw(DrawContext drawContext) {
        // TODO Auto-generated method stub

    }

    @Override
    public void tick(Game game) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void receiveDamage(Tower tower, int amount) {
        // TODO: Implement
    }

}
