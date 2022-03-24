package tetrisgame;

import java.awt.Color;
import java.util.Random;

class Tetromino {
	private Position mPosition;
	private Block mShape[][];
	private int mCurrRandNum;
	private static Random mRandom;

	public static final int SHAPE_COL = 4;
	public static final int SHAPE_ROW = 4;
	public static final Position START_POS = new Position(-1, 3);
	public static final int VAR_TETROMINOS = 7;

	private static final Block COLOR_BLOCK_ARR[];

	private static final boolean O = true;
	private static final boolean F = false;

	private static final boolean TET_SHAPES[][][] = {
			{
					{ F, F, F, F },
					{ F, O, O, F },
					{ F, O, O, F },
					{ F, F, F, F }
			},
			{
					{ F, F, F, F },
					{ O, O, O, O },
					{ F, F, F, F },
					{ F, F, F, F }
			},
			{
					{ F, F, F, F },
					{ F, F, O, O },
					{ F, O, O, F },
					{ F, F, F, F }
			},
			{
					{ F, F, F, F },
					{ F, O, O, F },
					{ F, F, O, O },
					{ F, F, F, F }
			},
			{
					{ F, F, F, F },
					{ F, O, O, O },
					{ F, O, F, F },
					{ F, F, F, F }
			},
			{
					{ F, F, F, F },
					{ F, O, O, O },
					{ F, F, F, O },
					{ F, F, F, F }
			},
			{
					{ F, F, F, F },
					{ F, O, O, O },
					{ F, F, O, F },
					{ F, F, F, F }
			},
	};

	static {
		Color yellow = new Color(255, 255, 102);
		Color skyBlue = new Color(51, 255, 255);
		Color lightGreen = new Color(153, 255, 153);
		Color red = new Color(255, 0, 0);
		Color orange = new Color(255, 102, 0);
		Color blue = new Color(0, 0, 255);
		Color purple = new Color(255, 255, 0);
		COLOR_BLOCK_ARR = new Block[VAR_TETROMINOS];
		COLOR_BLOCK_ARR[0] = new Block(yellow);
		COLOR_BLOCK_ARR[1] = new Block(skyBlue);
		COLOR_BLOCK_ARR[2] = new Block(lightGreen);
		COLOR_BLOCK_ARR[3] = new Block(red);
		COLOR_BLOCK_ARR[4] = new Block(orange);
		COLOR_BLOCK_ARR[5] = new Block(blue);
		COLOR_BLOCK_ARR[6] = new Block(purple);
	}

	public Tetromino() {
		mPosition = new Position(START_POS.mCol, START_POS.mRow);
		mShape = new Block[SHAPE_COL][SHAPE_ROW];
		mRandom = new Random();
		mRandom.setSeed(System.currentTimeMillis());
	}

	public void deepCopy(Tetromino source) {
		mPosition.deepCopy(source.mPosition);
		for (int c = 0; c < SHAPE_COL; c++) {
			for (int r = 0; r < SHAPE_ROW; r++) {
				mShape[c][r] = source.mShape[c][r];
			}
		}
		mCurrRandNum = source.mCurrRandNum;
	}

	public Block getBlock(int c, int r) {
		return mShape[c][r];
	}

	public boolean isFilled(int c, int r) {
		if (mShape[c][r] != null) {
			return true;
		} else {
			return false;
		}
	}

	public Color getColor() {
		return COLOR_BLOCK_ARR[mCurrRandNum].getColor();
	}

	public Position getPosition() {
		return mPosition;
	}

	public void setRandomShapeAndColor() {
		int mCurrRandNum = mRandom.nextInt(0, 6);
		for (int col = 0; col < SHAPE_COL; col++) {
			for (int row = 0; row < SHAPE_ROW; row++) {
				if (TET_SHAPES[mCurrRandNum][col][row]) {
					mShape[col][row] = COLOR_BLOCK_ARR[mCurrRandNum];
				} else {
					mShape[col][row] = null;
				}
			}
		}
	}

	public void rotate() {
		Block tmpBlock;
		tmpBlock = mShape[0][0];
		mShape[0][0] = mShape[3][0];
		mShape[3][0] = mShape[3][3];
		mShape[3][3] = mShape[0][3];
		mShape[0][3] = tmpBlock;

		tmpBlock = mShape[0][1];
		mShape[0][1] = mShape[2][0];
		mShape[2][0] = mShape[3][2];
		mShape[3][2] = mShape[1][3];
		mShape[1][3] = tmpBlock;

		tmpBlock = mShape[0][2];
		mShape[0][2] = mShape[1][0];
		mShape[1][0] = mShape[3][1];
		mShape[3][1] = mShape[2][3];
		mShape[2][3] = tmpBlock;

		tmpBlock = mShape[1][1];
		mShape[1][1] = mShape[2][1];
		mShape[2][1] = mShape[2][2];
		mShape[2][2] = mShape[1][2];
		mShape[1][2] = tmpBlock;
	}

	public void rotateBack() {
		Block tmpBlock;
		tmpBlock = mShape[0][0];
		mShape[0][0] = mShape[0][3];
		mShape[0][3] = mShape[3][3];
		mShape[3][3] = mShape[3][0];
		mShape[3][0] = tmpBlock;

		tmpBlock = mShape[0][1];
		mShape[0][1] = mShape[1][3];
		mShape[1][3] = mShape[3][2];
		mShape[3][2] = mShape[2][0];
		mShape[2][0] = tmpBlock;

		tmpBlock = mShape[0][2];
		mShape[0][2] = mShape[2][3];
		mShape[2][3] = mShape[3][1];
		mShape[3][1] = mShape[1][0];
		mShape[2][3] = tmpBlock;

		tmpBlock = mShape[1][1];
		mShape[1][1] = mShape[1][2];
		mShape[1][2] = mShape[2][2];
		mShape[2][2] = mShape[2][1];
		mShape[2][1] = tmpBlock;
	}

	public void move(eDirection dir) {
		switch (dir) {
			case DOWN:
				mPosition.mCol++;
				break;
			case LEFT:
				mPosition.mRow--;
				break;
			case RIGHT:
				mPosition.mRow++;
				break;
			default:
				assert (false);
				break;
		}
	}

	public void moveBack(eDirection dir) {
		switch (dir) {
			case DOWN:
				mPosition.mCol--;
				break;
			case LEFT:
				mPosition.mRow++;
				break;
			case RIGHT:
				mPosition.mRow--;
				break;
			default:
				assert (false);
				break;
		}
	}
}