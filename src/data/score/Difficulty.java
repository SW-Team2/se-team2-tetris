package data.score;

public enum Difficulty {
    EASY("Easy"),
    NORMAL("Normal"),
    HARD("Hard");

    private final String value;

    Difficulty(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
