package tetrisgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class TetrisGame extends JFrame {
	private GameBoard mGameBoard;
	private Tetromino mNextTetromino;
	private Timer mTimer;
	private int mSpeed;
	private long mScore;

	private float mAutoDownTime;
	private float mSumDeltaTime;
	private static final float START_AUTO_DOWN_TIME = 1.0f;

	private static final int SCORE_UNIT = 100;
	private static final int MULTIPLE_BREAK_BONUS_2L = 30;
	private static final int MULTIPLE_BREAK_BONUS_3L = 60;
	private static final int MULTIPLE_BREAK_BONUS_4L = 100;
	private static final float GAME_SPEED_ACCEL_UNIT = 0.1f;

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

		//
		// Temp GUI
		//
		mPane = new JTextPane();
		mPane.setEditable(false);
		mPane.setBackground(Color.BLACK);
		CompoundBorder border = BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 10),
				BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
		mPane.setBorder(border);
		this.getContentPane().add(mPane, BorderLayout.CENTER);

		addKeyListener(mInput);
		setFocusable(true);
		requestFocus();

		mStyleSet = new SimpleAttributeSet();
		StyleConstants.setFontSize(mStyleSet, 18);
		StyleConstants.setFontFamily(mStyleSet, "Courier");
		StyleConstants.setBold(mStyleSet, true);
		StyleConstants.setForeground(mStyleSet, Color.WHITE);
		StyleConstants.setAlignment(mStyleSet, StyleConstants.ALIGN_CENTER);
		//
		// Temp GUI
		//
		mSpeed = 1;
		mAutoDownTime = START_AUTO_DOWN_TIME;
		mSumDeltaTime = 0.0f;
	}

	public void run() {
		boolean bGameOverFlag = false;
		while (!bGameOverFlag) {
			bGameOverFlag = this.update();
			this.draw();
		}
	}

	//
	// Private Functions
	//
	private boolean update() {
		eGameOver gameOverFlag = eGameOver.CONTINUE;

		mTimer.tick();
		float dTime = mTimer.getDeltaTime();
		mSumDeltaTime += dTime;

		boolean bCollWithFloor1 = false;
		boolean bCollWithFloor2 = false;
		bCollWithFloor1 = mInput.GetCollWithFloor();
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
			//
			mGameBoard.setTetromino(mNextTetromino);
			gameOverFlag = mGameBoard.checkGameOver();
			mNextTetromino.setRandomShapeAndColor();
			mInput.Update();
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
		float dTime = currTime - prevTime;
		if (dTime < 0.1f) {
			return;
		}
		prevTime = currTime;

		StringBuffer strBuf = new StringBuffer();

		Tetromino tet = mGameBoard.getTetromino();
		Position pos = tet.getPosition();

		Block tmpBlock;
		for (int i = 0; i < 20; i++) {
			strBuf.append('X');
			for (int j = 0; j < 10; j++) {
				boolean bTetFilled = false;
				if (pos.mCol <= i && i < pos.mCol + Tetromino.SHAPE_COL &&
						pos.mRow <= j && j < pos.mRow + Tetromino.SHAPE_ROW) {
					bTetFilled = tet.isFilled(i - pos.mCol, j - pos.mRow);
				}
				tmpBlock = mGameBoard.getBlock(i, j);
				if (tmpBlock != null || bTetFilled) {
					strBuf.append('O');
				} else {
					strBuf.append(' ');
					strBuf.append(' ');
					strBuf.append(' ');
				}
				strBuf.append(' ');
			}
			strBuf.append('X');
			strBuf.append('\n');
		}
		for (int t = 0; t < GameBoard.BOARD_ROW + 1; t++) {
			strBuf.append('X');
			strBuf.append(' ');
		}
		strBuf.append('\n');
		strBuf.append('\n');
		strBuf.append("SCORE : ");
		strBuf.append(mScore);

		mPane.setText(strBuf.toString());
		StyledDocument doc = mPane.getStyledDocument();
		doc.setParagraphAttributes(0, doc.getLength(), mStyleSet, false);
		mPane.setStyledDocument(doc);
	}

	//
	// TEMP
	//
	private float prevTime = 0.0f;

	private JTextPane mPane;
	private SimpleAttributeSet mStyleSet;
	private myKeyListener mInput = new myKeyListener();

	class myKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					mGameBoard.moveTet(eDirection.LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					mGameBoard.moveTet(eDirection.RIGHT);
					break;
				case KeyEvent.VK_DOWN:
					mbCollWithFloor = mGameBoard.moveTet(eDirection.DOWN);
					break;
				case KeyEvent.VK_SPACE:
					mGameBoard.rotateTet();
					break;
				case KeyEvent.VK_UP:
					while (!mbCollWithFloor) {
						mbCollWithFloor = mGameBoard.moveTet(eDirection.DOWN);
					}
					break;
				default:
					break;
			}
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyTyped(KeyEvent e) {
		}

		public boolean GetCollWithFloor() {
			return mbCollWithFloor;
		}

		public void Update() {
			mbCollWithFloor = false;
		}

		private boolean mbCollWithFloor = false;
	}
}
