package tetrisgame.component.tetromino;

import tetrisgame.TetrisGame;
import tetrisgame.component.tile.ItemTile;
import tetrisgame.component.tile.Tile;
import tetrisgame.enumerations.eMsg;

public class ItemTetromino extends Tetromino {
    public ItemTetromino(TetrisGame game, Tile gb[][]) {
        super(game, gb);
    }

    @Override
    public void update(float deltaTime, int userInput) {
    }

    @Override
    public void react(eMsg msg) {
    }

    protected void genTetWithOneItemTile(ItemTile itemTile) {
        int eraserLocCount = mRandom.nextInt(4);
        int locIndex = 0;
        for (int col = 0; col < SHAPE_COL; col++) {
            for (int row = 0; row < SHAPE_ROW; row++) {
                if (mShape[col][row] != null) {
                    if (locIndex == eraserLocCount) {
                        mShape[col][row] = itemTile;
                        return;
                    } else {
                        locIndex++;
                    }
                }
            }
        }
    }
}
