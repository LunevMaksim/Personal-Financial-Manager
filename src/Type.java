public enum Type {
    INCOME("Доход"),
    EXPENSE("Расход");
    private final String russianType;

    Type(String type) {
        this.russianType = type;
    }

    public String getRussianType() {
        return russianType;
    }

    public static Type fromType(String str) {
        if (str == null) return null;

        String normalized = str.trim().toLowerCase();

        for (Type status : Type.values()) {
            if (status.getRussianType().toLowerCase().equals(normalized)) {
                return status;
            }
        }

        if (normalized.startsWith("дох")) return INCOME;
        if (normalized.startsWith("рас")) return EXPENSE;

        return null;
    }
}
