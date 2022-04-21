package graphics.screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import data.setting.SettingData;
import gamestarter.GameStarter;
import graphics.eScreenInfo;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.util.ImageLoader;

public class DifficultyScreen extends Screen {
    private BufferedImage mCurrButtonImage[];
    private BufferedImage mButtonImages[];
    private BufferedImage mFocusButtonImages[];
    private int mButtonIndex;
    private BufferedImage mBackButtonImage;
    private Image mBackgroundImage;

    private static final int NUM_BUTTONS = 3;

    public DifficultyScreen() {
        ImageLoader img = ImageLoader.getInstance();
        mButtonImages = new BufferedImage[NUM_BUTTONS];
        mButtonImages[0] = img.getTexture("btn_easy");
        mButtonImages[1] = img.getTexture("btn_normal");
        mButtonImages[2] = img.getTexture("btn_hard");

        mFocusButtonImages = new BufferedImage[NUM_BUTTONS];
        mFocusButtonImages[0] = img.getTexture("btn_easy_focus");
        mFocusButtonImages[1] = img.getTexture("btn_normal_focus");
        mFocusButtonImages[2] = img.getTexture("btn_hard_focus");

        mCurrButtonImage = new BufferedImage[NUM_BUTTONS];
        mCurrButtonImage[0] = mFocusButtonImages[0];
        mCurrButtonImage[1] = mButtonImages[1];
        mCurrButtonImage[2] = mButtonImages[2];

        super.setNumButtons(NUM_BUTTONS);
        super.setButtonWidthAndHeight(752, 165);

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
                Screen.LEFT_BUTTON_X,
                Screen.LEFT_BUTTON_Y,
                Screen.LEFT_BUTTON_WIDTH,
                Screen.LEFT_BUTTON_WIDTH, null);
    }

    @Override
    public eScreenInfo getUserInput(KeyEvent e) {
        eScreenInfo sr = eScreenInfo.NONE;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                mCurrButtonImage[mButtonIndex] = mButtonImages[mButtonIndex];
                mButtonIndex = mButtonIndex == NUM_BUTTONS - 1 ? mButtonIndex : mButtonIndex + 1;
                mCurrButtonImage[mButtonIndex] = mFocusButtonImages[mButtonIndex];
                this.repaint();
                break;
            case KeyEvent.VK_UP:
                mCurrButtonImage[mButtonIndex] = mButtonImages[mButtonIndex];
                mButtonIndex = mButtonIndex == 0 ? mButtonIndex : mButtonIndex - 1;
                mCurrButtonImage[mButtonIndex] = mFocusButtonImages[mButtonIndex];
                this.repaint();
                break;
            case KeyEvent.VK_ENTER:
                sr = eScreenInfo.GAME;
                switch (mButtonIndex) {
                    case 0:
                        GameStarter.setDifficulty(eDifficulty.EASY);
                        break;
                    case 1:
                        GameStarter.setDifficulty(eDifficulty.NORMAL);
                        break;
                    case 2:
                        GameStarter.setDifficulty(eDifficulty.HARD);
                        break;
                }
                break;
            case KeyEvent.VK_LEFT:
                sr = eScreenInfo.MAIN;
                break;
            default:
                break;
        }
        return sr;
    }
}
