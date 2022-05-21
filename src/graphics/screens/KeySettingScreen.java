package graphics.screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import data.setting.SettingData;
import graphics.eScreenInfo;
import tetrisgame.util.ImageLoader;

public class KeySettingScreen extends IScreen {
    private BufferedImage mCurrButtonImage[];
    private BufferedImage mButtonImages[];
    private BufferedImage mFocusButtonImages[];
    private BufferedImage mCurrKeyButtonImage[];

    private BufferedImage mEmptyImage;
    private BufferedImage mFocusEmptyImage;
    private boolean mbNowKeySetting;

    private String mCurrKeyName[];

    private int mButtonIndex;
    private BufferedImage mBackButtonImage;
    private Image mBackgroundImage;

    private static final int NUM_BUTTONS = 5;

    public KeySettingScreen() {
        ImageLoader img = ImageLoader.getInstance();
        mButtonImages = new BufferedImage[NUM_BUTTONS];
        mButtonImages[0] = img.getTexture("btn_left");
        mButtonImages[1] = img.getTexture("btn_down");
        mButtonImages[2] = img.getTexture("btn_right");
        mButtonImages[3] = img.getTexture("btn_hard_drop");
        mButtonImages[4] = img.getTexture("btn_rotate");

        mFocusButtonImages = new BufferedImage[NUM_BUTTONS];
        mFocusButtonImages[0] = img.getTexture("btn_left_focus");
        mFocusButtonImages[1] = img.getTexture("btn_down_focus");
        mFocusButtonImages[2] = img.getTexture("btn_right_focus");
        mFocusButtonImages[3] = img.getTexture("btn_hard_drop_focus");
        mFocusButtonImages[4] = img.getTexture("btn_rotate_focus");

        mCurrButtonImage = new BufferedImage[NUM_BUTTONS];
        mCurrButtonImage[0] = mFocusButtonImages[0];
        mCurrButtonImage[1] = mButtonImages[1];
        mCurrButtonImage[2] = mButtonImages[2];
        mCurrButtonImage[3] = mButtonImages[3];
        mCurrButtonImage[4] = mButtonImages[4];

        mEmptyImage = img.getTexture("current_key");
        mFocusEmptyImage = img.getTexture("current_key_focus");

        mCurrKeyButtonImage = new BufferedImage[NUM_BUTTONS];
        mCurrKeyButtonImage[0] = mEmptyImage;
        mCurrKeyButtonImage[1] = mEmptyImage;
        mCurrKeyButtonImage[2] = mEmptyImage;
        mCurrKeyButtonImage[3] = mEmptyImage;
        mCurrKeyButtonImage[4] = mEmptyImage;

        mCurrKeyName = new String[NUM_BUTTONS];
        SettingData setting = SettingData.getInstance();
        mCurrKeyName[0] = this.keyCodeToString(setting.getGameMoveLeft());
        mCurrKeyName[1] = this.keyCodeToString(setting.getGameMoveDown());
        mCurrKeyName[2] = this.keyCodeToString(setting.getGameMoveRight());
        mCurrKeyName[3] = this.keyCodeToString(setting.getGameMoveToFloor());
        mCurrKeyName[4] = this.keyCodeToString(setting.getRotateKey());

        super.setNumButtons(NUM_BUTTONS);
        super.setButtonWidthAndHeight(854, 193);

        mBackButtonImage = img.getTexture("btn_back");

        mBackgroundImage = img.getTexture("tetris_background");
        mButtonIndex = 0;
        mbNowKeySetting = false;
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
        refreshSetting();
        Dimension d = getSize();
        g.drawImage(mBackgroundImage, 0, 0, d.width, d.height, null);

        for (int i = 0; i < NUM_BUTTONS; i++) {
            BufferedImage image = mCurrButtonImage[i];
            int x = super.getButtonX(i) - super.getEmptyButtonWidth() / 2;
            int y = super.getButtonY(i);
            int w = super.getButtonWidth();
            int h = super.getButtonHeight();
            g.drawImage(image, x, y, w, h, null);
        }

        for (int i = 0; i < NUM_BUTTONS; i++) {
            BufferedImage image = mCurrKeyButtonImage[i];
            int x = super.getButtonX(i) + (int) (super.getButtonWidth() * 0.9);
            int y = super.getButtonY(i);
            int w = super.getEmptyButtonWidth();
            int h = super.getEmptyButtonHeight();
            g.drawImage(image, x, y, w, h, null);

            String keyStr = mCurrKeyName[i];
            int bold = (int) (100 * SettingData.getInstance().getHeight() / (double) 1920);
            g.setFont(new Font("S-Core_Dream_OTF", Font.BOLD, bold));
            g.setColor(Color.WHITE);
            x += super.getEmptyButtonWidth() * 0.07;
            y += super.getEmptyButtonHeight() * 0.8;
            g.drawString(keyStr, x, y);
        }

        g.drawImage(mBackButtonImage,
                IScreen.getEscPosX(),
                IScreen.getEscPosY(),
                IScreen.getEscButtonWidth(),
                IScreen.getEscButtonHeight(), null);
    }

    @Override
    public eScreenInfo getUserInput(KeyEvent e) {
        eScreenInfo sr = eScreenInfo.NONE;
        if (mbNowKeySetting == false) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    mCurrButtonImage[mButtonIndex] = mButtonImages[mButtonIndex];
                    mButtonIndex = mButtonIndex == NUM_BUTTONS - 1 ? 0 : mButtonIndex + 1;
                    mCurrButtonImage[mButtonIndex] = mFocusButtonImages[mButtonIndex];
                    break;
                case KeyEvent.VK_UP:
                    mCurrButtonImage[mButtonIndex] = mButtonImages[mButtonIndex];
                    mButtonIndex = mButtonIndex == 0 ? NUM_BUTTONS - 1 : mButtonIndex - 1;
                    mCurrButtonImage[mButtonIndex] = mFocusButtonImages[mButtonIndex];
                    break;
                case KeyEvent.VK_ENTER:
                    mCurrButtonImage[mButtonIndex] = mButtonImages[mButtonIndex];
                    mbNowKeySetting = true;
                    mCurrKeyButtonImage[mButtonIndex] = mFocusEmptyImage;
                    mCurrKeyName[mButtonIndex] = "";
                    break;
                case KeyEvent.VK_ESCAPE:
                    sr = eScreenInfo.SETTING;
                    break;
                default:
                    break;
            }
        } else {
            SettingData setting = SettingData.getInstance();
            int key = e.getKeyCode();
            switch (mButtonIndex) {
                case 0:
                    if (key == setting.getGameMoveDown() |
                            key == setting.getGameMoveRight() |
                            key == setting.getGameMoveToFloor() |
                            key == setting.getRotateKey()) {
                        return sr;
                    }
                    break;
                case 1:
                    if (key == setting.getGameMoveLeft() |
                            key == setting.getGameMoveRight() |
                            key == setting.getGameMoveToFloor() |
                            key == setting.getRotateKey()) {
                        return sr;
                    }
                    break;
                case 2:
                    if (key == setting.getGameMoveLeft() |
                            key == setting.getGameMoveDown() |
                            key == setting.getGameMoveToFloor() |
                            key == setting.getRotateKey()) {
                        return sr;
                    }
                    break;
                case 3:
                    if (key == setting.getGameMoveLeft() |
                            key == setting.getGameMoveDown() |
                            key == setting.getGameMoveRight() |
                            key == setting.getRotateKey()) {
                        return sr;
                    }
                    break;
                case 4:
                    if (key == setting.getGameMoveLeft() |
                            key == setting.getGameMoveDown() |
                            key == setting.getGameMoveRight() |
                            key == setting.getGameMoveToFloor()) {
                        return sr;
                    }
                    break;
                default:
                    assert (false) : "Buttion Index out of boudns";
                    break;
            }
            mCurrKeyName[mButtonIndex] = this.keyCodeToString(key);
            mCurrButtonImage[mButtonIndex] = mFocusButtonImages[mButtonIndex];
            mCurrKeyButtonImage[mButtonIndex] = mEmptyImage;
            mbNowKeySetting = false;
            switch (mButtonIndex) {
                case 0:
                    setting.storeGameMoveLeftKey(key);
                    break;
                case 1:
                    setting.storeGameMoveDownKey(key);
                    break;
                case 2:
                    setting.storeGameMoveRightKey(key);
                    break;
                case 3:
                    setting.storeGameMoveToFloorKey(key);
                    break;
                case 4:
                    setting.storeGameRotateKey(key);
                    break;
                default:
                    assert (false) : "Buttion Index out of boudns";
                    break;
            }
        }
        this.repaint();
        return sr;
    }

    private String keyCodeToString(int key) {
        String re = KeyEvent.getKeyText(key);
        return re;
    }

    private void refreshSetting() {
        SettingData setting = SettingData.getInstance();
        mCurrKeyName[0] = mCurrKeyName[0].equals("") ? "" : this.keyCodeToString(setting.getGameMoveLeft());
        mCurrKeyName[1] = mCurrKeyName[1].equals("") ? "" : this.keyCodeToString(setting.getGameMoveDown());
        mCurrKeyName[2] = mCurrKeyName[2].equals("") ? "" : this.keyCodeToString(setting.getGameMoveRight());
        mCurrKeyName[3] = mCurrKeyName[3].equals("") ? "" : this.keyCodeToString(setting.getGameMoveToFloor());
        mCurrKeyName[4] = mCurrKeyName[4].equals("") ? "" : this.keyCodeToString(setting.getRotateKey());
    }
}
