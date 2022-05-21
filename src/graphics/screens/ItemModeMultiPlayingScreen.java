package graphics.screens;

import gamemanager.GameManager;
import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eDifficulty;

public class ItemModeMultiPlayingScreen extends MultiPlayingScreen {

    @Override
    protected void startGame() {
        GameManager.setDifficulty(eDifficulty.NORMAL);
        GameManager.setMulti(true);
        mGame1 = new TetrisGame(this, true, GameManager.getDifficulty(), false);
        mGame2 = new TetrisGame(this, true, GameManager.getDifficulty(), true);
        mBoard1 = mGame1.getGameBoard();
        mBoard2 = mGame2.getGameBoard();
        mbAttackBoard1 = mGame1.getAttackBoard();
        mbAttackBoard2 = mGame2.getAttackBoard();
        GameManager.setGame(mGame1, mGame2);
        GameManager.setStart();
    }
}