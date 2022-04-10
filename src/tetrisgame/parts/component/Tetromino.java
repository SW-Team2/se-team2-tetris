package tetrisgame.parts.component;

import java.awt.event.KeyEvent;
import java.util.Random;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eCollResult;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.enumerations.eDirection;
import tetrisgame.enumerations.eMsg;
import tetrisgame.parts.Position;

public class Tetromino extends IGameComponent {
	private GameBoard mPubBoard;

	private Position mPosition;
	public Tile mShape[][];
	private int mShapeNColorIndex;
	private int mCurrRemoveLineCount;

	private float mAutoDownTime;
	private float mAutoDownTimeTick;
	private static final float START_AUTO_DOWN_TIME = 1.0f;
	private static float GAME_SPEED_ACCEL_UNIT = 0.1f;

	private static Random mRandom;
	private static int mProbWeightArr[];
	private static int mSumProbWeight;
	private static final int DEFAULT_WEIGHT = 100;

	public static final int SHAPE_COL = 4;
	public static final int SHAPE_ROW = 4;
	public static final Position START_POS = new Position(-1, 3);
	public static final int VAR_TETROMINOS = 7;
	private static final String TILE_NAME_ARR[];

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
		mRandom = new Random();
		mRandom.setSeed(System.currentTimeMillis());
		TILE_NAME_ARR = new String[VAR_TETROMINOS];
		TILE_NAME_ARR[0] = "tile_yellow";
		TILE_NAME_ARR[1] = "tile_skyblue";
		TILE_NAME_ARR[2] = "tile_lightgreen";
		TILE_NAME_ARR[3] = "tile_red";
		TILE_NAME_ARR[4] = "tile_orange";
		TILE_NAME_ARR[5] = "tile_blue";
		TILE_NAME_ARR[6] = "tile_purple";

		mProbWeightArr = new int[VAR_TETROMINOS];
		for (int i = 0; i < VAR_TETROMINOS; i++) {
			mProbWeightArr[i] = DEFAULT_WEIGHT;
		}
		mSumProbWeight = DEFAULT_WEIGHT * VAR_TETROMINOS;
	}

	public Tetromino(TetrisGame game, GameBoard gb) {
		super(game);
		mPubBoard = gb;
		mPosition = new Position(START_POS.mCol, START_POS.mRow);
		mShape = new Tile[SHAPE_COL][SHAPE_ROW];
		mAutoDownTime = START_AUTO_DOWN_TIME;
		this.setRandomShapeAndColor();
	}

	@Override
	public void update(float deltaTime, int userInput) {
		boolean bCollWithFloor = false;
		switch (userInput) {
			case KeyEvent.VK_SPACE:
				rotateOrIgnore();
				break;
			case KeyEvent.VK_DOWN:
				bCollWithFloor = moveOrIgnore(eDirection.DOWN);
				mAutoDownTimeTick = 0.f;
				break;
			case KeyEvent.VK_RIGHT:
				bCollWithFloor = moveOrIgnore(eDirection.RIGHT);
				break;
			case KeyEvent.VK_LEFT:
				bCollWithFloor = moveOrIgnore(eDirection.LEFT);
				break;
			case KeyEvent.VK_UP:
				fallToFloor();
				bCollWithFloor = true;
				break;
		}

		if (mCurrRemoveLineCount >= 10) {
			mAutoDownTime *= (1.0f - GAME_SPEED_ACCEL_UNIT);
			mCurrRemoveLineCount -= 10;
		}

		mAutoDownTimeTick += deltaTime;
		if (mAutoDownTimeTick >= mAutoDownTime) {
			mAutoDownTimeTick = 0.f;
			bCollWithFloor = moveOrIgnore(eDirection.DOWN);
		}

		if (bCollWithFloor) {
			mPubGame.broadcast(eMsg.COLL_WITH_FLOOR);
		}
	}

	@Override
	public void react(eMsg msg) {
		switch (msg) {
			case COLL_WITH_FLOOR:
				// Set empty
				for (int col = 0; col < SHAPE_COL; col++) {
					for (int row = 0; row < SHAPE_ROW; row++) {
						mShape[col][row] = null;
					}
				}
				break;
			case LINE_REMOVE:
				mCurrRemoveLineCount++;
				break;
		}
	}

	public static void setDifficulty(eDifficulty eDiff) {
		// Set shape generation probabilty
		int addWeight = (int) (DEFAULT_WEIGHT * 0.2f);
		int subWeight = addWeight / 6;
		switch (eDiff) {
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

		// Set auto down time acceleration unit
		switch (eDiff) {
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
	}

	public Position getPosition() {
		return mPosition;
	}

	protected void setRandomShapeAndColor() {
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
					mShape[col][row] = new Tile(mPubGame, mPubBoard, TILE_NAME_ARR[mShapeNColorIndex]);
				} else {
					mShape[col][row] = null;
				}
			}
		}
	}

	private void rotateOrIgnore() {
		this.rotate();
		eCollResult collResult = collisionTest();
		switch (collResult) {
			case COLL:
				this.rotateBack();
				break;
			case NOT_COLL:
				break;
			default:
				assert (false);
				break;
		}
	}

	private boolean moveOrIgnore(eDirection dir) {
		boolean bCollWithFloor = false;

		this.move(dir);
		eCollResult collResult = collisionTest();
		switch (collResult) {
			case COLL:
				this.moveBack(dir);
				if (dir == eDirection.DOWN) {
					//
					// Map current Tetromino on Board
					//
					Position pos = mPosition;
					for (int c = 0; c < Tetromino.SHAPE_COL; c++) {
						for (int r = 0; r < Tetromino.SHAPE_ROW; r++) {
							if (mShape[c][r] != null) {
								int col = pos.mCol + c;
								int row = pos.mRow + r;
								mPubBoard.mBoard[col][row] = mShape[c][r];
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

	private void fallToFloor() {
		Position floorPos = getFloorPos();
		mPosition = floorPos;
	}

	private Position getFloorPos() {
		int saveCol = mPosition.mCol;
		int saveRow = mPosition.mRow;

		boolean bCollWithFloor = false;
		while (!bCollWithFloor) {
			bCollWithFloor = moveOrIgnore(eDirection.DOWN);
		}

		Position result = new Position(mPosition);
		mPosition.mCol = saveCol;
		mPosition.mRow = saveRow;
		return result;
	}

	private void rotate() {
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

	private void rotateBack() {
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

	private void move(eDirection dir) {
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

	private void moveBack(eDirection dir) {
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

	private eCollResult collisionTest() {
		eCollResult re = eCollResult.NOT_COLL;

		Position pos = mPosition;
		for (int c = 0; c < Tetromino.SHAPE_COL; c++) {
			for (int r = 0; r < Tetromino.SHAPE_ROW; r++) {
				if (mShape[c][r] != null) {
					int col = pos.mCol + c;
					int row = pos.mRow + r;
					if (GameBoard.BOARD_COL <= col || col < 0 || row < 0 || GameBoard.BOARD_ROW <= row ||
							mPubBoard.mBoard[col][row] != null) {
						re = eCollResult.COLL;
						return re;
					}

				}
			}
		}
		return re;
	}
}