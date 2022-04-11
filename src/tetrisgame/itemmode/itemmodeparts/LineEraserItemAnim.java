package tetrisgame.itemmode.itemmodeparts;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;
import tetrisgame.parts.Position;
import tetrisgame.parts.component.IAnim;
import tetrisgame.parts.component.Tetromino;
import tetrisgame.parts.component.Tile;

public class LineEraserItemAnim extends IAnim {
    private Tile mPubBoard[][];

    private float mSumTime;
    private int mEraserCol;
    // private int mEraserRow;

    private static final float TOTAL_TIME = 0.5f;
    private static final float FRAME_1_TIME = 0.2f;

    public LineEraserItemAnim(TetrisGame g, Tile gb[][], Tetromino currTetromino) {
        super(g);
        mPubBoard = gb;
        LOOP_EXIT: for (int col = 0; col < Tetromino.SHAPE_COL; col++) {
            for (int row = 0; row < Tetromino.SHAPE_COL; row++) {
                if (currTetromino.mShape[col][row] != null
                        && currTetromino.mShape[col][row].getName() == "item_tile_lineeraser") {
                    Position pos = currTetromino.getPosition();
                    mEraserCol = pos.mCol + col;
                    // mEraserRow = pos.mRow;
                    break LOOP_EXIT;
                }
            }
        }
        mPubGame.broadcast(eMsg.LINEERASER_ITEM_ANIM_START);
    }

    @Override
    public void update(float deltaTime, int userInput) {
        mSumTime += deltaTime;
        if (mSumTime >= TOTAL_TIME) {
            for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                int col = mEraserCol;
                for (int sCol = col; sCol > 0; sCol--) {
                    mPubBoard[sCol][row] = mPubBoard[sCol - 1][row];

                }
            }
            mPubGame.broadcast(eMsg.FOCUS_ANIM_OVER);
            return;
        }

        if (mSumTime >= FRAME_1_TIME) {
            int col = mEraserCol;
            Tile tileRemove = new Tile(mPubGame, mPubBoard, "tile_remove2");
            for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                if (mPubBoard[col][row] != null) {
                    mPubBoard[col][row] = tileRemove;
                }
            }
            return;
        }

        if (mSumTime >= 0) {
            int col = mEraserCol;
            Tile tileRemove = new Tile(mPubGame, mPubBoard, "tile_remove1");
            for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                if (mPubBoard[col][row] != null) {
                    mPubBoard[col][row] = tileRemove;
                }
            }
            return;
        }
    }

    @Override
    public void react(eMsg msg) {

    }
}
