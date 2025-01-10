import org.junit.jupiter.api.Test;
import java.util.Map;
import hexlet.code.Parser;

import static hexlet.code.Utils.TestUtils.getFixturePath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {

    @Test
    void testParseJson() throws Exception {
        String filePath = getFixturePath("equal1.json");
        Map<String, Object> result = Parser.parseFile(filePath);
        assertEquals(true, result.get("verbose"));
        assertEquals("hexlet.io", result.get("host"));
        assertEquals(false, result.get("follow"));
    }

    @Test
    void testParseYaml() throws Exception {
        String filePath = getFixturePath("equal1.yaml");
        Map<String, Object> result = Parser.parseFile(filePath);
        assertEquals(4, result.size());
        assertEquals(50, result.get("timeout"));
        assertEquals(true, result.get("verbose"));
        assertEquals("hexlet.io", result.get("host"));
        assertEquals(false, result.get("follow"));
    }

    @Test
    void testParseIncorrectFormat() {
        assertThrows(Exception.class, () -> Parser.parseFile("incorrect.txt"));
    }

    @Test
    void testParseIncorrectFile() {
        assertThrows(Exception.class, () -> Parser.parseFile("incorrect"));
    }
}
