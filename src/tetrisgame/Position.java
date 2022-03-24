package tetrisgame;

class Position {
    public int mCol;
    public int mRow;

    public Position(int c, int r) {
        mCol = c;
        mRow = r;
    }

    public void deepCopy(Position in) {
        mCol = in.mCol;
        mRow = in.mRow;
    }
}
