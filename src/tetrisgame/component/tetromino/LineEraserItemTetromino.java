package tetrisgame.component.tetromino;

import tetrisgame.TetrisGame;
import tetrisgame.component.tile.LineEraserItemTile;
import tetrisgame.component.tile.Tile;
import tetrisgame.enumerations.eDirection;
import tetrisgame.enumerations.eMsg;

public class LineEraserItemTetromino extends ItemTetromino {
    private static final String ERASER_TEX_NAME = "item_tile_lineeraser";

    public LineEraserItemTetromino(TetrisGame game, Tile gb[][], boolean bPlayer2) {
        super(game, gb, bPlayer2);
        super.genTetWithOneItemTile(new LineEraserItemTile(game, gb, ERASER_TEX_NAME));
    }

    @Override
    public void update(float deltaTime, int userInput) {
        refreshSetting();
        boolean bCollWithFloor = false;
        if (userInput == mRotateKey) {
            rotateOrIgnore();
        } else if (userInput == mMoveDownKey) {
            bCollWithFloor = moveOrIgnore(eDirection.DOWN);
            mAutoDownTimeTick = 0.f;
        } else if (userInput == mMoveRightKey) {
            bCollWithFloor = moveOrIgnore(eDirection.RIGHT);
            mAutoDownTimeTick = 0.f;
        } else if (userInput == mMoveLeftKey) {
            bCollWithFloor = moveOrIgnore(eDirection.LEFT);
            mAutoDownTimeTick = 0.f;
        } else if (userInput == mHardDownKey) {
            fallToFloor();
            mAutoDownTimeTick = 100.f;
        }

        mAutoDownTimeTick += deltaTime;
        if (mAutoDownTimeTick >= mAutoDownTime) {
            mAutoDownTimeTick = 0.f;
            bCollWithFloor = moveOrIgnore(eDirection.DOWN);
        }

        if (bCollWithFloor) {
            mPubGame.broadcast(eMsg.LINEERASER_ITEM_COLL_WITH_FLOOR);
            // Set empty
            for (int col = 0; col < SHAPE_COL; col++) {
                for (int row = 0; row < SHAPE_ROW; row++) {
                    mShape[col][row] = null;
                }
            }
        }
    }

    @Override
    public void react(eMsg msg) {
    }
}
