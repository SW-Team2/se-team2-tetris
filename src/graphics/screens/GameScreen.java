package graphics.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import data.setting.SettingData;
import gamestarter.GameStarter;
import graphics.eScreenInfo;
import tetrisgame.TetrisGame;
import tetrisgame.component.tetromino.Tetromino;
import tetrisgame.component.tile.Tile;
import tetrisgame.util.ImageLoader;
import tetrisgame.util.Position;

public class GameScreen extends Screen {
    protected TetrisGame mTetrisGame;
    protected Tile mGameBoard[][];

    protected BufferedImage mPanelBackGroundImage;
    protected BufferedImage mGameBoardBackGroundImage;
    protected BufferedImage mNextTetBoardBackGroundImage;
    protected Font mScoreBoardFont;

    private int mScreenWidth;
    private int mScreenHeight;
    private int mGameBoardWidth;
    private int mGameBoardHeight;
    private int mNextTetBoardWidth;
    private int mNextTetBoardHeight;

    private int mGameBoardPosX;
    private int mGameBoardPosY;
    private int mNextTetBoardPosX;
    private int mNextTetBoardPosY;
    private int mScoreBoardPosX;
    private int mScoreBoardPosY;

    private SettingData mSettingInfo;

    public GameScreen() {
        this.init();
        this.refreshSetting();
    }

    protected void init() {
        mPanelBackGroundImage = ImageLoader.getInstance().getTexture("background_panel");
        mGameBoardBackGroundImage = ImageLoader.getInstance().getTexture("background_board");
        mNextTetBoardBackGroundImage = ImageLoader.getInstance().getTexture("background_nextboard");

        mScoreBoardFont = new Font("Consolas", Font.BOLD, 30);
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
                    mScreenWidth, mScreenHeight,
                    null);
            g2d.drawImage(mGameBoardBackGroundImage,
                    mGameBoardPosX, mGameBoardPosY,
                    mGameBoardWidth, mGameBoardHeight,
                    null);
            g2d.drawImage(mNextTetBoardBackGroundImage,
                    mNextTetBoardPosX, mNextTetBoardPosY,
                    mNextTetBoardWidth, mNextTetBoardHeight,
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
        }

        if (GameStarter.getState() == false) {
            return;
        }

        // Draw game board
        {
            // Draw static blocks
            for (int col = 0; col < TetrisGame.BOARD_COL; col++) {
                for (int row = 0; row < TetrisGame.BOARD_ROW; row++) {
                    if (mGameBoard[col][row] != null) {
                        image = mGameBoard[col][row].getTexture();
                        int posX = mGameBoardPosX + row * tileSize;
                        int posY = mGameBoardPosY + col * tileSize;
                        g2d.drawImage(image, posX, posY, tileSize, tileSize, null);
                    }
                }
            }
            // Map the focused tetromino
            Tetromino tet = mTetrisGame.getCurrTetromino();
            for (int col = 0; col < Tetromino.SHAPE_COL; col++) {
                for (int row = 0; row < Tetromino.SHAPE_ROW; row++) {
                    Position pos = tet.getPosition();
                    if (tet.mShape[col][row] != null) {
                        image = tet.mShape[col][row].getTexture();
                        int posX = mGameBoardPosX + (row + pos.mRow) * tileSize;
                        int posY = mGameBoardPosY + (col + pos.mCol) * tileSize;
                        g2d.drawImage(image, posX, posY, tileSize, tileSize, null);
                    }
                }
            }
        }
        // Draw next tetromino board
        {
            tileSize *= 0.85;
            Tetromino tet = mTetrisGame.getNextTetromion();
            for (int col = 0; col < Tetromino.SHAPE_COL; col++) {
                for (int row = 0; row < Tetromino.SHAPE_ROW; row++) {
                    if (tet.mShape[col][row] != null) {
                        int posX = mNextTetBoardPosX + row * tileSize + 5;
                        int posY = mNextTetBoardPosY + col * tileSize + 5;
                        g2d.drawImage(tet.mShape[col][row].getTexture(), posX, posY, tileSize, tileSize, null);
                    }
                }
            }
        }
        // Draw score board
        {
            StringBuffer scoreStrBuf = new StringBuffer();
            scoreStrBuf.append("SCORE: ");
            scoreStrBuf.append(String.valueOf(mTetrisGame.getCurrScore()));
            g2d.setFont(mScoreBoardFont);
            g2d.setXORMode(Color.white);
            g2d.drawString(scoreStrBuf.toString(), mScoreBoardPosX, mScoreBoardPosY);
        }
    }

    protected void startGame() {
        mTetrisGame = new TetrisGame(this, false, GameStarter.getDifficulty());
        mGameBoard = mTetrisGame.getGameBoard();
        GameStarter.setGame(mTetrisGame);
        GameStarter.setStart();
    }

    @Override
    public eScreenInfo getUserInput(KeyEvent e) {
        eScreenInfo sr = eScreenInfo.NONE;
        if (GameStarter.getState()) {
            mTetrisGame.getUserInput(e);
        } else {
            startGame();
        }
        return sr;
    }

    public eScreenInfo getUserInputKeyRealease(KeyEvent e) {
        eScreenInfo sr = eScreenInfo.NONE;
        if (GameStarter.getState()) {
            mTetrisGame.getUserInputKeyRealease(e);
        }
        return sr;
    }
}