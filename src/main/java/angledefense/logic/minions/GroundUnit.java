package angledefense.logic.minions;

import angledefense.logic.*;
import angledefense.logic.towers.*;
import angledefense.config.*;
import angledefense.draw.DrawContext;

public class GroundUnit extends Minion {

    GroundUnit(Node n) {
        super(n);
        this.goldReward = 3;
        this.health = 5;
    }

    @Override
    public void tick(Game game) {
        super.tick(game);
        // TODO Auto-generated method stub

    }

    @Override
    protected void receiveDamage(Tower tower, int amount) {
        // TODO: Implement
    }

}
