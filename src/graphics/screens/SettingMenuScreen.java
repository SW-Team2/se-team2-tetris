package graphics.screens;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

import graphics.eScreenInfo;

public class SettingMenuScreen extends Screen {
    private JLabel mTemp;
    private JLabel mButtons[];
    private int mButtonIndex;
    private static final int NUM_BUTTONS = 2;

    public SettingMenuScreen() {
        mTemp = new JLabel("Setting");
        super.setBackground(Color.orange);
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        mButtons = new JLabel[NUM_BUTTONS];
        mButtons[0] = new JLabel("MAIN");
        mButtons[1] = new JLabel("SCREEN SIZE");
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
                        sr = eScreenInfo.MAIN;
                        break;
                    case 1:
                        break;
                }
                break;
            default:
                break;
        }
        return sr;
    }
}
