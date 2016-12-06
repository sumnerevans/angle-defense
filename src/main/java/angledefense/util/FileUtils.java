package angledefense.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by sumner on 11/30/16.
 */
public class FileUtils {
    public static InputStream newFileStream(String path) throws FileNotFoundException {
        ClassLoader cl = FileUtils.class.getClassLoader();
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
}
