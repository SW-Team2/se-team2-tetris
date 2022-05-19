package graphics.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import data.setting.SettingData;
import gamemanager.GameManager;
import graphics.eScreenInfo;
import tetrisgame.TetrisGame;
import tetrisgame.component.tetromino.Tetromino;
import tetrisgame.component.tile.Tile;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.util.ImageCVDConverter;
import tetrisgame.util.ImageLoader;
import tetrisgame.util.Position;

public class MultiPlayingScreen extends IScreen {
    protected TetrisGame mGame1;
    protected Tile mBoard1[][];
    protected boolean mbAttackBoard1[][];
    protected TetrisGame mGame2;
    protected Tile mBoard2[][];
    protected boolean mbAttackBoard2[][];

    private int mCVDMode;

    protected BufferedImage mPanelBackGroundImage;
    protected BufferedImage mGameBoardBackGroundImage;
    protected BufferedImage mNextTetBoardBackGroundImage;
    protected Font mScoreBoardFont;
    protected Color mScoreFontColor;

    protected int mScreenWidth;
    protected int mScreenHeight;
    private int mGameBoardWidth;
    private int mGameBoardHeight;
    private int mNextTetBoardWidth;
    private int mNextTetBoardHeight;
    private int mPausePanelWidth;
    private int mPuasePanelHeight;
    private int mAttackBoardWidth;
    private int mAttackBoardHeight;

    private int mGameBoardPosX;
    private int mGameBoardPosY;
    private int mNextTetBoardPosX;
    private int mNextTetBoardPosY;
    private int mScoreBoardPosX;
    private int mScoreBoardPosY;
    private int mPausePanelPosX;
    private int mPausePanelPosY;
    private int mAttackBoardPosX;
    private int mAttackBoardPosY;

    private int mExitBtnWidth;
    private int mExitBtnHeight;
    private int mExitBtnPosX;
    private int mExitBtnPosY;
    private int mContinueBtnWidth;
    private int mContinueBtnHeight;
    private int mContinueBtnPosX;
    private int mContinueBtnPosY;

    private SettingData mSettingInfo;

    public MultiPlayingScreen() {
        this.init();
        this.refreshSetting();
    }

    protected void init() {
        mPanelBackGroundImage = ImageLoader.getInstance().getTexture("background_panel");
        mGameBoardBackGroundImage = ImageLoader.getInstance().getTexture("background_board");
        mNextTetBoardBackGroundImage = ImageLoader.getInstance().getTexture("background_nextboard");

        mScoreBoardFont = new Font("Consolas", Font.BOLD, 30);
        mScoreFontColor = Color.WHITE;
    }

    public void refreshSetting() {
        mSettingInfo = SettingData.getInstance();
        mScreenHeight = mSettingInfo.getHeight();
        mScreenWidth = mSettingInfo.getWidth();
        mGameBoardHeight = (int) (mScreenHeight * 0.9);
        mGameBoardWidth = mGameBoardHeight / 2;
        mGameBoardPosY = (int) (mScreenHeight * 0.02);
        mGameBoardPosX = mGameBoardPosY;
        mNextTetBoardHeight = mGameBoardHeight / 5;
        mNextTetBoardWidth = mNextTetBoardHeight;
        mNextTetBoardPosY = mGameBoardPosY;
        mNextTetBoardPosX = mGameBoardPosX + mGameBoardWidth + mGameBoardPosX;
        mScoreBoardPosY = mNextTetBoardPosY + mNextTetBoardHeight + mNextTetBoardPosY * 2;
        mScoreBoardPosX = mNextTetBoardPosX;
        mAttackBoardWidth = mNextTetBoardWidth / 10 * 10;
        mAttackBoardHeight = mAttackBoardWidth * 2;
        mAttackBoardPosX = mNextTetBoardPosX;
        mAttackBoardPosY = mScoreBoardPosY;

        mPausePanelWidth = mScreenWidth / 2;
        mPausePanelPosX = mScreenWidth / 2 - mPausePanelWidth / 2;
        mContinueBtnWidth = (int) (mPausePanelWidth * 0.8);
        mContinueBtnHeight = mContinueBtnWidth / (470 / 103);
        mContinueBtnPosX = mScreenWidth / 2 - mContinueBtnWidth / 2;
        mContinueBtnPosY = mScreenHeight / 2 - mContinueBtnHeight;
        mExitBtnWidth = (int) (mPausePanelWidth * 0.8);
        mExitBtnHeight = mExitBtnWidth / (470 / 103);
        mExitBtnPosX = mScreenWidth / 2 - mExitBtnWidth / 2;
        mExitBtnPosY = mScreenHeight / 2;
        mPuasePanelHeight = (int) ((mContinueBtnHeight + mExitBtnHeight) * 1.4);
        mPausePanelPosY = mScreenHeight / 2 - mPuasePanelHeight / 2;

        mCVDMode = mSettingInfo.getBlindnessMode();
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
    }

    @Override
    public void paint(Graphics g) {
        refreshSetting();
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);

        int tileSize = mGameBoardHeight / 20;
        BufferedImage frameImage = ImageLoader.getInstance().getTexture("tile_frame");
        BufferedImage image = null;
        assert (frameImage != null);

        // Draw Screen Configs
        {
            g2d.drawImage(mPanelBackGroundImage,
                    0, 0,
                    mScreenWidth * 2, mScreenHeight,
                    null);
            g2d.drawImage(mGameBoardBackGroundImage,
                    mGameBoardPosX, mGameBoardPosY,
                    mGameBoardWidth, mGameBoardHeight,
                    null);
            g2d.drawImage(mGameBoardBackGroundImage,
                    mGameBoardPosX + mScreenWidth, mGameBoardPosY,
                    mGameBoardWidth, mGameBoardHeight,
                    null);
            g2d.drawImage(mNextTetBoardBackGroundImage,
                    mNextTetBoardPosX, mNextTetBoardPosY,
                    mNextTetBoardWidth, mNextTetBoardHeight,
                    null);

            g2d.drawImage(mNextTetBoardBackGroundImage,
                    mNextTetBoardPosX
                            + mScreenWidth,
                    mNextTetBoardPosY,
                    mNextTetBoardWidth, mNextTetBoardHeight,
                    null);
            g2d.drawImage(
                    mGameBoardBackGroundImage,
                    mAttackBoardPosX,
                    mAttackBoardPosY,
                    mAttackBoardWidth, mAttackBoardHeight,
                    null);
            g2d.drawImage(
                    mGameBoardBackGroundImage,
                    mAttackBoardPosX + mScreenWidth,
                    mAttackBoardPosY,
                    mAttackBoardWidth, mAttackBoardHeight,
                    null);
        }
        // Draw board frames
        {
            for (int col = 0; col < TetrisGame.BOARD_COL; col++) {
                for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                    image = frameImage;
                    int posX = mGameBoardPosX + row * tileSize;
                    int posY = mGameBoardPosY + col * tileSize;
                    g2d.drawImage(image, posX, posY, tileSize, tileSize, null);
                }
            }

            for (int col = 0; col < TetrisGame.BOARD_COL; col++) {
                for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                    image = frameImage;
                    int posX = mGameBoardPosX + row * tileSize + mScreenWidth;
                    int posY = mGameBoardPosY + col * tileSize;
                    g2d.drawImage(image, posX, posY, tileSize, tileSize, null);
                }
            }
        }

        if (GameManager.getState() == false) {
            return;
        }

        // Draw game board
        {
            // Draw static blocks
            for (int col = 0; col < TetrisGame.BOARD_COL; col++) {
                for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                    if (mBoard1[col][row] != null) {
                        image = mBoard1[col][row].getTexture();
                        //
                        image = ImageCVDConverter.convert(image, mCVDMode);
                        //
                        int posX = mGameBoardPosX + row * tileSize;
                        int posY = mGameBoardPosY + col * tileSize;
                        g2d.drawImage(image, posX, posY, tileSize, tileSize, null);
                    }
                }
            }

            for (int col = 0; col < TetrisGame.BOARD_COL; col++) {
                for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                    if (mBoard2[col][row] != null) {
                        image = mBoard2[col][row].getTexture();
                        //
                        image = ImageCVDConverter.convert(image, mCVDMode);
                        //
                        int posX = mGameBoardPosX + row * tileSize + mScreenWidth;
                        int posY = mGameBoardPosY + col * tileSize;
                        g2d.drawImage(image, posX, posY, tileSize, tileSize, null);
                    }
                }
            }
            // Map the focused tetromino
            Tetromino tet = mGame1.getCurrTetromino();
            for (int col = 0; col < Tetromino.SHAPE_COL; col++) {
                for (int row = 0; row < Tetromino.SHAPE_ROW; row++) {
                    Position pos = tet.getPosition();
                    if (tet.mShape[col][row] != null) {
                        image = tet.mShape[col][row].getTexture();
                        //
                        image = ImageCVDConverter.convert(image, mCVDMode);
                        //
                        int posX = mGameBoardPosX + (row + pos.mRow) * tileSize;
                        int posY = mGameBoardPosY + (col + pos.mCol) * tileSize;
                        g2d.drawImage(image, posX, posY, tileSize, tileSize, null);
                    }
                }
            }

            tet = mGame2.getCurrTetromino();
            for (int col = 0; col < Tetromino.SHAPE_COL; col++) {
                for (int row = 0; row < Tetromino.SHAPE_ROW; row++) {
                    Position pos = tet.getPosition();
                    if (tet.mShape[col][row] != null) {
                        image = tet.mShape[col][row].getTexture();
                        //
                        image = ImageCVDConverter.convert(image, mCVDMode);
                        //
                        int posX = mGameBoardPosX + (row + pos.mRow) * tileSize + mScreenWidth;
                        int posY = mGameBoardPosY + (col + pos.mCol) * tileSize;
                        g2d.drawImage(image, posX, posY, tileSize, tileSize, null);
                    }
                }
            }
        }
        // Draw next tetromino board
        {
            tileSize *= 0.85;
            Tetromino tet = mGame1.getNextTetromion();
            for (int col = 0; col < Tetromino.SHAPE_COL; col++) {
                for (int row = 0; row < Tetromino.SHAPE_ROW; row++) {
                    if (tet.mShape[col][row] != null) {
                        int posX = mNextTetBoardPosX + row * tileSize + 5;
                        int posY = mNextTetBoardPosY + col * tileSize + 5;
                        image = tet.mShape[col][row].getTexture();
                        //
                        image = ImageCVDConverter.convert(image, mCVDMode);
                        //
                        g2d.drawImage(image, posX, posY, tileSize, tileSize, null);
                    }
                }
            }

            tet = mGame2.getNextTetromion();
            for (int col = 0; col < Tetromino.SHAPE_COL; col++) {
                for (int row = 0; row < Tetromino.SHAPE_ROW; row++) {
                    if (tet.mShape[col][row] != null) {
                        int posX = mNextTetBoardPosX + row * tileSize + 5 + mScreenWidth;
                        int posY = mNextTetBoardPosY + col * tileSize + 5;
                        image = tet.mShape[col][row].getTexture();
                        //
                        image = ImageCVDConverter.convert(image, mCVDMode);
                        //
                        g2d.drawImage(image, posX, posY, tileSize, tileSize, null);
                    }
                }
            }
        }
        // Draw score board
        {
            StringBuffer scoreStrBuf = new StringBuffer();
            scoreStrBuf.append("SCORE: ");
            scoreStrBuf.append(String.valueOf(mGame1.getCurrScore()));
            g2d.setFont(mScoreBoardFont);
            g2d.setColor(mScoreFontColor);
            g2d.drawString(scoreStrBuf.toString(), mScoreBoardPosX, mScoreBoardPosY);

            scoreStrBuf = new StringBuffer();
            scoreStrBuf.append("SCORE: ");
            scoreStrBuf.append(String.valueOf(mGame2.getCurrScore()));
            g2d.setFont(mScoreBoardFont);
            g2d.setColor(mScoreFontColor);
            g2d.drawString(scoreStrBuf.toString(), mScoreBoardPosX + mScreenWidth, mScoreBoardPosY);
        }
        // Draw attack board
        {
            int attackTileSize = mAttackBoardWidth / 10;
            image = ImageLoader.getInstance().getTexture("tile_white");

            for (int col = 9; col >= 0; col--) {
                for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                    if (mbAttackBoard1[9 - col][row] == true) {
                        int posX = mAttackBoardPosX + row * attackTileSize;
                        int posY = mAttackBoardPosY + (col + 10) * attackTileSize;
                        //
                        image = ImageCVDConverter.convert(image, mCVDMode);
                        //
                        g2d.drawImage(image, posX, posY, attackTileSize,
                                attackTileSize, null);
                    }
                }
            }

            for (int col = 9; col >= 0; col--) {
                for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                    if (mbAttackBoard2[9 - col][row] == true) {
                        int posX = mAttackBoardPosX + row * attackTileSize + mScreenWidth;
                        int posY = mAttackBoardPosY + (col + 10) * attackTileSize;
                        //
                        image = ImageCVDConverter.convert(image, mCVDMode);
                        //
                        g2d.drawImage(image, posX, posY, attackTileSize,
                                attackTileSize, null);
                    }
                }
            }
        }

        // Draw pause screen
        {
            if (mGame1.getPauseFlag() == true) {
                image = ImageLoader.getInstance().getTexture("pause_panel");
                g2d.drawImage(image, mPausePanelPosX, mPausePanelPosY, mPausePanelWidth, mPuasePanelHeight, null);

                int focusBtnIndex = mGame1.getPauseBtnIndex();
                if (focusBtnIndex == 0) {
                    image = ImageLoader.getInstance().getTexture("btn_continue_focus");
                } else {
                    image = ImageLoader.getInstance().getTexture("btn_continue");
                }
                g2d.drawImage(image, mContinueBtnPosX, mContinueBtnPosY, mContinueBtnWidth, mContinueBtnHeight, null);
                if (focusBtnIndex == 1) {
                    image = ImageLoader.getInstance().getTexture("btn_exit_focus");
                } else {
                    image = ImageLoader.getInstance().getTexture("btn_exit");
                }
                g2d.drawImage(image, mExitBtnPosX, mExitBtnPosY, mExitBtnWidth, mExitBtnHeight, null);
            }
        }
    }

    protected void startGame() {
        GameManager.setDifficulty(eDifficulty.NORMAL);
        GameManager.setMulti(true);
        mGame1 = new TetrisGame(this, false, GameManager.getDifficulty(), false);
        mGame2 = new TetrisGame(this, false, GameManager.getDifficulty(), true);
        mBoard1 = mGame1.getGameBoard();
        mBoard2 = mGame2.getGameBoard();
        mbAttackBoard1 = mGame1.getAttackBoard();
        mbAttackBoard2 = mGame2.getAttackBoard();
        GameManager.setGame(mGame1, mGame2);
        GameManager.setStart();
    }

    public eScreenInfo getUserInput(KeyEvent e) {
        eScreenInfo sr = eScreenInfo.NONE;
        if (GameManager.getState()) {
            mGame1.getUserInput(e);
            mGame2.getUserInput(e);
        } else {
            this.startGame();
        }

        return sr;
    }
}
