package gamestarter;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eDifficulty;

public class GameStarter {
    private static volatile boolean mbStartFlag = false;
    private static TetrisGame mGame = null;
    private static eDifficulty meDiff;

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

    public static void setDifficulty(eDifficulty diff) {
        meDiff = diff;
    }

    public static TetrisGame getGame() {
        assert (mGame != null);
        return mGame;
    }

    public static eDifficulty getDifficulty() {
        return meDiff;
    }

    public static void waitForSignal(boolean startFlag) {
        while (mbStartFlag == startFlag) {
        }
    }
}
