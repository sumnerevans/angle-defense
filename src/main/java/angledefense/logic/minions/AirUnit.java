package angledefense.logic.minions;

import angledefense.config.Node;
import angledefense.draw.ModelHandle;
import angledefense.logic.towers.GroundTower;
import angledefense.logic.towers.Tower;

public class AirUnit extends Minion {
    private static ModelHandle flytea = ModelHandle.create("flytea");

    protected AirUnit(Node n) {
        super(n);
        this.health = 10;
        this.speed = 3;
        this.type = Type.AIR;
        this.model = flytea;
    }

    @Override
    protected boolean receiveDamage(Tower tower, int amount) {
        if (tower instanceof GroundTower) return false;

        this.health -= amount;
        // this.speed *= 0.9;

        return true;
    }

}
