package hexlet.code.Formatters;

import hexlet.code.DiffType;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public final class PlainFormatter implements Formatter {

    @Override
    public String format(List<Map<String, Object>> diff) {
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> diffItem : diff) {
            String key = (String) diffItem.get("key");
            String type = (String) diffItem.get("type");
            Object value;
            Object newValue;
            Object oldValue;
            switch (DiffType.fromString(type)) {
                case ADDED:
                    newValue = diffItem.get("value");
                    sb.append("Property '")
                            .append(key)
                            .append("' was added with value: ")
                            .append(formatValue(newValue))
                            .append("\n");
                    break;
                case REMOVED:
                    sb.append("Property '").append(key).append("' was removed\n");
                    break;
                case CHANGED:
                    oldValue = diffItem.get("oldValue");
                    newValue = diffItem.get("newValue");
                    sb.append("Property '")
                            .append(key)
                            .append("' was updated. From ")
                            .append(formatValue(oldValue)).append(" to ")
                            .append(formatValue(newValue)).append("\n");
                    break;
                default:
                    break;
            }
        }
        return sb.toString().trim();
    }

    private String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "'" + value + "'";
        } else if (value instanceof Map || value instanceof List) {
            return "[complex value]";
        }
        return Objects.toString(value);
    }
}
