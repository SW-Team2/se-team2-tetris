package data.score;

public class ScoreInfo {
    private String mName;
    private long mScore;

    public ScoreInfo(String name, long score) {
        mName = name;
        mScore = score;
    }

    public String getName() {
        return mName;
    }

    public long getScore() {
        return mScore;
    }
}
