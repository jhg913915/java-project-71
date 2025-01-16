import hexlet.code.Differ;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Files;
import java.nio.file.Path;

import static utils.TestUtils.getFixturePath;
import static utils.TestUtils.normalize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DifferTest {
    @ParameterizedTest
    @CsvSource({
        "actual/empty1.json, actual/empty2.json, expected/empty_result.txt",
        "actual/equal1.json, actual/equal2.json, expected/equal_result.txt",
        "actual/diff1.json, actual/diff2.json, expected/diff_result.txt",
        "actual/diff1.yaml, actual/diff2.yaml, expected/diff_result.txt"
    })

    void testGenerate(String file1, String file2, String expectedFile) throws Exception {
        String file1Path = getFixturePath(file1);
        String file2Path = getFixturePath(file2);
        String expected = Files.readString(Path.of(getFixturePath(expectedFile)));

        String actual = Differ.generate(file1Path, file2Path);

        assertEquals(normalize(expected), normalize(actual));
    }

    @ParameterizedTest
    @CsvSource({
        "actual/one_empty1.json, actual/one_empty2.json, "
                +
                "expected/one_empty_result1.txt, expected/one_empty_result2.txt",
        "actual/one_add1.json, actual/one_add2.json, "
                +
                "expected/one_add_result1.txt, expected/one_add_result2.txt"
    })
    void testGenerateWithReversedFiles(String file1, String file2, String expectedFile1,
                                       String expectedFile2) throws Exception {
        String file1Path = getFixturePath(file1);
        String file2Path = getFixturePath(file2);
        String expected1 = Files.readString(Path.of(getFixturePath(expectedFile1)));
        String expected2 = Files.readString(Path.of(getFixturePath(expectedFile2)));

        String actual1 = Differ.generate(file1Path, file2Path);
        String actual2 = Differ.generate(file2Path, file1Path);

        assertEquals(normalize(expected1), normalize(actual1));
        assertEquals(normalize(expected2), normalize(actual2));
    }

    @ParameterizedTest
    @CsvSource({
        "actual/nested1.json, actual/nested2.json, expected/nested_result_stylish.txt, stylish",
        "actual/nested1.json, actual/nested2.json, expected/nested_result_plain.txt, plain",
        "actual/nested1.json, actual/nested2.json, expected/nested_result_json.txt, json"
    })
    void testGenerateWithFormats(String file1, String file2, String expectedFile, String format) throws Exception {
        String file1Path = getFixturePath(file1);
        String file2Path = getFixturePath(file2);
        String expected = Files.readString(Path.of(getFixturePath(expectedFile)));

        String actual = Differ.generate(file1Path, file2Path, format);

        assertEquals(normalize(expected), normalize(actual));
    }

    @ParameterizedTest
    @CsvSource({
        "incorrect, incorrect"
    })
    void testGenerateWithIncorrectFile(String file1, String file2) {
        assertThrows(Exception.class, () -> Differ.generate(file1, file2));
    }
}
