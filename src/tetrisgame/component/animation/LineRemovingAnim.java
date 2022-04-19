package tetrisgame.component.animation;

import tetrisgame.TetrisGame;
import tetrisgame.component.tile.Tile;
import tetrisgame.enumerations.eMsg;

public class LineRemovingAnim extends IAnim {
    private Tile mPubBoard[][];

    private float mSumTime;
    private int mColArr[];
    private int mSize;

    private static final float TOTAL_TIME = 0.5f;
    private static final float FRAME_1_TIME = 0.2f;

    public LineRemovingAnim(TetrisGame g, Tile gb[][]) {
        super(g);
        mPubBoard = gb;
        mColArr = new int[4];
    }

    @Override
    public void update(float deltaTime, int userInput) {
        if (mSize > 0) {
            mSumTime += deltaTime;

            if (mSumTime >= TOTAL_TIME) {
                mPubGame.broadcast(eMsg.FOCUS_ANIM_OVER);
                return;
            }

            if (mSumTime >= FRAME_1_TIME) {
                for (int i = 0; i < mSize; i++) {
                    int col = mColArr[i];
                    for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                        mPubBoard[col][row].setName("tile_remove2");
                    }
                }
                return;
            }

            if (mSumTime >= 0) {
                for (int i = 0; i < mSize; i++) {
                    int col = mColArr[i];
                    for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                        mPubBoard[col][row].setName("tile_remove1");
                    }
                }
                return;
            }
        }
    }

    @Override
    public void react(eMsg msg) {
        assert (mSize <= 4);
        int line = -1;
        switch (msg) {
            case LINE_REMOVE_0:
                line = 0;
                break;
            case LINE_REMOVE_1:
                line = 1;
                break;
            case LINE_REMOVE_2:
                line = 2;
                break;
            case LINE_REMOVE_3:
                line = 3;
                break;
            case LINE_REMOVE_4:
                line = 4;
                break;
            case LINE_REMOVE_5:
                line = 5;
                break;
            case LINE_REMOVE_6:
                line = 6;
                break;
            case LINE_REMOVE_7:
                line = 7;
                break;
            case LINE_REMOVE_8:
                line = 8;
                break;
            case LINE_REMOVE_9:
                line = 9;
                break;
            case LINE_REMOVE_10:
                line = 10;
                break;
            case LINE_REMOVE_11:
                line = 11;
                break;
            case LINE_REMOVE_12:
                line = 12;
                break;
            case LINE_REMOVE_13:
                line = 13;
                break;
            case LINE_REMOVE_14:
                line = 14;
                break;
            case LINE_REMOVE_15:
                line = 15;
                break;
            case LINE_REMOVE_16:
                line = 16;
                break;
            case LINE_REMOVE_17:
                line = 17;
                break;
            case LINE_REMOVE_18:
                line = 18;
                break;
            case LINE_REMOVE_19:
                line = 19;
                break;
            default:
                return;
        }
        mColArr[mSize++] = line;
    }
}
