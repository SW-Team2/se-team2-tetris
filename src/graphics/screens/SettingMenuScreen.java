package graphics.screens;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingMenuScreen extends JPanel {
    private JLabel mTemp;

    public SettingMenuScreen() {
        mTemp = new JLabel("Setting");
        super.setBackground(Color.orange);
        super.add(mTemp);
    }
}
