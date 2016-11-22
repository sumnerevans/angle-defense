package angleDefenseLogic;

/**
 * Created by Mobius on 11/22/16.
 */
public class Location {
	private float x, y;

	public Location(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Location))
			return false;

		Location other = (Location) o;
		return other.x == this.x && other.y == this.y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
}
