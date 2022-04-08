package graphics.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import data.setting.SettingInfoDesc;
import graphics.eScreenInfo;
import tetrisgame.TetrisGame;
import tetrisgame.parts.GameBoard;
import tetrisgame.parts.Position;
import tetrisgame.parts.Tetromino;

public class GameScreen extends Screen {
    private TetrisGame mTetrisGame;
    private GameBoard mGameBoard;
    private boolean mbPlayingTetrisGame;

    private BufferedImage mGameBoardBackGroundImage;
    private BufferedImage mNextTetBoardBackGroundImage;
    private Font mScoreBoardFont;

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

    private SettingInfoDesc mSettingInfo;

    public GameScreen() {
        mTetrisGame = new TetrisGame(this);
        mTetrisGame.initialize();
        mGameBoard = mTetrisGame.getGameBoard();
        mbPlayingTetrisGame = false;

        // Set Window back ground
        super.setBackground(Color.gray);
        // Set game board back ground image
        try {
            mGameBoardBackGroundImage = ImageIO
                    .read(getClass().getResourceAsStream("../../res/background/background.jpg"));
        } catch (IOException e) {
            assert (false) : "Open File";
        }
        // Set next tetromino board back ground image
        try {
            mNextTetBoardBackGroundImage = ImageIO
                    .read(getClass().getResourceAsStream("../../res/background/background.jpg"));
        } catch (IOException e) {
            assert (false) : "Open File";
        }

        mScoreBoardFont = new Font("Consolas", Font.BOLD, 30);

        // TODO: Reflect setting infos
        mSettingInfo = SettingInfoDesc.getInstance();

        mScreenWidth = mSettingInfo.mScreen.mWidth;
        mScreenHeight = mSettingInfo.mScreen.mHeight;
        mGameBoardWidth = 400;
        mGameBoardHeight = 800;
        mNextTetBoardWidth = 160;
        mNextTetBoardHeight = 160;
        mGameBoardPosX = 50;
        mGameBoardPosY = 50;
        mNextTetBoardPosX = 460;
        mNextTetBoardPosY = 50;
        mScoreBoardPosX = 460;
        mScoreBoardPosY = 260;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
        // Draw Screen Configs
        {
            g2d.drawImage(mGameBoardBackGroundImage,
                    mGameBoardPosX, mGameBoardPosY,
                    mGameBoardWidth, mGameBoardHeight,
                    null);
            g2d.drawImage(mNextTetBoardBackGroundImage,
                    mNextTetBoardPosX, mNextTetBoardPosY,
                    mNextTetBoardWidth, mNextTetBoardHeight,
                    null);
        }
        if (mbPlayingTetrisGame == false) {
            return;
        }

        // TODO: Temp tile size
        int tileSize = 40;
        // Draw game board
        {
            // Map the focused tetromino
            Tetromino tet = mGameBoard.getTetromino();
            for (int col = 0; col < Tetromino.SHAPE_COL; col++) {
                for (int row = 0; row < Tetromino.SHAPE_ROW; row++) {
                    Position pos = tet.getPosition();
                    if (tet.mShape[col][row] != null) {
                        int posX = mGameBoardPosX + (row + pos.mRow) * tileSize;
                        int posY = mGameBoardPosY + (col + pos.mCol) * tileSize;
                        g2d.setXORMode(tet.getColor());
                        g2d.drawImage(tet.mShape[col][row].getTexture(), posX, posY, tileSize, tileSize, null);
                    }
                }
            }
            // Draw static blocks
            for (int col = 0; col < GameBoard.BOARD_COL; col++) {
                for (int row = 0; row < GameBoard.BOARD_ROW; row++) {
                    if (mGameBoard.mBoard[col][row] != null) {
                        int posX = mGameBoardPosX + row * tileSize;
                        int posY = mGameBoardPosY + col * tileSize;
                        g2d.setXORMode(mGameBoard.mBoard[col][row].getColor());
                        g2d.drawImage(mGameBoard.mBoard[col][row].getTexture(), posX, posY, tileSize, tileSize, null);
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
                        g2d.setXORMode(tet.getColor());
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

    private void startGame() {
        mbPlayingTetrisGame = true;
        Thread gameThread = new Thread(mTetrisGame);
        gameThread.start();
    }

    @Override
    public eScreenInfo getUserInput(KeyEvent e) {
        eScreenInfo sr = eScreenInfo.NONE;
        if (mbPlayingTetrisGame) {
            mTetrisGame.getUserInput(e);
        } else {
            startGame();
        }
        return sr;
    }
}