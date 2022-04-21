package tetrisgame.component.tetromino;

import tetrisgame.TetrisGame;
import tetrisgame.component.tile.BonusScoreItemTile;
import tetrisgame.component.tile.Tile;
import tetrisgame.enumerations.eDirection;
import tetrisgame.enumerations.eMsg;

public class BonusScoreItemTetroino extends ItemTetromino {
    public BonusScoreItemTetroino(TetrisGame game, Tile gb[][]) {
        super(game, gb);
        super.genTetWithOneItemTile(new BonusScoreItemTile(game, gb, "tile_item_bonusscore"));
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
            mPubGame.broadcast(eMsg.COLL_WITH_FLOOR);
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
