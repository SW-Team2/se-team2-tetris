package graphics.screens;

import java.awt.*;
import java.awt.event.KeyEvent;

import data.setting.SettingData;
import graphics.eScreenInfo;

import javax.swing.*;

public class DifficultyScreen extends Screen{

    //private JLabel mTemp;
    private JLabel mButtons[];
    private ImageIcon btnImageIcon[];
    private ImageIcon updatedBtnImage[];
    private static final int NUM_BUTTONS = 3;
    private int mButtonIndex;

    public void paintComponent(Graphics g) {
        int screenWidth = SettingData.getInstance().getWidth();
        int screenHeight = SettingData.getInstance().getHeight();
        Dimension d = getSize();
        int imageWidth = updatedBtnImage[0].getIconWidth();
        int imageHeight = updatedBtnImage[0].getIconHeight();
        ImageIcon image = new ImageIcon("images\\main\\tetris_background.png");
        mButtons[0].setBounds(screenWidth / 2 - imageWidth / 2, 280, imageWidth, imageHeight);
        mButtons[1].setBounds(screenWidth / 2 - imageWidth / 2, 340, imageWidth, imageHeight);
        mButtons[2].setBounds(screenWidth / 2 - imageWidth / 2, 400, imageWidth, imageHeight);

        g.drawImage(image.getImage(), 0, 0, d.width, d.height, null);
    }

    //ImageIcon은 크기조절이 안돼서 Image로 get한 다음에 크기조절하고 다시 ImageIcon으로 만든다.
    public ImageIcon resizeBtn(int width, int height, ImageIcon icon) {
        Image image = icon.getImage();
        int resizedWidth = (int) (width * 0.5);
        int resizedHeight = (int) (height * 0.5);
        Image resizedImage = image.getScaledInstance(resizedWidth, resizedHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }

    public DifficultyScreen() {
        //mTemp = new JLabel("Main");
        //super.setBackground(Color.gray);
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        mButtons = new JLabel[NUM_BUTTONS];
        this.btnImageIcon = new ImageIcon[NUM_BUTTONS];
        //this.tmpImage = new ImageIcon[NUM_BUTTONS];
        this.updatedBtnImage = new ImageIcon[NUM_BUTTONS];

        this.btnImageIcon[0] = new ImageIcon("images\\difficulty\\btn_easy.png");
        this.btnImageIcon[1] = new ImageIcon("images\\difficulty\\btn_normal.png");
        this.btnImageIcon[2] = new ImageIcon("images\\difficulty\\btn_hard.png");


        /*this.updatedBtnImage[0] = resizeBtn(this.btnImageIcon[0].getIconWidth(), this.btnImageIcon[0].getIconHeight(), this.btnImageIcon[0]);
        this.updatedBtnImage[1] = resizeBtn(this.btnImageIcon[1].getIconWidth(), this.btnImageIcon[1].getIconHeight(), this.btnImageIcon[1]);
        this.updatedBtnImage[2] = resizeBtn(this.btnImageIcon[2].getIconWidth(), this.btnImageIcon[2].getIconHeight(), this.btnImageIcon[2]);*/


        mButtons[0] = new JLabel(this.updatedBtnImage[0]);
        mButtons[1] = new JLabel(this.updatedBtnImage[1]);
        mButtons[2] = new JLabel(this.updatedBtnImage[2]);


        //mButtonIndex = 0;

        //super.add(mTemp);
        for (JLabel btn : mButtons) {
            super.add(btn);
        }
        //mButtons[mButtonIndex].setForeground(Color.red);
    }

    @Override
    public eScreenInfo getUserInput(KeyEvent e) {
        eScreenInfo sr = eScreenInfo.NONE;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                mButtons[mButtonIndex].setForeground(Color.black);
                mButtonIndex = mButtonIndex == NUM_BUTTONS - 1 ? mButtonIndex : mButtonIndex + 1;
                mButtons[mButtonIndex].setForeground(Color.red);
                break;
            case KeyEvent.VK_UP:
                mButtons[mButtonIndex].setForeground(Color.black);
                mButtonIndex = mButtonIndex == 0 ? mButtonIndex : mButtonIndex - 1;
                mButtons[mButtonIndex].setForeground(Color.red);
                break;
            case KeyEvent.VK_ENTER:
                switch (mButtonIndex) {
                    case 0:
                        sr = eScreenInfo.GAME;
                        break;
                    case 1:
                        sr = eScreenInfo.ITEMGAME;
                        break;
                    case 2:
                        sr = eScreenInfo.SETTING;
                        break;
                    case 3:
                        sr = eScreenInfo.SCOREBOARD;
                }
                break;
            default:
                break;
        }
        return sr;
    }
}
