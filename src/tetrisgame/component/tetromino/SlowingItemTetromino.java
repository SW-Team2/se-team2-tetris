package tetrisgame.component.tetromino;

import tetrisgame.TetrisGame;
import tetrisgame.component.tile.SlowingItemTile;
import tetrisgame.component.tile.Tile;
import tetrisgame.enumerations.eDirection;
import tetrisgame.enumerations.eMsg;

public class SlowingItemTetromino extends ItemTetromino {
    private static final String SLOWING_TEX_NAME = "item_tile_slowing";

    public SlowingItemTetromino(TetrisGame game, Tile gb[][], boolean bPlayer2) {
        super(game, gb, bPlayer2);
        super.genTetWithOneItemTile(new SlowingItemTile(game, gb, SLOWING_TEX_NAME));
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
        }
    }

    @Override
    public void react(eMsg msg) {
    }
}
