package angledefense.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by sumner on 11/30/16.
 */
public class Util {
    public static InputStream newFileStream(String path) throws FileNotFoundException {
        ClassLoader cl = Util.class.getClassLoader();
        InputStream stream = cl.getResourceAsStream(path);


        if (stream == null) {
            try {
                stream = new FileInputStream(new File(new File("data/"), path));
            } catch (FileNotFoundException ex) {
                throw new FileNotFoundException(String.format("Could not find file %s in jar or data/", path));
            }
        }

        return stream;
    }

    public static float boundAngle(float theta) {
        theta /= Math.PI;
        theta = (theta + 1) / 2;
        if (theta > 1 || theta < 0) theta -= (int) (theta);
        theta = theta * 2 - 1;
        return theta * (float) Math.PI;
    }

    public static boolean angleInRange(float theta1, float theta2, float tolerance) {
        theta1 = boundAngle(theta1);
        theta2 = boundAngle(theta2);
        float dtheta = Math.abs(theta1 - theta2);

        if (dtheta > Math.PI) {
            if (2 * Math.PI - dtheta <= tolerance)
                return true;
        } else {
            if (dtheta <= tolerance)
                return true;
        }

        return false;
    }

    public static float toDegrees(float angle) {
        return (float) 180 / (float) Math.PI * angle;
    }
}
