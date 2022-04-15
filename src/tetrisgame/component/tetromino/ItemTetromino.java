package tetrisgame.component.tetromino;

import tetrisgame.TetrisGame;
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
}
