package tetrisgame.component.tile;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;

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
