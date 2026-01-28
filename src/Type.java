public enum Type {
    INCOME("Доход"),
    EXPENSE("Расход");
    private final String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Type fromType(String str) {
        if (str == null) return null;

        String normalized = str.trim().toLowerCase();

        for (Type status : Type.values()) {
            if (status.getType().toLowerCase().equals(normalized)) {
                return status;
            }
        }

        if (normalized.startsWith("дох")) return INCOME;
        if (normalized.startsWith("рас")) return EXPENSE;

        return null;
    }
}
