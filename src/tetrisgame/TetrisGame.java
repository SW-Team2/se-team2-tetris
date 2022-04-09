package tetrisgame;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import gamestarter.GameStarter;
import graphics.screens.GameScreen;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.enumerations.eDirection;
import tetrisgame.enumerations.eGameOver;
import tetrisgame.parts.GameBoard;
import tetrisgame.parts.Tetromino;
import tetrisgame.parts.Tile;
import tetrisgame.parts.Timer;

public class TetrisGame implements Runnable {
	private GameScreen mScreen;

	private GameBoard mGameBoard;
	private Tetromino mNextTetromino;
	private Timer mTimer;
	private int mSpeed;
	private int mScore;
	// TODO: Using Temp enum
	private eDifficulty meDifficulty;
	private float GAME_SPEED_ACCEL_UNIT = 0.1f;

	private float mAutoDownTime;
	private float mAutoDownTimeTick;
	// private float mPrevTimeForDraw;

	private int mCurrKeyCode;
	private float mKeyReactTimeTick;

	private boolean mbCollWithFloor = false;

	private static final float START_AUTO_DOWN_TIME = 1.0f;

	private static final float KEY_REACT_TIME = 0.11f;

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
		mGameBoard.clear();
		mNextTetromino.setRandomShapeAndColor();
		mGameBoard.setTetromino(mNextTetromino);
		mNextTetromino.setRandomShapeAndColor();
		mTimer.initialize();
		// mTimer.pause();
		mScore = 0;
		mSpeed = 1;
		// TODO: Using Temp value
		meDifficulty = eDifficulty.EASY;
		Tetromino.setDifficulty(meDifficulty);
		switch (meDifficulty) {
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
		mAutoDownTimeTick = 0.f;
		// mPrevTimeForDraw = 0.0f;

		mCurrKeyCode = -1;
		mKeyReactTimeTick = 0.f;
	}

	@Override
	public void run() {
		boolean bGameOverFlag = false;
		while (!bGameOverFlag) {
			bGameOverFlag = this.update();
			this.draw();
		}
		// Save user record
		String userName = JOptionPane.showInputDialog("Enter your name");
		System.out.println(userName);
		GameStarter.setOver();
	}

	public Tetromino getNextTetromion() {
		return mNextTetromino;
	}

	public long getCurrScore() {
		return mScore;
	}

	public void getUserInput(KeyEvent e) {
		if (mCurrKeyCode == e.getKeyCode()) {
			return;
		}

		mCurrKeyCode = e.getKeyCode();
		if (mTimer.getPauseState()) {
			mCurrKeyCode &= KeyEvent.VK_U;
		}
		mKeyReactTimeTick = 0.f;
		reactAtContinuousKeyInput();
		reactAtDisContinuousKeyInput();
	}

	public void getUserInputKeyRealease(KeyEvent e) {
		if (mCurrKeyCode == e.getKeyCode()) {
			mCurrKeyCode = -1;
		}
	}

	public GameBoard getGameBoard() {
		return mGameBoard;
	}

	private boolean update() {
		eGameOver gameOverFlag = eGameOver.CONTINUE;

		mTimer.tick();

		float dTime = mTimer.getDeltaTime();
		mAutoDownTimeTick += dTime;
		mKeyReactTimeTick += dTime;

		// React at input
		if (mKeyReactTimeTick >= KEY_REACT_TIME) {
			mKeyReactTimeTick = 0.f;
			reactAtContinuousKeyInput();
		}

		if (mbCollWithFloor == false && mAutoDownTime <= mAutoDownTimeTick) {
			mbCollWithFloor |= mGameBoard.moveTet(eDirection.DOWN);
			mAutoDownTimeTick = 0.0f;
		}

		if (mbCollWithFloor) {
			mGameBoard.getTetromino().setEmptyShapeAndColor();
			int removeColArr[] = new int[4];
			int numRemovedLines = 0;
			numRemovedLines = mGameBoard.findRemovableLines(removeColArr);
			if (numRemovedLines > 0) {
				// Swap to removing tile 1
				for (int i = 0; i < numRemovedLines; i++) {
					int col = removeColArr[i];
					Tile tileRemove = new Tile("tile_remove1");
					for (int row = 0; row < GameBoard.BOARD_ROW; row++) {
						mGameBoard.mBoard[col][row] = tileRemove;
					}
				}
				mScreen.repaint();

				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					assert (false);
				}

				// Swap to removing tile 1
				for (int i = 0; i < numRemovedLines; i++) {
					int col = removeColArr[i];
					Tile tileRemove = new Tile("tile_remove2");
					for (int row = 0; row < GameBoard.BOARD_ROW; row++) {
						mGameBoard.mBoard[col][row] = tileRemove;
					}
				}
				mScreen.repaint();
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					assert (false);
				}

				mGameBoard.removeLines(removeColArr, numRemovedLines);
				mScreen.repaint();
			}
			mGameBoard.fallDownLines(removeColArr, numRemovedLines);
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
			mAutoDownTimeTick = 0.0f;
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

	private void reactAtDisContinuousKeyInput() {
		switch (mCurrKeyCode) {
			// Game Playing
			case KeyEvent.VK_SPACE:
				mGameBoard.rotateTet();
				break;
			case KeyEvent.VK_UP:
				while (!mbCollWithFloor) {
					mbCollWithFloor = mGameBoard.moveTet(eDirection.DOWN);
				}
				break;
			// Pause and UnPause
			case KeyEvent.VK_P:
				mTimer.pause();
				break;
			case KeyEvent.VK_U:
				mTimer.unPause();
				break;
		}
	}

	private void reactAtContinuousKeyInput() {
		switch (mCurrKeyCode) {
			// None
			case -1:
				break;
			case KeyEvent.VK_LEFT:
				mGameBoard.moveTet(eDirection.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				mGameBoard.moveTet(eDirection.RIGHT);
				break;
			case KeyEvent.VK_DOWN:
				mAutoDownTimeTick = 0.f;
				mbCollWithFloor = mGameBoard.moveTet(eDirection.DOWN);
				break;
		}
	}
}
