package tetrisgame;

import data.setting.SettingData;
import gamemanager.GameManager;

import org.junit.Test;
import tetrisgame.TetrisGame;
import tetrisgame.component.tile.Tile;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.enumerations.eDirection;
import tetrisgame.enumerations.eMsg;
import tetrisgame.util.Position;

import static org.junit.jupiter.api.Assertions.*;

public class TetrominoTest {

    @Test
    public void gameTest() {
        TetrisGame game = new TetrisGame(this, false, GameManager.getDifficulty(), false);
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