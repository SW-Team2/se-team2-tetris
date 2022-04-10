package tetrisgame;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import gamestarter.GameStarter;
import graphics.screens.GameScreen;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.enumerations.eMsg;
import tetrisgame.parts.component.Tetromino;
import tetrisgame.parts.Timer;
import tetrisgame.parts.component.GameBoard;
import tetrisgame.parts.component.Score;

public class TetrisGame implements Runnable {
	protected GameScreen mScreen;

	private volatile boolean mbGameOverFlag;

	private GameBoard mGameBoard;
	private Score mScore;

	private Timer mTimer;

	private int mCurrKeyCode;
	private float mKeyReactTimeTick;
	private volatile boolean mbKeyReactFlag;
	private static final float KEY_REACT_TIME = 0.1f;

	public TetrisGame(GameScreen gameScreen) {
		mScreen = gameScreen;

		mGameBoard = new GameBoard(this);
		mGameBoard.clear();

		mScore = new Score(this);

		// TODO:
		eDifficulty eDiff = eDifficulty.EASY;
		Tetromino.setDifficulty(eDiff);

		mTimer = new Timer();
		mTimer.initialize();

		mCurrKeyCode = -1;
		mKeyReactTimeTick = 0.f;
		mbKeyReactFlag = true;
	}

	public void broadcast(eMsg msg) {
		if (msg == eMsg.GAME_OVER) {
			mbGameOverFlag = true;
			return;
		}
		mGameBoard.react(msg);
		mScore.react(msg);
	}

	@Override
	public void run() {
		while (mbGameOverFlag == false) {
			this.update();
			this.draw();
		}
		// Save user record
		String userName = JOptionPane.showInputDialog("Enter your name");
		System.out.println(userName);
		GameStarter.setOver();
	}

	protected synchronized void update() {
		mTimer.tick();

		float deltaTime = mTimer.getDeltaTime();
		mKeyReactTimeTick += deltaTime;

		// React at input
		int userInput = -1;
		if (mKeyReactTimeTick >= KEY_REACT_TIME && mbKeyReactFlag == false) {
			userInput = mCurrKeyCode;
			if (mCurrKeyCode != KeyEvent.VK_DOWN && mCurrKeyCode != KeyEvent.VK_RIGHT
					&& mCurrKeyCode != KeyEvent.VK_LEFT) {
				mbKeyReactFlag = true;
			}
			mKeyReactTimeTick = 0.f;
		}

		mGameBoard.update(deltaTime, userInput);
	}

	public synchronized void getUserInput(KeyEvent e) {
		if (mCurrKeyCode == e.getKeyCode()) {
			return;
		}

		mCurrKeyCode = e.getKeyCode();
		mKeyReactTimeTick = KEY_REACT_TIME;
		mbKeyReactFlag = false;
		if (mTimer.getPauseState()) {
			mCurrKeyCode &= KeyEvent.VK_U;
		}

		switch (mCurrKeyCode) {
			case KeyEvent.VK_P:
				mTimer.pause();
				break;
			case KeyEvent.VK_U:
				mTimer.unPause();
				break;
		}
		update();
	}

	public void getUserInputKeyRealease(KeyEvent e) {
		if (mCurrKeyCode == e.getKeyCode()) {
			mCurrKeyCode = -1;
			mbKeyReactFlag = false;
		}
	}

	public GameBoard getGameBoard() {
		return mGameBoard;
	}

	public long getCurrScore() {
		return mScore.getScore();
	}

	private void draw() {
		// TODO: refact
		mScreen.repaint();
	}
}
