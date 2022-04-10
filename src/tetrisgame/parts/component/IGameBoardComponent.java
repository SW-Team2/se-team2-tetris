package tetrisgame.parts.component;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;

public abstract class IGameBoardComponent extends IGameComponent {
    protected GameBoard mPubBoard;

    public IGameBoardComponent(TetrisGame g, GameBoard gb) {
        super(g);
        mPubBoard = gb;
    }

    @Override
    public abstract void update(float deltaTime, int userInpt);

    @Override
    public abstract void react(eMsg msg);
}
