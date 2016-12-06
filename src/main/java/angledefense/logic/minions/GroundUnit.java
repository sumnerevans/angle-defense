package angledefense.logic.minions;

import angledefense.config.Node;
import angledefense.draw.ModelHandle;
import angledefense.logic.towers.AirTower;
import angledefense.logic.towers.Tower;

public class GroundUnit extends Minion {
    private static ModelHandle teapot = ModelHandle.create("teapot");

    GroundUnit(Node n) {
        super(n);
        this.health = 10;
        this.speed = 3;
        this.type = Type.GROUND;
        this.model = teapot;
    }

    @Override
    protected void receiveDamage(Tower tower, int amount) {
        if (tower instanceof AirTower) return;

        this.health -= amount;
        this.speed *= 0.9;
    }

}
