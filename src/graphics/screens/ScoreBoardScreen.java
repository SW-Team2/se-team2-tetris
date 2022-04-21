package graphics.screens;

import data.score.Score;
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
    private BufferedImage mDefaultButtonImage;
    private BufferedImage mFocusDefaultButtonImage;
    private BufferedImage mCurrDefaultButtonImage;
    private BufferedImage mItemButtonImage;
    private BufferedImage mFocusItemButtonImage;
    private BufferedImage mCurrItemButtonImage;
    private BufferedImage mBackButtonImage;
    private Image mBackgroundImage;

    private int mButtonIndex;

    private ArrayList<HashMap<String, Object>> defaultModeScores;
    private ArrayList<HashMap<String, Object>> itemModeScores;

    private static final int NUM_BUTTONS = 3;

    public ScoreBoardScreen() {
        this.refreshScoreBoard();

        ImageLoader img = ImageLoader.getInstance();

        mDefaultButtonImage = img.getTexture("btn_default");
        mFocusDefaultButtonImage = img.getTexture("btn_default_focus");
        mItemButtonImage = img.getTexture("btn_item");
        mFocusItemButtonImage = img.getTexture("btn_item_focus");
        mBackButtonImage = img.getTexture("btn_back");
        mBackgroundImage = img.getTexture("tetris_background");

        mCurrDefaultButtonImage = mFocusDefaultButtonImage;
        mCurrItemButtonImage = mItemButtonImage;

        mButtonIndex = 0;
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

        g.drawImage(
                mCurrDefaultButtonImage, 186 * ScreenWidth / 720, 81 * ScreenHeight / 720,
                mCurrDefaultButtonImage.getWidth() * 7 / 20 * ScreenWidth / 720,
                mCurrDefaultButtonImage.getHeight() * 7 / 20 * ScreenHeight / 720, null);
        g.drawImage(
                mCurrItemButtonImage, 402 * ScreenWidth / 720, 81 * ScreenHeight / 720,
                mCurrItemButtonImage.getWidth() * 7 / 20 * ScreenWidth / 720,
                mCurrItemButtonImage.getHeight() * 7 / 20 * ScreenHeight / 720, null);

        g.drawImage(mBackButtonImage,
                Screen.getEscPosX(),
                Screen.getEscPosY(),
                Screen.getEscButtonWidth(),
                Screen.getEscButtonHeight(), null);

        StringBuffer str = new StringBuffer();
        ArrayList<HashMap<String, Object>> scoreArr;
        int bold = (int) (100 * SettingData.getInstance().getHeight() / (double) 1920);
        g.setFont(new Font("S-Core_Dream_OTF", Font.BOLD, bold));
        g.setColor(Color.WHITE);
        if (mButtonIndex == 0) {
            scoreArr = defaultModeScores;
            for (int i = 0; i < scoreArr.size(); i++) {
                HashMap<String, Object> hs = scoreArr.get(i);
                String name = (String) hs.get("name");
                Integer score = Integer.parseInt(String.valueOf(hs.get("score")));
                String difficulty = (String) hs.get("difficulty");

                str.append(name);
                str.append("  ");
                str.append(score.toString());
                str.append("  ");
                str.append(difficulty);
                str.append('\n');
            }
            g.drawString(str.toString(), 300, 300);
        } else {
            scoreArr = itemModeScores;
            for (int i = 0; i < scoreArr.size(); i++) {
                HashMap<String, Object> hs = scoreArr.get(i);
                String name = (String) hs.get("name");
                Integer score = Integer.parseInt(String.valueOf(hs.get("score")));

                str.append(name);
                str.append("  ");
                str.append(score.toString());
                str.append('\n');
            }
            g.drawString(str.toString(), 300, 300);
        }

    }

    @Override
    public eScreenInfo getUserInput(KeyEvent e) {
        eScreenInfo sr = eScreenInfo.NONE;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                if (mButtonIndex == 0) {
                    mButtonIndex++;
                    mCurrDefaultButtonImage = mDefaultButtonImage;
                    mCurrItemButtonImage = mFocusItemButtonImage;
                }
                this.repaint();
                break;
            case KeyEvent.VK_LEFT:
                if (mButtonIndex == 1) {
                    mButtonIndex--;
                    mCurrDefaultButtonImage = mFocusDefaultButtonImage;
                    mCurrItemButtonImage = mItemButtonImage;
                }
                this.repaint();
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