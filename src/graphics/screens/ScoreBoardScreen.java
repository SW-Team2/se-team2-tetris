package graphics.screens;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreBoardScreen extends JPanel {
    private JLabel mTemp;

    public ScoreBoardScreen() {
        mTemp = new JLabel("Score");
        super.setBackground(Color.green);
        super.add(mTemp);
    }
}
