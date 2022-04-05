package graphics.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import graphics.eScreenInfo;

public class MainMenuScreen extends Screen {
    private JLabel mTemp;
    private JLabel mButtons[];
    private static final int NUM_BUTTONS = 3;
    private int mButtonIndex;

    public MainMenuScreen() {
        mTemp = new JLabel("Main");
        super.setBackground(Color.gray);
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        mButtons = new JLabel[NUM_BUTTONS];
        mButtons[0] = new JLabel("GAME");
        mButtons[1] = new JLabel("SETTING");
        mButtons[2] = new JLabel("SCORE BOARD");
        mButtonIndex = 0;

        super.add(mTemp);
        for (JLabel btn : mButtons) {
            super.add(btn);
        }
        mButtons[mButtonIndex].setForeground(Color.red);
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
