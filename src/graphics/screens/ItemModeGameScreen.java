package graphics.screens;

import gamemanager.GameManager;
import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eDifficulty;

public class ItemModeGameScreen extends GameScreen {

    @Override
    protected void startGame() {
        mTetrisGame = new TetrisGame(this, true, eDifficulty.NORMAL, false);
        mGameBoard = mTetrisGame.getGameBoard();
        GameManager.setGame(mTetrisGame);
        GameManager.setStart();
    }
}