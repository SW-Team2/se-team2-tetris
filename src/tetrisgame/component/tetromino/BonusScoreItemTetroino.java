package tetrisgame.component.tetromino;

import java.awt.event.KeyEvent;

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
        boolean bCollWithFloor = false;
        switch (userInput) {
            case KeyEvent.VK_SPACE:
                rotateOrIgnore();
                break;
            case KeyEvent.VK_DOWN:
                bCollWithFloor = moveOrIgnore(eDirection.DOWN);
                mAutoDownTimeTick = 0.f;
                break;
            case KeyEvent.VK_RIGHT:
                bCollWithFloor = moveOrIgnore(eDirection.RIGHT);
                break;
            case KeyEvent.VK_LEFT:
                bCollWithFloor = moveOrIgnore(eDirection.LEFT);
                break;
            case KeyEvent.VK_UP:
                fallToFloor();
                bCollWithFloor = true;
                break;
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
