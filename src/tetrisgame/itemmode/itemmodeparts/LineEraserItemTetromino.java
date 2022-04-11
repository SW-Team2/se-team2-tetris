package tetrisgame.itemmode.itemmodeparts;

import java.awt.event.KeyEvent;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eDirection;
import tetrisgame.enumerations.eMsg;
import tetrisgame.parts.component.Tile;

public class LineEraserItemTetromino extends ItemTetromino {
    private static final String ERASER_TEX_NAME = "item_tile_lineeraser";

    public LineEraserItemTetromino(TetrisGame game, Tile gb[][]) {
        super(game, gb);
        int eraserLocCount = mRandom.nextInt(4);
        int locIndex = 0;
        for (int col = 0; col < SHAPE_COL; col++) {
            for (int row = 0; row < SHAPE_ROW; row++) {
                if (mShape[col][row] != null) {
                    if (locIndex == eraserLocCount) {
                        mShape[col][row] = new Tile(mPubGame, mPubBoard, ERASER_TEX_NAME);
                        return;
                    } else {
                        locIndex++;
                    }
                }
            }
        }
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

        if (mCurrRemoveLineCount >= 10) {
            mAutoDownTime *= (1.0f - GAME_SPEED_ACCEL_UNIT);
            mCurrRemoveLineCount -= 10;
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
