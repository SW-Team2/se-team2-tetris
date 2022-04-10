package tetrisgame.parts.component;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;

public class RemovingAnim extends IGameBoardComponent {
    private float mSumTime;

    private int mColArr[];
    private int mSize;

    private static final float TOTAL_TIME = 0.5f;

    private static final float TIME_FRAME_NEXT_1 = 0.2f;

    public RemovingAnim(TetrisGame g, GameBoard gb, int colArr[], int size) {
        super(g, gb);
        mColArr = colArr;
        mSize = size;

        for (int i = 0; i < mSize; i++) {
            int col = mColArr[i];
            Tile tileRemove = new Tile(mPubGame, mPubBoard, "tile_remove1");
            for (int row = 0; row < GameBoard.BOARD_ROW; row++) {
                mPubBoard.mBoard[col][row] = tileRemove;
            }
        }
    }

    @Override
    public void update(float deltaTime, int userInput) {
        mSumTime += deltaTime;

        if (mSumTime >= TIME_FRAME_NEXT_1) {
            for (int i = 0; i < mSize; i++) {
                int col = mColArr[i];
                Tile tileRemove = new Tile(mPubGame, mPubBoard, "tile_remove2");
                for (int row = 0; row < GameBoard.BOARD_ROW; row++) {
                    mPubBoard.mBoard[col][row] = tileRemove;
                }
            }
        }

        if (mSumTime >= TOTAL_TIME) {
            mPubGame.broadcast(eMsg.REMOVE_ANIM_OVER);
        }

    }

    @Override
    public void react(eMsg msg) {

    }
}
