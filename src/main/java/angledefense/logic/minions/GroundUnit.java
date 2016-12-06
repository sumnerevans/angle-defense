package angledefense.logic.minions;

import angledefense.config.Node;
import angledefense.logic.towers.AirTower;
import angledefense.logic.towers.Tower;

public class GroundUnit extends Minion {

    GroundUnit(Node n) {
        super(n);
        this.goldReward = 3;
        this.health = 10;
        this.speed = 2;
    }

    @Override
    protected void receiveDamage(Tower tower, int amount) {
        if (tower instanceof AirTower) return;

        this.health -= amount;
    }

}
