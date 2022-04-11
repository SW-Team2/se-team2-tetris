package tetrisgame.parts.component;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;

public abstract class IAnim extends IGameComponent {
    public IAnim(TetrisGame g) {
        super(g);
    }

    public abstract void update(float deltaTime, int userInput);

    public abstract void react(eMsg msg);
}
