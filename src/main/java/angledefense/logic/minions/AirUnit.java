package angledefense.logic.minions;

import angledefense.config.Node;
import angledefense.logic.towers.GroundTower;
import angledefense.logic.towers.Tower;

public class AirUnit extends Minion {

	protected AirUnit(Node n) {
		super(n);
		this.goldReward = 5;
		this.health = 7;
		this.speed = 4;
	}

	@Override
	protected void receiveDamage(Tower tower, int amount) {
		if (tower instanceof GroundTower) return;

		this.health -= amount;
	}

}
