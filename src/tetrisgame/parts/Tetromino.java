package tetrisgame.parts;

import java.util.Random;

import tetrisgame.enumerations.eDifficulty;
import tetrisgame.enumerations.eDirection;

public class Tetromino {
	private Position mPosition;
	public Tile mShape[][];
	private int mShapeNColorIndex;
	private static Random mRandom;

	private static int mProbWeightArr[];
	private static int mSumProbWeight;
	private static final int DEFAULT_WEIGHT = 100;

	public static final int SHAPE_COL = 4;
	public static final int SHAPE_ROW = 4;
	public static final Position START_POS = new Position(-1, 3);
	public static final int VAR_TETROMINOS = 7;

	private static final Tile COLOR_BLOCK_ARR[];

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
			{
					{ F, F, F, F },
					{ F, F, F, F },
					{ F, F, F, F },
					{ F, F, F, F }
			},
	};

	static {
		COLOR_BLOCK_ARR = new Tile[VAR_TETROMINOS];
		COLOR_BLOCK_ARR[0] = new Tile("tile_yellow");
		COLOR_BLOCK_ARR[1] = new Tile("tile_skyblue");
		COLOR_BLOCK_ARR[2] = new Tile("tile_lightgreen");
		COLOR_BLOCK_ARR[3] = new Tile("tile_red");
		COLOR_BLOCK_ARR[4] = new Tile("tile_orange");
		COLOR_BLOCK_ARR[5] = new Tile("tile_blue");
		COLOR_BLOCK_ARR[6] = new Tile("tile_purple");

		mProbWeightArr = new int[VAR_TETROMINOS];
		for (int i = 0; i < VAR_TETROMINOS; i++) {
			mProbWeightArr[i] = DEFAULT_WEIGHT;
		}
		mSumProbWeight = DEFAULT_WEIGHT * VAR_TETROMINOS;
	}

	public Tetromino() {
		mPosition = new Position(START_POS.mCol, START_POS.mRow);
		mShape = new Tile[SHAPE_COL][SHAPE_ROW];
		mRandom = new Random();
		mRandom.setSeed(System.currentTimeMillis());
	}

	public static void setDifficulty(eDifficulty diff) {
		int addWeight = (int) (DEFAULT_WEIGHT * 0.2f);
		int subWeight = addWeight / 6;
		switch (diff) {
			case EASY:
				mProbWeightArr[0] -= subWeight;
				mProbWeightArr[1] += addWeight;
				mProbWeightArr[2] -= subWeight;
				mProbWeightArr[3] -= subWeight;
				mProbWeightArr[4] -= subWeight;
				mProbWeightArr[5] -= subWeight;
				mProbWeightArr[6] -= subWeight;
				break;
			case NORMAL:
				break;
			case HARD:
				mProbWeightArr[0] += subWeight;
				mProbWeightArr[1] -= addWeight;
				mProbWeightArr[2] += subWeight;
				mProbWeightArr[3] += subWeight;
				mProbWeightArr[4] += subWeight;
				mProbWeightArr[5] += subWeight;
				mProbWeightArr[6] += subWeight;
				break;
			default:
				assert (false);
				break;
		}
		mSumProbWeight = 0;
		for (int pw : mProbWeightArr) {
			mSumProbWeight += pw;
		}
	}

	public void deepCopy(Tetromino source) {
		mPosition.deepCopy(source.mPosition);
		for (int col = 0; col < SHAPE_COL; col++) {
			for (int row = 0; row < SHAPE_ROW; row++) {
				mShape[col][row] = source.mShape[col][row];
			}
		}
		mShapeNColorIndex = source.mShapeNColorIndex;
	}

	public boolean isFilled(int c, int r) {
		if (mShape[c][r] != null) {
			return true;
		} else {
			return false;
		}
	}

	public Position getPosition() {
		return mPosition;
	}

	public void setEmptyShapeAndColor() {
		for (int col = 0; col < SHAPE_COL; col++) {
			for (int row = 0; row < SHAPE_ROW; row++) {
				mShape[col][row] = null;
			}
		}
	}

	public void setRandomShapeAndColor() {
		int randomNum = mRandom.nextInt(mSumProbWeight);

		int weight = 0;
		for (int index = 0; index < VAR_TETROMINOS; index++) {
			weight += mProbWeightArr[index];
			if (randomNum < weight) {
				mShapeNColorIndex = index;
				break;
			}
		}

		for (int col = 0; col < SHAPE_COL; col++) {
			for (int row = 0; row < SHAPE_ROW; row++) {
				if (TET_SHAPES[mShapeNColorIndex][col][row]) {
					mShape[col][row] = COLOR_BLOCK_ARR[mShapeNColorIndex];
				} else {
					mShape[col][row] = null;
				}
			}
		}
	}

	public void rotate() {
		Tile tmpBlock;
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
		Tile tmpBlock;
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
		mShape[1][0] = tmpBlock;

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