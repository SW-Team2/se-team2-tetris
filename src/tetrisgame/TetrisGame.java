package tetrisgame;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JOptionPane;

import data.score.Difficulty;
import data.score.Mode;
import data.score.ScoreBoardData;
import gamestarter.GameStarter;
import graphics.screens.GameScreen;
import tetrisgame.component.animation.BounusScoreItemAnim;
import tetrisgame.component.animation.IAnim;
import tetrisgame.component.animation.LineEraserItemAnim;
import tetrisgame.component.animation.LineRemovingAnim;
import tetrisgame.component.animation.RemovingAllItemAnim;
import tetrisgame.component.animation.SlowingItemAnim;
import tetrisgame.component.animation.WeightItemAnim;
import tetrisgame.component.score.Score;
import tetrisgame.component.tetromino.BonusScoreItemTetroino;
import tetrisgame.component.tetromino.LineEraserItemTetromino;
import tetrisgame.component.tetromino.RemovingAllItemTetromino;
import tetrisgame.component.tetromino.SlowingItemTetromino;
import tetrisgame.component.tetromino.Tetromino;
import tetrisgame.component.tetromino.WeightItemTetromino;
import tetrisgame.component.tile.Tile;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.enumerations.eMsg;
import tetrisgame.util.Position;
import tetrisgame.util.Timer;

public class TetrisGame implements Runnable {
	protected GameScreen mScreen;

	private volatile boolean mbGameOverFlag;
	private boolean mbPauseFlag;
	private int mPausePanBtnIndex;
	private boolean mbItemMode;
	private eDifficulty meDifficulty;
	private static final int VAR_ITEMS = 5;

	public Tile mBoard[][];
	protected Tetromino mCurrTetromino;
	protected Tetromino mNextTetromino;
	private IAnim mFocusAnim;
	protected Score mScore;

	private int mRemoveColArr[];
	private int mNumRemovableLines;

	private Timer mTimer;

	private int mCurrKeyCode;
	private float mKeyReactTimeTick;
	private boolean mbKeyReactFlag;
	private static final float KEY_REACT_TIME = 0.1f;

	public static final int BOARD_COL = 20;
	public static final int BOARD_ROW = 10;

	public TetrisGame(GameScreen gameScreen, boolean bItemMode, eDifficulty diff) {
		mScreen = gameScreen;
		mbItemMode = bItemMode;
		mBoard = new Tile[BOARD_COL][BOARD_ROW];

		meDifficulty = diff;
		Tetromino.setDifficulty(meDifficulty);
		mCurrTetromino = new Tetromino(this, mBoard);
		mNextTetromino = new Tetromino(this, mBoard);
		mScore = new Score(this);

		mTimer = new Timer();
		mTimer.initialize();

		mRemoveColArr = new int[4];

		mCurrKeyCode = -1;
		mKeyReactTimeTick = 0.f;
		mbKeyReactFlag = true;

		mbPauseFlag = false;
		mPausePanBtnIndex = 0;
	}

	public void broadcast(eMsg msg) {
		// Send msg
		// TODO: Convert to IGameComponent array
		mCurrTetromino.react(msg);
		if (mFocusAnim != null) {
			mFocusAnim.react(msg);
		}
		mScore.react(msg);
		for (Tile tileLine[] : mBoard) {
			for (Tile tile : tileLine) {
				if (tile != null) {
					tile.react(msg);
				}
			}
		}
		switch (msg) {
			case GAME_OVER:
				mbGameOverFlag = true;
				return;
			case COLL_WITH_FLOOR:
				findRemovableLines();
				broadRemoveLine();
				break;
			case WEIGHT_ITEM_COLL_WITH_FLOOR:
				mFocusAnim = new WeightItemAnim(this, mBoard, mCurrTetromino.getPosition());
				break;
			case LINEERASER_ITEM_COLL_WITH_FLOOR:
				mFocusAnim = new LineEraserItemAnim(this, mBoard, mCurrTetromino);
				break;
			case SLOWING_ITEM_ERASED:
				mFocusAnim = new SlowingItemAnim(this);
				break;
			case BONUSSCORE_ITEM_ERASED:
				mFocusAnim = new BounusScoreItemAnim(this);
				break;
			case REMOVINGALL_ITEM_ERASED:
				mFocusAnim = new RemovingAllItemAnim(this, mBoard);
				break;
			case FOCUS_ANIM_OVER:
				mCurrTetromino = mNextTetromino;
				mNextTetromino = new Tetromino(this, mBoard);
				mFocusAnim = null;
				if (checkGameOver()) {
					this.broadcast(eMsg.GAME_OVER);
				}
				removeLines();
				fallDownLines();
				mNumRemovableLines = 0;
				break;
			case ERASE_10xN_LINES:
				if (mbItemMode) {
					Random random = new Random();
					int itemIndex = random.nextInt(VAR_ITEMS);
					switch (itemIndex) {
						case 0:
							mNextTetromino = new WeightItemTetromino(this, mBoard);
							break;
						case 1:
							mNextTetromino = new LineEraserItemTetromino(this, mBoard);
							break;
						case 2:
							mNextTetromino = new SlowingItemTetromino(this, mBoard);
							break;
						case 3:
							mNextTetromino = new RemovingAllItemTetromino(this, mBoard);
							break;
						case 4:
							mNextTetromino = new BonusScoreItemTetroino(this, mBoard);
							break;
					}
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void run() {
		while (mbGameOverFlag == false) {
			this.update();
			this.draw();
		}
		// Save user record
		String userName;
		do {
			userName = JOptionPane.showInputDialog("Enter your name");
		} while (userName.equals(""));

		if (mbItemMode == true) {
			ScoreBoardData.getInstance()
					.addItemModeScore(
							new data.score.Score(userName, mScore.getScore(), Difficulty.NORMAL.getValue(),
									Mode.ITEM_MODE.getValue()));
		} else {
			switch (meDifficulty) {
				case EASY:
					ScoreBoardData.getInstance()
							.addDefaultModeScore(
									new data.score.Score(userName, mScore.getScore(), Difficulty.EASY.getValue(),
											Mode.DEFAULT.getValue()));
					break;
				case NORMAL:
					ScoreBoardData.getInstance()
							.addDefaultModeScore(
									new data.score.Score(userName, mScore.getScore(), Difficulty.NORMAL.getValue(),
											Mode.DEFAULT.getValue()));
					break;
				case HARD:
					ScoreBoardData.getInstance()
							.addDefaultModeScore(
									new data.score.Score(userName, mScore.getScore(), Difficulty.HARD.getValue(),
											Mode.DEFAULT.getValue()));
					break;
				default:
					assert (false) : "Undefined eDifficulty";
					break;
			}
		}
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

		mCurrTetromino.update(deltaTime, userInput);
		if (mFocusAnim != null) {
			mFocusAnim.update(deltaTime, userInput);
		}
		mScore.update(deltaTime, userInput);
		for (Tile tileLine[] : mBoard) {
			for (Tile tile : tileLine) {
				if (tile != null) {
					tile.update(deltaTime, userInput);
				}
			}
		}
	}

	public synchronized void getUserInput(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (mbPauseFlag == false) {
				mbPauseFlag = true;
				mTimer.pause();
			}
			mCurrKeyCode = -1;
			return;
		}
		if (mbPauseFlag == true) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				mPausePanBtnIndex = mPausePanBtnIndex == 1 ? mPausePanBtnIndex : mPausePanBtnIndex + 1;
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				mPausePanBtnIndex = mPausePanBtnIndex == 0 ? mPausePanBtnIndex : mPausePanBtnIndex - 1;
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (mPausePanBtnIndex == 0) {
					mTimer.unPause();
					mbPauseFlag = false;
				} else if (mPausePanBtnIndex == 1) {
					this.broadcast(eMsg.GAME_OVER);
				}
			}
		}

		if (mCurrKeyCode == e.getKeyCode()) {
			return;
		}

		mCurrKeyCode = e.getKeyCode();
		mKeyReactTimeTick = KEY_REACT_TIME;
		mbKeyReactFlag = false;
		if (mTimer.getPauseState()) {
			mCurrKeyCode &= KeyEvent.VK_U;
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

	public int getPauseBtnIndex() {
		return mPausePanBtnIndex;
	}

	public boolean getPauseFlag() {
		return mbPauseFlag;
	}

	private void draw() {
		mScreen.repaint();
	}

	private boolean checkGameOver() {
		boolean collResult = collisionTest();
		return collResult;
	}

	private void findRemovableLines() {
		boolean bRemovable = true;
		mNumRemovableLines = 0;
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
	}

	private void removeLines() {
		for (int index = 0; index < mNumRemovableLines; index++) {
			int col = mRemoveColArr[index];
			for (int row = 0; row < BOARD_ROW; row++) {
				mBoard[col][row].eraseAct();
				mBoard[col][row] = null;
			}
		}
	}

	private void fallDownLines() {
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

	private void broadRemoveLine() {
		if (mNumRemovableLines > 0) {
			mFocusAnim = new LineRemovingAnim(this, mBoard);
			for (int j = 0; j < mNumRemovableLines; j++) {
				int col = mRemoveColArr[j];
				switch (col) {
					case 0:
						this.broadcast(eMsg.LINE_REMOVE_0);
						break;
					case 1:
						this.broadcast(eMsg.LINE_REMOVE_1);
						break;
					case 2:
						this.broadcast(eMsg.LINE_REMOVE_2);
						break;
					case 3:
						this.broadcast(eMsg.LINE_REMOVE_3);
						break;
					case 4:
						this.broadcast(eMsg.LINE_REMOVE_4);
						break;
					case 5:
						this.broadcast(eMsg.LINE_REMOVE_5);
						break;
					case 6:
						this.broadcast(eMsg.LINE_REMOVE_6);
						break;
					case 7:
						this.broadcast(eMsg.LINE_REMOVE_7);
						break;
					case 8:
						this.broadcast(eMsg.LINE_REMOVE_8);
						break;
					case 9:
						this.broadcast(eMsg.LINE_REMOVE_9);
						break;
					case 10:
						this.broadcast(eMsg.LINE_REMOVE_10);
						break;
					case 11:
						this.broadcast(eMsg.LINE_REMOVE_11);
						break;
					case 12:
						this.broadcast(eMsg.LINE_REMOVE_12);
						break;
					case 13:
						this.broadcast(eMsg.LINE_REMOVE_13);
						break;
					case 14:
						this.broadcast(eMsg.LINE_REMOVE_14);
						break;
					case 15:
						this.broadcast(eMsg.LINE_REMOVE_15);
						break;
					case 16:
						this.broadcast(eMsg.LINE_REMOVE_16);
						break;
					case 17:
						this.broadcast(eMsg.LINE_REMOVE_17);
						break;
					case 18:
						this.broadcast(eMsg.LINE_REMOVE_18);
						break;
					case 19:
						this.broadcast(eMsg.LINE_REMOVE_19);
						break;
				}
			}
		} else {
			mCurrTetromino = mNextTetromino;
			mNextTetromino = new Tetromino(this, mBoard);
			if (checkGameOver()) {
				this.broadcast(eMsg.GAME_OVER);
			}
		}
	}
}
