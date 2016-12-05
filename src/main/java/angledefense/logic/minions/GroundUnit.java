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
        this.speed = 3;
    }

    @Override
    public void draw(DrawContext drawContext) {
        // TODO Auto-generated method stub

    }

    @Override
    public void tick(Game game,float dt) {
        // TODO Auto-generated method stub

        this.moveForward(this.speed*dt);
    }

    @Override
    protected void receiveDamage(Tower tower, int amount) {
        if (tower instanceof AirTower) return;

        this.health -= amount;
    }

}
