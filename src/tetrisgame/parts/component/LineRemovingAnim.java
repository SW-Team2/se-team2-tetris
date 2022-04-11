package tetrisgame.parts.component;

import tetrisgame.TetrisGame;
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
        mPubGame.broadcast(eMsg.LINE_REMOVE_ANIM_START);
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
                    Tile tileRemove = new Tile(mPubGame, mPubBoard, "tile_remove2");
                    for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                        mPubBoard[col][row] = tileRemove;
                    }
                }
                return;
            }

            if (mSumTime >= 0) {
                for (int i = 0; i < mSize; i++) {
                    int col = mColArr[i];
                    Tile tileRemove = new Tile(mPubGame, mPubBoard, "tile_remove1");
                    for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                        mPubBoard[col][row] = tileRemove;
                    }
                }
                return;
            }
        }
    }

    @Override
    public void react(eMsg msg) {
        assert (mSize < 4);
        int col = -1;
        switch (msg) {
            case LINE_REMOVE_0:
                col = 0;
                break;
            case LINE_REMOVE_1:
                col = 1;
                break;
            case LINE_REMOVE_2:
                col = 2;
                break;
            case LINE_REMOVE_3:
                col = 3;
                break;
            case LINE_REMOVE_4:
                col = 4;
                break;
            case LINE_REMOVE_5:
                col = 5;
                break;
            case LINE_REMOVE_6:
                col = 6;
                break;
            case LINE_REMOVE_7:
                col = 7;
                break;
            case LINE_REMOVE_8:
                col = 8;
                break;
            case LINE_REMOVE_9:
                col = 9;
                break;
            case LINE_REMOVE_10:
                col = 10;
                break;
            case LINE_REMOVE_11:
                col = 11;
                break;
            case LINE_REMOVE_12:
                col = 12;
                break;
            case LINE_REMOVE_13:
                col = 13;
                break;
            case LINE_REMOVE_14:
                col = 14;
                break;
            case LINE_REMOVE_15:
                col = 15;
                break;
            case LINE_REMOVE_16:
                col = 16;
                break;
            case LINE_REMOVE_17:
                col = 17;
                break;
            case LINE_REMOVE_18:
                col = 18;
                break;
            case LINE_REMOVE_19:
                col = 19;
                break;
            default:
                return;
        }
        mColArr[mSize++] = col;
    }
}
