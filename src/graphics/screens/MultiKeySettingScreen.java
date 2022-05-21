package graphics.screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import data.setting.Player;
import data.setting.SettingData;
import graphics.eScreenInfo;
import tetrisgame.util.ImageLoader;

public class MultiKeySettingScreen extends IScreen {
    private int mPlayer;

    protected BufferedImage mCurrButtonImage[];
    protected BufferedImage mButtonImages[];
    protected BufferedImage mFocusButtonImages[];
    protected BufferedImage mCurrKeyButtonImage[];

    protected BufferedImage mEmptyImage;
    protected BufferedImage mFocusEmptyImage;
    protected boolean mbNowKeySetting;

    protected String mCurrKeyName[];

    protected int mButtonIndex;
    private int mPIndex;
    protected BufferedImage mBackButtonImage;
    protected Image mBackgroundImage;

    private BufferedImage mP1ButtonImage;
    private BufferedImage mP2ButtonImage;
    private BufferedImage mP1FocusButtonImage;
    private BufferedImage mP2FocusButtonImage;
    private BufferedImage mP1SelectButtonImage;
    private BufferedImage mP2SelectButtonImage;

    protected static final int NUM_BUTTONS = 6;

    public MultiKeySettingScreen() {
        mPlayer = 1;
        ImageLoader img = ImageLoader.getInstance();
        mButtonImages = new BufferedImage[NUM_BUTTONS];
        mButtonImages[0] = null;
        mButtonImages[1] = img.getTexture("btn_left");
        mButtonImages[2] = img.getTexture("btn_down");
        mButtonImages[3] = img.getTexture("btn_right");
        mButtonImages[4] = img.getTexture("btn_hard_drop");
        mButtonImages[5] = img.getTexture("btn_rotate");

        mFocusButtonImages = new BufferedImage[NUM_BUTTONS];
        mFocusButtonImages[0] = null;
        mFocusButtonImages[1] = img.getTexture("btn_left_focus");
        mFocusButtonImages[2] = img.getTexture("btn_down_focus");
        mFocusButtonImages[3] = img.getTexture("btn_right_focus");
        mFocusButtonImages[4] = img.getTexture("btn_hard_drop_focus");
        mFocusButtonImages[5] = img.getTexture("btn_rotate_focus");

        mCurrButtonImage = new BufferedImage[NUM_BUTTONS];
        mCurrButtonImage[0] = null;
        mCurrButtonImage[1] = mButtonImages[1];
        mCurrButtonImage[2] = mButtonImages[2];
        mCurrButtonImage[3] = mButtonImages[3];
        mCurrButtonImage[4] = mButtonImages[4];
        mCurrButtonImage[5] = mButtonImages[5];

        mEmptyImage = img.getTexture("current_key");
        mFocusEmptyImage = img.getTexture("current_key_focus");

        mCurrKeyButtonImage = new BufferedImage[NUM_BUTTONS];
        mCurrKeyButtonImage[0] = null;
        mCurrKeyButtonImage[1] = mEmptyImage;
        mCurrKeyButtonImage[2] = mEmptyImage;
        mCurrKeyButtonImage[3] = mEmptyImage;
        mCurrKeyButtonImage[4] = mEmptyImage;
        mCurrKeyButtonImage[5] = mEmptyImage;

        mCurrKeyName = new String[NUM_BUTTONS];
        refreshPlayerKeys();

        mP1ButtonImage = img.getTexture("btn_1p");
        mP2ButtonImage = img.getTexture("btn_2p");
        mP1FocusButtonImage = img.getTexture("btn_1p_focus");
        mP2FocusButtonImage = img.getTexture("btn_2p_focus");
        mP1SelectButtonImage = img.getTexture("btn_1p_select");
        mP2SelectButtonImage = img.getTexture("btn_2p_select");

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
        refreshPlayerKeys();
        Dimension d = getSize();
        g.drawImage(mBackgroundImage, 0, 0, d.width, d.height, null);

        {
            BufferedImage imageP1 = null;
            BufferedImage imageP2 = null;
            if (mButtonIndex == 0) {
                if (mPIndex == 0) {
                    imageP1 = mP1FocusButtonImage;
                    imageP2 = mP2ButtonImage;
                } else if (mPIndex == 1) {
                    imageP1 = mP1ButtonImage;
                    imageP2 = mP2FocusButtonImage;
                }
            } else {
                if (mPIndex == 0) {
                    imageP1 = mP1SelectButtonImage;
                    imageP2 = mP2ButtonImage;
                } else if (mPIndex == 1) {
                    imageP1 = mP1ButtonImage;
                    imageP2 = mP2SelectButtonImage;
                }
            }

            int x = super.getButtonX(0) - super.getEmptyButtonWidth() / 2;
            int y = super.getButtonY(0);
            int w = (super.getButtonWidth() + super.getEmptyButtonWidth()) / 2;
            int h = super.getButtonHeight();
            g.drawImage(imageP1, x, y, w, h, null);

            x = super.getButtonX(0) + (int) (super.getButtonWidth() * 0.9) + super.getEmptyButtonWidth() - w;
            g.drawImage(imageP2, x, y, w, h, null);
        }

        for (int i = 1; i < NUM_BUTTONS; i++) {
            BufferedImage image = mCurrButtonImage[i];
            int x = super.getButtonX(i) - super.getEmptyButtonWidth() / 2;
            int y = super.getButtonY(i);
            int w = super.getButtonWidth();
            int h = super.getButtonHeight();
            g.drawImage(image, x, y, w, h, null);
        }

        for (int i = 1; i < NUM_BUTTONS; i++) {
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
                case KeyEvent.VK_LEFT:
                    // Intentinal fallthrough
                case KeyEvent.VK_RIGHT:
                    if (mButtonIndex == 0) {
                        mPlayer = (mPlayer) % 2 + 1;
                        mPIndex = mPlayer - 1;
                    }
                    break;
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
                    if (mButtonIndex != 0) {
                        mCurrButtonImage[mButtonIndex] = mButtonImages[mButtonIndex];
                        mbNowKeySetting = true;
                        mCurrKeyButtonImage[mButtonIndex] = mFocusEmptyImage;
                        mCurrKeyName[mButtonIndex] = "";
                    }
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
                case 1:
                    if (key == setting.getPlayerFirstMoveDown() |
                            key == setting.getPlayerFirstMoveRight() |
                            key == setting.getPlayerFirstMoveToFloor() |
                            key == setting.getPlayerFirstRotate() |
                            key == setting.getPlayerSecondMoveDown() |
                            key == setting.getPlayerSecondMoveRight() |
                            key == setting.getPlayerSecondMoveToFloor() |
                            key == setting.getPlayerSecondRotate()) {
                        return sr;
                    }
                    break;
                case 2:
                    if (key == setting.getPlayerFirstMoveLeft() |
                            key == setting.getPlayerFirstMoveRight() |
                            key == setting.getPlayerFirstMoveToFloor() |
                            key == setting.getPlayerFirstRotate() |
                            key == setting.getPlayerSecondMoveLeft() |
                            key == setting.getPlayerSecondMoveRight() |
                            key == setting.getPlayerSecondMoveToFloor() |
                            key == setting.getPlayerSecondRotate()) {
                        return sr;
                    }
                    break;
                case 3:
                    if (key == setting.getPlayerFirstMoveDown() |
                            key == setting.getPlayerFirstMoveLeft() |
                            key == setting.getPlayerFirstMoveToFloor() |
                            key == setting.getPlayerFirstRotate() |
                            key == setting.getPlayerSecondMoveDown() |
                            key == setting.getPlayerSecondMoveLeft() |
                            key == setting.getPlayerSecondMoveToFloor() |
                            key == setting.getPlayerSecondRotate()) {
                        return sr;
                    }
                    break;
                case 4:
                    if (key == setting.getPlayerFirstMoveDown() |
                            key == setting.getPlayerFirstMoveLeft() |
                            key == setting.getPlayerFirstMoveRight() |
                            key == setting.getPlayerFirstRotate() |
                            key == setting.getPlayerSecondMoveDown() |
                            key == setting.getPlayerSecondMoveLeft() |
                            key == setting.getPlayerSecondMoveRight() |
                            key == setting.getPlayerSecondRotate()) {
                        return sr;
                    }
                    break;
                case 5:
                    if (key == setting.getPlayerFirstMoveDown() |
                            key == setting.getPlayerFirstMoveLeft() |
                            key == setting.getPlayerFirstMoveRight() |
                            key == setting.getPlayerFirstMoveToFloor() |
                            key == setting.getPlayerSecondMoveDown() |
                            key == setting.getPlayerSecondMoveLeft() |
                            key == setting.getPlayerSecondMoveRight() |
                            key == setting.getPlayerSecondMoveToFloor()) {
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
                case 1:
                    if (mPlayer == 1) {
                        setting.storeMultiModeMoveLeftKey(key, Player.FIRST);
                    } else if (mPlayer == 2) {
                        setting.storeMultiModeMoveLeftKey(key, Player.SECOND);
                    }
                    break;
                case 2:
                    if (mPlayer == 1) {
                        setting.storeMultiModeMoveDownKey(key, Player.FIRST);
                    } else if (mPlayer == 2) {
                        setting.storeMultiModeMoveDownKey(key, Player.SECOND);
                    }
                    break;
                case 3:
                    if (mPlayer == 1) {
                        setting.storeMultiModeMoveRightKey(key, Player.FIRST);
                    } else if (mPlayer == 2) {
                        setting.storeMultiModeMoveRightKey(key, Player.SECOND);
                    }
                    break;
                case 4:
                    if (mPlayer == 1) {
                        setting.storeMultiModeMoveToFloorKey(key, Player.FIRST);
                    } else if (mPlayer == 2) {
                        setting.storeMultiModeMoveToFloorKey(key, Player.SECOND);
                    }
                    break;
                case 5:
                    if (mPlayer == 1) {
                        setting.storeMultiModeRotateKey(key, Player.FIRST);
                    } else if (mPlayer == 2) {
                        setting.storeMultiModeRotateKey(key, Player.SECOND);
                    }
                    break;
                default:
                    assert (false) : "Buttion Index out of boudns";
                    break;
            }
        }
        this.repaint();
        return sr;
    }

    private void refreshPlayerKeys() {
        mCurrKeyName = new String[NUM_BUTTONS];
        SettingData setting = SettingData.getInstance();
        if (mPlayer == 1) {
            mCurrKeyName[1] = this.keyCodeToString(setting.getPlayerFirstMoveLeft());
            mCurrKeyName[2] = this.keyCodeToString(setting.getPlayerFirstMoveDown());
            mCurrKeyName[3] = this.keyCodeToString(setting.getPlayerFirstMoveRight());
            mCurrKeyName[4] = this.keyCodeToString(setting.getPlayerFirstMoveToFloor());
            mCurrKeyName[5] = this.keyCodeToString(setting.getPlayerFirstRotate());
        } else if (mPlayer == 2) {
            mCurrKeyName[1] = this.keyCodeToString(setting.getPlayerSecondMoveLeft());
            mCurrKeyName[2] = this.keyCodeToString(setting.getPlayerSecondMoveDown());
            mCurrKeyName[3] = this.keyCodeToString(setting.getPlayerSecondMoveRight());
            mCurrKeyName[4] = this.keyCodeToString(setting.getPlayerSecondMoveToFloor());
            mCurrKeyName[5] = this.keyCodeToString(setting.getPlayerSecondRotate());
        } else {
            assert (false);
        }
    }

    private String keyCodeToString(int key) {
        String re = KeyEvent.getKeyText(key);
        return re;
    }
}
