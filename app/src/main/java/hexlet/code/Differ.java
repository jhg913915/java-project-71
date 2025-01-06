package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Differ {
    public static Map<String, Object> parseFile(String filePath) throws Exception {
        Path path = Paths.get(filePath).toAbsolutePath();
        String content = readFile(path);
        return parse(content);
    }

    private static String readFile(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }

    private static Map<String, Object> parse(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        Map<String, Object> file1Data = parseFile(filePath1);
        Map<String, Object> file2Data = parseFile(filePath2);
        return buildDiff(file1Data, file2Data);
    }

    private static String buildDiff(Map<String, Object> file1Data, Map<String, Object> file2Data) {
        TreeMap<String, Object> sortedKeys = new TreeMap<>(file1Data);
        sortedKeys.putAll(file2Data);
        Set<String> allKeys = sortedKeys.keySet();
        StringBuilder sb = new StringBuilder("{\n");
        for (String key : allKeys) {
            Object value1 = file1Data.get(key);
            Object value2 = file2Data.get(key);
            if (value1 == null) {
                sb.append("  + ").append(key).append(": ").append(value2).append("\n");
            } else if (value2 == null) {
                sb.append("  - ").append(key).append(": ").append(value1).append("\n");
            } else if (!value1.equals(value2)) {
                sb.append("  - ").append(key).append(": ").append(value1).append("\n");
                sb.append("  + ").append(key).append(": ").append(value2).append("\n");
            } else {
                sb.append("    ").append(key).append(": ").append(value1).append("\n");
            }
        }
        sb.append("}\n");
        return sb.toString();
    }
}
