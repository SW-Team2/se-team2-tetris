package tetrisgame.component.tetromino;

import data.setting.SettingData;
import org.junit.Test;
import tetrisgame.TetrisGame;
import tetrisgame.component.tile.Tile;
import tetrisgame.enumerations.eDifficulty;
import tetrisgame.enumerations.eDirection;
import tetrisgame.util.Position;

import static org.junit.jupiter.api.Assertions.*;

public class TetrominoTest {

    @Test
    public void BlockGenProbabilityTest() {
        final int NUM_TESTING = 10000;
        final int NUM_BLOCK_KIND = Tetromino.VAR_TETROMINOS;
        {
            Tetromino.setDifficulty(eDifficulty.NORMAL);
            int countArr[] = new int[NUM_BLOCK_KIND];
            for (int i = 0; i < NUM_TESTING; i++) {
                int index = new Tetromino(null, null).getRandomIndex();
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
            Tetromino.setDifficulty(eDifficulty.EASY);
            int countArr[] = new int[NUM_BLOCK_KIND];
            for (int i = 0; i < NUM_TESTING; i++) {
                int index = new Tetromino(null, null).getRandomIndex();
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
            Tetromino.setDifficulty(eDifficulty.HARD);
            int countArr[] = new int[NUM_BLOCK_KIND];
            for (int i = 0; i < NUM_TESTING; i++) {
                int index = new Tetromino(null, null).getRandomIndex();
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
        int col = Tetromino.SHAPE_COL;
        int row = Tetromino.SHAPE_ROW;

        Tile emptyBoard[][] = new Tile[TetrisGame.BOARD_COL][TetrisGame.BOARD_ROW];
        Tetromino.setDifficulty(eDifficulty.NORMAL);
        Tetromino tet = new Tetromino(null,emptyBoard);
        
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

        for(int c = 0;c<Tetromino.SHAPE_COL;c++) {
            for(int r = 0;r<Tetromino.SHAPE_ROW;r++){
                assertEquals(resultShape[c][r], saveShape[c][r]);
            }
        }
    }

    @Test
    public void moveTest() {
        Tile emptyBoard[][] = new Tile[TetrisGame.BOARD_COL][TetrisGame.BOARD_ROW];
        Tetromino.setDifficulty(eDifficulty.NORMAL);
        Tetromino tet = new Tetromino(null,emptyBoard);
        int savePosCol = tet.getPosition().mCol;
        int savePosRow = tet.getPosition().mRow;

        tet.update(0.f, SettingData.getInstance().getGameMoveRight());
        tet.update(0.f, SettingData.getInstance().getGameMoveLeft());

        assertEquals(savePosCol, tet.getPosition().mCol);
        assertEquals(savePosRow, tet.getPosition().mRow);
    }

    private void getTetShape(boolean dest[][], Tetromino source) {
        int col = Tetromino.SHAPE_COL;
        int row = Tetromino.SHAPE_ROW;

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