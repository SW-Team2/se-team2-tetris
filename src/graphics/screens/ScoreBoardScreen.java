package graphics.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

import graphics.eScreenInfo;

public class ScoreBoardScreen extends Screen {
    private JLabel mTemp;

    public ScoreBoardScreen() {
        mTemp = new JLabel("Score");
        super.setBackground(Color.green);
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
