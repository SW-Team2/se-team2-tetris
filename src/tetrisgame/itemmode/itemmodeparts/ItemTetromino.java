package tetrisgame.itemmode.itemmodeparts;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;
import tetrisgame.parts.component.Tetromino;
import tetrisgame.parts.component.Tile;

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
