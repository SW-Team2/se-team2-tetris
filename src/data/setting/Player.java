package data.setting;

public enum Player {
    FIRST(1),
    SECOND(2);

    private final int value;

    Player(int value) { this.value = value; }

    public int getValue() { return value; }
}
