package graphics.screens;

import java.awt.Color;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import graphics.screens.screen.IScreen;

public class SettingMenuScreen extends JPanel implements IScreen {
    private JLabel mTemp;

    public SettingMenuScreen() {
        mTemp = new JLabel("Setting");
        super.setBackground(Color.orange);
        super.add(mTemp);
    }

    @Override
    public void setKeyListener(KeyListener kl) {

    }

    @Override
    public void unsetKeyListener() {

    }
}
