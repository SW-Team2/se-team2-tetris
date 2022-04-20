package gamestarter;

import tetrisgame.TetrisGame;

public class GameStarter {
    private static volatile boolean mbStartFlag = false;
    private static TetrisGame mGame = null;

    public static void setStart() {
        mbStartFlag = true;
    }

    public static void setOver() {
        mbStartFlag = false;
    }

    public static boolean getState() {
        return mbStartFlag;
    }

    public static void setGame(TetrisGame g) {
        assert (g != null);
        mGame = g;
    }

    public static TetrisGame getGame() {
        assert (mGame != null);
        return mGame;
    }

    public static void waitForSignal(boolean startFlag) {
        // TODO: Busy wait
        while (mbStartFlag == startFlag) {
        }
    }
}
