package tetrisgame;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import gamestarter.GameStarter;
import graphics.screens.GameScreen;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.enumerations.eMsg;
import tetrisgame.parts.Position;
import tetrisgame.parts.Timer;
import tetrisgame.parts.component.RemovingAnim;
import tetrisgame.parts.component.Score;
import tetrisgame.parts.component.Tetromino;
import tetrisgame.parts.component.Tile;

public class TetrisGame implements Runnable {
	protected GameScreen mScreen;

	private volatile boolean mbGameOverFlag;

	public Tile mBoard[][];
	private Tetromino mCurrTetromino;
	private Tetromino mNextTetromino;
	private RemovingAnim mRemoveAnim;
	private Score mScore;

	private boolean mbRemovingFlag;
	private int mRemoveColArr[];
	private int mNumRemovableLines;

	private Timer mTimer;

	private int mCurrKeyCode;
	private float mKeyReactTimeTick;
	private volatile boolean mbKeyReactFlag;
	private static final float KEY_REACT_TIME = 0.1f;

	public static final int BOARD_COL = 20;
	public static final int BOARD_ROW = 10;

	public TetrisGame(GameScreen gameScreen) {
		mScreen = gameScreen;

		mBoard = new Tile[BOARD_COL][BOARD_ROW];
		mCurrTetromino = new Tetromino(this, mBoard);
		mNextTetromino = new Tetromino(this, mBoard);
		mScore = new Score(this);

		// TODO:
		eDifficulty eDiff = eDifficulty.EASY;
		Tetromino.setDifficulty(eDiff);

		mTimer = new Timer();
		mTimer.initialize();

		mRemoveColArr = new int[4];

		mCurrKeyCode = -1;
		mKeyReactTimeTick = 0.f;
		mbKeyReactFlag = true;
	}

	public void broadcast(eMsg msg) {
		if (msg == eMsg.GAME_OVER) {
			mbGameOverFlag = true;
			return;
		}
		if (msg == eMsg.COLL_WITH_FLOOR) {
			boolean bRemovable = true;
			int posCol = mCurrTetromino.getPosition().mCol;
			int startCol = posCol + 3 < BOARD_COL ? posCol + 3 : BOARD_COL - 1;
			posCol = posCol >= 0 ? posCol : 0;
			for (int boardCol = startCol; boardCol >= posCol; boardCol--) {
				for (Tile currBolck : mBoard[boardCol]) {
					if (currBolck == null) {
						bRemovable = false;
						break;
					}
				}
				if (bRemovable) {
					mRemoveColArr[mNumRemovableLines++] = boardCol;
				}
				bRemovable = true;
			}
			if (mNumRemovableLines > 0) {
				mCurrTetromino.react(msg);
				mRemoveAnim = new RemovingAnim(this, mBoard, mRemoveColArr, mNumRemovableLines);
				mbRemovingFlag = true;
				for (int j = 0; j < mNumRemovableLines; j++) {
					this.broadcast(eMsg.LINE_REMOVE);
				}
			} else {
				mCurrTetromino = mNextTetromino;
				mNextTetromino = new Tetromino(this, mBoard);
				if (checkGameOver()) {
					this.broadcast(eMsg.GAME_OVER);
				}
			}
		}
		if (msg == eMsg.REMOVE_ANIM_OVER) {
			mCurrTetromino = mNextTetromino;
			mNextTetromino = new Tetromino(this, mBoard);
			mbRemovingFlag = false;
			mRemoveAnim = null;

			if (checkGameOver()) {
				this.broadcast(eMsg.GAME_OVER);
			}

			removeLines();
			fallDownLines();
			mNumRemovableLines = 0;
		}

		if (mbRemovingFlag) {
			mCurrTetromino.react(msg);
		}
		if (mRemoveAnim != null) {
			mRemoveAnim.react(msg);
		}
		mScore.react(msg);
		for (Tile tileLine[] : mBoard) {
			for (Tile tile : tileLine) {
				if (tile != null) {
					tile.react(msg);
				}
			}
		}
	}

	@Override
	public void run() {
		while (mbGameOverFlag == false) {
			this.update();
			this.draw();
		}
		// Save user record
		String userName = JOptionPane.showInputDialog("Enter your name");
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

		if (mbRemovingFlag == false) {
			mCurrTetromino.update(deltaTime, userInput);
		}
		if (mRemoveAnim != null) {
			mRemoveAnim.update(deltaTime, userInput);
		}
		for (Tile tileLine[] : mBoard) {
			for (Tile tile : tileLine) {
				if (tile != null) {
					tile.update(deltaTime, userInput);
				}
			}
		}
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

	public Tile[][] getGameBoard() {
		return mBoard;
	}

	public Tetromino getCurrTetromino() {
		return mCurrTetromino;
	}

	public Tetromino getNextTetromion() {
		return mNextTetromino;
	}

	public long getCurrScore() {
		return mScore.getScore();
	}

	private void draw() {
		// TODO: refact
		mScreen.repaint();
	}

	private boolean checkGameOver() {
		boolean collResult = collisionTest();
		return collResult;
	}

	public void removeLines() {
		for (int index = 0; index < mNumRemovableLines; index++) {
			int col = mRemoveColArr[index];
			for (int row = 0; row < BOARD_ROW; row++) {
				mBoard[col][row] = null;
			}
		}
	}

	public void fallDownLines() {
		for (int index = 0; index < mNumRemovableLines; index++) {
			int col = mRemoveColArr[index];
			Tile removedLine[] = mBoard[col];
			for (int c = col; c > 0; c--) {
				mBoard[c] = mBoard[c - 1];
			}
			for (int i = index; i < mNumRemovableLines; i++) {
				mRemoveColArr[i]++;
			}
			mBoard[0] = removedLine;
		}
	}

	private boolean collisionTest() {
		boolean re = false;
		Position pos = mCurrTetromino.getPosition();
		for (int c = 0; c < Tetromino.SHAPE_COL; c++) {
			for (int r = 0; r < Tetromino.SHAPE_ROW; r++) {
				if (mCurrTetromino.mShape[c][r] != null) {
					int col = pos.mCol + c;
					int row = pos.mRow + r;
					if (BOARD_COL <= col || col < 0 || row < 0 || BOARD_ROW <= row ||
							mBoard[col][row] != null) {
						re = true;
						return re;
					}

				}
			}
		}
		return re;
	}
}
