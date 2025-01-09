package hexlet.code.Formatters;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StylishFormatter implements Formatter {
    @Override
    public String format(List<Map<String, Object>> diff) {
        StringBuilder sb = new StringBuilder("{\n");
        for (Map<String, Object> diffItem : diff) {
            String key = (String) diffItem.get("key");
            String type = (String) diffItem.get("type");
            Object value;
            Object newValue;
            Object oldValue;
            switch (type) {
                case "added":
                    value = diffItem.get("value");
                    if (value == null) {
                        sb.append("  + ").append(key).append(": ").append(Objects.toString(null)).append("\n");
                    } else {
                        sb.append("  + ").append(key).append(": ").append(Objects.toString(value)).append("\n");
                    }
                    break;
                case "removed":
                    value = diffItem.get("value");
                    sb.append("  - ").append(key).append(": ").append(Objects.toString(value)).append("\n");
                    break;
                case "changed":
                    oldValue = diffItem.get("oldValue");
                    newValue = diffItem.get("newValue");
                    sb.append("  - ").append(key).append(": ").append(Objects.toString(oldValue)).append("\n");
                    sb.append("  + ").append(key).append(": ").append(Objects.toString(newValue)).append("\n");
                    break;
                case "unchanged":
                    value = diffItem.get("value");
                    sb.append("    ").append(key).append(": ").append(Objects.toString(value)).append("\n");
                    break;
                default:
                    break;
            }
        }
        sb.append("}\n");
        return sb.toString();
    }
}
