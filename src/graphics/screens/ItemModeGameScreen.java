package graphics.screens;

import java.awt.Font;

import tetrisgame.ItemModeTetrisGame;
import tetrisgame.parts.ImageLoader;

public class ItemModeGameScreen extends GameScreen {
    @Override
    protected void init() {
        mTetrisGame = new ItemModeTetrisGame(this);
        mGameBoard = mTetrisGame.getGameBoard();

        mPanelBackGroundImage = ImageLoader.getInstance().getTexture("background_panel");
        mGameBoardBackGroundImage = ImageLoader.getInstance().getTexture("background_board");
        mNextTetBoardBackGroundImage = ImageLoader.getInstance().getTexture("background_nextboard");

        mScoreBoardFont = new Font("Consolas", Font.BOLD, 30);
    }
}
