package hexlet.code;

public enum DiffType {
    REMOVED("removed"),
    ADDED("added"),
    CHANGED("changed"),
    UNCHANGED("unchanged");
    private final String typeName;

    DiffType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public static DiffType fromString(String typeName) {
        for (DiffType diffType : DiffType.values()) {
            if (diffType.getTypeName().equals(typeName)) {
                return diffType;
            }
        }
        return UNCHANGED;
    }
}
