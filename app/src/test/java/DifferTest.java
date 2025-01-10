import hexlet.code.Differ;
import hexlet.code.Utils.TestUtils;
import org.junit.jupiter.api.Test;

import static hexlet.code.Utils.TestUtils.getFixturePath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DifferTest {

    @Test
    void testGenerateWithEmptyFiles() throws Exception {
        String file1Path = getFixturePath("empty1.json");
        String file2Path = getFixturePath("empty2.json");
        String expected = "{\n}\n";
        String actual = Differ.generate(file1Path, file2Path);
        assertEquals(expected, actual);
    }

    @Test
    void testGenerateWithEqualFiles() throws Exception {
        String file1Path = getFixturePath("equal1.json");
        String file2Path = getFixturePath("equal2.json");
        String expected = """
                {
                    follow: false
                    host: hexlet.io
                    timeout: 50
                    verbose: true
                }
                """;
        String actual = Differ.generate(file1Path, file2Path);
        assertEquals(expected, actual);
    }

    @Test
    void testGenerateWithDifferentFiles() throws Exception {
        String file1Path = getFixturePath("diff1.json");
        String file2Path = getFixturePath("diff2.json");
        String expected = """
                {
                    follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }
                """;
        String actual = Differ.generate(file1Path, file2Path);
        assertEquals(expected, actual);
    }

    @Test
    void testGenerateWithDifferentFilesYaml() throws Exception {
        String file1Path = getFixturePath("diff1.yaml");
        String file2Path = getFixturePath("diff2.yaml");
        String expected = """
                {
                    follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }
                """;
        String actual = Differ.generate(file1Path, file2Path);
        assertEquals(expected, actual);
    }

    @Test
    void testGenerateWithOneFileEmpty() throws Exception {
        String file1Path = getFixturePath("one_empty1.json");
        String file2Path = getFixturePath("one_empty2.json");

        String expected = """
                {
                  + timeout: 20
                  + verbose: true
                }
                """;
        String actual = Differ.generate(file1Path, file2Path);
        assertEquals(expected, actual);
        String expected1 = """
                {
                  - timeout: 20
                  - verbose: true
                }
                """;
        String actual1 = Differ.generate(file2Path, file1Path);
        assertEquals(expected1, actual1);

    }
    @Test
    void testGenerateWithOneFileAdded() throws Exception {
        String file1Path = getFixturePath("one_add1.json");
        String file2Path = getFixturePath("one_add2.json");
        String expected = """
                {
                    timeout: 20
                  + verbose: true
                }
                """;

        String actual = Differ.generate(file1Path, file2Path);
        assertEquals(expected, actual);
        String expected1 = """
                 {
                     timeout: 20
                   - verbose: true
                 }
                 """;
        String actual1 = Differ.generate(file2Path, file1Path);
        assertEquals(expected1, actual1);

    }

    @Test
    void testGenerateWithNestedStructure() throws Exception {
        String file1Path = TestUtils.getFixturePath("nested1.json");
        String file2Path = TestUtils.getFixturePath("nested2.json");
        String expected = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }
                """;
        String actual = Differ.generate(file1Path, file2Path);
        assertEquals(expected, actual);
        String file3Path = TestUtils.getFixturePath("nested1.yaml");
        String file4Path = TestUtils.getFixturePath("nested2.yaml");
        String actual1 = Differ.generate(file3Path, file4Path);
        assertEquals(expected, actual1);
    }

    @Test
    void testGenerateWithIncorrectFile() {
        assertThrows(Exception.class, () -> Differ.generate("incorrect", "incorrect"));
    }
}
