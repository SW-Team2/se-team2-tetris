package tetrisgame.util;

public class Position {
    public int mCol;
    public int mRow;

    public Position(int c, int r) {
        mCol = c;
        mRow = r;
    }

    public Position(Position in) {
        mCol = in.mCol;
        mRow = in.mRow;
    }

    public void deepCopy(Position in) {
        mCol = in.mCol;
        mRow = in.mRow;
    }
}
