package tetrisgame;

import java.awt.event.KeyEvent;

import graphics.screens.GameScreen;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.enumerations.eDirection;
import tetrisgame.enumerations.eGameOver;
import tetrisgame.parts.GameBoard;
import tetrisgame.parts.Tetromino;
import tetrisgame.parts.Timer;

public class TetrisGame implements Runnable {
	private GameScreen mScreen;

	private GameBoard mGameBoard;
	private Tetromino mNextTetromino;
	private Timer mTimer;
	private int mSpeed;
	private long mScore;
	// TODO: Using Temp enum
	private eDifficulty mDifficulty;
	private float GAME_SPEED_ACCEL_UNIT = 0.1f;

	private float mAutoDownTime;
	private float mSumDeltaTime;
	// private float mPrevTimeForDraw;

	private boolean mbCollWithFloor = false;

	private static final float START_AUTO_DOWN_TIME = 1.0f;

	private static final int SCORE_UNIT = 100;
	private static final int MULTIPLE_BREAK_BONUS_2L = 30;
	private static final int MULTIPLE_BREAK_BONUS_3L = 60;
	private static final int MULTIPLE_BREAK_BONUS_4L = 100;

	public TetrisGame(GameScreen gameScreen) {
		mScreen = gameScreen;
		mGameBoard = new GameBoard();
		mNextTetromino = new Tetromino();
		mTimer = new Timer();
	}

	public void initialize() {
		mNextTetromino.setRandomShapeAndColor();
		mGameBoard.setTetromino(mNextTetromino);
		mNextTetromino.setRandomShapeAndColor();
		mTimer.initialize();
		mTimer.pause();
		mScore = 0;
		mSpeed = 1;
		// TODO: Using Temp value
		mDifficulty = eDifficulty.HARD;
		Tetromino.setDifficulty(mDifficulty);
		switch (mDifficulty) {
			case EASY:
				GAME_SPEED_ACCEL_UNIT *= 0.8f;
				break;
			case NORMAL:
				break;
			case HARD:
				GAME_SPEED_ACCEL_UNIT *= 1.2f;
				break;
			default:
				assert (false);
				break;
		}

		mAutoDownTime = START_AUTO_DOWN_TIME;
		mSumDeltaTime = 0.0f;
		// mPrevTimeForDraw = 0.0f;
	}

	@Override
	public void run() {
		boolean bGameOverFlag = false;
		while (!bGameOverFlag) {
			bGameOverFlag = this.update();
			this.draw();
		}
	}

	public Tetromino getNextTetromion() {
		return mNextTetromino;
	}

	public long getCurrScore() {
		return mScore;
	}

	public void getUserInput(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (mTimer.getPauseState()) {
			keyCode &= KeyEvent.VK_U;
		}
		switch (keyCode) {
			// Game playing
			case KeyEvent.VK_LEFT:
				mGameBoard.moveTet(eDirection.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				mGameBoard.moveTet(eDirection.RIGHT);
				break;
			case KeyEvent.VK_DOWN:
				mbCollWithFloor = mGameBoard.moveTet(eDirection.DOWN);
				break;
			case KeyEvent.VK_SPACE:
				mGameBoard.rotateTet();
				break;
			case KeyEvent.VK_UP:
				while (!mbCollWithFloor) {
					mbCollWithFloor = mGameBoard.moveTet(eDirection.DOWN);
				}
				break;
			// Pause & Unpause
			case KeyEvent.VK_P:
				// intentional fallthrough
			case KeyEvent.VK_ESCAPE:
				mTimer.pause();
				break;
			case KeyEvent.VK_U:
				mTimer.unPause();
				break;
			default:
				break;
		}
	}

	public GameBoard getGameBoard() {
		return mGameBoard;
	}

	private boolean update() {
		eGameOver gameOverFlag = eGameOver.CONTINUE;

		mTimer.tick();
		float dTime = mTimer.getDeltaTime();
		mSumDeltaTime += dTime;

		if (mAutoDownTime <= mSumDeltaTime) {
			mbCollWithFloor |= mGameBoard.moveTet(eDirection.DOWN);
			mSumDeltaTime = 0.0f;
		}

		if (mbCollWithFloor) {
			int removeColArr[] = new int[4];
			int numRemovedLines = 0;
			numRemovedLines = mGameBoard.findRemovableLines(removeColArr);
			mGameBoard.removeLines(removeColArr, numRemovedLines);
			// Calculate Score
			int addedScore = 0;
			switch (numRemovedLines) {
				case 0:
					break;
				case 1:
					addedScore = SCORE_UNIT;
					break;
				case 2:
					addedScore = SCORE_UNIT * 2 + MULTIPLE_BREAK_BONUS_2L;
					break;
				case 3:
					addedScore = SCORE_UNIT * 3 + MULTIPLE_BREAK_BONUS_3L;
					break;
				case 4:
					addedScore = SCORE_UNIT * 4 + MULTIPLE_BREAK_BONUS_4L;
					break;
				default:
					assert (false) : "Unspecified";
					break;
			}
			addedScore *= (float) (1 + (mSpeed - 1) / 10.0f);
			mScore += addedScore;
			mSpeed = (int) (mScore / (SCORE_UNIT * 10) + 1);

			mGameBoard.setTetromino(mNextTetromino);
			gameOverFlag = mGameBoard.checkGameOver();
			mNextTetromino.setRandomShapeAndColor();
			mbCollWithFloor = false;
			mSumDeltaTime = 0.0f;
			mAutoDownTime = START_AUTO_DOWN_TIME;
			for (int i = 0; i < mSpeed - 1; i++) {
				mAutoDownTime = mAutoDownTime * (1.0f - GAME_SPEED_ACCEL_UNIT);
			}

			if (gameOverFlag == eGameOver.OVER) {
				return true;
			}
		}
		return false;
	}

	private void draw() {
		// TODO: refact
		mScreen.repaint();
		// float currTime = mTimer.getGameTime();
		// float dTime = currTime - mPrevTimeForDraw;
		// // TODO: Set frame
		// if (dTime < 0.1f) {
		// return;
		// }
		// mPrevTimeForDraw = currTime;
	}
}
