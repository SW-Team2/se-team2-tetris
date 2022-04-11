package tetrisgame.itemmode.itemmodeparts;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;
import tetrisgame.parts.Position;
import tetrisgame.parts.component.IAnim;
import tetrisgame.parts.component.Tile;

public class WeightItemAnim extends IAnim {
    private Tile mPubBoard[][];

    private float mSumTime;
    private int mCurrCol;
    private int mStartRow;
    private int mRowLen;
    private static final int mEndCol = 17;

    private static final float TIME_SLICE = 0.1f;

    public WeightItemAnim(TetrisGame g, Tile gb[][], Position startPos) {
        super(g);
        mPubBoard = gb;
        mCurrCol = startPos.mCol;
        mStartRow = startPos.mRow;
        mRowLen = mStartRow + 4;
    }

    @Override
    public void update(float deltaTime, int userInput) {
        mSumTime += deltaTime;
        if (mCurrCol == mEndCol) {
            mPubGame.broadcast(eMsg.FOCUS_ANIM_OVER);
            return;
        }

        if (mSumTime >= TIME_SLICE) {
            mSumTime -= 0.f;
            int eraseCol = mCurrCol + 3;
            for (int row = mStartRow; row < mRowLen; row++) {
                mPubBoard[eraseCol][row] = null;
            }
            for (int col = eraseCol; col != mCurrCol; col--) {
                for (int row = mStartRow; row < mRowLen; row++) {
                    mPubBoard[col][row] = mPubBoard[col - 1][row];
                }
            }
            mCurrCol++;
            mSumTime = 0.f;
        }
    }

    @Override
    public void react(eMsg msg) {

    }
}
