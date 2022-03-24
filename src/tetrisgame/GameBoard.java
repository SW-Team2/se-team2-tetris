package tetrisgame;

import java.awt.Color;

class GameBoard {
	private Block mBoard[][];
	private Tetromino mTetromino;

	public static final int BOARD_COL = 20;
	public static final int BOARD_ROW = 10;
	//
	// Block for mapping Tetromino on Board
	//
	static final Block BLOCK = new Block(true);

	public eResult initialize() {
		eResult re = eResult.SUCCESS;

		mBoard = new Block[BOARD_COL][BOARD_ROW];
		for (int c = 0; c < BOARD_COL; c++) {
			for (int r = 0; r < BOARD_ROW; r++) {
				mBoard[c][r] = new Block();
			}
		}
		mTetromino = new Tetromino();
		return re;
	}

	public void setTetromino(Tetromino in) {
		mTetromino.deepCopy(in);
	}

	public eGameOver setTetrominoAndCheckGameOver(Tetromino in) {
		eGameOver gameOver = eGameOver.CONTINUE;
		setTetromino(in);
		eCollResult collResult = collisionTest();
		if (collResult == eCollResult.Y) {
			gameOver = eGameOver.OVER;
		}
		return gameOver;
	}

	public int removeLine() {
		int removeLines = 0;
		BLOCK.setFill(false);
		BLOCK.setColor(Color.black);
		boolean bRemovable = true;
		for (int i = BOARD_COL - 1; i >= 0; i--) {
			for (int j = 0; j < BOARD_ROW; j++) {
				if (!mBoard[i][j].isFilled()) {
					bRemovable = false;
					break;
				}
			}
			if (bRemovable) {
				for (int c = i; c > 0; c--) {
					for (int r = 0; r < BOARD_ROW; r++) {
						mBoard[c][r].deepCopy(mBoard[c - 1][r]);
					}
				}
				for (int r = 0; r < BOARD_ROW; r++) {
					mBoard[0][r].deepCopy(BLOCK);
				}
				i++;
			}
			bRemovable = true;
		}
		return removeLines;
	}

	public boolean moveTet(eDirection dir) {
		boolean bCollWithFloor = false;

		mTetromino.move(dir);
		eCollResult collResult = collisionTest();
		switch (collResult) {
			case Y:
				mTetromino.moveBack(dir);
				if (dir == eDirection.DOWN) {
					//
					// Map current Tetromino on Board
					//
					Position pos = mTetromino.getPosition();
					BLOCK.setFill(true);
					BLOCK.setColor(mTetromino.getColor());
					for (int c = 0; c < Tetromino.SHAPE_COL; c++) {
						for (int r = 0; r < Tetromino.SHAPE_ROW; r++) {
							if (mTetromino.isFilled(c, r)) {
								int col = pos.mCol + c;
								int row = pos.mRow + r;
								mBoard[col][row].deepCopy(BLOCK);
							}
						}
					}
					bCollWithFloor = true;
				}
				break;

			case N:
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
			case Y:
				mTetromino.rotate();
				mTetromino.rotate();
				mTetromino.rotate();
				break;

			case N:
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
		eCollResult re = eCollResult.N;

		Position pos = mTetromino.getPosition();
		for (int c = 0; c < Tetromino.SHAPE_COL; c++) {
			for (int r = 0; r < Tetromino.SHAPE_ROW; r++) {
				if (mTetromino.isFilled(c, r)) {
					int col = pos.mCol + c;
					int row = pos.mRow + r;
					if (BOARD_COL <= col || row < 0 || BOARD_ROW <= row ||
							mBoard[col][row].isFilled()) {
						re = eCollResult.Y;
						return re;
					}

				}
			}
		}
		return re;
	}
}
