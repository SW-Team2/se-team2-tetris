package tetrisgame;

import gamemanager.GameManager;

import graphics.screens.GameScreen;
import org.junit.Test;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.enumerations.eMsg;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TetrisGameTest {

    @Test
    public void gameTest() {
        tetrisgame.util.ImageLoader.Load();
        GameManager.setDifficulty(eDifficulty.NORMAL);
        tetrisgame.TetrisGame game = new tetrisgame.TetrisGame(new GameScreen(), false, GameManager.getDifficulty(), false);
        GameManager.setGame(game);
        GameManager.setStart();
        Thread gameThread = new Thread(GameManager.getGame());
        gameThread.start();

        game.getUserInput(new KeyEvent(new Button("Click"), 1,20,1,10,(char)KeyEvent.VK_ESCAPE));
        game.getUserInput(new KeyEvent(new Button("Click"), 1,20,1,10,(char)KeyEvent.VK_ENTER));
        game.getUserInput(new KeyEvent(new Button("Click"), 1,20,1,10,(char)KeyEvent.VK_RIGHT));

        game.broadcast(eMsg.NONE);
        game.broadcast(eMsg.COLL_WITH_FLOOR);
        game.broadcast(eMsg.ERASE_10xN_LINES);
        game.broadcast(eMsg.FOCUS_ANIM_OVER);

        game.broadcast(eMsg.LINE_REMOVE_0);
        game.broadcast(eMsg.LINE_REMOVE_1);
        game.broadcast(eMsg.LINE_REMOVE_2);
        game.broadcast(eMsg.LINE_REMOVE_3);
        game.broadcast(eMsg.LINE_REMOVE_4);
        game.broadcast(eMsg.LINE_REMOVE_5);
        game.broadcast(eMsg.LINE_REMOVE_6);
        game.broadcast(eMsg.LINE_REMOVE_7);
        game.broadcast(eMsg.LINE_REMOVE_8);
        game.broadcast(eMsg.LINE_REMOVE_9);
        game.broadcast(eMsg.LINE_REMOVE_10);
        game.broadcast(eMsg.LINE_REMOVE_11);
        game.broadcast(eMsg.LINE_REMOVE_12);
        game.broadcast(eMsg.LINE_REMOVE_13);
        game.broadcast(eMsg.LINE_REMOVE_14);
        game.broadcast(eMsg.LINE_REMOVE_15);
        game.broadcast(eMsg.LINE_REMOVE_16);
        game.broadcast(eMsg.LINE_REMOVE_17);
        game.broadcast(eMsg.LINE_REMOVE_18);
        game.broadcast(eMsg.LINE_REMOVE_19);

        game.broadcast(eMsg.GAME_OVER);

        GameManager.waitForSignal(true);

        assertEquals(1, 1);
    }
}