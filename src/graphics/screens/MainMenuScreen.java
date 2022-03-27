package graphics.screens;

import java.awt.Color;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import graphics.screens.screen.IScreen;

public class MainMenuScreen extends JPanel implements IScreen {
    private JLabel mTemp;

    public MainMenuScreen() {
        mTemp = new JLabel("Main");
        super.setBackground(Color.gray);
        super.add(mTemp);
    }

    @Override
    public void setKeyListener(KeyListener kl) {

    }

    @Override
    public void unsetKeyListener() {

    }
}
