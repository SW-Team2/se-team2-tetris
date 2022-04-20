package graphics.screens;

import data.score.Score;

import javax.swing.*;
import java.awt.*;

public class ScoreRenderer extends JLabel implements ListCellRenderer<Score> {
    public ScoreRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Score> list, Score value, int index, boolean isSelected, boolean cellHasFocus) {
        String name = value.getName();
        int score = value.getScore();
        setText(name + Integer.toString(score));
        return this;
    }
}
