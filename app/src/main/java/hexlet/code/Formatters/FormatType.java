package hexlet.code.Formatters;

public enum FormatType {
    STYLISH("stylish"),
    PLAIN("plain"),
    JSON("json");
    private final String formatName;

    FormatType(String formatName) {
        this.formatName = formatName;
    }

    public String getFormatName() {
        return formatName;
    }

    public static FormatType fromString(String formatName) {
        for (FormatType formatType : FormatType.values()) {
            if (formatType.getFormatName().equals(formatName)) {
                return formatType;
            }
        }
        return STYLISH;
    }
}
