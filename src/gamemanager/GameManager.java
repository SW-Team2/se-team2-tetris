package gamemanager;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eDifficulty;

public class GameManager {
    private static volatile boolean mbStartFlag;
    private static volatile TetrisGame mGame1;
    private static volatile TetrisGame mGame2;
    private static volatile eDifficulty meDiff;
    private static volatile boolean mbMulti;
    private static volatile boolean mbTimeLimitMode;
    private static volatile int mTimeLimitModeCurrTime;

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
        mGame1 = g;
    }

    public static void setGame(TetrisGame g1, TetrisGame g2) {
        assert (g1 != null);
        assert (g2 != null);
        mGame1 = g1;
        mGame2 = g2;
    }

    public static void setDifficulty(eDifficulty diff) {
        meDiff = diff;
    }

    public static void setMulti(boolean b) {
        mbMulti = b;
    }

    public static TetrisGame getGame() {
        assert (mGame1 != null);
        return mGame1;
    }

    public static TetrisGame getGame2() {
        assert (mGame2 != null);
        return mGame2;
    }

    public static eDifficulty getDifficulty() {
        return meDiff;
    }

    public static boolean isMulti() {
        return mbMulti;
    }

    public static void setTimeLimitMode(boolean b) {
        mbTimeLimitMode = b;
    }

    public static boolean isTimeLimitMode() {
        return mbTimeLimitMode;
    }

    public static void setCurrTime(int t) {
        mTimeLimitModeCurrTime = t;
    }

    public static int getCurrTime() {
        return mTimeLimitModeCurrTime;
    }

    public static void waitForSignal(boolean startFlag) {
        while (mbStartFlag == startFlag) {
        }
    }
}
