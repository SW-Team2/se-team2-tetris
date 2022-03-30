package graphics.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

import graphics.eScreenInfo;

public class SettingMenuScreen extends Screen {
    private JLabel mTemp;

    public SettingMenuScreen() {
        mTemp = new JLabel("Setting");
        super.setBackground(Color.orange);
        super.add(mTemp);
    }

    @Override
    public eScreenInfo getUserInput(KeyEvent e) {
        eScreenInfo sr = eScreenInfo.NONE;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                super.setBackground(Color.red);
                break;
            case KeyEvent.VK_ENTER:
                sr = eScreenInfo.MAIN;
                break;
            default:
                break;
        }
        return sr;
    }
}
