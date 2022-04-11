package tetrisgame.parts.component;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;

public class Score extends IGameComponent {
    private int mScore;
    private int mCurrRemoveLineCount;
    private int mTotalRemoveLineCount;
    private int mTenLineCount;

    private static final int SCORE_UNIT = 100;
    private static final int MULTIPLE_BREAK_BONUS_2L = 30;
    private static final int MULTIPLE_BREAK_BONUS_3L = 60;
    private static final int MULTIPLE_BREAK_BONUS_4L = 100;

    public Score(TetrisGame g) {
        super(g);
    }

    @Override
    public void update(float deltaTime, int userInput) {
        if (mTenLineCount >= 3) {
            mTenLineCount -= 3;
            mPubGame.broadcast(eMsg.ERASE_10xN_LINES);
        }
    }

    @Override
    public void react(eMsg msg) {
        switch (msg) {
            case LINE_REMOVE_0:
            case LINE_REMOVE_1:
            case LINE_REMOVE_2:
            case LINE_REMOVE_3:
            case LINE_REMOVE_4:
            case LINE_REMOVE_5:
            case LINE_REMOVE_6:
            case LINE_REMOVE_7:
            case LINE_REMOVE_8:
            case LINE_REMOVE_9:
            case LINE_REMOVE_10:
            case LINE_REMOVE_11:
            case LINE_REMOVE_12:
            case LINE_REMOVE_13:
            case LINE_REMOVE_14:
            case LINE_REMOVE_15:
            case LINE_REMOVE_16:
            case LINE_REMOVE_17:
            case LINE_REMOVE_18:
            case LINE_REMOVE_19:
                mCurrRemoveLineCount++;
                break;
            default:
                break;
        }
        if (msg == eMsg.FOCUS_ANIM_OVER) {
            int addedScore = 0;
            switch (mCurrRemoveLineCount) {
                case 0:
                    break;
                case 1:
                    addedScore = SCORE_UNIT;
                    break;
                case 2:
                    addedScore = SCORE_UNIT * 2 + MULTIPLE_BREAK_BONUS_2L;
                    break;
                case 3:
                    addedScore = SCORE_UNIT * 3 + MULTIPLE_BREAK_BONUS_3L;
                    break;
                case 4:
                    addedScore = SCORE_UNIT * 4 + MULTIPLE_BREAK_BONUS_4L;
                    break;
            }
            mTotalRemoveLineCount += mCurrRemoveLineCount;
            mTenLineCount += mCurrRemoveLineCount;
            addedScore += addedScore * (mTotalRemoveLineCount / 10) / 10.f;

            mScore += addedScore;
            mCurrRemoveLineCount = 0;
        }
    }

    public int getScore() {
        return mScore;
    }

    public int getRemoveLineCount() {
        return mTotalRemoveLineCount;
    }
}
