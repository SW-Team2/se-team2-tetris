package graphics.screens;

import java.awt.Color;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import graphics.screens.screen.IScreen;

public class ScoreBoardScreen extends JPanel implements IScreen {
    private JLabel mTemp;

    public ScoreBoardScreen() {
        mTemp = new JLabel("Score");
        super.setBackground(Color.green);
        super.add(mTemp);
    }

    @Override
    public void setKeyListener(KeyListener kl) {

    }

    @Override
    public void unsetKeyListener() {

    }
}
