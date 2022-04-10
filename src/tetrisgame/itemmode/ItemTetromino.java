package tetrisgame.itemmode;

import tetrisgame.enumerations.eItemID;
import tetrisgame.parts.Tetromino;

public class ItemTetromino extends Tetromino {
    private eItemID meID;

    private static final ItemTile ITEM_BLOCK_ARR[];
    public static final int VAR_ITEMS;

    private static final boolean WEIGHT_TET_SHAPE[][] = {
            { F, F, F, F },
            { F, O, O, F },
            { O, O, O, O },
            { F, F, F, F }
    };

    static {
        VAR_ITEMS = eItemID.values().length - 1;
        ITEM_BLOCK_ARR = new ItemTile[VAR_ITEMS];
        int index = 0;
        ITEM_BLOCK_ARR[index++] = new ItemTile("item_tile_lineeraser", eItemID.LINE_ERASER);
        ITEM_BLOCK_ARR[index++] = new ItemTile("item_tile_weight", eItemID.WEIGHT);
        assert (VAR_ITEMS == index);
    }

    public ItemTetromino() {
        super();
    }

    @Override
    protected void setRandomShapeAndColor() {
        int randomNum = super.mRandom.nextInt(VAR_ITEMS);
        switch (randomNum) {
            case 0:
                meID = eItemID.LINE_ERASER;
                super.setRandomShapeAndColor();

                int itemLoc = super.mRandom.nextInt(4) + 1;
                int locIndex = 0;
                while (locIndex != itemLoc) {
                    locIndex++;
                    for (int c = 0; c < super.SHAPE_COL; c++) {
                        for (int r = 0; r < super.SHAPE_ROW; r++) {
                            if (super.mShape[c][r] != null) {
                                if (locIndex == itemLoc) {
                                    mShape[c][r] = ITEM_BLOCK_ARR[randomNum];
                                    return;
                                }
                            }
                        }
                    }
                }
                return;
            case 1:
                meID = eItemID.WEIGHT;
                for (int c = 0; c < super.SHAPE_COL; c++) {
                    for (int r = 0; r < super.SHAPE_ROW; r++) {
                        if (WEIGHT_TET_SHAPE[c][r] == O) {
                            super.mShape[c][r] = ITEM_BLOCK_ARR[randomNum];
                        }
                    }
                }
                return;
            default:
                assert (false);
                return;
        }
    }

    @Override
    public eItemID isItem() {
        return meID;
    }
}
