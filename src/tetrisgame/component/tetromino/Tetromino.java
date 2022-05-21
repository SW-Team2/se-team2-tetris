package tetrisgame.component.tetromino;

import java.util.Random;
import java.awt.event.KeyEvent;
import data.setting.SettingData;
import gamemanager.GameManager;
import tetrisgame.TetrisGame;
import tetrisgame.component.IGameComponent;
import tetrisgame.component.tile.Tile;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.enumerations.eDirection;
import tetrisgame.enumerations.eMsg;
import tetrisgame.util.Position;

public class Tetromino extends IGameComponent {
	protected Tile mPubBoard[][];

	protected Position mPosition;
	public Tile mShape[][];
	protected int mShapeNColorIndex;

	protected boolean mbPlayer2;

	protected int mMoveRightKey;
	protected int mMoveLeftKey;
	protected int mMoveDownKey;
	protected int mRotateKey;
	protected int mHardDownKey;
	protected int mPauseKey;

	protected static float mAutoDownTime;
	protected float mAutoDownTimeTick;
	private static final float START_AUTO_DOWN_TIME = 1.0f;
	protected static float GAME_SPEED_ACCEL_UNIT = 0.1f;

	protected static Random mRandom;
	private static int mProbWeightArr[];
	private static int mSumProbWeight;
	private static final int DEFAULT_WEIGHT = 100;

	public static final int SHAPE_COL = 4;
	public static final int SHAPE_ROW = 4;
	public static final Position START_POS = new Position(-1, 3);
	public static final int VAR_TETROMINOS = 7;
	private static final String TILE_TEX_NAME_ARR[];

	protected static final boolean O = true;
	protected static final boolean F = false;
	protected static final boolean TET_SHAPES[][][] = {
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
		TILE_TEX_NAME_ARR = new String[VAR_TETROMINOS];
		TILE_TEX_NAME_ARR[0] = "tile_yellow";
		TILE_TEX_NAME_ARR[1] = "tile_skyblue";
		TILE_TEX_NAME_ARR[2] = "tile_lightgreen";
		TILE_TEX_NAME_ARR[3] = "tile_red";
		TILE_TEX_NAME_ARR[4] = "tile_orange";
		TILE_TEX_NAME_ARR[5] = "tile_blue";
		TILE_TEX_NAME_ARR[6] = "tile_purple";
	}

	public Tetromino(TetrisGame game, Tile gb[][], boolean bPlayer2) {
		super(game);
		mPubBoard = gb;
		mbPlayer2 = bPlayer2;
		mPosition = new Position(START_POS.mCol, START_POS.mRow);
		mShape = new Tile[SHAPE_COL][SHAPE_ROW];
		mAutoDownTime = START_AUTO_DOWN_TIME;
		this.setRandomShapeAndColor();
		refreshSetting();
	}

	@Override
	public void update(float deltaTime, int userInput) {
		refreshSetting();
		boolean bCollWithFloor = false;
		if (userInput == mRotateKey) {
			rotateOrIgnore();
		} else if (userInput == mMoveDownKey) {
			bCollWithFloor = moveOrIgnore(eDirection.DOWN);
			mAutoDownTimeTick = 0.f;
		} else if (userInput == mMoveRightKey) {
			bCollWithFloor = moveOrIgnore(eDirection.RIGHT);
			mAutoDownTimeTick = 0.f;
		} else if (userInput == mMoveLeftKey) {
			bCollWithFloor = moveOrIgnore(eDirection.LEFT);
			mAutoDownTimeTick = 0.f;
		} else if (userInput == mHardDownKey) {
			fallToFloor();
			mAutoDownTimeTick = 100.f;
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
				break;
			case ERASE_10xN_LINES:
				mAutoDownTime *= (1.f - GAME_SPEED_ACCEL_UNIT);
				break;
			case SLOWING_ITEM_ERASED:
				int slowingUnit = 3;
				mAutoDownTime /= Math.pow((1.f - GAME_SPEED_ACCEL_UNIT), (double) slowingUnit);
			default:
				break;
		}
	}

	public static void setDifficulty(eDifficulty eDiff) {
		// Set shape generation probabilty
		mProbWeightArr = new int[VAR_TETROMINOS];
		for (int i = 0; i < VAR_TETROMINOS; i++) {
			mProbWeightArr[i] = DEFAULT_WEIGHT;
		}
		mSumProbWeight = DEFAULT_WEIGHT * VAR_TETROMINOS;
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
					mShape[col][row] = new Tile(mPubGame, mPubBoard, TILE_TEX_NAME_ARR[mShapeNColorIndex]);
				} else {
					mShape[col][row] = null;
				}
			}
		}
	}

	protected void refreshSetting() {
		SettingData setting = SettingData.getInstance();
		// TODO: Reflect setting
		if (GameManager.isMulti()) {
			if (mbPlayer2 == false) {
				mMoveRightKey = KeyEvent.VK_D;
				mMoveLeftKey = KeyEvent.VK_A;
				mMoveDownKey = KeyEvent.VK_S;
				mRotateKey = KeyEvent.VK_SPACE;
				mHardDownKey = KeyEvent.VK_W;
			} else {
				mMoveRightKey = KeyEvent.VK_RIGHT;
				mMoveLeftKey = KeyEvent.VK_LEFT;
				mMoveDownKey = KeyEvent.VK_DOWN;
				mRotateKey = KeyEvent.VK_M;
				mHardDownKey = KeyEvent.VK_UP;
			}
		} else {
			mMoveRightKey = setting.getGameMoveRight();
			mMoveLeftKey = setting.getGameMoveLeft();
			mMoveDownKey = setting.getGameMoveDown();
			mRotateKey = setting.getRotateKey();
			mHardDownKey = setting.getGameMoveToFloor();
		}
	}

	protected void rotateOrIgnore() {
		this.rotate();
		boolean collResult = checkCollision();
		if (collResult) {
			this.rotateBack();
		}
	}

	protected boolean moveOrIgnore(eDirection dir) {
		boolean bCollWithFloor = false;

		this.move(dir);
		boolean collResult = checkCollision();
		if (collResult) {
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
							mPubBoard[col][row] = mShape[c][r];
						}
					}
				}
				bCollWithFloor = true;
			}
		}
		return bCollWithFloor;
	}

	protected void fallToFloor() {
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

	private boolean checkCollision() {
		boolean re = false;
		Position pos = mPosition;
		for (int c = 0; c < Tetromino.SHAPE_COL; c++) {
			for (int r = 0; r < Tetromino.SHAPE_ROW; r++) {
				if (mShape[c][r] != null) {
					int col = pos.mCol + c;
					int row = pos.mRow + r;
					if (TetrisGame.BOARD_COL <= col || col < 0 || row < 0 || TetrisGame.BOARD_ROW <= row ||
							mPubBoard[col][row] != null) {
						re = true;
						return re;
					}

				}
			}
		}
		return re;
	}

	// Testing method
	public int getRandomIndex() {
		return mShapeNColorIndex;
	}
}