import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gamemanager.GameManager;
import graphics.WindowManager;
import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;
import tetrisgame.util.ImageLoader;
import tetrisgame.util.Timer;

public class TestMain {
    public static void main(String args[]) {
        final boolean GAME_START = false;
        final boolean GAME_END = true;
        try {
            ImageLoader.Load();

            WindowManager win = new WindowManager();

            while (true) {
                GameManager.waitForSignal(GAME_START);

                if (!GameManager.isMulti()) {
                    TetrisGame game = GameManager.getGame();
                    Thread gameThread = new Thread(game);
                    gameThread.start();
                } else if (GameManager.isMulti() && !GameManager.isTimeLimitMode()) {
                    TetrisGame game1 = GameManager.getGame();
                    TetrisGame game2 = GameManager.getGame2();
                    game1.setMultGame(game2);
                    game2.setMultGame(game1);
                    Thread thread1 = new Thread(game1);
                    Thread thread2 = new Thread(game2);

                    thread1.start();
                    thread2.start();
                } else if (GameManager.isMulti() && GameManager.isTimeLimitMode()) {
                    TetrisGame game1 = GameManager.getGame();
                    TetrisGame game2 = GameManager.getGame2();
                    game1.setMultGame(game2);
                    game2.setMultGame(game1);
                    Thread thread1 = new Thread(game1);
                    Thread thread2 = new Thread(game2);

                    thread1.start();
                    thread2.start();

                    Timer timer = new Timer();
                    timer.initialize();
                    int timeLimit = 100;
                    float currTime = 0.f;
                    while (timeLimit >= currTime && GameManager.getState()) {
                        timer.tick();
                        currTime += timer.getDeltaTime();
                        GameManager.setCurrTime(timeLimit - (int) currTime);
                    }

                    GameManager.getGame2().broadcast(eMsg.TIMELIMITEND);
                    GameManager.setTimeLimitMode(false);
                }

                GameManager.waitForSignal(GAME_END);
                if(!GameManager.isMulti() && GameManager.mbPauseExit == false && GameManager.getNewRank() != -1){
                    try{
                    Thread.sleep(50L);
                    } catch (Exception e){
                        
                    }
                    win.showScore();
                } else {
                    GameManager.setMulti(false);
                    GameManager.mbPauseExit = false;
                    win.showMain();
                }
            }
        } catch (RuntimeException re) {
            JOptionPane.showMessageDialog(new JFrame(), re.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
        }
    }
}