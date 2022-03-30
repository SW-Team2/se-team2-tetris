package graphics.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

import graphics.eScreenInfo;

public class MainMenuScreen extends Screen {
    private JLabel mTemp;

    public MainMenuScreen() {
        mTemp = new JLabel("Main");
        super.setBackground(Color.gray);
        super.add(mTemp);
    }

    @Override
    public eScreenInfo getUserInput(KeyEvent e) {
        eScreenInfo sr = eScreenInfo.NONE;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_G:
                sr = eScreenInfo.GAME;
                break;
            case KeyEvent.VK_H:
                sr = eScreenInfo.SETTING;
                break;
            case KeyEvent.VK_J:
                sr = eScreenInfo.SCOREBOARD;
                break;
            default:
                break;
        }
        return sr;
    }
}
