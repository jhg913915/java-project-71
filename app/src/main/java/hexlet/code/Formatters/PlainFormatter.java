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
            DiffType diffType = DiffType.fromString(type);
            switch (diffType) {
                case ADDED:
                    processAdded(sb, key, diffItem);
                    break;
                case REMOVED:
                    processRemoved(sb, key);
                    break;
                case CHANGED:
                    processChanged(sb, key, diffItem);
                    break;
                default:
                    break;
            }
        }
        return sb.toString().trim();
    }

    private void processAdded(StringBuilder sb, String key, Map<String, Object> diffItem) {
        Object newValue = diffItem.get("value");
        sb.append("Property '")
                .append(key)
                .append("' was added with value: ")
                .append(formatValue(newValue))
                .append("\n");
    }

    private void processRemoved(StringBuilder sb, String key) {
        sb.append("Property '")
                .append(key)
                .append("' was removed\n");
    }

    private void processChanged(StringBuilder sb, String key, Map<String, Object> diffItem) {
        Object oldValue = diffItem.get("oldValue");
        Object newValue = diffItem.get("newValue");
        sb.append("Property '")
                .append(key)
                .append("' was updated. From ")
                .append(formatValue(oldValue))
                .append(" to ")
                .append(formatValue(newValue))
                .append("\n");
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
