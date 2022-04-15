package tetrisgame.component.tile;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;

public class RemovingAllItemTile extends ItemTile {
    public RemovingAllItemTile(TetrisGame g, Tile[][] gb, String name) {
        super(g, gb, name);
    }

    @Override
    public void update(float deltaTime, int userInput) {
    }

    @Override
    public void react(eMsg msg) {
    }

    @Override
    public void eraseAct() {
        mPubGame.broadcast(eMsg.REMOVINGALL_ITEM_ERASED);
    }
}
