package graphics.screens;

import data.score.ScoreBoardData;
import data.setting.SettingData;
import graphics.eScreenInfo;
import tetrisgame.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class ScoreBoardScreen extends Screen {
    private BufferedImage[] mCurrButtonImage;
    private BufferedImage[] mButtonImages;
    private BufferedImage[] mFocusButtonImages;

    private BufferedImage mBackButtonImage;
    private int mButtonIndex;
    private Image mBackgroundImage;

    private ArrayList<HashMap<String, Object>> defaultModeScores;
    private ArrayList<HashMap<String, Object>> itemModeScores;
    private JPanel scorePanel;
    private JScrollPane scoreboard;

    private static final int NUM_BUTTONS = 3;

    public ScoreBoardScreen() {
        this.refreshScoreBoard();

        ImageLoader img = ImageLoader.getInstance();
        mButtonImages = new BufferedImage[NUM_BUTTONS];

        mButtonImages[0] = img.getTexture("btn_default");
        mButtonImages[1] = img.getTexture("btn_item");
        mButtonImages[2] = img.getTexture("btn_next_page");

        mFocusButtonImages = new BufferedImage[NUM_BUTTONS];

        mFocusButtonImages[0] = img.getTexture("btn_default_focus");
        mFocusButtonImages[1] = img.getTexture("btn_item_focus");
        mFocusButtonImages[2] = img.getTexture("btn_next_page_focus");

        mCurrButtonImage = new BufferedImage[NUM_BUTTONS];
        mCurrButtonImage[0] = mFocusButtonImages[0];
        mCurrButtonImage[1] = mButtonImages[1];
        mCurrButtonImage[2] = mButtonImages[2];

        mBackButtonImage = img.getTexture("btn_back");

        mBackgroundImage = img.getTexture("tetris_background");
        mButtonIndex = 0;

        scoreboard = new JScrollPane();
        scoreboard.setSize(100,100);
        scoreboard.setVisible(false);
    }
    public void refreshScoreBoard() {
        ScoreBoardData scoreBoardData = ScoreBoardData.getInstance();
        this.defaultModeScores = scoreBoardData.getDefaultModeScore();
        this.itemModeScores = scoreBoardData.getItemModeScore();
    }

    @Override
    public void repaint() {
        super.repaint();
        this.refreshScoreBoard();
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int ScreenWidth = SettingData.getInstance().getWidth();
        int ScreenHeight = SettingData.getInstance().getHeight();
        Dimension d = getSize();
        g.drawImage(mBackgroundImage, 0, 0, d.width, d.height, null);
        g.drawImage(mCurrButtonImage[0], 186 * ScreenWidth / 720, 81 * ScreenHeight / 720,
                mCurrButtonImage[0].getWidth() * 7 / 20 * ScreenWidth / 720, mCurrButtonImage[0].getHeight() * 7 / 20 * ScreenHeight / 720, null);
        g.drawImage(mCurrButtonImage[1], 402 * ScreenWidth / 720, 81 * ScreenHeight / 720,
                mCurrButtonImage[1].getWidth() * 7 / 20 * ScreenWidth / 720, mCurrButtonImage[1].getHeight() * 7 / 20 * ScreenHeight / 720, null);
        g.drawImage(mCurrButtonImage[2], 492 * ScreenWidth / 720, 512 * ScreenHeight / 720,
                mCurrButtonImage[2].getWidth() * 7 / 20 * ScreenWidth / 720, mCurrButtonImage[2].getHeight() * 7 / 20 * ScreenHeight / 720, null);

        g.drawImage(mBackButtonImage,
                Screen.getEscPosX(),
                Screen.getEscPosY(),
                Screen.getEscButtonWidth(),
                Screen.getEscButtonHeight(), null);
        scoreboard.setVisible(true);

    }

    @Override
    public eScreenInfo getUserInput(KeyEvent e) {
        eScreenInfo sr = eScreenInfo.NONE;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                if (mButtonIndex == 0 || mButtonIndex == 1) {
                    mCurrButtonImage[mButtonIndex] = mButtonImages[mButtonIndex];
                    mButtonIndex = 2;
                    mCurrButtonImage[2] = mFocusButtonImages[2];
                }
                this.repaint();
                break;
            case KeyEvent.VK_UP:
                if (mButtonIndex == 2) {
                    mCurrButtonImage[2] = mButtonImages[2];
                    mButtonIndex = 0;
                    mCurrButtonImage[0] = mFocusButtonImages[0];
                }
                this.repaint();
                break;
            case KeyEvent.VK_RIGHT:
                if (mButtonIndex == 0) {
                    mCurrButtonImage[0] = mButtonImages[0];
                    mButtonIndex = 1;
                    mCurrButtonImage[1] = mFocusButtonImages[1];
                }
                this.repaint();
                break;
            case KeyEvent.VK_LEFT:
                if (mButtonIndex == 1) {
                    mCurrButtonImage[1] = mButtonImages[1];
                    mButtonIndex = 0;
                    mCurrButtonImage[0] = mFocusButtonImages[0];
                }
                this.repaint();
                break;
            case KeyEvent.VK_ENTER:
                switch (mButtonIndex) {
                    case 0:
                        sr = eScreenInfo.MAIN;//일반모드 스코어보드 표시 기능 넣어야
                        break;
                    case 1:
                        sr = eScreenInfo.SETTING;//아이템모드 스코어보드 표시 기능 넣어야
                        break;
                    case 2:
                        break;
                }
                break;
            case KeyEvent.VK_ESCAPE:
                sr = eScreenInfo.MAIN;
                break;
            default:
                break;
        }
        return sr;
    }
}