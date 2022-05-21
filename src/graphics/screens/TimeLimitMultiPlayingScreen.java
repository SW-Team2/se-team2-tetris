package graphics.screens;

import java.awt.Graphics;
import java.awt.Graphics2D;

import gamemanager.GameManager;
import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eDifficulty;

public class TimeLimitMultiPlayingScreen extends MultiPlayingScreen {

    @Override
    protected void startGame() {
        GameManager.setDifficulty(eDifficulty.NORMAL);
        GameManager.setMulti(true);
        mGame1 = new TetrisGame(this, true, GameManager.getDifficulty(), false);
        mGame2 = new TetrisGame(this, true, GameManager.getDifficulty(), true);
        mBoard1 = mGame1.getGameBoard();
        mBoard2 = mGame2.getGameBoard();
        mbAttackBoard1 = mGame1.getAttackBoard();
        mbAttackBoard2 = mGame2.getAttackBoard();
        GameManager.setTimeLimitMode(true);
        GameManager.setGame(mGame1, mGame2);
        GameManager.setStart();
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        int timerPosX = (int) (mScreenWidth * 0.8);
        int timerPosY = (int) (mScreenHeight * 0.9);

        StringBuffer scoreStrBuf = new StringBuffer();
        scoreStrBuf.append(GameManager.getCurrTime());
        g2d.setFont(mFont);
        g2d.setColor(mFontColor);
        g2d.drawString(scoreStrBuf.toString(), timerPosX, timerPosY);
    }
}