import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gamestarter.GameStarter;
import graphics.WindowManager;
import tetrisgame.TetrisGame;
import tetrisgame.util.ImageLoader;

public class TestMain {
    public static void main(String args[]) {
        final boolean GAME_START = false;
        final boolean GAME_END = true;
        try {
            ImageLoader.Load();

            WindowManager win = new WindowManager();

            while (true) {
                GameStarter.waitForSignal(GAME_START);

                TetrisGame game = GameStarter.getGame();
                Thread gameThread = new Thread(game);
                gameThread.start();

                GameStarter.waitForSignal(GAME_END);
                win.showMain();
            }
        } catch (RuntimeException re) {
            JOptionPane.showMessageDialog(new JFrame(), re.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // TODO: Save all data and log
        }
    }
}