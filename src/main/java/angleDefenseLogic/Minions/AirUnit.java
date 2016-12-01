package angleDefenseLogic.Minions;

import angleDefenseGui.*;
import angleDefenseLogic.*;

public class AirUnit extends Minion {

    public AirUnit(float x, float y) {
        super(x, y);
        this.goldReward = 5;
        this.health = 7;
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
        // TODO Implement
    }

}
