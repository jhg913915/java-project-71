package hexlet.code;

import hexlet.code.Formatters.Formatter;
import hexlet.code.Formatters.PlainFormatter;
import hexlet.code.Formatters.StylishFormatter;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.LinkedHashMap;

public class Differ {

    public static String generate(String filePath1, String filePath2, String formatName) throws Exception {
        Map<String, Object> file1Data = Parser.parseFile(filePath1);
        Map<String, Object> file2Data = Parser.parseFile(filePath2);
        List<Map<String, Object>> diff = buildDiff(file1Data, file2Data);
        return format(diff, formatName);
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        return generate(filePath1, filePath2, "stylish");
    }

    private static String format(List<Map<String, Object>> diff, String formatName) {
        Formatter formatter = switch (formatName) {
            case "stylish" -> new StylishFormatter();
            case "plain" -> new PlainFormatter();
            default -> new StylishFormatter();
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
            Map<String, Object> diffItem = new LinkedHashMap<>();
            diffItem.put("key", key);
            if (file1Data.containsKey(key) && !file2Data.containsKey(key)) {
                diffItem.put("type", "removed");
                diffItem.put("value", value1);
            } else if (!file1Data.containsKey(key) && file2Data.containsKey(key)) {
                diffItem.put("type", "added");
                diffItem.put("value", value2);
            } else if (value1 != null && value2 != null && !value1.equals(value2)) {
                diffItem.put("type", "changed");
                diffItem.put("oldValue", value1);
                diffItem.put("newValue", value2);
            } else if ((value1 == null && value2 != null) || (value1 != null && value2 == null)) {
                diffItem.put("type", "changed");
                diffItem.put("oldValue", value1);
                diffItem.put("newValue", value2);
            } else {
                diffItem.put("type", "unchanged");
                diffItem.put("value", value1);
            }
            diff.add(diffItem);
        }
        return diff;
    }
}
