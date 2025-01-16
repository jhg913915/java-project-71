package utils;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class TestUtils {
    public static String getFixturePath(String fileName) throws URISyntaxException {
        return Paths.get(TestUtils.class.getClassLoader().getResource(fileName).toURI()).toAbsolutePath().toString();
    }

    public static String normalize(String input) {
        return input.replace("\r\n", "\n").replace("\r", "\n");
    }
}
