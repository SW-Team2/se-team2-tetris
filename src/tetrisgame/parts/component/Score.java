package tetrisgame.parts.component;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;

public class Score extends IGameComponent {
    private int mScore;
    private int mCurrRemoveLineCount;
    private int mTotalRemoveLineCount;

    private static final int SCORE_UNIT = 100;
    private static final int MULTIPLE_BREAK_BONUS_2L = 30;
    private static final int MULTIPLE_BREAK_BONUS_3L = 60;
    private static final int MULTIPLE_BREAK_BONUS_4L = 100;

    public Score(TetrisGame g) {
        super(g);
    }

    @Override
    public void update(float deltaTime, int userInput) {
    }

    @Override
    public void react(eMsg msg) {
        if (msg == eMsg.LINE_REMOVE) {
            mCurrRemoveLineCount++;
        }
        if (msg == eMsg.REMOVE_ANIM_OVER) {
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
            addedScore += addedScore * (mTotalRemoveLineCount / 10) / 10.f;

            mScore += addedScore;
            mCurrRemoveLineCount = 0;
        }
    }

    public int getScore() {
        return mScore;
    }
}
