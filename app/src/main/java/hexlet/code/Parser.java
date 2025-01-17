package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

public class Parser {
    public static Map<String, Object> parseFile(String filePath) throws Exception {
        Path path = Paths.get(filePath).toAbsolutePath();
        String content = readFile(path.toString());
        return parse(content, Objects.requireNonNull(getFormat(filePath)));
    }

    private static String readFile(String path) throws Exception {
        Path filePath = Paths.get(path).toAbsolutePath().normalize();
        if (!Files.exists(filePath)) {
            throw new Exception("File does not exist: " + filePath);
        }
        return Files.readString(filePath);
    }

    private static Map<String, Object> parse(String content, FileType fileType) throws Exception {
        ObjectMapper mapper;
        switch (fileType) {
            case JSON:
                mapper = new ObjectMapper();
                break;
            case YAML, YML:
                mapper = new YAMLMapper();
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
        try {
            return mapper.readValue(content, new TypeReference<>() { });
        } catch (MismatchedInputException e) {
            throw new Exception("Incorrect format of file");
        }
    }


    private static FileType getFormat(String path) {
        String trimmedPath = path.trim();
        for (FileType fileType : FileType.values()) {
            if (trimmedPath.endsWith(fileType.getFileExtension())) {
                return fileType;
            }
        }
        return null;
    }
}
