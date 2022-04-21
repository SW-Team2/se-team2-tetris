package graphics.screens;

import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import data.setting.SettingData;
import graphics.eScreenInfo;

public abstract class Screen extends JPanel {
    private static final int FIXED_SCREEN_LEN = 1920;
    private static final int FIXED_BTN_GAP = 10;
    protected int mNumBtns;
    protected int mBtnHeight;
    protected int mBtnWidth;

    public static final int LEFT_BUTTON_X = 50;
    public static final int LEFT_BUTTON_Y = 50;

    public static final int LEFT_BUTTON_WIDTH = 80;
    public static final int LEFT_BUTTON_HEIGHT = 80;

    public void setNumButtons(int n) {
        assert (n > 0);
        mNumBtns = n;
    }

    public void setButtonWidthAndHeight(int w, int h) {
        assert (w > 0);
        assert (w <= FIXED_SCREEN_LEN);
        assert (h > 0);
        assert (h <= FIXED_SCREEN_LEN);
        mBtnHeight = h;
        mBtnWidth = w;
    }

    public int getButtonX(int index) {
        assert (0 <= index && index < mNumBtns);
        int x = FIXED_SCREEN_LEN / 2 - mBtnWidth / 2;
        double ratioX = SettingData.getInstance().getWidth() / (double) FIXED_SCREEN_LEN;
        x = (int) (x * ratioX);
        return x;
    }

    public int getButtonY(int index) {
        assert (0 <= index && index < mNumBtns);
        int y = FIXED_SCREEN_LEN / 2;
        if (mNumBtns % 2 == 0) { // 짝수
            if (index < mNumBtns / 2) { // 중간보다 위
                y += FIXED_BTN_GAP / 2;
            } else { // 중간보다 아래
                y -= FIXED_BTN_GAP / 2;
            }
            int count = index - mNumBtns / 2;
            y += count * (mBtnHeight + FIXED_BTN_GAP);
        } else { // 홀수
            y -= mBtnHeight / 2;
            int count = index - mNumBtns / 2;
            y += count * (mBtnHeight + FIXED_BTN_GAP);
        }
        double ratioY = SettingData.getInstance().getHeight() / (double) FIXED_SCREEN_LEN;
        y = (int) (y * ratioY);
        return y;
    }

    public int getButtonWidth() {
        return (int) (mBtnWidth * SettingData.getInstance().getWidth() / (double) FIXED_SCREEN_LEN);
    }

    public int getButtonHeight() {
        return (int) (mBtnHeight * SettingData.getInstance().getHeight() / (double) FIXED_SCREEN_LEN);
    }

    public abstract eScreenInfo getUserInput(KeyEvent e);
}