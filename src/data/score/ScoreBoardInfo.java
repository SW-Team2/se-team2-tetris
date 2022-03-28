package data.score;

public class ScoreBoardInfo {
    private static ScoreBoardInfo mUniqueInstance = null;

    private ScoreInfo mScoreInfos[];
    private static final int mPersonnel = 10;

    private ScoreBoardInfo() {
        // TODO:
    }

    public static ScoreBoardInfo getInstance() {
        if (mUniqueInstance == null) {
            mUniqueInstance = new ScoreBoardInfo();
        }
        return mUniqueInstance;
    }

    public boolean addNewScore(String name, long score) {
        boolean addable = true;
        // TODO:
        return addable;
    }
}
