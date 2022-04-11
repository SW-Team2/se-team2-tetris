package tetrisgame.parts.component;

import tetrisgame.TetrisGame;
import tetrisgame.enumerations.eMsg;

public class LineRemover extends IGameComponent {
    private Tile mPubBoard[][];

    private int mRemoveColArr[];
    private int mNumRemovableLines;

    public LineRemover(TetrisGame g, Tile gb[][]) {
        super(g);
        mPubBoard = gb;
        mRemoveColArr = new int[4];
    }

    @Override
    public void update(float deltaTime, int userInput) {
        if (mNumRemovableLines > 0) {
            for (int j = 0; j < mNumRemovableLines; j++) {
                int col = mRemoveColArr[j];
                switch (col) {
                    case 0:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_0);
                        break;
                    case 1:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_1);
                        break;
                    case 2:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_2);
                        break;
                    case 3:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_3);
                        break;
                    case 4:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_4);
                        break;
                    case 5:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_5);
                        break;
                    case 6:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_6);
                        break;
                    case 7:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_7);
                        break;
                    case 8:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_8);
                        break;
                    case 9:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_9);
                        break;
                    case 10:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_10);
                        break;
                    case 11:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_11);
                        break;
                    case 12:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_12);
                        break;
                    case 13:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_13);
                        break;
                    case 14:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_14);
                        break;
                    case 15:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_15);
                        break;
                    case 16:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_16);
                        break;
                    case 17:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_17);
                        break;
                    case 18:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_18);
                        break;
                    case 19:
                        mPubGame.broadcast(eMsg.LINE_REMOVE_19);
                        break;
                }
            }
        }
    }

    @Override
    public void react(eMsg msg) {
        switch (msg) {
            case COLL_WITH_FLOOR:
                // findRemovableLines();
        }
    }
}
