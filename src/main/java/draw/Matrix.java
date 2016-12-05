package draw;

import angleDefenseLogic.Location;

/**
 * Created by Sam Sartor on 12/4/2016.
 */
public class Matrix {
    private static float cos(float rot) {
        return (float) Math.cos(rot);
    }

    private static float sin(float rot) {
        return (float) Math.sin(rot);
    }

    public static float[] gen(float x, float y, float z, float rot) {
        return new float[] {
            cos(rot), 0, -sin(rot), x,
            0,        1, 0,         y,
            sin(rot), 0, cos(rot),  z,
            0,        0, 0,         1
        };
    }

    public static float[] gen(Location pos, float alt, float rot) {
        return gen(pos.getX(), alt, pos.getY(), rot);
    }
}
