package graphics.screens;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameScreen extends JPanel {
    private JLabel mTemp;

    public GameScreen() {
        mTemp = new JLabel("Game");
        super.setBackground(Color.red);
        super.add(mTemp);
    }
}