package hexlet.code.Formatters;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import hexlet.code.DiffType;

public final class StylishFormatter implements Formatter {

    @Override
    public String format(List<Map<String, Object>> diff) {
        StringBuilder sb = new StringBuilder("{\n");
        for (Map<String, Object> diffItem : diff) {
            String key = (String) diffItem.get("key");
            String type = (String) diffItem.get("type");
            Object value;
            Object newValue;
            Object oldValue;
            switch (DiffType.fromString(type)) {
                case ADDED:
                    value = diffItem.get("value");
                    if (value == null) {
                        sb.append("  + ").append(key).append(": ").append(Objects.toString(null)).append("\n");
                    } else {
                        sb.append("  + ").append(key).append(": ").append(Objects.toString(value)).append("\n");
                    }
                    break;
                case REMOVED:
                    value = diffItem.get("value");
                    sb.append("  - ").append(key).append(": ").append(Objects.toString(value)).append("\n");
                    break;
                case CHANGED:
                    oldValue = diffItem.get("oldValue");
                    newValue = diffItem.get("newValue");
                    sb.append("  - ").append(key).append(": ").append(Objects.toString(oldValue)).append("\n");
                    sb.append("  + ").append(key).append(": ").append(Objects.toString(newValue)).append("\n");
                    break;
                case UNCHANGED:
                    value = diffItem.get("value");
                    sb.append("    ").append(key).append(": ").append(Objects.toString(value)).append("\n");
                    break;
                default:
                    break;
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
