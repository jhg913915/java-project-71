import hexlet.code.Differ;
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
    void testGenerateWithIncorrectFile() {
        assertThrows(Exception.class, () -> Differ.generate("incorrect", "incorrect"));
    }
}
