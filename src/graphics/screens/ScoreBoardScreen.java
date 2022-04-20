package graphics.screens;

import data.score.Score;
import data.score.ScoreBoardData;
import graphics.eScreenInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class ScoreBoardScreen extends Screen {
    private JLabel label;

    private ScoreBoardData scoreData;

    public ScoreBoardScreen() {
        this.label = new JLabel("Score");
        super.setBackground(Color.green);
        super.add(this.label);
        this.scoreData = ScoreBoardData.getInstance();

        DefaultListModel<Score> listModel = new DefaultListModel<Score>();

        JList<Score> scoreList = new JList<>(listModel);
        super.add(new JScrollPane(scoreList));
        scoreList.setCellRenderer(new ScoreRenderer());
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