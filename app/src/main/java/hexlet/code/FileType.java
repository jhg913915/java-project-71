package hexlet.code;

public enum FileType {
    JSON(".json"),
    YML(".yml"),
    YAML(".yaml");

    private final String fileExtension;

    FileType(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public static FileType fromString(String fileExtension) {
        for (FileType fileType : FileType.values()) {
            if (fileType.getFileExtension().equals(fileExtension)) {
                return fileType;
            }
        }
        return null;
    }
}
