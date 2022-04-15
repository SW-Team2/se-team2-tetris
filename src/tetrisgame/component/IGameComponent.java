package tetrisgame.component;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;

public abstract class IGameComponent {
    protected TetrisGame mPubGame;

    protected IGameComponent(TetrisGame g) {
        mPubGame = g;
    }

    public abstract void update(float deltaTime, int userInput);

    public abstract void react(eMsg msg);
}
