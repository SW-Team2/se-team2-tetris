package tetrisgame.parts.component;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eCollResult;
import tetrisgame.enumerations.eMsg;
import tetrisgame.parts.Position;

public class GameBoard extends IGameComponent {
	public Tile mBoard[][];
	private Tetromino mCurrTetromino;
	private Tetromino mNextTetromino;
	private RemovingAnim mRemoveAnim;
	private boolean mbRemovingFlag;

	private int mRemoveColArr[];
	private int mNumRemovableLines;

	public static final int BOARD_COL = 20;
	public static final int BOARD_ROW = 10;

	public GameBoard(TetrisGame g) {
		super(g);
		mBoard = new Tile[BOARD_COL][BOARD_ROW];

		mCurrTetromino = new Tetromino(mPubGame, this);
		mNextTetromino = new Tetromino(mPubGame, this);

		mRemoveColArr = new int[4];
	}

	@Override
	public void update(float deltaTime, int userInput) {
		if (mbRemovingFlag == false) {
			mCurrTetromino.update(deltaTime, userInput);
		}
		if (mRemoveAnim != null) {
			mRemoveAnim.update(deltaTime, userInput);
		}
	}

	@Override
	public void react(eMsg msg) {
		mCurrTetromino.react(msg);
		switch (msg) {
			case COLL_WITH_FLOOR:
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
					mRemoveAnim = new RemovingAnim(mPubGame, this, mRemoveColArr, mNumRemovableLines);
					mbRemovingFlag = true;
					for (int j = 0; j < mNumRemovableLines; j++) {
						mPubGame.broadcast(eMsg.LINE_REMOVE);
					}
				} else {
					mCurrTetromino = mNextTetromino;
					mNextTetromino = new Tetromino(mPubGame, this);
					if (checkGameOver()) {
						mPubGame.broadcast(eMsg.GAME_OVER);
					}
				}
				break;
			case REMOVE_ANIM_OVER:
				mCurrTetromino = mNextTetromino;
				mNextTetromino = new Tetromino(mPubGame, this);
				mbRemovingFlag = false;
				mRemoveAnim = null;

				if (checkGameOver()) {
					mPubGame.broadcast(eMsg.GAME_OVER);
				}

				removeLines();
				fallDownLines();
				mNumRemovableLines = 0;
				break;
		}

	}

	public void clear() {
		for (int c = 0; c < BOARD_COL; c++) {
			for (int r = 0; r < BOARD_ROW; r++) {
				mBoard[c][r] = null;
			}
		}
	}

	public boolean checkGameOver() {
		eCollResult collResult = collisionTest();
		if (collResult == eCollResult.COLL) {
			return true;
		}
		return false;
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

	public Tetromino getCurrTetromino() {
		return mCurrTetromino;
	}

	public Tetromino getNextTetromion() {
		return mNextTetromino;
	}

	private eCollResult collisionTest() {
		eCollResult re = eCollResult.NOT_COLL;

		Position pos = mCurrTetromino.getPosition();
		for (int c = 0; c < Tetromino.SHAPE_COL; c++) {
			for (int r = 0; r < Tetromino.SHAPE_ROW; r++) {
				if (mCurrTetromino.mShape[c][r] != null) {
					int col = pos.mCol + c;
					int row = pos.mRow + r;
					if (BOARD_COL <= col || col < 0 || row < 0 || BOARD_ROW <= row ||
							mBoard[col][row] != null) {
						re = eCollResult.COLL;
						return re;
					}

				}
			}
		}
		return re;
	}
}
