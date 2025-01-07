package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parseFile(String filePath) throws Exception {
        Path path = Paths.get(filePath).toAbsolutePath();
        String content = readFile(path);
        return parse(content, getFormat(filePath));
    }

    private static String readFile(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }

    private static String getFormat(String path) {
        String trimmedPath = path.trim();
        if (trimmedPath.endsWith(".json")) {
            return "json";
        } else if (trimmedPath.endsWith(".yml") || trimmedPath.endsWith(".yaml")) {
            return "yaml";
        }
        return null;
    }

    private static Map<String, Object> parse(String content, String format) throws Exception {
        ObjectMapper mapper;
        if ("json".equals(format)) {
            mapper = new ObjectMapper();
        } else if ("yaml".equals(format)) {
            mapper = new YAMLMapper();
        } else {
            throw new Exception("Unsupported file format");
        }
        return mapper.readValue(content, Map.class);
    }
}
