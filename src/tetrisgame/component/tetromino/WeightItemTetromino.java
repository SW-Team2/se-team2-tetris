package tetrisgame.component.tetromino;

import java.awt.event.KeyEvent;

import tetrisgame.TetrisGame;
import tetrisgame.component.tile.Tile;
import tetrisgame.enumerations.eDirection;
import tetrisgame.enumerations.eMsg;

public class WeightItemTetromino extends ItemTetromino {
    private static final boolean WEIGHT_TET_SHAPE[][] = {
            { F, F, F, F },
            { F, O, O, F },
            { O, O, O, O },
            { F, F, F, F }
    };

    private static final String WEIGHT_TEX_NAME = "item_tile_weight";

    public WeightItemTetromino(TetrisGame game, Tile gb[][]) {
        super(game, gb);
    }

    @Override
    public void update(float deltaTime, int userInput) {
        boolean bCollWithFloor = false;
        switch (userInput) {
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
            // Set empty
            for (int col = 0; col < SHAPE_COL; col++) {
                for (int row = 0; row < SHAPE_ROW; row++) {
                    mShape[col][row] = null;
                }
            }
            mPubGame.broadcast(eMsg.WEIGHT_ITEM_COLL_WITH_FLOOR);
        }
    }

    @Override
    public void react(eMsg msg) {
        if (msg == eMsg.COLL_WITH_FLOOR) {
            super.react(msg);
        }
    }

    @Override
    protected void setRandomShapeAndColor() {
        for (int col = 0; col < SHAPE_COL; col++) {
            for (int row = 0; row < SHAPE_ROW; row++) {
                if (WEIGHT_TET_SHAPE[col][row]) {
                    mShape[col][row] = new Tile(mPubGame, mPubBoard, WEIGHT_TEX_NAME);
                } else {
                    mShape[col][row] = null;
                }
            }
        }
    }
}