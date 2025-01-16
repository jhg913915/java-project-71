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

            DiffType diffType = DiffType.fromString(type);
            switch (diffType) {
                case ADDED:
                    processAdded(sb, key, diffItem);
                    break;
                case REMOVED:
                    processRemoved(sb, key, diffItem);
                    break;
                case CHANGED:
                    processChanged(sb, key, diffItem);
                    break;
                case UNCHANGED:
                    processUnchanged(sb, key, diffItem);
                    break;
                default:
                    break;
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private void processAdded(StringBuilder sb, String key, Map<String, Object> diffItem) {
        Object value = diffItem.get("value");
        sb.append("  + ").append(key).append(": ")
                .append(Objects.toString(value, "null"))
                .append("\n");
    }

    private void processRemoved(StringBuilder sb, String key, Map<String, Object> diffItem) {
        Object value = diffItem.get("value");
        sb.append("  - ").append(key).append(": ")
                .append(Objects.toString(value, "null"))
                .append("\n");
    }

    private void processChanged(StringBuilder sb, String key, Map<String, Object> diffItem) {
        Object oldValue = diffItem.get("oldValue");
        Object newValue = diffItem.get("newValue");
        sb.append("  - ").append(key).append(": ")
                .append(Objects.toString(oldValue, "null"))
                .append("\n");
        sb.append("  + ").append(key).append(": ")
                .append(Objects.toString(newValue, "null"))
                .append("\n");
    }

    private void processUnchanged(StringBuilder sb, String key, Map<String, Object> diffItem) {
        Object value = diffItem.get("value");
        sb.append("    ").append(key).append(": ")
                .append(Objects.toString(value, "null"))
                .append("\n");
    }

}
