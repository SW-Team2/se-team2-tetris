package tetrisgame;

import java.awt.Color;
import java.util.Random;

class Tetromino {
	private Position mPosition;
	private boolean mShape[][];
	private Color mColor;

	private static Random mRandom;

	public static final int SHAPE_COL = 4;
	public static final int SHAPE_ROW = 4;

	public static final Position START_POS = new Position(-1, 3);
	public static final int VAR_TETROMINOS = 7;

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

	public Tetromino() {
		mPosition = new Position(START_POS.mCol, START_POS.mRow);
		mShape = new boolean[SHAPE_COL][SHAPE_ROW];
		mRandom = new Random();
		mRandom.setSeed(System.currentTimeMillis());
	}

	public void setRandomShapeAndColor() {
		int randNum = mRandom.nextInt(0, 6);
		copyShape(mShape, TET_SHAPES[randNum]);
	}

	public boolean isFilled(int c, int r) {
		return mShape[c][r];
	}

	public Color getColor() {
		return mColor;
	}

	public Position getPosition() {
		return mPosition;
	}

	public void rotate() {
		boolean t;

		t = mShape[0][0];
		mShape[0][0] = mShape[3][0];
		mShape[3][0] = mShape[3][3];
		mShape[3][3] = mShape[0][3];
		mShape[0][3] = t;

		t = mShape[0][1];
		mShape[0][1] = mShape[2][0];
		mShape[2][0] = mShape[3][2];
		mShape[3][2] = mShape[1][3];
		mShape[1][3] = t;

		t = mShape[0][2];
		mShape[0][2] = mShape[1][0];
		mShape[1][0] = mShape[3][1];
		mShape[3][1] = mShape[2][3];
		mShape[2][3] = t;

		t = mShape[1][1];
		mShape[1][1] = mShape[2][1];
		mShape[2][1] = mShape[2][2];
		mShape[2][2] = mShape[1][2];
		mShape[1][2] = t;
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

	public void deepCopy(Tetromino in) {
		mPosition.deepCopy(in.mPosition);
		for (int c = 0; c < Tetromino.SHAPE_COL; c++) {
			for (int r = 0; r < Tetromino.SHAPE_COL; r++) {
				mShape[c][r] = in.mShape[c][r];
			}
		}
		mColor = in.mColor;
	}

	private static void copyShape(boolean dest[][], boolean source[][]) {
		for (int c = 0; c < SHAPE_COL; c++) {
			for (int r = 0; r < SHAPE_ROW; r++) {
				dest[c][r] = source[c][r];
			}
		}
	}
}