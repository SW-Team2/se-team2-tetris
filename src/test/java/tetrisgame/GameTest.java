package tetrisgame;

import gamemanager.GameManager;

import graphics.screens.GameScreen;
import org.junit.Test;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.enumerations.eMsg;

public class GameTest {

    @Test
    public void gameTest() {
        TetrisGame game = new TetrisGame(new GameScreen(), false, GameManager.getDifficulty(), false);
        GameManager.setDifficulty(eDifficulty.NORMAL);
        GameManager.setGame(game);
        GameManager.setStart();
        Thread gameThread = new Thread(GameManager.getGame());
        gameThread.start();

        game.broadcast(eMsg.NONE);

        GameManager.waitForSignal(true);

        assert(true);
    }
}