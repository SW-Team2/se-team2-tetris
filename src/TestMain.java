import gamestarter.GameStarter;
import graphics.WindowManager;
import tetrisgame.TetrisGame;

public class TestMain {
    public static void main(String args[]) {
        WindowManager win = new WindowManager();

        while (true) {
            GameStarter.waitForSignal(false);
            TetrisGame game = GameStarter.getGame();
            game.initialize();
            Thread gameThread = new Thread(game);
            gameThread.start();

            GameStarter.waitForSignal(true);
            win.showMain();
        }
    }
}
