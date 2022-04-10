package tetrisgame;

import graphics.screens.GameScreen;
import tetrisgame.itemmode.ItemTetromino;

public class ItemModeTetrisGame extends TetrisGame {
    private int mItemCount;

    public ItemModeTetrisGame(GameScreen gameScreen) {
        super(gameScreen);
        mItemCount = 0;
    }

    @Override
    protected void getNextTetromino() {
        if (mItemCount < m1000ScoreCount) {
            mNextTetromino = new ItemTetromino();
            mItemCount = super.m1000ScoreCount;
        } else {
            super.getNextTetromino();
        }
    }
}
