package hexlet.code.Utils;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class TestUtils {
    public static String getFixturePath(String fileName) throws URISyntaxException {
        return Paths.get(TestUtils.class.getClassLoader().getResource(fileName).toURI()).toAbsolutePath().toString();
    }
}
