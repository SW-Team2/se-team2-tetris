package tetrisgame.itemmode.itemmodeparts;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;
import tetrisgame.parts.component.Tile;

public class ItemTile extends Tile {
    public ItemTile(TetrisGame g, Tile[][] gb, String name) {
        super(g, gb, name);
    }

    @Override
    public void update(float deltaTime, int userInput) {
    }

    @Override
    public void react(eMsg msg) {
    }
}