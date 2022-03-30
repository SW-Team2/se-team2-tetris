package graphics.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import graphics.eScreenInfo;

public class MainMenuScreen extends Screen {
    private JLabel mTemp;
    private JLabel mBtns[];
    private static final int NUM_BTNS = 3;
    private int mBtnIndex;

    public MainMenuScreen() {
        mTemp = new JLabel("Main");
        super.setBackground(Color.gray);
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        mBtns = new JLabel[NUM_BTNS];
        mBtns[0] = new JLabel("GAME");
        mBtns[1] = new JLabel("SETTING");
        mBtns[2] = new JLabel("SCORE BOARD");
        mBtnIndex = 0;

        super.add(mTemp);
        for (JLabel btn : mBtns) {
            super.add(btn);
        }
        mBtns[mBtnIndex].setForeground(Color.red);
    }

    @Override
    public eScreenInfo getUserInput(KeyEvent e) {
        eScreenInfo sr = eScreenInfo.NONE;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                mBtns[mBtnIndex].setForeground(Color.black);
                mBtnIndex = mBtnIndex == NUM_BTNS - 1 ? mBtnIndex : mBtnIndex + 1;
                mBtns[mBtnIndex].setForeground(Color.red);
                break;
            case KeyEvent.VK_UP:
                mBtns[mBtnIndex].setForeground(Color.black);
                mBtnIndex = mBtnIndex == 0 ? mBtnIndex : mBtnIndex - 1;
                mBtns[mBtnIndex].setForeground(Color.red);
                break;
            case KeyEvent.VK_ENTER:
                switch (mBtnIndex) {
                    case 0:
                        sr = eScreenInfo.GAME;
                        break;
                    case 1:
                        sr = eScreenInfo.SETTING;
                        break;
                    case 2:
                        sr = eScreenInfo.SCOREBOARD;
                }
                break;
            default:
                break;
        }
        return sr;
    }
}
