package angleDefenseLogic.minions;

import angleDefenseLogic.*;
import angleDefenseLogic.towers.*;
import config.*;
import draw.DrawContext;

public class AirUnit extends Minion {

    protected AirUnit(Node n) {
        super(n);
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
