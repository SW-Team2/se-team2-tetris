package tetrisgame;

class GameBoard {
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

	public int removeLine() {
		int removeLines = 0;
		boolean bRemovable = true;
		for (int i = BOARD_COL - 1; i >= 0; i--) {
			for (int j = 0; j < BOARD_ROW; j++) {
				if (mBoard[i][j] == null) {
					bRemovable = false;
					break;
				}
			}
			if (bRemovable) {
				for (int c = i; c > 0; c--) {
					for (int r = 0; r < BOARD_ROW; r++) {
						mBoard[c][r] = mBoard[c - 1][r];
					}
				}
				for (int r = 0; r < BOARD_ROW; r++) {
					mBoard[0][r] = null;
				}
				removeLines++;
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

	//
	// FOR DRAWING
	// TEMP
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
