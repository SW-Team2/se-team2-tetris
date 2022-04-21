package graphics.screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import graphics.eScreenInfo;
import tetrisgame.util.ImageLoader;

public class MainMenuScreen extends Screen {
    private BufferedImage mCurrButtonImage[];
    private BufferedImage mButtonImages[];
    private BufferedImage mFocusButtonImages[];
    private int mButtonIndex;
    private Image mBackgroundImage;

    private static final int NUM_BUTTONS = 5;

    public MainMenuScreen() {
        ImageLoader img = ImageLoader.getInstance();
        mButtonImages = new BufferedImage[NUM_BUTTONS];
        mButtonImages[0] = img.getTexture("btn_start");
        mButtonImages[1] = img.getTexture("btn_item_mode");
        mButtonImages[2] = img.getTexture("btn_setting");
        mButtonImages[3] = img.getTexture("btn_score_board");
        mButtonImages[4] = img.getTexture("btn_exit");

        mFocusButtonImages = new BufferedImage[NUM_BUTTONS];
        mFocusButtonImages[0] = img.getTexture("btn_start_focus");
        mFocusButtonImages[1] = img.getTexture("btn_item_mode_focus");
        mFocusButtonImages[2] = img.getTexture("btn_setting_focus");
        mFocusButtonImages[3] = img.getTexture("btn_score_board_focus");
        mFocusButtonImages[4] = img.getTexture("btn_exit_focus");

        mCurrButtonImage = new BufferedImage[NUM_BUTTONS];
        mCurrButtonImage[0] = mFocusButtonImages[0];
        mCurrButtonImage[1] = mButtonImages[1];
        mCurrButtonImage[2] = mButtonImages[2];
        mCurrButtonImage[3] = mButtonImages[3];
        mCurrButtonImage[4] = mButtonImages[4];

        super.setNumButtons(NUM_BUTTONS);
        super.setButtonWidthAndHeight(470, 103);

        mBackgroundImage = img.getTexture("main_background");
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
                switch (mButtonIndex) {
                    case 0:
                        sr = eScreenInfo.DIFFICULTY;
                        break;
                    case 1:
                        sr = eScreenInfo.ITEMGAME;
                        break;
                    case 2:
                        sr = eScreenInfo.SETTING;
                        break;
                    case 3:
                        sr = eScreenInfo.SCOREBOARD;
                        break;
                    case 4:
                        sr = eScreenInfo.EXIT;
                        break;
                    default:
                        assert (false) : "Undefined eScreenInfo";
                }
                break;
            default:
                break;
        }
        return sr;
    }
}
