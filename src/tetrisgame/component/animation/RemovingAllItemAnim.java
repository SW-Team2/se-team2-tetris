package tetrisgame.component.animation;

import tetrisgame.TetrisGame;
import tetrisgame.component.tile.Tile;
import tetrisgame.enumerations.eMsg;

public class RemovingAllItemAnim extends IAnim {
    private Tile mPubBoard[][];

    public RemovingAllItemAnim(TetrisGame g, Tile gb[][]) {
        super(g);
        mPubBoard = gb;
    }

    public void update(float deltaTime, int userInput) {
        for (int c = 0; c < TetrisGame.BOARD_COL; c++) {
            for (int r = 0; r < TetrisGame.BOARD_ROW; r++) {
                if (mPubBoard[c][r] != null) {
                    mPubBoard[c][r].eraseAct();
                    mPubBoard[c][r] = null;
                }
            }
        }
        mPubGame.broadcast(eMsg.FOCUS_ANIM_OVER);
    }

    public void react(eMsg msg) {

    }
}
