package graphics.screens;

import java.awt.Font;

import gamestarter.GameStarter;
import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.util.ImageLoader;

public class ItemModeGameScreen extends GameScreen {
    @Override
    protected void init() {
        mPanelBackGroundImage = ImageLoader.getInstance().getTexture("background_panel");
        mGameBoardBackGroundImage = ImageLoader.getInstance().getTexture("background_board");
        mNextTetBoardBackGroundImage = ImageLoader.getInstance().getTexture("background_nextboard");

        mScoreBoardFont = new Font("Consolas", Font.BOLD, 30);
    }

    @Override
    protected void startGame() {
        mTetrisGame = new TetrisGame(this, true, eDifficulty.NORMAL);
        mGameBoard = mTetrisGame.getGameBoard();
        GameStarter.setGame(mTetrisGame);
        GameStarter.setStart();
    }
}