package angleDefenseLogic.Minions;

import angleDefenseGui.*;
import angleDefenseLogic.*;
import angleDefenseLogic.Towers.Tower;
import config.*;

public class GroundUnit extends Minion {

    GroundUnit(Node n) {
        super(n);
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
