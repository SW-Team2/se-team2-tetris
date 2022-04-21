package graphics.screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import data.setting.SettingData;
import graphics.eScreenInfo;
import tetrisgame.util.ImageLoader;

public class ColorBlindModeScreen extends Screen {
    private BufferedImage mCurrButtonImage[];
    private BufferedImage mButtonImages[];
    private BufferedImage mFocusButtonImages[];
    private int mButtonIndex;
    private BufferedImage mBackButtonImage;
    private Image mBackgroundImage;

    private static final int NUM_BUTTONS = 3;

    public ColorBlindModeScreen() {
        // TODO:
        ImageLoader img = ImageLoader.getInstance();
        mButtonImages = new BufferedImage[NUM_BUTTONS];
        mButtonImages[0] = img.getTexture("btn_normal");
        mButtonImages[1] = img.getTexture("btn_red_green");
        mButtonImages[2] = img.getTexture("btn_blue_yellow");

        mFocusButtonImages = new BufferedImage[NUM_BUTTONS];
        mFocusButtonImages[0] = img.getTexture("btn_normal_focus");
        mFocusButtonImages[1] = img.getTexture("btn_red_green_focus");
        mFocusButtonImages[2] = img.getTexture("btn_blue_yellow_focus");

        mCurrButtonImage = new BufferedImage[NUM_BUTTONS];
        mCurrButtonImage[0] = mFocusButtonImages[0];
        mCurrButtonImage[1] = mButtonImages[1];
        mCurrButtonImage[2] = mButtonImages[2];

        super.setNumButtons(NUM_BUTTONS);
        super.setButtonWidthAndHeight(1249, 193);

        mBackButtonImage = img.getTexture("btn_back");

        mBackgroundImage = img.getTexture("tetris_background");
        mButtonIndex = 0;
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Dimension d = getSize();
        g.drawImage(mBackgroundImage, 0, 0, d.width, d.height, null);

        for (int i = 0; i < NUM_BUTTONS; i++) {
            BufferedImage image = mCurrButtonImage[i];
            int x = super.getButtonX(i);
            int y = super.getButtonY(i);
            int w = super.getButtonWidth();
            int h = super.getButtonHeight();
            g.drawImage(image, x, y, w, h, null);
        }

        g.drawImage(mBackButtonImage,
                Screen.getEscPosX(),
                Screen.getEscPosY(),
                Screen.getEscButtonWidth(),
                Screen.getEscButtonHeight(), null);
    }

    @Override
    public eScreenInfo getUserInput(KeyEvent e) {
        eScreenInfo sr = eScreenInfo.NONE;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                mCurrButtonImage[mButtonIndex] = mButtonImages[mButtonIndex];
                mButtonIndex = mButtonIndex == NUM_BUTTONS - 1 ? 0 : mButtonIndex + 1;
                mCurrButtonImage[mButtonIndex] = mFocusButtonImages[mButtonIndex];
                this.repaint();
                break;
            case KeyEvent.VK_UP:
                mCurrButtonImage[mButtonIndex] = mButtonImages[mButtonIndex];
                mButtonIndex = mButtonIndex == 0 ? NUM_BUTTONS - 1 : mButtonIndex - 1;
                mCurrButtonImage[mButtonIndex] = mFocusButtonImages[mButtonIndex];
                this.repaint();
                break;
            case KeyEvent.VK_ENTER:
                switch (mButtonIndex) {
                    case 0:
                        SettingData.getInstance().setBlindnessMode(0);
                        break;
                    case 1:
                        SettingData.getInstance().setBlindnessMode(1);
                        break;
                    case 2:
                        SettingData.getInstance().setBlindnessMode(2);
                        break;
                    default:
                        assert (false) : "Button index out of bounds";
                }
                // intentinal fall through
            case KeyEvent.VK_ESCAPE:
                sr = eScreenInfo.SETTING;
                break;
            default:
                break;
        }
        return sr;
    }
}
