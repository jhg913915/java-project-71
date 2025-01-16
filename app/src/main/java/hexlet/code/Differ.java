package hexlet.code;

import hexlet.code.Formatters.FormatType;
import hexlet.code.Formatters.Formatter;
import hexlet.code.Formatters.PlainFormatter;
import hexlet.code.Formatters.StylishFormatter;
import hexlet.code.Formatters.JsonFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.LinkedHashMap;

public class Differ {
    private static final FormatType DEFAULT_FORMAT = FormatType.STYLISH;

    public static String generate(String filePath1, String filePath2, String formatName) throws Exception {
        Map<String, Object> file1Data = Parser.parseFile(filePath1);
        Map<String, Object> file2Data = Parser.parseFile(filePath2);
        List<Map<String, Object>> diff = buildDiff(file1Data, file2Data);
        return format(diff, formatName);
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        return generate(filePath1, filePath2, DEFAULT_FORMAT.getFormatName());
    }

    private static String format(List<Map<String, Object>> diff, String formatName) {
        Formatter formatter = switch (FormatType.fromString(formatName)) {
            case STYLISH -> new StylishFormatter();
            case PLAIN -> new PlainFormatter();
            case JSON -> new JsonFormatter();
        };
        return formatter.format(diff);
    }

    public static List<Map<String, Object>> buildDiff(Map<String, Object> file1Data, Map<String, Object> file2Data) {
        TreeMap<String, Object> sortedKeys = new TreeMap<>(file1Data);
        sortedKeys.putAll(file2Data);
        Set<String> allKeys = sortedKeys.keySet();
        List<Map<String, Object>> diff = new ArrayList<>();
        for (String key : allKeys) {
            Object value1 = file1Data.get(key);
            Object value2 = file2Data.get(key);
            Map<String, Object> diffItem = createDiffItem(key, value1, value2, file1Data, file2Data);
            diff.add(diffItem);
        }
        return diff;
    }

    private static Map<String, Object> createDiffItem(String key, Object value1, Object value2,
                                                      Map<String, Object> file1Data, Map<String, Object> file2Data) {
        Map<String, Object> diffItem = new LinkedHashMap<>();
        diffItem.put("key", key);
        if (file1Data.containsKey(key) && !file2Data.containsKey(key)) {
            diffItem.put("type", DiffType.REMOVED.getTypeName());
            diffItem.put("value", value1);
        } else if (!file1Data.containsKey(key) && file2Data.containsKey(key)) {
            diffItem.put("type", DiffType.ADDED.getTypeName());
            diffItem.put("value", value2);
        } else if (valuesAreDifferent(value1, value2)) {
            diffItem.put("type", DiffType.CHANGED.getTypeName());
            diffItem.put("oldValue", value1);
            diffItem.put("newValue", value2);
        } else {
            diffItem.put("type", DiffType.UNCHANGED.getTypeName());
            diffItem.put("value", value1);
        }
        return diffItem;
    }

    private static boolean valuesAreDifferent(Object value1, Object value2) {
        if (value1 == null && value2 == null) {
            return false;
        }
        if (value1 == null || value2 == null) {
            return true;
        }
        return !value1.equals(value2);
    }
}
