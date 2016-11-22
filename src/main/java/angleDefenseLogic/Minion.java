package angleDefenseLogic;

import angleDefenseGui.*;

import java.awt.*;

public abstract class Minion implements IDrawable, ITickable {
	protected int health;
	protected int goldReward;
	protected Location location;

	public void moveForward(float distance) {
		// TODO: Implement
	}

	abstract public void attacked(Tower tower, int amount);

	public int getGoldReward() {
		return this.goldReward;
	}

	public void _setLocation(int x, int y) {
		this.location.setX(x);
		this.location.setY(y);
	}

	public void _setLocation(Location l) {
		this.location = l;
	}

	public void _setLocation(float x, float y) {
		this.location.setX(x);
		this.location.setY(y);
	}

	public Location _getLocation() {
		return this.location;
	}
}
