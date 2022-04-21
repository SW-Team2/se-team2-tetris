package graphics.screens;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

import data.setting.SettingData;
import graphics.eScreenInfo;

public class MainMenuScreen extends Screen {
    //private JLabel mTemp;
    private JLabel mButtons[];
    private ImageIcon btnImageIcon[];
    private ImageIcon updatedBtnImage[];
    private static final int NUM_BUTTONS = 5;
    private int mButtonIndex;

    public void paintComponent(Graphics g) {
        int screenWidth = SettingData.getInstance().getWidth();
        int screenHeight = SettingData.getInstance().getHeight();
        Dimension d = getSize();
        int imageWidth = updatedBtnImage[0].getIconWidth();
        int imageHeight = updatedBtnImage[0].getIconHeight();
        super.setNumButtons(5);
        super.setButtonWidthAndHeight(235, 50);
        ImageIcon image = new ImageIcon("images\\main\\main_background.png");

        mButtons[0].setBounds(super.getButtonX(0), super.getButtonY(0), imageWidth, imageHeight);
        mButtons[1].setBounds(super.getButtonX(1), super.getButtonY(1), imageWidth, imageHeight);
        mButtons[2].setBounds(super.getButtonX(2), super.getButtonY(2), imageWidth, imageHeight);
        mButtons[3].setBounds(super.getButtonX(3), super.getButtonY(3), imageWidth, imageHeight);
        mButtons[4].setBounds(super.getButtonX(4), super.getButtonY(4), imageWidth, imageHeight);

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

    public MainMenuScreen() {
        //mTemp = new JLabel("Main");
        //super.setBackground(Color.gray);
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        mButtons = new JLabel[NUM_BUTTONS];
        this.btnImageIcon = new ImageIcon[NUM_BUTTONS];
        //this.tmpImage = new ImageIcon[NUM_BUTTONS];
        this.updatedBtnImage = new ImageIcon[NUM_BUTTONS];

        this.btnImageIcon[0] = new ImageIcon("images\\main\\btn_start.png");
        this.btnImageIcon[1] = new ImageIcon("images\\main\\btn_item_mode.png");
        this.btnImageIcon[2] = new ImageIcon("images\\main\\btn_setting.png");
        this.btnImageIcon[3] = new ImageIcon("images\\main\\btn_score_board.png");
        this.btnImageIcon[4] = new ImageIcon("images\\main\\btn_exit.png");

        this.updatedBtnImage[0] = resizeBtn(this.btnImageIcon[0].getIconWidth(), this.btnImageIcon[0].getIconHeight(), this.btnImageIcon[0]);
        this.updatedBtnImage[1] = resizeBtn(this.btnImageIcon[1].getIconWidth(), this.btnImageIcon[1].getIconHeight(), this.btnImageIcon[1]);
        this.updatedBtnImage[2] = resizeBtn(this.btnImageIcon[2].getIconWidth(), this.btnImageIcon[2].getIconHeight(), this.btnImageIcon[2]);
        this.updatedBtnImage[3] = resizeBtn(this.btnImageIcon[3].getIconWidth(), this.btnImageIcon[3].getIconHeight(), this.btnImageIcon[3]);
        this.updatedBtnImage[4] = resizeBtn(this.btnImageIcon[4].getIconWidth(), this.btnImageIcon[4].getIconHeight(), this.btnImageIcon[4]);

        mButtons[0] = new JLabel(this.updatedBtnImage[0]);
        mButtons[1] = new JLabel(this.updatedBtnImage[1]);
        mButtons[2] = new JLabel(this.updatedBtnImage[2]);
        mButtons[3] = new JLabel(this.updatedBtnImage[3]);
        mButtons[4] = new JLabel(this.updatedBtnImage[4]);

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
