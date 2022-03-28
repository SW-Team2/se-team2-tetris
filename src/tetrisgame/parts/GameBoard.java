package tetrisgame.parts;

import tetrisgame.enumerations.eCollResult;
import tetrisgame.enumerations.eDirection;
import tetrisgame.enumerations.eGameOver;

public class GameBoard {
	private Block mBoard[][];
	private Tetromino mTetromino;

	public static final int BOARD_COL = 20;
	public static final int BOARD_ROW = 10;

	public GameBoard() {
		mBoard = new Block[BOARD_COL][BOARD_ROW];
		mTetromino = new Tetromino();
	}

	public void setTetromino(Tetromino tetIn) {
		mTetromino.deepCopy(tetIn);
	}

	public eGameOver checkGameOver() {
		eGameOver gameOver = eGameOver.CONTINUE;
		eCollResult collResult = collisionTest();
		if (collResult == eCollResult.COLL) {
			gameOver = eGameOver.OVER;
		}
		return gameOver;
	}

	public int findRemovableLines(int outColArr[]) {
		int size = 0;
		boolean bRemovable = true;
		int posCol = mTetromino.getPosition().mCol;
		int startCol = posCol + 3 < BOARD_COL ? posCol + 3 : BOARD_COL - 1;
		posCol = posCol >= 0 ? posCol : 0;
		for (int boardCol = startCol; boardCol >= posCol; boardCol--) {
			for (Block currBolck : mBoard[boardCol]) {
				if (currBolck == null) {
					bRemovable = false;
					break;
				}
			}
			if (bRemovable) {
				outColArr[size] = boardCol;
				size++;
			}
			bRemovable = true;
		}
		return size;
	}

	public void removeLines(int colArr[], int size) {
		for (int index = 0; index < size; index++) {
			int col = colArr[index];
			for (int row = 0; row < BOARD_ROW; row++) {
				mBoard[col][row] = null;
			}
			Block removedLine[] = mBoard[col];
			for (int c = col; c > 0; c--) {
				mBoard[c] = mBoard[c - 1];
			}
			for (int i = index; i < size; i++) {
				colArr[i]++;
			}
			mBoard[0] = removedLine;
		}
	}

	public boolean moveTet(eDirection dir) {
		boolean bCollWithFloor = false;

		mTetromino.move(dir);
		eCollResult collResult = collisionTest();
		switch (collResult) {
			case COLL:
				mTetromino.moveBack(dir);
				if (dir == eDirection.DOWN) {
					//
					// Map current Tetromino on Board
					//
					Position pos = mTetromino.getPosition();
					for (int c = 0; c < Tetromino.SHAPE_COL; c++) {
						for (int r = 0; r < Tetromino.SHAPE_ROW; r++) {
							if (mTetromino.isFilled(c, r)) {
								int col = pos.mCol + c;
								int row = pos.mRow + r;
								mBoard[col][row] = mTetromino.getBlock(c, r);
							}
						}
					}
					bCollWithFloor = true;
				}
				break;
			case NOT_COLL:
				break;
			default:
				assert (false);
				break;
		}
		return bCollWithFloor;
	}

	public void rotateTet() {
		mTetromino.rotate();
		eCollResult collResult = collisionTest();
		switch (collResult) {
			case COLL:
				mTetromino.rotateBack();
				break;
			case NOT_COLL:
				break;
			default:
				assert (false);
				break;
		}
	}

	public Block getBlock(int c, int r) {
		return mBoard[c][r];
	}

	public Tetromino getTetromino() {
		return mTetromino;
	}

	private eCollResult collisionTest() {
		eCollResult re = eCollResult.NOT_COLL;

		Position pos = mTetromino.getPosition();
		for (int c = 0; c < Tetromino.SHAPE_COL; c++) {
			for (int r = 0; r < Tetromino.SHAPE_ROW; r++) {
				if (mTetromino.isFilled(c, r)) {
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
