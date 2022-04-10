package tetrisgame.parts.component;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;

public class RemovingAnim extends IGameComponent {
    private Tile mPubBoard[][];

    private float mSumTime;
    private int mColArr[];
    private int mSize;

    private boolean mbLoadFrame1;
    private boolean mbLoadFrame2;

    private static final float TOTAL_TIME = 0.5f;
    private static final float TIME_FRAME_NEXT_1 = 0.1f;

    public RemovingAnim(TetrisGame g, Tile gb[][]) {
        super(g);
        mPubBoard = gb;
        mColArr = new int[4];
    }

    @Override
    public void update(float deltaTime, int userInput) {
        if (mSize > 0) {
            mSumTime += deltaTime;
            if (mbLoadFrame1 == false) {
                for (int i = 0; i < mSize; i++) {
                    int col = mColArr[i];
                    assert (col != -1);
                    Tile tileRemove = new Tile(mPubGame, mPubBoard, "tile_remove1");
                    for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                        mPubBoard[col][row] = tileRemove;
                    }
                }
                mbLoadFrame1 = true;
            }

            if (mbLoadFrame2 == false && mSumTime >= TIME_FRAME_NEXT_1) {
                for (int i = 0; i < mSize; i++) {
                    int col = mColArr[i];
                    Tile tileRemove = new Tile(mPubGame, mPubBoard, "tile_remove2");
                    for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                        mPubBoard[col][row] = tileRemove;
                    }
                }
                mbLoadFrame2 = true;
            }

            if (mSumTime >= TOTAL_TIME) {
                mPubGame.broadcast(eMsg.REMOVE_ANIM_OVER);
                mSumTime = 0.f;
                mbLoadFrame1 = false;
                mbLoadFrame2 = false;
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
