import gamestarter.GameStarter;
import graphics.WindowManager;
import tetrisgame.TetrisGame;
import tetrisgame.util.ImageLoader;

public class TestMain {
    public static void main(String args[]) {
        final boolean GAME_START = false;
        final boolean GAME_END = true;

        ImageLoader.Load();

        WindowManager win = new WindowManager();

        win.showMain();

      /*  while (true) {
            GameStarter.waitForSignal(GAME_START);

            TetrisGame game = GameStarter.getGame();
            Thread gameThread = new Thread(game);
            gameThread.start();

            GameStarter.waitForSignal(GAME_END);
            win.showMain();
        }*/
    }
}