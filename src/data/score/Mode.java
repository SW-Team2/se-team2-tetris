package data.score;

public enum Mode {
    DEFAULT("Default"),
    ITEM_MODE("Item");

    private final String value;

    Mode(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
