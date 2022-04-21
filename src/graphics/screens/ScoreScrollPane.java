package graphics.screens;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ScoreScrollPane extends JScrollPane {
    public ScoreScrollPane() {
    }
    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
    }
    public void paint(Graphics g) {
        super.paint(g);
        Dimension d = getSize();
    }
}
