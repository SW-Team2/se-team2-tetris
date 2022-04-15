package tetrisgame.component.tile;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;

public class SlowingItemTile extends ItemTile {
    public SlowingItemTile(TetrisGame g, Tile[][] gb, String name) {
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
        mPubGame.broadcast(eMsg.SLOWING_ITEM_ERASED);
    }
}
