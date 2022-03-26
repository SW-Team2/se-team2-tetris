package tetrisgame;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import graphics.screens.GameScreen;
import tetrisgame.enumerations.eDirection;
import tetrisgame.enumerations.eGameOver;
import tetrisgame.parts.Block;
import tetrisgame.parts.GameBoard;
import tetrisgame.parts.Position;
import tetrisgame.parts.Tetromino;
import tetrisgame.parts.Timer;

public class TetrisGame {
	private GameBoard mGameBoard;
	private Tetromino mNextTetromino;
	private Timer mTimer;
	private int mSpeed;
	private long mScore;

	private float mAutoDownTime;
	private float mSumDeltaTime;
	private float mPrevTimeForDraw;

	private static final float START_AUTO_DOWN_TIME = 1.0f;

	private static final int SCORE_UNIT = 100;
	private static final int MULTIPLE_BREAK_BONUS_2L = 30;
	private static final int MULTIPLE_BREAK_BONUS_3L = 60;
	private static final int MULTIPLE_BREAK_BONUS_4L = 100;
	private static final float GAME_SPEED_ACCEL_UNIT = 0.1f;

	private static final char BORDER_CHAR = 'X';
	private static final char BLOCK_CHAR = 'O';

	public TetrisGame() {
		mGameBoard = new GameBoard();
		mNextTetromino = new Tetromino();
		mTimer = new Timer();
	}

	public void initialize() {
		mNextTetromino.setRandomShapeAndColor();
		mGameBoard.setTetromino(mNextTetromino);
		mNextTetromino.setRandomShapeAndColor();
		mTimer.initialize();
		mScore = 0;
		mSpeed = 1;
		mAutoDownTime = START_AUTO_DOWN_TIME;
		mSumDeltaTime = 0.0f;
		mPrevTimeForDraw = 0.0f;
	}

	public void run() {
		boolean bGameOverFlag = false;
		while (!bGameOverFlag) {
			bGameOverFlag = this.update();
			this.draw();
		}
	}

	private boolean update() {
		eGameOver gameOverFlag = eGameOver.CONTINUE;

		mTimer.tick();
		float dTime = mTimer.getDeltaTime();
		mSumDeltaTime += dTime;

		boolean bCollWithFloor1 = false;
		boolean bCollWithFloor2 = false;
		// TODO:
		bCollWithFloor1 = false;

		if (mAutoDownTime <= mSumDeltaTime) {
			bCollWithFloor2 = mGameBoard.moveTet(eDirection.DOWN);
			mSumDeltaTime = 0.0f;
		}
		if (bCollWithFloor1 || bCollWithFloor2) {
			int numRemovedLines = mGameBoard.removeLine();
			// Calculate Score
			int addedScore = 0;
			switch (numRemovedLines) {
				case 0:
					break;
				case 1:
					addedScore = SCORE_UNIT;
					break;
				case 2:
					addedScore = SCORE_UNIT + MULTIPLE_BREAK_BONUS_2L;
					break;
				case 3:
					addedScore = SCORE_UNIT + MULTIPLE_BREAK_BONUS_3L;
					break;
				case 4:
					addedScore = SCORE_UNIT + MULTIPLE_BREAK_BONUS_4L;
					break;
				default:
					assert (false) : "Unspecified";
					break;
			}
			addedScore *= (float) (1 + (mSpeed - 1) / 10.0f);
			mScore += addedScore;
			mSpeed = (int) (mScore / (SCORE_UNIT * 10) + 1);

			mGameBoard.setTetromino(mNextTetromino);
			gameOverFlag = mGameBoard.checkGameOver();
			mNextTetromino.setRandomShapeAndColor();
			mSumDeltaTime = 0.0f;
			mAutoDownTime = START_AUTO_DOWN_TIME;
			for (int i = 0; i < mSpeed - 1; i++) {
				mAutoDownTime = mAutoDownTime * (1.0f - GAME_SPEED_ACCEL_UNIT);
			}

			if (gameOverFlag == eGameOver.OVER) {
				return true;
			}
		}
		return false;
	}

	private void draw() {
		float currTime = mTimer.getGameTime();
		float dTime = currTime - mPrevTimeForDraw;
		// TODO: Set frame
		if (dTime < 0.1f) {
			return;
		}
		mPrevTimeForDraw = currTime;

		GameScreen gameScreen = GameScreen.getInstance();
		// Game board pane
		{
			JTextPane gameBoardPane = gameScreen.getGameBoardTextPane();
			StringBuffer gameBoardBuffer = new StringBuffer();
			Tetromino tet = mGameBoard.getTetromino();
			Position pos = tet.getPosition();
			Block tmpBlock;
			// Make string buffer
			for (int t = 0; t < GameBoard.BOARD_ROW + 2; t++) {
				gameBoardBuffer.append(BORDER_CHAR);
			}
			gameBoardBuffer.append('\n');
			for (int c = 0; c < GameBoard.BOARD_COL; c++) {
				gameBoardBuffer.append(BORDER_CHAR);
				for (int r = 0; r < GameBoard.BOARD_ROW; r++) {
					boolean bTetFilled = false;
					if (pos.mCol <= c && c < pos.mCol + Tetromino.SHAPE_COL &&
							pos.mRow <= r && r < pos.mRow + Tetromino.SHAPE_ROW) {
						bTetFilled = tet.isFilled(c - pos.mCol, r - pos.mRow);
					}
					tmpBlock = mGameBoard.getBlock(c, r);
					if (tmpBlock != null || bTetFilled) {
						gameBoardBuffer.append(BLOCK_CHAR);
					} else {
						gameBoardBuffer.append(' ');
					}
				}
				gameBoardBuffer.append(BORDER_CHAR);
				gameBoardBuffer.append('\n');
			}
			for (int t = 0; t < GameBoard.BOARD_ROW + 2; t++) {
				gameBoardBuffer.append(BORDER_CHAR);
			}
			gameBoardPane.setText(gameBoardBuffer.toString());
			// Make style set
			StyledDocument doc = gameBoardPane.getStyledDocument();
			SimpleAttributeSet styles = new SimpleAttributeSet();
			int offset = GameBoard.BOARD_ROW + 3;
			for (int c = 0; c < GameBoard.BOARD_COL; c++) {
				offset++;
				for (int r = 0; r < GameBoard.BOARD_ROW; r++) {
					boolean bTetFilled = false;
					if (pos.mCol <= c && c < pos.mCol + Tetromino.SHAPE_COL &&
							pos.mRow <= r && r < pos.mRow + Tetromino.SHAPE_ROW) {
						bTetFilled = tet.isFilled(c - pos.mCol, r - pos.mRow);
					}
					tmpBlock = mGameBoard.getBlock(c, r);
					Color currColor;
					if (tmpBlock != null) {
						StyleConstants.setForeground(styles, tmpBlock.getColor());
						doc.setCharacterAttributes(offset, 1, styles, true);
						offset++;
					} else if (bTetFilled) {
						StyleConstants.setForeground(styles, tet.getColor());
						doc.setCharacterAttributes(offset, 1, styles, true);
						offset++;
					} else {
						offset++;
					}
				}
				offset++;
				offset++;
			}
			for (int t = 0; t < GameBoard.BOARD_ROW + 2; t++) {
				gameBoardBuffer.append(BORDER_CHAR);
			}
			gameBoardPane.setStyledDocument(doc);
		}
		// Next tet board
		{
			JTextPane nextTetPane = gameScreen.getNextTetBoardTextPane();
			StringBuffer nextTetBuffer = new StringBuffer();
			Tetromino tet = mNextTetromino;
			// Make string buffer
			for (int c = 0; c < Tetromino.SHAPE_COL + 2; c++) {
				nextTetBuffer.append(' ');
			}
			nextTetBuffer.append('\n');
			for (int c = 0; c < Tetromino.SHAPE_COL; c++) {
				nextTetBuffer.append(' ');
				for (int r = 0; r < Tetromino.SHAPE_ROW; r++) {
					if (tet.isFilled(c, r)) {
						nextTetBuffer.append(BLOCK_CHAR);
					} else {
						nextTetBuffer.append(' ');
					}
				}
				nextTetBuffer.append(' ');
				nextTetBuffer.append('\n');
			}
			// for (int c = 0; c < Tetromino.SHAPE_COL + 2; c++) {
			// nextTetBuffer.append(' ');
			// }
			nextTetPane.setText(nextTetBuffer.toString());
			// Make style set
			// TODO: Optimize by painting same color all
			//
			StyledDocument doc = nextTetPane.getStyledDocument();
			SimpleAttributeSet styles = new SimpleAttributeSet();
			int offset = Tetromino.SHAPE_ROW + 3;
			Color tetColor = tet.getColor();
			for (int c = 0; c < Tetromino.SHAPE_COL; c++) {
				offset++;
				for (int r = 0; r < Tetromino.SHAPE_ROW; r++) {
					if (tet.isFilled(c, r)) {
						StyleConstants.setForeground(styles, tetColor);
						doc.setCharacterAttributes(offset, 1, styles, true);
						offset++;
					} else {
						offset++;
					}
				}
				offset++;
				offset++;
			}
			// Draw score board
			{
				JLabel scoreBoard = gameScreen.getScoreBoardLabel();
				StringBuffer scoreBuf = new StringBuffer();
				scoreBuf.append("SCORE: ");
				scoreBuf.append(Long.toString(mScore));
				scoreBoard.setText(scoreBuf.toString());
			}
		}
	}
}
