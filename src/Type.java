public enum Type {
    INCOME("Доход"),
    EXPENSE("Расход");
    private final String type;

    Type(String type) {
        this.type = type;
    }

    public String getRussianName() {
        return type;
    }
}
