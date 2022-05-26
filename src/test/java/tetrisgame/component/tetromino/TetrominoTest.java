package tetrisgame.component.tetromino;

import data.setting.SettingData;
import graphics.screens.GameScreen;
import org.junit.Test;
import tetrisgame.TetrisGame;
import tetrisgame.component.tile.Tile;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.util.ImageLoader;

import java.awt.event.KeyEvent;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TetrominoTest {

    @Test
    public void itemBlockGet() {
        ImageLoader.Load();
        Tetromino tet = null;
        Random random = new Random();
        TetrisGame game = new TetrisGame(new GameScreen(), false, eDifficulty.NORMAL, false);
        int probArr[] = new int[5];

        final int COUNT = 10000;
        for(int i =0;i<COUNT;i++) {
            int itemIndex = random.nextInt(5);
            switch (itemIndex) {
                case 0:
                    tet = new WeightItemTetromino(game, game.mBoard, false);
                    probArr[0]++;
                    break;
                case 1:
                    tet = new LineEraserItemTetromino(game, game.mBoard, false);
                    probArr[1]++;
                    break;
                case 2:
                    tet = new SlowingItemTetromino(game, game.mBoard, false);
                    probArr[2]++;
                    break;
                case 3:
                    tet = new RemovingAllItemTetromino(game, game.mBoard, false);
                    probArr[3]++;
                    break;
                case 4:
                    tet = new BonusScoreItemTetroino(game, game.mBoard, false);
                    probArr[4]++;
                    break;
            }
            tet.update(0.01f, KeyEvent.VK_DOWN);
            }
        double expectProb = COUNT / 5;
        for(int j = 0;j<5;j++) {
            double prob = probArr[j];
            assertEquals(expectProb *0.9 <= prob && prob <= expectProb * 1.1, true);
        }
    }

    @Test
    public void blockGenProbabilityTest() {
        ImageLoader.Load();
        final int NUM_TESTING = 10000;
        final int NUM_BLOCK_KIND = tetrisgame.component.tetromino.Tetromino.VAR_TETROMINOS;
        TetrisGame game = new TetrisGame(new GameScreen(), false, eDifficulty.NORMAL, false);
        {
            tetrisgame.component.tetromino.Tetromino.setDifficulty(eDifficulty.NORMAL);
            int countArr[] = new int[NUM_BLOCK_KIND];
            for (int i = 0; i < NUM_TESTING; i++) {
                int index = new tetrisgame.component.tetromino.Tetromino(game, game.mBoard, false).getRandomIndex();
                countArr[index]++;
            }
            for (int i = 0; i < 7; i++) {
                int c = countArr[i];
                int prob = (int) ((double) c / NUM_TESTING * 100);
                // System.out.printf("%d ", prob);
                int expectProb = 100 / NUM_BLOCK_KIND;
                assertEquals(expectProb - 5 <= prob && prob <= expectProb + 5, true);
            }
        }
        // System.out.printf("\n\n");
        {
            tetrisgame.component.tetromino.Tetromino.setDifficulty(eDifficulty.EASY);
            int countArr[] = new int[NUM_BLOCK_KIND];
            for (int i = 0; i < NUM_TESTING; i++) {
                int index = new tetrisgame.component.tetromino.Tetromino(game, game.mBoard, false).getRandomIndex();
                countArr[index]++;
            }
            for (int i = 0; i < 7; i++) {
                int c = countArr[i];
                int prob = (int) ((double) c / NUM_TESTING * 100);
                // System.out.printf("%d ", prob);
                int expectProb;
                if (i == 1) { // I block
                    expectProb = 16;
                } else {
                    expectProb = 14;
                }
                assertEquals(expectProb - 5 <= prob && prob <= expectProb + 5, true);
            }
        }
        // System.out.printf("\n\n");
        {
            tetrisgame.component.tetromino.Tetromino.setDifficulty(eDifficulty.HARD);
            int countArr[] = new int[NUM_BLOCK_KIND];
            for (int i = 0; i < NUM_TESTING; i++) {
                int index = new tetrisgame.component.tetromino.Tetromino(game, game.mBoard, false).getRandomIndex();
                countArr[index]++;
            }
            for (int i = 0; i < 7; i++) {
                int c = countArr[i];
                int prob = (int) ((double) c / NUM_TESTING * 100);
                // System.out.printf("%d ", prob);
                int expectProb;
                if (i == 1) { // I block
                    expectProb = 11;
                } else {
                    expectProb = 15;
                }
                assertEquals(expectProb - 5 <= prob && prob <= expectProb + 5, true);
            }
        }
    }

    @Test
    public void rotateTest() {
        ImageLoader.Load();
        TetrisGame game = new TetrisGame(new GameScreen(), false, eDifficulty.NORMAL, false);
        int col = tetrisgame.component.tetromino.Tetromino.SHAPE_COL;
        int row = tetrisgame.component.tetromino.Tetromino.SHAPE_ROW;

        tetrisgame.component.tetromino.Tetromino.setDifficulty(eDifficulty.NORMAL);
        tetrisgame.component.tetromino.Tetromino tet = new tetrisgame.component.tetromino.Tetromino(game,game.mBoard,false);

        boolean saveShape[][] = new boolean[col][row];
        for(int i=0;i<row;i++) {
            saveShape[i] = new boolean[row];
        }
        this.getTetShape(saveShape, tet);

        tet.update(0.f, SettingData.getInstance().getGameMoveDown());
        tet.update(0.f, SettingData.getInstance().getGameMoveDown());
        tet.update(0.f, SettingData.getInstance().getGameMoveDown());

        tet.update(0.f, SettingData.getInstance().getRotateKey());
        tet.update(0.f, SettingData.getInstance().getRotateKey());
        tet.update(0.f, SettingData.getInstance().getRotateKey());
        tet.update(0.f, SettingData.getInstance().getRotateKey());

        boolean resultShape[][] = new boolean[col][row];
        for(int i=0;i<row;i++) {
            resultShape[i] = new boolean[row];
        }
        this.getTetShape(resultShape, tet);

        for(int c = 0; c< tetrisgame.component.tetromino.Tetromino.SHAPE_COL; c++) {
            for(int r = 0; r< tetrisgame.component.tetromino.Tetromino.SHAPE_ROW; r++){
                assertEquals(resultShape[c][r], saveShape[c][r]);
            }
        }
    }

    @Test
    public void moveTest() {
        ImageLoader.Load();
        TetrisGame game = new TetrisGame(new GameScreen(), false, eDifficulty.NORMAL, false);
        Tile emptyBoard[][] = new Tile[TetrisGame.BOARD_COL][TetrisGame.BOARD_ROW];
        tetrisgame.component.tetromino.Tetromino.setDifficulty(eDifficulty.NORMAL);
        tetrisgame.component.tetromino.Tetromino tet = new tetrisgame.component.tetromino.Tetromino(game,game.mBoard,false);
        int savePosCol = tet.getPosition().mCol;
        int savePosRow = tet.getPosition().mRow;

        tet.update(0.f, SettingData.getInstance().getGameMoveRight());
        tet.update(0.f, SettingData.getInstance().getGameMoveLeft());

        assertEquals(savePosCol, tet.getPosition().mCol);
        assertEquals(savePosRow, tet.getPosition().mRow);
    }

    private void getTetShape(boolean dest[][], tetrisgame.component.tetromino.Tetromino source) {
        int col = tetrisgame.component.tetromino.Tetromino.SHAPE_COL;
        int row = tetrisgame.component.tetromino.Tetromino.SHAPE_ROW;

        for(int c=0;c<col;c++) {
            for (int r=0;r<row;r++) {
                if(source.mShape[c][r] != null) {
                    dest[c][r] = true;
                }
                else {
                    dest[c][r] = false;
                }
            }
        }
    }
}