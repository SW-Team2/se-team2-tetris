package tetrisgame.itemmode.itemmodeparts;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;
import tetrisgame.parts.component.Tile;

public class LineEraserItemTile extends Tile {
    public LineEraserItemTile(TetrisGame g, Tile[][] gb, String name) {
        super(g, gb, name);
    }

    public LineEraserItemTile(LineEraserItemTile rhs) {
        this(rhs.mPubGame, rhs.mPubBoard, rhs.mName);
    }

    @Override
    public void update(float deltaTime, int userInput) {
    }

    @Override
    public void react(eMsg msg) {
    }
}
