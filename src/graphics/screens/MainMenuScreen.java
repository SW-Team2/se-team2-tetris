package graphics.screens;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenuScreen extends JPanel {
    private JLabel mTemp;

    public MainMenuScreen() {
        mTemp = new JLabel("Main");
        super.setBackground(Color.gray);
        super.add(mTemp);
    }
}
