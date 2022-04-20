package data.score;

public class Score {
    private String name;
    private int score;
    private String difficulty;
    private String gameMode;
    public Score() {
    }

    public Score(String name, int score, String difficulty, String gameMode) {
        this.name = name;
        this.score = score;
        this.difficulty = difficulty;
        this.gameMode = gameMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }
}
