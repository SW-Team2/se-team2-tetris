import gamestarter.GameStarter;
import graphics.WindowManager;
import tetrisgame.TetrisGame;

public class TestMain {
    public static void main(String args[]) {
        final boolean GAME_START = false;
        final boolean GAME_END = true;

        WindowManager win = new WindowManager();

        while (true) {
            GameStarter.waitForSignal(GAME_START);

            TetrisGame game = GameStarter.getGame();
            game.initialize();
            Thread gameThread = new Thread(game);
            gameThread.start();

            GameStarter.waitForSignal(GAME_END);
            win.showMain();
        }
    }
}
