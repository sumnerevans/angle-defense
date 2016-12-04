package angleDefenseLogic;

/**
 * Created by Mobius on 11/22/16.
 */
public class Location {
    private float x, y;
    private static final float delta = 0.0001f;

    public Location(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Location))
            return false;

        Location other = (Location) o;
        return equals(other.x, other.y);
    }

    public boolean equals(float x, float y) {
        return Math.abs(this.x - x) < delta && Math.abs(this.y - y) < delta;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
