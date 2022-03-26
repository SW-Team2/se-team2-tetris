package graphics.screens;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import data.setting.SettingInfoDesc;

public class GameScreen extends JPanel {
    private static GameScreen mUniqueInstance = null;

    private JTextPane mGameBoard;
    private JTextPane mNextTetrominoBoard;
    private SimpleAttributeSet mTetStyleSet;
    private JLabel mScoreBoard;
    private JLabel mKeySettingBoard;

    private SettingInfoDesc mSettingInfo;

    private GameScreen() {
        mSettingInfo = SettingInfoDesc.getInstance();

        mGameBoard = new JTextPane();
        mGameBoard.setEditable(false);
        mGameBoard.setBackground(Color.black);
        mNextTetrominoBoard = new JTextPane();
        mNextTetrominoBoard.setEditable(false);
        mNextTetrominoBoard.setBackground(Color.black);
        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 15),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        mGameBoard.setBorder(border);
        mNextTetrominoBoard.setBorder(border);
        mScoreBoard = new JLabel();

        super.add(mGameBoard);
        super.add(mNextTetrominoBoard);
        super.add(mScoreBoard);

        mTetStyleSet = new SimpleAttributeSet();
        StyleConstants.setFontSize(mTetStyleSet, 30);
        StyleConstants.setFontFamily(mTetStyleSet, "Courier New");
        StyleConstants.setBold(mTetStyleSet, true);
        StyleConstants.setForeground(mTetStyleSet, Color.WHITE);
        StyleConstants.setAlignment(mTetStyleSet, StyleConstants.ALIGN_LEFT);

        StyledDocument docGameBoard = mGameBoard.getStyledDocument();
        docGameBoard.setParagraphAttributes(0, 22 * 12, mTetStyleSet, false);
        mGameBoard.setStyledDocument(docGameBoard);
        StyledDocument docNextTetBoard = mNextTetrominoBoard.getStyledDocument();
        docNextTetBoard.setParagraphAttributes(0, 6 * 6, mTetStyleSet, false);
        mNextTetrominoBoard.setStyledDocument(docNextTetBoard);

        super.setBackground(Color.gray);
    }

    public static GameScreen getInstance() {
        if (mUniqueInstance == null) {
            mUniqueInstance = new GameScreen();
        }
        return mUniqueInstance;
    }

    public JTextPane getGameBoardTextPane() {
        return mGameBoard;
    }

    public JTextPane getNextTetBoardTextPane() {
        return mNextTetrominoBoard;
    }

    public JLabel getScoreBoardLabel() {
        return mScoreBoard;
    }
}