package angledefense.logic;

/**
 * Created by Mobius on 11/22/16.
 */
public class Location implements Cloneable {
	public final float x, y;

	public Location(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public static float distSq(Location a, Location b) {
		float dx = b.x - a.x;
		float dy = b.y - a.y;
		return dx * dx + dy * dy;
	}

	public static float dist(Location a, Location b) {
		return (float) Math.sqrt(distSq(a, b));
	}

	public static Location lerp(Location a, Location b, float d) {
		float x = a.x * (1 - d) + b.x * d;
		float y = a.y * (1 - d) + b.y * d;
		return new Location(x, y);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Location))
			return false;

		Location other = (Location) o;
		return equals(other.x, other.y);
	}

	public boolean equals(float x, float y) {
		return Math.abs(this.x - x) < .0001 && Math.abs(this.y - y) < .0001;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public int intX() {
		return (int) this.x;
	}

	public int intY() {
		return (int) this.y;
	}

	public Location withY(float y) {
		return new Location(x, y);
	}

	public Location withX(float x) {
		return new Location(x, y);
	}

	public Location floor() {
		return new Location(this.intX(), this.intY());
	}

	@Override
	public String toString() {
		return String.format("(%.3f, %.3f)", x, y);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Location(x, y);
	}
}
